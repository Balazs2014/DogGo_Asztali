package hu.doggo.doggo_admininterface;

import hu.doggo.doggo_admininterface.controllers.Ablakok;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController extends Ablakok {
    @FXML
    private Button felhasznalokButton;
    @FXML
    private Button helyszinekButton;
    @FXML
    private Button statisztikakButton;
    @FXML
    private BorderPane borderPane;

    private Stage stage;
    private Scene scene;

    private double x = 0;
    private double y = 0;


    public void initialize() throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("statisztikak-view.fxml"));
        borderPane.setCenter(view);

    }

    @FXML
    private void onFelhasznalokButtonClick(ActionEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("felhasznalok-view.fxml"));
        borderPane.setCenter(view);
    }

    @FXML
    private void onHelyszinekButtonClick(ActionEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("helyszinek-view.fxml"));
        borderPane.setCenter(view);
    }

    @FXML
    private  void onStatisztikakButtonClick(ActionEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("statisztikak-view.fxml"));
        borderPane.setCenter(view);
    }

    @FXML
    public void onBorderPaneTopDragged(MouseEvent event) {
        Stage stage = (Stage)borderPane.getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    @FXML
    public void onBorderPaneTopPressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    public void onCloseClick(Event event) {
        if(!(confirm("Ki szeretne lépni a programból?"))) {
            return;
        }
        javafx.application.Platform.exit();
    }
}
