package dashboard;

import common_stage.CommonStageSingleton;
import db_utils.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    public Tab tab1;
    @FXML
    public TableView eq_table;
    @FXML
    public TableColumn clm_id;
    @FXML
    public TableColumn clm_name;
    @FXML
    public TableColumn clm_model;
    @FXML
    public TableColumn clm_company;
    @FXML
    public TableColumn clm_usable;
    @FXML
    public TableColumn clm_un_usable;
    @FXML
    public TextField eq_name;
    @FXML
    public TextField eq_model;
    @FXML
    public TextField eq_company;
    @FXML
    public Button eq_add_btn;
    @FXML
    public Button form_clear_btn;
    @FXML
    public Button load_data_btn;
    @FXML
    public Tab tab2;
    @FXML
    public TableView unit_table;
    @FXML
    public TableColumn u_clm_id;
    @FXML
    public TableColumn u_clm_name;
    @FXML
    public TextField unit_name;
    @FXML
    public Button unit_add_btn;
    @FXML
    public Button unit_clear_btn1;
    @FXML
    public Button unit_load_data_btn;

    private DatabaseConnection db;
    private ObservableList<EquipmentData> eqData;
    private ObservableList<UnitData> unitData;
    private String selectEquipmentQuery = "SELECT * FROM equipments ";
    private String insertEquipmentQuery = "INSERT INTO equipments(name,modelNo,company) VALUES(?,?,?)";
    private String selectUnitQuery = "SELECT * FROM units ";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.db = new DatabaseConnection();
        try {
            loadEquipmentData();
            loadUnitData();
        }catch(Exception e){
            System.out.println(e.toString());
        }

        eq_table.setRowFactory(tv -> {
            TableRow<EquipmentData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    EquipmentData rowData = row.getItem();
                    System.out.println("Double click on: "+rowData.getName());
                    viewEquipmentDetails();
                }
            });
            return row ;
        });
    }

    @FXML
    private void loadEquipmentData() throws SQLException {
        try{
            Connection conn = DatabaseConnection.getConnection();
            this.eqData = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(selectEquipmentQuery);

            while(rs.next()){
                this.eqData.add(new EquipmentData(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));
            }

        } catch (SQLException e) {
            System.err.println("Error: "+ e);
        }

        this.clm_id.setCellValueFactory(new PropertyValueFactory<EquipmentData, String>("ID"));
        this.clm_name.setCellValueFactory(new PropertyValueFactory<EquipmentData, String>("name"));
        this.clm_model.setCellValueFactory(new PropertyValueFactory<EquipmentData, String>("model"));
        this.clm_company.setCellValueFactory(new PropertyValueFactory<EquipmentData, String>("company"));
        this.clm_usable.setCellValueFactory(new PropertyValueFactory<EquipmentData, String>("usable"));
        this.clm_un_usable.setCellValueFactory(new PropertyValueFactory<EquipmentData, String>("un_usable"));

        this.eq_table.setItems(null);
        this.eq_table.setItems(this.eqData);

    }

    @FXML
    private void addEquipment(ActionEvent event){

        try{
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement st = conn.prepareStatement(insertEquipmentQuery);

            st.setString(1,this.eq_name.getText());
            st.setString(2,this.eq_model.getText());
            st.setString(3,this.eq_company.getText());

            st.execute();


        } catch (SQLException e){
            e.printStackTrace();
        }

        try {
            loadEquipmentData();
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }

    @FXML
    private void clearEqFields(ActionEvent event){
        this.eq_name.setText("");
        this.eq_model.setText("");
        this.eq_company.setText("");
    }

    private void viewEquipmentDetails(){
        try{
            CommonStageSingleton stageSingleton = CommonStageSingleton.getInstance();
            Stage userStage = stageSingleton.getMainWindow();
            FXMLLoader loader = new FXMLLoader();
            Pane equipmentScene = (Pane) loader.load(getClass().getResource("/equipment_details/EquipmentDetails.fxml").openStream());


            Scene scene = new Scene(equipmentScene);
            userStage.setScene(scene);
            userStage.setTitle("Equipment Details");
            userStage.setResizable(false);
            userStage.show();
        } catch (IOException e){
            System.out.println(e.toString());
        }
    }

    //Tab 2 Methods
    @FXML
    private void loadUnitData() throws SQLException {
        try{
            Connection conn = DatabaseConnection.getConnection();
            this.unitData = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(selectUnitQuery);

            while(rs.next()){
                this.unitData.add(new UnitData(rs.getString(1),rs.getString(2)));
            }

        } catch (SQLException e) {
            System.err.println("Error: "+ e);
        }

        this.u_clm_id.setCellValueFactory(new PropertyValueFactory<EquipmentData, String>("unit_ID"));
        this.u_clm_name.setCellValueFactory(new PropertyValueFactory<EquipmentData, String>("unit_name"));

        this.unit_table.setItems(null);
        this.unit_table.setItems(this.unitData);

    }

    @FXML
    private void addUnit(ActionEvent event){
        String sqlInsertUnit = "INSERT INTO units(name) VALUES(?)";

        try{
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement st = conn.prepareStatement(sqlInsertUnit);

            st.setString(1,this.unit_name.getText());

            st.execute();


        } catch (SQLException e){
            e.printStackTrace();
        }

        try {
            loadUnitData();
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }

    @FXML
    private void clearUnitFields(ActionEvent event){
        this.unit_name.setText("");
    }
}
