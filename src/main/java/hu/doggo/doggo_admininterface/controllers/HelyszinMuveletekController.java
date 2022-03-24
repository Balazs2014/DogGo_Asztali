package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.api.HelyszinApi;
import hu.doggo.doggo_admininterface.classes.Helyszin;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private AnchorPane mainAnchor;
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField nameInput;
    @FXML
    private TextArea descriptionInput;
    @FXML
    private Button allowButton;

    private Stage stage;
    private Helyszin reszletes;
    private boolean allowed;
    private boolean saved = true;
    private double x = 0;
    private double y = 0;
    @FXML
    private Label charCountLabel;
    @FXML
    private Label charCountLabel2;


    public Helyszin getReszletes() {
        return reszletes;
    }

    public void setReszletes(Helyszin reszletes) {
        this.reszletes = reszletes;
        writeData();
    }

    public void writeData() {
        nameInput.setText(reszletes.getName());
        descriptionInput.setFont(Font.font("Verdana", 12));
        descriptionInput.setText(reszletes.getDescription());

        allowed = reszletes.isAllowed();

        allowButton.setDisable(allowed);
    }

    private void save() {
        String nev = nameInput.getText().trim();
        String leiras = descriptionInput.getText().trim();
        if (nev.isEmpty()) {
            alert("Név megadása kötelező!");
            return;
        }
        if (nev.length() < 5) {
            alert("A névnek minimum 5 karakter hosszúnak kell lennie!");
            return;
        }
        if (nev.length() > 40) {
            alert("A név maximum 40 karakter hosszú lehet!");
            return;
        }
        if (leiras.length() > 255) {
            alert("A leírás maximum 255 karakter hosszú lehet!");
            return;
        }

        if (!(reszletes.getName().equals(nev) && reszletes.getDescription().equals(leiras)
                && reszletes.isAllowed() == allowed)) {
            reszletes.setName(nev);
            reszletes.setDescription(leiras);
            reszletes.setAllowed(allowed);

            try {
                Helyszin modositandoHelyszin = HelyszinApi.updateLocation(reszletes);
                if (modositandoHelyszin != null) {
                    alert("Sikeres mentés!");
                    saved = true;
                } else {
                    alert("Sikertelen mentés!");
                }
            } catch (IOException e) {
                error(e);
            }
        } else {
            alert("Nem törént változtatás!");
        }
    }

    private void close() {
        String nev = nameInput.getText().trim();
        String leiras = descriptionInput.getText().trim();
        if (!reszletes.getName().equals(nev) || !reszletes.getDescription().equals(leiras)) {
            saved = false;
        }

        if (!saved) {
            if (!confirmation("A módosítások el fognak veszni! Szerete menteni?")) {
                ((Stage) mainAnchor.getScene().getWindow()).close();
                return;
            }
            save();
        }

        if (saved) {
            ((Stage) mainAnchor.getScene().getWindow()).close();
        }

    }

    @FXML
    public void onAllowClick(ActionEvent actionEvent) {
        allowed = true;
        saved = false;
        allowButton.setDisable(allowed);
    }

    @FXML
    public void onSaveClick(ActionEvent actionEvent) {
        save();
    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        close();
    }

    @FXML
    public void onCloseClick(Event event) {
        close();
    }

    @FXML
    public void onMinimizeClick(Event event) {
        ((Stage) mainAnchor.getScene().getWindow()).setIconified(true);
    }

    @FXML
    public void onBorderPaneTopDragged(MouseEvent mouseEvent) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        dragWindow(stage, mouseEvent, x, y);
    }

    @FXML
    public void onBorderPaneTopPressed(MouseEvent mouseEvent) {
        x = mouseEvent.getSceneX();
        y = mouseEvent.getSceneY();
    }

    @FXML
    public void getCharCountTextField(Event event) {
        if (nameInput.getText().length() > 40 || nameInput.getText().length() < 5) {
            charCountLabel.setText(nameInput.getText().length() + "");
        }else {
            charCountLabel.setText("");
        }
    }

    @FXML
    public void getCharCountTextArea(Event event) {
        if (descriptionInput.getText().length() > 255) {
            charCountLabel2.setText(descriptionInput.getText().length() + "");
        } else {
            charCountLabel2.setText("");
        }
    }
}
