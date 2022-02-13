package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.api.Api;
import hu.doggo.doggo_admininterface.classes.Felhasznalo;
import hu.doggo.doggo_admininterface.api.FelhasznaloApi;
import javafx.beans.binding.FloatExpression;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FelhasznalokController extends Controller {

    @FXML
    private TableColumn<Felhasznalo, Date> created_atCol;
    @FXML
    private TableColumn<Felhasznalo, String> usernameCol;
    @FXML
    private TableColumn<Felhasznalo, Integer> permissionCol;
    @FXML
    private TableColumn<Felhasznalo, String> emailCol;
    @FXML
    private TableView<Felhasznalo> felhasznalokTableView;
    @FXML
    private TextField textFieldFelhKereses;

    private ObservableList<Felhasznalo> felhasznaloLista = FXCollections.observableArrayList();


    public void initialize() {
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        created_atCol.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        permissionCol.setCellValueFactory(new PropertyValueFactory<>("permission"));

        felhasznaloListaFeltoltes();

        FilteredList<Felhasznalo> filteredList = new FilteredList<>(felhasznaloLista, b -> true);
        textFieldFelhKereses.textProperty().addListener((observable, oldValue, newValue ) -> {
            filteredList.setPredicate(felhasznalo -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }

                String kereses = newValue.toLowerCase();

                if (felhasznalo.getUsername().toLowerCase().indexOf(kereses) > -1) {
                    return true;
                } else if (felhasznalo.getEmail().toLowerCase().indexOf(kereses) > -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Felhasznalo> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(felhasznalokTableView.comparatorProperty());

        felhasznalokTableView.setItems(sortedList);
    }

    private void felhasznaloListaFeltoltes() {
        try {
            felhasznaloLista.addAll(FelhasznaloApi.getFelhasznalok());
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
}