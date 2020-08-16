package edit_equipment;

import common_stage.CommonStageSingleton;
import db_utils.DatabaseConnection;
import equipment_details.EquipmentDetailsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import location_details.LocationDetailsController;
import models.EquipmentDataLong;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditEquipmentController implements Initializable {
    @FXML
    public TextField eq_serialNo;
    @FXML
    public TextField eq_invoiceNo;
    @FXML
    public TextField eq_invoiceDate;
    @FXML
    public TextField eq_name;
    @FXML
    public TextField eq_make;
    @FXML
    public TextField eq_model;
    @FXML
    public TextField eq_DOI;
    @FXML
    public TextField eq_localAgent;
    @FXML
    public TextField eq_warrantyDate;
    @FXML
    public TextField eq_location;
    @FXML
    public TextField eq_isUsable;
    @FXML
    public TextArea ta_imagePath;
    @FXML
    public ImageView eq_image;

    private DatabaseConnection db;
    private EquipmentDataLong initEquipmentData;
    private Image image;
    private FileChooser fileChooser;
    private File file;
    private FileInputStream fis;
    private String fromWhere;

    private String editEquipmentQuery = "UPDATE equipment_details SET SerialNo = ?, InvoiceNo = ?, InvoiceDate = ?, Name = ?, Make = ?, Model = ?, DoI = ?, LAgent = ?, Warranty = ?, Location = ?, Usable = ?, Image = ? WHERE SerialNo = ?";


    @FXML
    void chooseFiles() throws FileNotFoundException {
        CommonStageSingleton stageSingleton = CommonStageSingleton.getInstance();
        Stage userStage = stageSingleton.getMainWindow();
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png")
        );

        file = fileChooser.showOpenDialog(userStage);
        if (file != null) {
            fis = new FileInputStream(file);
            ta_imagePath.setText(file.getAbsolutePath());
            image = new Image(file.toURI().toString());
            this.eq_image.setImage(image);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.db = new DatabaseConnection();
    }

    public void initData(EquipmentDataLong initData, String fromWhere) throws FileNotFoundException {
        this.fromWhere = fromWhere;
        this.initEquipmentData = initData;
        this.eq_serialNo.setText(initData.getSerialNo());
        this.eq_invoiceNo.setText(initData.getInvoiceNo());
        this.eq_invoiceDate.setText(initData.getInvoiceDate());
        this.eq_name.setText(initData.getName());
        this.eq_make.setText(initData.getMake());
        this.eq_model.setText(initData.getModel());
        this.eq_DOI.setText(initData.getDOI());
        this.eq_localAgent.setText(initData.getlocalAgent());
        this.eq_warrantyDate.setText(initData.getWarranty());
        this.eq_location.setText(initData.getLocation());
        this.eq_isUsable.setText(initData.getUsable());

        this.file = new File("image.jpg");
        this.fis = new FileInputStream(file);
        image = new Image(file.toURI().toString());
        ta_imagePath.setText(file.getAbsolutePath());
        this.eq_image.setImage(image);
        this.eq_image.setPreserveRatio(true);
        this.eq_image.setFitWidth(475.0);
        this.eq_image.setFitHeight(582.0);
    }

    @FXML
    private void goBack() {
        try {
            CommonStageSingleton stageSingleton = CommonStageSingleton.getInstance();
            Stage userStage = stageSingleton.getMainWindow();
            FXMLLoader loader = new FXMLLoader();
            Scene scene = null;
            if(this.fromWhere.equals("equipmentDetails")) {

                Pane equipmentDetailsScene = (Pane) loader.load(getClass().getResource("/equipment_details/EquipmentDetails.fxml").openStream());
                EquipmentDetailsController controller = loader.getController();
                controller.initData(initEquipmentData.getName(), initEquipmentData.getMake(), initEquipmentData.getModel());
                scene = new Scene(equipmentDetailsScene);

            } else if(this.fromWhere.equals("locationDetails")) {

                Pane locationDetailsScene = (Pane) loader.load(getClass().getResource("/location_details/LocationDetails.fxml").openStream());
                LocationDetailsController controller = loader.getController();
                controller.initLocationData(initEquipmentData.getName(),initEquipmentData.getUsable());
                scene = new Scene(locationDetailsScene);
            }

            userStage.setScene(scene);
            userStage.setResizable(false);
            userStage.show();
        } catch (IOException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

    @FXML
    private void editEquipment(ActionEvent event) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (
                    this.eq_serialNo.getText() == null ||
                    this.eq_invoiceNo.getText() == null ||
                    this.eq_invoiceDate.getText() == null ||
                    this.eq_name.getText() == null ||
                    this.eq_make.getText() == null ||
                    this.eq_model.getText() == null ||
                    this.eq_DOI.getText() == null ||
                    this.eq_localAgent.getText() == null ||
                    this.eq_warrantyDate.getText() == null ||
                    this.eq_location.getText() == null ||
                    this.eq_isUsable.getText() == null ||
                    this.file == null
            ) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty fields");
                alert.setHeaderText(null);
                alert.setContentText("Some fields are empty. You cannot leave required fields empty!");
                alert.showAndWait();
            } else {
                PreparedStatement st = conn.prepareStatement(editEquipmentQuery);
                st.setString(1, this.eq_serialNo.getText());
                st.setString(2, this.eq_invoiceNo.getText());
                st.setString(3, this.eq_invoiceDate.getText());
                st.setString(4, this.eq_name.getText());
                st.setString(5, this.eq_make.getText());
                st.setString(6, this.eq_model.getText());
                st.setString(7, this.eq_DOI.getText());
                st.setString(8, this.eq_localAgent.getText());
                st.setString(9, this.eq_warrantyDate.getText());
                st.setString(10, this.eq_location.getText());
                st.setString(11, this.eq_isUsable.getText());
                st.setBinaryStream(12, fis, (int) file.length());
                st.setString(13, this.initEquipmentData.getSerialNo());
                st.execute();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful!");
                alert.setHeaderText(null);
                alert.setContentText("Equipment details successfully updated!");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
