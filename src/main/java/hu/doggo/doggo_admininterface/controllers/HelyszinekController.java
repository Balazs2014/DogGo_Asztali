package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.api.Api;
import hu.doggo.doggo_admininterface.api.HelyszinApi;
import hu.doggo.doggo_admininterface.classes.Helyszin;
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

import java.io.IOException;

public class HelyszinekController {

    @FXML
    private TableColumn<Helyszin, Double> lngCol;
    @FXML
    private TableView<Helyszin> helyszinekTableView;
    @FXML
    private TableColumn<Helyszin, String> nameCol;
    @FXML
    private TableColumn<Helyszin, Double> latCol;
    @FXML
    private TextField textFieldHelyszinKereses;

    private ObservableList<Helyszin> helyszinLista = FXCollections.observableArrayList();

    public void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        latCol.setCellValueFactory(new PropertyValueFactory<>("lat"));
        lngCol.setCellValueFactory(new PropertyValueFactory<>("lng"));

        helyszinListaFeltoltes();

        FilteredList<Helyszin> filteredList = new FilteredList<>(helyszinLista, b -> true);
        textFieldHelyszinKereses.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(helyszin -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }

                String kereses = newValue.toLowerCase();

                if (helyszin.getName().toLowerCase().indexOf(kereses) > -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Helyszin> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(helyszinekTableView.comparatorProperty());

        helyszinekTableView.setItems(sortedList);

    }

    private void helyszinListaFeltoltes() {
        try {
            helyszinLista.addAll(HelyszinApi.getHelyszin());
            helyszinekTableView.getItems().clear();
            for (Helyszin helyszin : helyszinLista) {
                helyszinekTableView.getItems().add(helyszin);
            }

        } catch (IOException e) {
            e.getMessage();
        }
    }
}
