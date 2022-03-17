package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.AdminInterface;
import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.api.LoginApi;
import hu.doggo.doggo_admininterface.classes.Felhasznalo;
import hu.doggo.doggo_admininterface.classes.Login;
import hu.doggo.doggo_admininterface.classes.Token;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class BejelentkezesController extends Controller {
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;

    @FXML
    public void onLoginClick(ActionEvent actionEvent) {
        String felh = usernameInput.getText();
        String jelszo = passwordInput.getText();

        Login login = new Login(felh, jelszo);
        try {
            Token token = LoginApi.postLogin(login);
            Felhasznalo felhAdatai = LoginApi.getLoginData(token.getToken());
            if (felhAdatai.getPermission() > 1) {
                AdminInterface.superAdmin = felhAdatai.getPermission() == 3;

                ((Stage) mainAnchor.getScene().getWindow()).close();

                Stage stage = new Stage();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hu/doggo/doggo_admininterface/fxml/main-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1300, 700);
                stage.initStyle(StageStyle.UNDECORATED);
                Image icon = new Image(getClass().getResourceAsStream("/hu/doggo/doggo_admininterface/icons/logo.png"));
                stage.getIcons().add(icon);
                stage.setTitle("DogGo - Admin Interface");
                stage.setScene(scene);
                stage.setMinWidth(1300);
                stage.setMinHeight(700);
                stage.show();
            } else {
                alert("Nem rendelkezik admin jogosultsággal!");
            }
        } catch (IOException e) {
            error(e);
        }
    }

    @FXML
    public void onCloseClick(ActionEvent actionEvent) {
        System.exit(0);
    }
}
