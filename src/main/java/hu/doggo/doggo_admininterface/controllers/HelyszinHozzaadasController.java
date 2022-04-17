package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.api.HelyszinApi;
import hu.doggo.doggo_admininterface.classes.Helyszin;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelyszinHozzaadasController extends Controller {
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button allowButton;
    @FXML
    private Label charCountLabel;
    @FXML
    private TextArea descriptionInput;
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private TextField nameInput;
    @FXML
    private Label charCountLabel2;
    @FXML
    private Spinner<Double> longitudeInput;
    @FXML
    private Spinner<Double> latitudeInput;

    private Stage stage;
    private boolean allowed = false;
    private double x = 0;
    private double y = 0;
    public static int user_id = 0;

    public void initialize() {
        Platform.runLater(() ->
                mainAnchor.requestFocus()
        );
    }

    @FXML
    public void onCloseClick(Event event) {
        close();
    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        close();
    }

    private void save() {
        String nev = nameInput.getText().trim();
        String leiras = descriptionInput.getText().trim();
        double szelesseg = 0;
        double hosszusag = 0;
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

        try {
            szelesseg = latitudeInput.getValue();
        } catch (NullPointerException e) {
            alert("A szélesség megadása kötelező");
            return;
        } catch (Exception e) {
            alert("A szélesség minimum -90, maximum 90 lehet!");
            return;
        }

        if (szelesseg < -90 || szelesseg > 90) {
            alert("A szélesség minimum -90, maximum 90 lehet!");
            return;
        }

        try {
            hosszusag = longitudeInput.getValue();
        } catch (NullPointerException e) {
            alert("A hosszúság megadása kötelező");
            return;
        } catch (Exception e) {
            alert("A hosszúság minimum -180, maximum 180 lehet!");
            return;
        }

        if (hosszusag < -180 || hosszusag > 180) {
            alert("A hosszúság minimum -180, maximum 180 lehet!");
            return;
        }

        try {
            Helyszin ujHelyszin = new Helyszin(0, nev, leiras, hosszusag, szelesseg, user_id, allowed);
            Helyszin letrehozott = HelyszinApi.postLocation(ujHelyszin);
            if (letrehozott != null) {
                alert("Sikeres hozzáadás!");
                nameInput.setText("");
                descriptionInput.setText("");
                longitudeInput.getValueFactory().setValue(0.0);
                latitudeInput.getValueFactory().setValue(0.0);
                allowButton.setDisable(false);
            } else {
                alert("Sikertelen hozzáadás!");
            }
        } catch (IOException e) {
            error(e);
        }
    }

    private void close() {
        if (confirmation("Megszakítja a hozzáadást?")) {
            ((Stage) mainAnchor.getScene().getWindow()).close();
        }
    }

    @FXML
    public void onMinimizeClick(Event event) {
        ((Stage) mainAnchor.getScene().getWindow()).setIconified(true);
    }

    @FXML
    public void onAllowClick(ActionEvent actionEvent) {
        allowed = true;
        allowButton.setDisable(allowed);
    }

    @FXML
    public void getCharCountTextField(Event event) {
        int charCount = nameInput.getText().length();
        if (charCount > 40) {
            charCountLabel.setText("" + (40 - charCount));
        } else if (charCount < 5) {
            charCountLabel.setText("+" + (5 - charCount));
        } else {
            charCountLabel.setText("");
        }
    }

    @FXML
    public void getCharCountTextArea(Event event) {
        int charCount = descriptionInput.getText().length();
        if (charCount > 255) {
            charCountLabel2.setText("" + (255 - charCount));
        } else {
            charCountLabel2.setText("");
        }
    }

    @FXML
    public void onBorderPaneTopPressed(MouseEvent mouseEvent) {
        x = mouseEvent.getSceneX();
        y = mouseEvent.getSceneY();
    }

    @FXML
    public void onBorderPaneTopDragged(MouseEvent mouseEvent) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        dragWindow(stage, mouseEvent, x, y);
    }

    @FXML
    public void onAddClick(ActionEvent actionEvent) {
        save();
    }
}
