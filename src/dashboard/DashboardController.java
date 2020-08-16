package dashboard;

import common_stage.CommonStageSingleton;
import db_utils.DatabaseConnection;
import equipment_details.EquipmentDetailsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import location_details.LocationDetailsController;
import models.EquipmentDataLong;
import models.EquipmentDataShort;
import models.UnitDataShort;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    //------------------------------------------TAB1--------------------------
    @FXML
    public Tab tab1;
    @FXML
    public TableView eq_table;
    @FXML
    public TableColumn clm_name;
    @FXML
    public TableColumn clm_make;
    @FXML
    public TableColumn clm_model;
    @FXML
    public TableColumn clm_localAgent;
    @FXML
    public TableColumn clm_usability;
    @FXML
    public TableColumn clm_count;
    @FXML
    public TextField eq_serialNo;
    @FXML
    public TextField eq_invoiceNo;
    @FXML
    public TextField dp_invoiceDate;
    @FXML
    public TextField eq_name;
    @FXML
    public TextField eq_make;
    @FXML
    public TextField eq_model;
    @FXML
    public TextField eq_localAgent;
    @FXML
    public TextField dp_DOI;
    @FXML
    public TextField dp_warranty;
    @FXML
    public TextField eq_location;
    @FXML
    public TextField eq_isUsable;
    @FXML
    public TextArea ta_imagePath;

    //------------------------------------------TAB2--------------------------
    @FXML
    public Tab tab2;
    @FXML
    public TableView unit_table;
    @FXML
    public TableColumn u_clm_eq_count;
    @FXML
    public TableColumn u_clm_location;
    @FXML
    public TableColumn u_clm_eq_usability;

    //------------------------------------------TAB3--------------------------
    @FXML
    public Tab tab3;
    @FXML
    public TableView disposable_equipments_table;
    @FXML
    public TableColumn t2_serialNo;
    @FXML
    public TableColumn t2_invoiceNo;
    @FXML
    public TableColumn t2_invoiceDate;
    @FXML
    public TableColumn t2_name;
    @FXML
    public TableColumn t2_make;
    @FXML
    public TableColumn t2_model;
    @FXML
    public TableColumn t2_DOI;
    @FXML
    public TableColumn t2_localAgent;
    @FXML
    public TableColumn t2_warranty;
    @FXML
    public TableColumn t2_location;


    private DatabaseConnection db;
    private ObservableList<EquipmentDataShort> eqDataShort;
    private ObservableList<EquipmentDataLong> disposableEqDataLong;
    private ObservableList<UnitDataShort> unitData;
    private String selectEquipmentQuery = "SELECT Name, Make, Model, LAgent, Usable, COUNT (*) numOfElements FROM equipment_details GROUP BY Name,Make,Model,LAgent,Usable";
    private String checkEquipmentQuery = "SELECT COUNT(SerialNo) FROM equipment_details WHERE SerialNo=?";
    private String insertEquipmentQuery = "INSERT INTO equipment_details(SerialNo,InvoiceNo,InvoiceDate,Name,Make,Model,DoI,LAgent,Warranty,Location,Usable,Image) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
    private String selectUnitQuery = "SELECT Location, Usable, COUNT (*) numOfElements FROM equipment_details GROUP BY Location,Usable";
    private String deleteEquipmentQuery = "DELETE FROM equipment_details WHERE Name = ? AND Usable = ?";
    private String selectDisposableEquipmentQuery = "SELECT * FROM equipment_details WHERE Usable = 'No'";
    private EquipmentDataShort eqRowData;
    private UnitDataShort unitRowData;
    private Image image;
    private FileChooser fileChooser;
    private File file;
    private FileInputStream fis;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.db = new DatabaseConnection();
        try {
            loadEquipmentData();
            loadUnitData();
            loadDisposableEquipmentData();
        }catch(Exception e){
            System.out.println(e.toString());
        }

        eq_table.setRowFactory(tv -> {
            TableRow<EquipmentDataShort> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                eqRowData = row.getItem();
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    System.out.println("Selected Item: "+eqRowData.getName());
                }else if (event.getClickCount() == 2 && (! row.isEmpty()) ){
                    System.out.println("Double click on: "+eqRowData.getName());
                    viewEquipmentDetails(eqRowData.getName(), eqRowData.getMake(), eqRowData.getModel());
                }
            });
            return row ;
        });

        unit_table.setRowFactory(tv -> {
            TableRow<UnitDataShort> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                unitRowData = row.getItem();
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    System.out.println("Selected Item: "+unitRowData.getLocation());
                }else if (event.getClickCount() == 2 && (! row.isEmpty()) ){
                    System.out.println("Double click on: "+unitRowData.getLocation()+" "+ unitRowData.getUsability());
                    viewLocationDetails(unitRowData.getLocation(), unitRowData.getUsability());
                }
            });
            return row ;
        });


    }


    @FXML
    private void loadEquipmentData() throws SQLException {
        try{
            Connection conn = DatabaseConnection.getConnection();
            this.eqDataShort = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(selectEquipmentQuery);

            while(rs.next()){
                this.eqDataShort.add(
                        new EquipmentDataShort(
                                rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6)
                        )
                );
            }

        } catch (SQLException e) {
            System.err.println("Error: "+ e);
        }
        this.clm_name.setCellValueFactory(new PropertyValueFactory<EquipmentDataShort, String>("name"));
        this.clm_make.setCellValueFactory(new PropertyValueFactory<EquipmentDataShort, String>("make"));
        this.clm_model.setCellValueFactory(new PropertyValueFactory<EquipmentDataShort, String>("model"));
        this.clm_localAgent.setCellValueFactory(new PropertyValueFactory<EquipmentDataShort, String>("localAgent"));
        this.clm_usability.setCellValueFactory(new PropertyValueFactory<EquipmentDataShort, String>("usability"));
        this.clm_count.setCellValueFactory(new PropertyValueFactory<EquipmentDataShort, String>("count"));

        this.eq_table.setItems(null);
        this.eq_table.setItems(this.eqDataShort);

    }

    @FXML
    private void addEquipment(ActionEvent event){

        try{
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement checkStmt = conn.prepareStatement(checkEquipmentQuery);
            checkStmt.setString(1,this.eq_serialNo.getText());
            ResultSet res = checkStmt.executeQuery();
            if(res.getBoolean(1)){
                System.out.println("Equipment already exists!!");
                System.out.println(res.next());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText(null);
                alert.setContentText("The equipment you entered, already exists!");
                Optional <ButtonType> action = alert.showAndWait();
            } else {
                if(
                        this.eq_serialNo.getText() == null ||
                                this.eq_invoiceNo.getText() == null ||
                                this.dp_invoiceDate.getText() == null ||
                                this.eq_name.getText() == null ||
                                this.eq_make.getText() == null ||
                                this.eq_model.getText() == null ||
                                this.dp_DOI.getText() == null ||
                                this.eq_localAgent.getText() == null ||
                                this.dp_warranty.getText() == null ||
                                this.eq_location.getText() == null ||
                                this.eq_isUsable.getText() == null ||
                                this.file == null
                ){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Empty fields");
                    alert.setHeaderText(null);
                    alert.setContentText("Some fields are empty. You cannot leave required fields empty!");
                    alert.showAndWait();
                } else{
                    PreparedStatement st = conn.prepareStatement(insertEquipmentQuery);
                    st.setString(1,this.eq_serialNo.getText());
                    st.setString(2,this.eq_invoiceNo.getText());
                    st.setString(3,this.dp_invoiceDate.getText());
                    st.setString(4,this.eq_name.getText());
                    st.setString(5,this.eq_make.getText());
                    st.setString(6,this.eq_model.getText());
                    st.setString(7,this.dp_DOI.getText().toString());
                    st.setString(8,this.eq_localAgent.getText());
                    st.setString(9,this.dp_warranty.getText());
                    st.setString(10,this.eq_location.getText());
                    st.setString(11,this.eq_isUsable.getText());
                    st.setBinaryStream(12, fis, (int)file.length());
                    st.execute();
                }
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        try {
            loadEquipmentData();
            loadUnitData();;
            loadDisposableEquipmentData();
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }


    @FXML
    void chooseFiles() throws FileNotFoundException {
        CommonStageSingleton stageSingleton = CommonStageSingleton.getInstance();
        Stage userStage = stageSingleton.getMainWindow();
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files","*.jpg","*.jpeg","*.png")
        );

        file = fileChooser.showOpenDialog(userStage);
        if(file != null){
            fis = new FileInputStream(file);
            ta_imagePath.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void clearEqFields(ActionEvent event){
        this.eq_serialNo.setText("");
        this.eq_invoiceNo.setText("");
        this.eq_name.setText("");
        this.eq_make.setText("");
        this.eq_model.setText("");
        this.eq_localAgent.setText("");
        this.eq_location.setText("");
        this.eq_isUsable.setText("");
        this.dp_DOI.setText("");
        this.dp_warranty.setText("");
        this.fis = null;
        this.ta_imagePath.setText("");

    }

    private void viewEquipmentDetails(String eqName, String eqMake, String eqModel){
        try{
            CommonStageSingleton stageSingleton = CommonStageSingleton.getInstance();
            Stage userStage = stageSingleton.getMainWindow();
            FXMLLoader loader = new FXMLLoader();
            Pane equipmentScene = (Pane) loader.load(getClass().getResource("/equipment_details/EquipmentDetails.fxml").openStream());
            EquipmentDetailsController controller = loader.getController();
            controller.initData(eqName, eqMake, eqModel);
            Scene scene = new Scene(equipmentScene);
            userStage.setScene(scene);
            userStage.setResizable(false);
            userStage.show();
        } catch (IOException e){
            System.out.println(e.toString());
        }
    }

    @FXML
    private void deleteEquipmentEntry(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are you sure?");
        alert.setHeaderText(null);
        alert.setContentText("You are going to delete a complete equipment! This action cannot be undone!");
        Optional <ButtonType> action = alert.showAndWait();

        if(action.get() == ButtonType.OK){
            try {
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement st = conn.prepareStatement(deleteEquipmentQuery);
                st.setString(1, eqRowData.getName());
                st.setString(2, eqRowData.getUsability());
                st.execute();
                loadEquipmentData();
                loadUnitData();;
                loadDisposableEquipmentData();
            } catch (Exception e){
                System.out.println(e.toString());
            }
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
                this.unitData.add(new UnitDataShort(rs.getString(1),rs.getString(2),rs.getString(3)));
            }

        } catch (SQLException e) {
            System.err.println("Error: "+ e);
        }

        this.u_clm_location.setCellValueFactory(new PropertyValueFactory<UnitDataShort, String>("location"));
        this.u_clm_eq_count.setCellValueFactory(new PropertyValueFactory<UnitDataShort, String>("count"));
        this.u_clm_eq_usability.setCellValueFactory(new PropertyValueFactory<UnitDataShort, String>("usability"));

        this.unit_table.setItems(null);
        this.unit_table.setItems(this.unitData);

    }


    private void viewLocationDetails(String location, String usability){
        try{
            CommonStageSingleton stageSingleton = CommonStageSingleton.getInstance();
            Stage userStage = stageSingleton.getMainWindow();
            FXMLLoader loader = new FXMLLoader();
            Pane locationScene = (Pane) loader.load(getClass().getResource("/location_details/LocationDetails.fxml").openStream());
            LocationDetailsController controller = loader.getController();
            controller.initLocationData(location, usability);
            Scene scene = new Scene(locationScene);
            userStage.setScene(scene);
            userStage.setResizable(false);
            userStage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //------------------------------------------TAB3----------------------------
    @FXML
    private void loadDisposableEquipmentData() throws IOException {
        try{
            this.disposableEqDataLong = FXCollections.observableArrayList();
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement checkStmt = conn.prepareStatement(selectDisposableEquipmentQuery);
            ResultSet rs = checkStmt.executeQuery();

            while(rs.next()){
                this.disposableEqDataLong.add(
                        new EquipmentDataLong(
                                rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getString(8),
                                rs.getString(9),
                                rs.getString(10),
                                rs.getString(11)
                        )
                );

//                InputStream is = rs.getBinaryStream("Image");
//                FileOutputStream os = new FileOutputStream(new File("disposableEqImage.jpg"));
//                byte[] content = new byte[1024];
//                int size = 0;
//                while ((size = is.read(content)) != -1){
//                    os.write(content, 0, size);
//                }
//                os.close();
//                is.close();
//
//                File file = new File("disposableEqImage.jpg");
//                image = new Image(file.toURI().toString());
            }

        } catch (SQLException e) {
            System.err.println("Error: "+ e);
            e.printStackTrace();
        }

        this.t2_serialNo.setCellValueFactory(new PropertyValueFactory<EquipmentDataLong, String>("serialNo"));
        this.t2_invoiceNo.setCellValueFactory(new PropertyValueFactory<EquipmentDataLong, String>("invoiceNo"));
        this.t2_invoiceDate.setCellValueFactory(new PropertyValueFactory<EquipmentDataLong, String>("invoiceDate"));
        this.t2_name.setCellValueFactory(new PropertyValueFactory<EquipmentDataLong, String>("name"));
        this.t2_make.setCellValueFactory(new PropertyValueFactory<EquipmentDataLong, String>("make"));
        this.t2_model.setCellValueFactory(new PropertyValueFactory<EquipmentDataLong, String>("model"));
        this.t2_DOI.setCellValueFactory(new PropertyValueFactory<EquipmentDataLong, String>("DOI"));
        this.t2_localAgent.setCellValueFactory(new PropertyValueFactory<EquipmentDataLong, String>("localAgent"));
        this.t2_warranty.setCellValueFactory(new PropertyValueFactory<EquipmentDataLong, String>("warranty"));
        this.t2_location.setCellValueFactory(new PropertyValueFactory<EquipmentDataLong, String>("location"));


        this.disposable_equipments_table.setItems(null);
        this.disposable_equipments_table.setItems(this.disposableEqDataLong);

    }
}
