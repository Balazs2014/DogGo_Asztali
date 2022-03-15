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
import java.util.Timer;
import java.util.TimerTask;

public class FelhasznalokController extends Controller {
    @FXML
    private TextField userSearchField;
    @FXML
    private TableView<Felhasznalo> usersTableView;
    @FXML
    private TableColumn<Felhasznalo, String> usernameCol;
    @FXML
    private TableColumn<Felhasznalo, String> emailCol;
    @FXML
    private TableColumn<Felhasznalo, String> created_atCol;
    @FXML
    private TableColumn<Felhasznalo, String> permissionCol;

    private ObservableList<Felhasznalo> userList = FXCollections.observableArrayList();
    private Timer timer = new Timer();


    public void initialize() {
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        created_atCol.setCellValueFactory(new PropertyValueFactory<>("formattedDate"));
        permissionCol.setCellValueFactory(new PropertyValueFactory<>("formattedPermission"));

        userListUpload();
        search();
    }

    private void userListUpload() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    userList.clear();
                    userList.addAll(FelhasznaloApi.getUsers());
                } catch (IOException e) {
                    error(e);
                }
            }
        };
        timer.schedule(timerTask, 1);
    }

    private void search() {
        FilteredList<Felhasznalo> filteredList = new FilteredList<>(userList, b -> true);
        userSearchField.textProperty().addListener((observable, oldValue, newValue ) -> {
            filteredList.setPredicate(felhasznalo -> {
                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }

                usersTableView.getSelectionModel().select(null);
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

        sortedList.comparatorProperty().bind(usersTableView.comparatorProperty());

        usersTableView.setItems(sortedList);
    }

    @FXML
    public void onUserDoubleClick(MouseEvent mouseEvent) {
        int selectedIndex = usersTableView.getSelectionModel().getSelectedIndex();
        Felhasznalo reszletesFelh = usersTableView.getSelectionModel().getSelectedItem();
        if (!(selectedIndex == -1) && mouseEvent.getClickCount() == 2) {
            try {
                FelhasznalokReszletesController reszletes = (FelhasznalokReszletesController) newWindow("fxml/felhasznalok-reszletes-view.fxml", "Felhasznalo kezelése", 650, 769);
                reszletes.setReszletes(reszletesFelh);
                reszletes.getStage().setOnHiding(event -> usersTableView.refresh());
                reszletes.getStage().show();
            } catch (Exception e) {
                error(e);
            }
        }
    }
}