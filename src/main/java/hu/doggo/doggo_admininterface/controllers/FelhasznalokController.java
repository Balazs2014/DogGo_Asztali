package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.api.FelhasznaloApi;
import hu.doggo.doggo_admininterface.classes.Felhasznalo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class FelhasznalokController extends Controller {

    @FXML
    private TableColumn<Felhasznalo, String> created_atCol;
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
        created_atCol.setCellValueFactory(new PropertyValueFactory<>("formattedDate"));
        permissionCol.setCellValueFactory(new PropertyValueFactory<>("permission"));

        felhasznaloListaFeltoltes();

        FilteredList<Felhasznalo> filteredList = new FilteredList<>(felhasznaloLista, b -> true);
        textFieldFelhKereses.textProperty().addListener((observable, oldValue, newValue ) -> {
            filteredList.setPredicate(felhasznalo -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }

                String kereses = newValue.toLowerCase();

                if (felhasznalo.getUsername().toLowerCase().contains(kereses)) {
                    return true;
                } else if (felhasznalo.getEmail().toLowerCase().contains(kereses)) {
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
            felhasznaloLista.clear();
            felhasznaloLista.addAll(FelhasznaloApi.getFelhasznalok());
        } catch (IOException e) {
            hibaKiir(e);
        }
    }

    @FXML
    public void onFelhasznaloDoubleClick(MouseEvent mouseEvent) {
        int selectedIndex = felhasznalokTableView.getSelectionModel().getSelectedIndex();
        Felhasznalo reszletesFelh = felhasznalokTableView.getSelectionModel().getSelectedItem();
        if (!(selectedIndex == -1) && mouseEvent.getClickCount() == 2) {
            try {
                FelhasznalokReszletesController reszletes = (FelhasznalokReszletesController) ujAblak("fxml/felhasznalok-reszletes-view.fxml", "Felhasznalo kezelése", 650, 769);
                reszletes.setReszletes(reszletesFelh);
                reszletes.getStage().setOnHiding(event -> felhasznalokTableView.refresh());
                reszletes.getStage().show();
            } catch (Exception e) {
                hibaKiir(e);
            }
        }
    }
}