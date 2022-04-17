package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.AdminInterface;
import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.MainController;
import hu.doggo.doggo_admininterface.api.LoginApi;
import hu.doggo.doggo_admininterface.classes.Felhasznalo;
import hu.doggo.doggo_admininterface.classes.Login;
import hu.doggo.doggo_admininterface.classes.Token;
import javafx.application.Platform;
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
    @FXML
    private Button closeButton;

    public void initialize() {
        loginButton.setDefaultButton(true);
        Platform.runLater(() ->
                mainAnchor.requestFocus()
        );
    }

    @FXML
    public void onLoginClick(ActionEvent actionEvent) {
        String felh = usernameInput.getText().trim();
        String jelszo = passwordInput.getText().trim();

        Login login = new Login(felh, jelszo);
        try {
            Token token = LoginApi.postLogin(login);
            Felhasznalo felhAdatai = LoginApi.getLoginData(token.getToken());
            if (felhAdatai.getPermission() > 1) {
                AdminInterface.superAdmin = felhAdatai.getPermission() == 3;
                HelyszinHozzaadasController.user_id = felhAdatai.getId();

                ((Stage) mainAnchor.getScene().getWindow()).close();

                MainController main = (MainController) newWindow(
                        "/hu/doggo/doggo_admininterface/fxml/main-view.fxml", "DogGo - Admin Interface", 1300, 700
                );
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
