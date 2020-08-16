package location_details;

import common_stage.CommonStageSingleton;
import db_utils.DatabaseConnection;
import edit_equipment.EditEquipmentController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.EquipmentDataLong;
import models.EquipmentDataShort;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LocationDetailsController implements Initializable {
    @FXML
    public Label eq_name;
    @FXML
    public ImageView eq_img;
    @FXML
    public TableView general_info_table;
    @FXML
    public TableColumn t1_eq_name;
    @FXML
    public TableColumn t1_eq_count;
    @FXML
    public TableView details_table;
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
    public TableColumn t2_usability;

    private DatabaseConnection db;
    private ObservableList<EquipmentDataLong> eqDataLong;
    private ObservableList<EquipmentDataShort> equipmentCountData;
    private String selectEquipmentQuery = "SELECT * FROM equipment_details WHERE Location = ? AND Usable = ?";
    private String selectEquipmentCountQuery = "SELECT Name, Make, Model, LAgent, Usable, COUNT (*) numOfElements FROM equipment_details WHERE Location=? GROUP BY Name";
    private String location;
    private String usability;
    private Image image;
    private EquipmentDataLong eqRowData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.db = new DatabaseConnection();

        File file = new File("assets/hospital.png");
        image = new Image(file.toURI().toString());
        this.eq_img.setImage(image);
        this.eq_img.setPreserveRatio(true);

        details_table.setRowFactory(tv -> {
            TableRow<EquipmentDataLong> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                eqRowData = row.getItem();
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    System.out.println("Selected Item: "+eqRowData.getName());
                }
            });
            return row ;
        });


    }

    public void initLocationData(String location, String usability){
        this.location = location;
        this.usability = usability;

        try{
            loadEquipmentData();
            loadEquipmentCounts();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void loadEquipmentData() throws IOException {
        try{
            this.eqDataLong = FXCollections.observableArrayList();
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement checkStmt = conn.prepareStatement(selectEquipmentQuery);
            checkStmt.setString(1,this.location);
            checkStmt.setString(2,this.usability);
            ResultSet rs = checkStmt.executeQuery();

            while(rs.next()){
                this.eqDataLong.add(
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

                InputStream is = rs.getBinaryStream("Image");
                FileOutputStream os = new FileOutputStream(new File("image.jpg"));
                byte[] content = new byte[1024];
                int size = 0;
                while ((size = is.read(content)) != -1){
                    os.write(content, 0, size);
                }
                os.close();
                is.close();

                File file = new File("image.jpg");
                image = new Image(file.toURI().toString());
            }

        } catch (SQLException e) {
            System.err.println("Error: "+ e);
            e.printStackTrace();
        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
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
        this.t2_usability.setCellValueFactory(new PropertyValueFactory<EquipmentDataLong, String>("usability"));


        this.details_table.setItems(null);
        this.details_table.setItems(this.eqDataLong);

    }

    @FXML
    private void loadEquipmentCounts(){
        try{
            this.equipmentCountData = FXCollections.observableArrayList();
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement checkStmt = conn.prepareStatement(selectEquipmentCountQuery);
            checkStmt.setString(1,this.location);
            ResultSet rs = checkStmt.executeQuery();

            while(rs.next()){
                this.equipmentCountData.add(
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
        }catch(Exception e){
            e.printStackTrace();
        }

        this.t1_eq_name.setCellValueFactory(new PropertyValueFactory<EquipmentDataShort, String>("name"));
        this.t1_eq_count.setCellValueFactory(new PropertyValueFactory<EquipmentDataShort, String>("count"));

        this.general_info_table.setItems(null);
        this.general_info_table.setItems(this.equipmentCountData);
    }


    @FXML
    private void editEquipmentDetails(){

        if(eqRowData == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selected Equipment");
            alert.setHeaderText(null);
            alert.setContentText("First, you have to select an equipment to edit! Select equipment from the bottom table.");
            alert.showAndWait();
        } else {
            try{
                CommonStageSingleton stageSingleton = CommonStageSingleton.getInstance();
                Stage userStage = stageSingleton.getMainWindow();
                FXMLLoader loader = new FXMLLoader();
                Pane EditEquipmentScene = (Pane) loader.load(getClass().getResource("/edit_equipment/EditEquipment.fxml").openStream());
                EditEquipmentController controller = loader.getController();
                controller.initData(eqRowData, "locationDetails");
                Scene scene = new Scene(EditEquipmentScene);
                userStage.setScene(scene);
                userStage.setResizable(false);
                userStage.show();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void goBack(){
        try{
            CommonStageSingleton stageSingleton = CommonStageSingleton.getInstance();
            Stage userStage = stageSingleton.getMainWindow();
            FXMLLoader loader = new FXMLLoader();
            Pane dashboardScene = (Pane) loader.load(getClass().getResource("/dashboard/Dashboard.fxml").openStream());


            Scene scene = new Scene(dashboardScene);
            userStage.setScene(scene);
            userStage.setResizable(false);
            userStage.show();
        } catch (IOException e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }


}