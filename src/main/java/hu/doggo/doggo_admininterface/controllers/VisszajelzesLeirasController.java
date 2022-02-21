package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.api.ErtekelesApi;
import hu.doggo.doggo_admininterface.api.VisszajelzesApi;
import hu.doggo.doggo_admininterface.classes.Ertekeles;
import hu.doggo.doggo_admininterface.classes.Visszajelzes;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class VisszajelzesLeirasController extends Controller {
    @FXML
    private TextArea txtAreaLeiras;
    @FXML
    private CheckBox chckBoxOlvasva;
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private BorderPane borderPane;

    private Visszajelzes leiras;
    private Stage stage;

    private double x = 0;
    private double y = 0;


    public Visszajelzes getLeiras() {
        return leiras;
    }

    public void setLeiras(Visszajelzes leiras) {
        this.leiras = leiras;

        txtAreaLeiras.setFont(Font.font("Verdana", 12));
        txtAreaLeiras.setText(leiras.getComment());
        chckBoxOlvasva.setSelected(leiras.getRead());
    }

    @FXML
    public void onMentesClick(ActionEvent actionEvent) {

        leiras.setRead(chckBoxOlvasva.isSelected());

        try {
            Visszajelzes olvasottLeiras = VisszajelzesApi.updateVisszajelzes(leiras);
            if (olvasottLeiras != null) {
                alertWait("Sikeres mentés");
            } else {
                alertWait("Sikertelen mentés");
            }
        } catch (IOException e) {
            hibaKiir(e);
        }
    }

    @FXML
    public void onCloseClick(Event event) {
        ((Stage) mainAnchor.getScene().getWindow()).close();
    }

    @FXML
    public void onMinimizeClick(Event event) {
        ((Stage) mainAnchor.getScene().getWindow()).setIconified(true);
    }

    @FXML
    public void onBorderPaneTopDragged(MouseEvent event) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        dragWindow(stage, event, x, y);
    }

    @FXML
    public void onBorderPaneTopPressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }
}
