package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.api.VisszajelzesApi;
import hu.doggo.doggo_admininterface.classes.Visszajelzes;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class VisszajelzesMuveletekController extends Controller {
    @FXML
    private TextArea txtAreaLeiras;
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label lblDatum;
    @FXML
    private Button btnOlvas;

    private Visszajelzes reszletes;
    private Stage stage;
    private double x = 0;
    private double y = 0;
    private boolean olvasva;
    private boolean mentve = true;

    public Visszajelzes getReszletes() {
        return reszletes;
    }

    public void setReszletes(Visszajelzes reszletes) {
        this.reszletes = reszletes;

        adatokKiirasa();
    }

    private void adatokKiirasa() {
        txtAreaLeiras.setFont(Font.font("Verdana", 12));
        txtAreaLeiras.setText(reszletes.getComment());
        lblDatum.setText(reszletes.getFormattedDate());

        btnOlvas.setDisable(reszletes.isRead());
    }

    private void bezárás() {
        if (!mentve) {
            if (!megerosites("A módosítások el fognak veszni!")) {
                return;
            }
        }

        ((Stage) mainAnchor.getScene().getWindow()).close();
    }

    @FXML
    public void onMentesClick(ActionEvent actionEvent) {

        reszletes.setRead(olvasva);

        try {
            Visszajelzes olvasottLeiras = VisszajelzesApi.updateVisszajelzes(reszletes);
            if (olvasottLeiras != null) {
                alert("Sikeres mentés!");
                ((Stage) mainAnchor.getScene().getWindow()).close();
            } else {
                alert("Sikertelen mentés!");
            }
        } catch (IOException e) {
            hibaKiir(e);
        }
    }

    @FXML
    public void onMegseClick(ActionEvent actionEvent) {
        bezárás();
    }

    @FXML
    public void onOlvasvaClick(ActionEvent actionEvent) {
        olvasva = true;
        mentve = false;
        btnOlvas.setDisable(olvasva);
    }
    
    @FXML
    public void onCloseClick(Event event) {
        bezárás();
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