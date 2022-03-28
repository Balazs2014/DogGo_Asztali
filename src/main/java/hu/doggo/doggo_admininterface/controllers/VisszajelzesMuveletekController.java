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
    private AnchorPane mainAnchor;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label receiveDateLabel;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Button readButton;

    private Stage stage;
    private Visszajelzes reszletes;
    private boolean olvasva;
    private boolean mentve = true;
    private double x = 0;
    private double y = 0;


    public Visszajelzes getReszletes() {
        return reszletes;
    }

    public void setReszletes(Visszajelzes reszletes) {
        this.reszletes = reszletes;

        writeData();
    }

    private void writeData() {
        descriptionTextArea.setFont(Font.font("Verdana", 12));
        descriptionTextArea.setText(reszletes.getComment());
        receiveDateLabel.setText(reszletes.getFormattedDate());

        olvasva = reszletes.isRead();
        readButton.setDisable(olvasva);
    }

    private void save() {
        if (!reszletes.isRead() == olvasva) {
            reszletes.setRead(olvasva);

            try {
                Visszajelzes olvasottLeiras = VisszajelzesApi.updateReadFeedback(reszletes);
                if (olvasottLeiras != null) {
                    alert("Sikeres mentés!");
                    ((Stage) mainAnchor.getScene().getWindow()).close();
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
        if (mentve) {
            ((Stage) mainAnchor.getScene().getWindow()).close();
        }
        if (!mentve) {
            if (!confirmation("A módosítások el fognak veszni! Szeretne menteni?")) {
                ((Stage) mainAnchor.getScene().getWindow()).close();
                return;
            }
            save();
        }

    }

    @FXML
    public void onReadClick(ActionEvent actionEvent) {
        olvasva = true;
        mentve = false;
        readButton.setDisable(olvasva);
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
}