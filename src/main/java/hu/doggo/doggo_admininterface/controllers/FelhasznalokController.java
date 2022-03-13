package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.api.FelhasznaloApi;
import hu.doggo.doggo_admininterface.api.HelyszinApi;
import hu.doggo.doggo_admininterface.api.VisszajelzesApi;
import hu.doggo.doggo_admininterface.classes.Felhasznalo;
import hu.doggo.doggo_admininterface.classes.HelyszinErtekeles;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

public class FelhasznalokController extends Controller {
    @FXML
    private TableColumn<Felhasznalo, String> created_atCol;
    @FXML
    private TableColumn<Felhasznalo, String> usernameCol;
    @FXML
    private TableColumn<Felhasznalo, String> permissionCol;
    @FXML
    private TableColumn<Felhasznalo, String> emailCol;
    @FXML
    private TableView<Felhasznalo> felhasznalokTableView;
    @FXML
    private TextField textFieldFelhKereses;

    private ObservableList<Felhasznalo> felhasznaloLista = FXCollections.observableArrayList();
    private Timer timer = new Timer();

    public void initialize() {
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        created_atCol.setCellValueFactory(new PropertyValueFactory<>("formattedDate"));
        permissionCol.setCellValueFactory(new PropertyValueFactory<>("formattedPermission"));

        felhasznaloListaFeltoltes();
        kereses();
    }

    private void kereses() {
        FilteredList<Felhasznalo> filteredList = new FilteredList<>(felhasznaloLista, b -> true);
        textFieldFelhKereses.textProperty().addListener((observable, oldValue, newValue ) -> {
            filteredList.setPredicate(felhasznalo -> {
                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }

                felhasznalokTableView.getSelectionModel().select(null);
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
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    felhasznaloLista.clear();
                    felhasznaloLista.addAll(FelhasznaloApi.getFelhasznalok());
                } catch (IOException e) {
                    hibaKiir(e);
                }
            }
        };
        timer.schedule(timerTask, 1);
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