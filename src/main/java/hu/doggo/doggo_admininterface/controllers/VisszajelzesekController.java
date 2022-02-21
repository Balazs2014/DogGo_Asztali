package hu.doggo.doggo_admininterface.controllers;

import com.jfoenix.controls.JFXButton;
import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.api.VisszajelzesApi;
import hu.doggo.doggo_admininterface.classes.Visszajelzes;
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
import java.util.Date;

public class VisszajelzesekController extends Controller {
    @FXML
    private TableColumn<Visszajelzes, String> commentCol;
    @FXML
    private TableView<Visszajelzes> visszajelzesekTableView;
    @FXML
    private TableColumn<Visszajelzes, Boolean> readCol;
    @FXML
    private TextField textFieldVisszajelzesKereses;
    @FXML
    private TableColumn<Visszajelzes, Date> created_atCol;
    @FXML
    private JFXButton btnModositas;
    @FXML
    private JFXButton btnTorles;

    private ObservableList<Visszajelzes> visszajelzesLista = FXCollections.observableArrayList();

    public void initialize() {
        commentCol.setCellValueFactory(new PropertyValueFactory<>("comment"));
        created_atCol.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        readCol.setCellValueFactory(new PropertyValueFactory<>("read"));

        visszajelzesekListaFeltoltese();

        FilteredList<Visszajelzes> filteredList = new FilteredList<>(visszajelzesLista, b -> true);
        textFieldVisszajelzesKereses.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(visszajelzes -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }

                String kereses = newValue.toLowerCase();

                if (visszajelzes.getComment().toLowerCase().contains(kereses)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Visszajelzes> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(visszajelzesekTableView.comparatorProperty());

        visszajelzesekTableView.setItems(sortedList);
    }

    private void visszajelzesekListaFeltoltese() {
        try {
            visszajelzesLista.clear();
            visszajelzesLista.addAll(VisszajelzesApi.getVisszajelzesek());
        } catch (IOException e) {
            hibaKiir(e);
        }
    }

    @FXML
    public void onTorlesClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onModositasClick(ActionEvent actionEvent) {
    }
}
