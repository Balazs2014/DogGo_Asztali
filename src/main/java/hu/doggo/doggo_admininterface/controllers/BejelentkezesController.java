package hu.doggo.doggo_admininterface.controllers;

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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class BejelentkezesController extends Controller {
    @FXML
    private PasswordField inputJelszo;
    @FXML
    private TextField inputFelh;
    @FXML
    private AnchorPane mainAnchor;

    @FXML
    public void onBejelentkezesClick(ActionEvent actionEvent) {
        String felh = inputFelh.getText();
        String jelszo = inputJelszo.getText();

        Login login = new Login(felh, jelszo);
        try {
            Token token = LoginApi.login(login);
            Felhasznalo felhAdatai = LoginApi.getLoginData(token.getToken());
            if (felhAdatai.getPermission() > 1) {
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
            hibaKiir(e);
        }
    }

    @FXML
    public void onBezarasClick(ActionEvent actionEvent) {
        System.exit(0);
    }
}
