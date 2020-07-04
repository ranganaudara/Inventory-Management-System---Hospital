package loginapp;

import common_stage.CommonStageSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.LoginModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    LoginModel loginModel = new LoginModel();

    @FXML
    private Label db_status;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button login_btn;
    @FXML
    private Label login_status;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (this.loginModel.isDatabaseConnected()) {
            this.db_status.setText("Connected");
        } else {
            this.db_status.setText("Not connected");
        }
    }

    @FXML
    public void Login(ActionEvent event) {
        try {
            boolean isExists = this.loginModel.isLogin(this.username.getText(), this.password.getText());
            if (isExists) {
                Stage stage = (Stage) this.login_btn.getScene().getWindow();
                stage.close();
                userLogin();
            } else {
                this.login_status.setText("Wrong Credentials");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void userLogin() {
        try {
            CommonStageSingleton stageSingleton = CommonStageSingleton.getInstance();
            Stage userStage = stageSingleton.getMainWindow();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/dashboard/Dashboard.fxml").openStream());


            Scene scene = new Scene(root);
            userStage.setScene(scene);
//            userStage.setTitle("Inventory Dashboard");
            userStage.setResizable(false);
            userStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
