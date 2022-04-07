package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.AdminInterface;
import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.MainController;
import hu.doggo.doggo_admininterface.api.LoginApi;
import hu.doggo.doggo_admininterface.classes.Felhasznalo;
import hu.doggo.doggo_admininterface.classes.Login;
import hu.doggo.doggo_admininterface.classes.Token;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class BejelentkezesController extends Controller {
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private Button loginButton;

    public void initialize() {
        loginButton.setDefaultButton(true);
    }

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

                MainController main = (MainController) newWindow(
                        "/hu/doggo/doggo_admininterface/fxml/main-view.fxml", "DogGo - Admin Interface", 1300, 700
                );
                Image icon = new Image(getClass().getResourceAsStream("/hu/doggo/doggo_admininterface/icons/logo.png"));
                main.getStage().getIcons().add(icon);
                main.getStage().show();
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
