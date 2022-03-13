package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.api.HelyszinApi;
import hu.doggo.doggo_admininterface.classes.Helyszin;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class HelyszinMuveletekController extends Controller {
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextArea txtAreaLeiras;
    @FXML
    private TextField inputHelyszinNev;
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private Button btnEngedelyezes;

    private Helyszin reszletes;
    private Stage stage;
    private double x = 0;
    private double y = 0;
    private boolean engedelyezve;
    private boolean mentve = true;

    public Helyszin getReszletes() {
        return reszletes;
    }
    public void setReszletes(Helyszin reszletes) {
        this.reszletes = reszletes;

        adatokkiirasa();
    }

    private void adatokkiirasa() {
        inputHelyszinNev.setText(reszletes.getName());
        txtAreaLeiras.setFont(Font.font("Verdana", 12));
        txtAreaLeiras.setText(reszletes.getDescription());

        engedelyezve = reszletes.isAllowed();

        btnEngedelyezes.setDisable(engedelyezve);
    }

    private void bezaras() {
        String nev = inputHelyszinNev.getText().trim();
        String leiras = txtAreaLeiras.getText().trim();
        if (!reszletes.getName().equals(nev) || !reszletes.getDescription().equals(leiras)) {
            mentve = false;
        }

        if (!mentve) {
            if (!megerosites("A módosítások el fognak veszni! Szerete menteni?")) {
                ((Stage) mainAnchor.getScene().getWindow()).close();
                return;
            }
            mentes();
        }

        if (mentve) {
            ((Stage) mainAnchor.getScene().getWindow()).close();
        }

    }

    private void mentes() {
        String nev = inputHelyszinNev.getText().trim();
        String leiras = txtAreaLeiras.getText().trim();
        if (nev.isEmpty()) {
            alert("Név megadása kötelező!");
            return;
        } else if (nev.length() < 5) {
            alert("A névnek minimum 5 karakter hosszúnak kell lennie!");
            return;
        }

        reszletes.setName(nev);
        reszletes.setDescription(leiras);
        reszletes.setAllowed(engedelyezve);

        try {
            Helyszin modositandoHelyszin = HelyszinApi.updateHelyszin(reszletes);
            if (modositandoHelyszin != null) {
                alert("Sikeres mentés!");
                mentve = true;
            } else {
                alert("Sikertelen mentés!");
            }
        } catch (IOException e) {
            hibaKiir(e);
        }
    }

    @FXML
    public void onMentesClick(ActionEvent actionEvent) {
        mentes();
    }

    @FXML
    public void onVisszaClick(ActionEvent actionEvent) {
        bezaras();
    }

    @FXML
    public void onEngedelyezesClick(ActionEvent actionEvent) {
        engedelyezve = true;
        mentve = false;
        btnEngedelyezes.setDisable(engedelyezve);
    }

    @FXML
    public void onCloseClick(Event event) {
        bezaras();
    }

    @FXML
    public void onBorderPaneTopDragged(MouseEvent mouseEvent) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        dragWindow(stage, mouseEvent, x, y);
    }

    @FXML
    public void onMinimizeClick(Event event) {
        ((Stage) mainAnchor.getScene().getWindow()).setIconified(true);
    }

    @FXML
    public void onBorderPaneTopPressed(MouseEvent mouseEvent) {
        x = mouseEvent.getSceneX();
        y = mouseEvent.getSceneY();
    }
}
