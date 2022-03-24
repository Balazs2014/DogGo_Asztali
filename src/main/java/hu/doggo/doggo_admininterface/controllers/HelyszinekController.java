package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.api.HelyszinApi;
import hu.doggo.doggo_admininterface.classes.Helyszin;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class HelyszinekController extends Controller {
    @FXML
    private TextField locationSearchField;
    @FXML
    private ChoiceBox<String> locationStatusChoiceBox;
    @FXML
    private Button locationDeleteButton;
    @FXML
    private TableView<Helyszin> locationsTableView;
    @FXML
    private TableColumn<Helyszin, String> nameCol;
    @FXML
    private TableColumn<Helyszin, String> descriptionCol;
    @FXML
    private TableColumn<Helyszin, Double> latCol;
    @FXML
    private TableColumn<Helyszin, Double> lngCol;
    @FXML
    private TableColumn<Helyszin, String> statusCol;

    private ObservableList<Helyszin> locationList = FXCollections.observableArrayList();


    public void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        latCol.setCellValueFactory(new PropertyValueFactory<>("lat"));
        lngCol.setCellValueFactory(new PropertyValueFactory<>("lng"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("formattedStatus"));

        filter();
        locationListUpload();
        search();
    }

    private void locationListUpload() {
        Platform.runLater(() -> {
            try {
                locationList.clear();
                locationList.addAll(HelyszinApi.getLocations());
            } catch (IOException e) {
                error(e);
            }
        });
    }

    private void filter() {
        locationStatusChoiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            locationDeleteButton.setDisable(true);
            try {
                if (newValue.equals("új")) {
                    locationList.clear();
                    locationList.addAll(HelyszinApi.getNewLocations());
                } else if (newValue.equals("engedélyezve")) {
                    locationList.clear();
                    locationList.addAll(HelyszinApi.getAllowedLocations());
                } else {
                    locationListUpload();
                }
            } catch (IOException e) {
                error(e);
            }
        });
    }

    private void search() {
        FilteredList<Helyszin> filteredList = new FilteredList<>(locationList, b -> true);
        locationSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(helyszin -> {
                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }

                locationDeleteButton.setDisable(true);
                locationsTableView.getSelectionModel().select(null);
                String kereses = newValue.toLowerCase();

                if (helyszin.getName().toLowerCase().contains(kereses)) {
                    return true;
                } else if (helyszin.getDescription().toLowerCase().contains(kereses)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Helyszin> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(locationsTableView.comparatorProperty());

        locationsTableView.setItems(sortedList);
    }

    @FXML
    public void onLocationDeleteClick(ActionEvent actionEvent) {
        Helyszin torlendo = locationsTableView.getSelectionModel().getSelectedItem();
        if (!confirmation("Biztos törölni szeretné a(z) " + torlendo.getName() + " nevű helyszínt?")) {
            return;
        }
        try {
            boolean siker = HelyszinApi.deleteLocation(torlendo.getId());
            if (siker) {
                alert("Sikeres törlés!");
                locationListUpload();
                locationDeleteButton.setDisable(true);
            } else {
                alert("Sikertelen törlés!");
            }
        } catch (IOException e) {
            error(e);
        }
    }

    @FXML
    public void onLocationDoubleClick(MouseEvent mouseEvent) {
        int selectedIndex = locationsTableView.getSelectionModel().getSelectedIndex();
        Helyszin reszletesHelyszin = locationsTableView.getSelectionModel().getSelectedItem();
        if (!(selectedIndex == -1) && mouseEvent.getClickCount() == 2) {
            try {
                HelyszinMuveletekController reszletes = (HelyszinMuveletekController) newWindow("fxml/helyszin-muveletek-view.fxml", "Helyszín", 365, 400);
                reszletes.setReszletes(reszletesHelyszin);
                reszletes.getStage().setOnHiding(event -> locationsTableView.refresh());
                reszletes.getStage().show();
            } catch (Exception e) {
                error(e);
            }
        } else if (!(selectedIndex == -1)) {
            locationDeleteButton.setDisable(false);
        }
    }
}