package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.classes.Felhasznalo;
import hu.doggo.doggo_admininterface.api.FelhasznaloApi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;

public class FelhasznalokController extends Controller {

    @FXML
    private TableColumn created_atCol;
    @FXML
    private TableColumn usernameCol;
    @FXML
    private TableColumn permissionCol;
    @FXML
    private TableColumn emailCol;
    @FXML
    private TableView felhasznalokTableView;
    @FXML
    private TextField textFieldFelhKereses;

    public void initialize() {
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        created_atCol.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        permissionCol.setCellValueFactory(new PropertyValueFactory<>("permission"));

        felhasznaloListaFeltoltes();
    }

    private void felhasznaloListaFeltoltes() {
        try {
            List<Felhasznalo> felhasznaloLista = FelhasznaloApi.getFelhasznalok();
            felhasznalokTableView.getItems().clear();
            for (Felhasznalo felhasznalo : felhasznaloLista) {
                felhasznalokTableView.getItems().add(felhasznalo);
            }
        } catch (IOException e) {
            hibaKiir(e);
        }
    }

    @FXML
    public void onFelhasznaloDoubleClick(MouseEvent mouseEvent) {
        int selectedIndex = felhasznalokTableView.getSelectionModel().getSelectedIndex();
        if (!(selectedIndex == -1) && mouseEvent.getClickCount() == 2) {
            try {
                Controller modositas = ujAblak("felhasznalok-reszletes-view.fxml", "Felhasznalo kezelése", 650, 750);
                modositas.getStage().setOnCloseRequest(event -> felhasznaloListaFeltoltes());
                modositas.getStage().show();
            } catch (Exception e) {
                hibaKiir(e);
            }
        }
    }

    @FXML
    public void onFelhKeresesClick(ActionEvent actionEvent) {
    }
}