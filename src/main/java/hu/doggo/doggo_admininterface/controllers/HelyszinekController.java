package hu.doggo.doggo_admininterface.controllers;

import com.jfoenix.controls.JFXButton;
import hu.doggo.doggo_admininterface.Controller;
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
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class HelyszinekController extends Controller {

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
    @FXML
    private TextField inputHelyszinNev;
    @FXML
    private JFXButton btnModositas;
    @FXML
    private JFXButton btnTorles;

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

                if (helyszin.getName().toLowerCase().contains(kereses)) {
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
        btnModositas.setDisable(true);
        btnTorles.setDisable(true);
        try {
            helyszinLista.clear();
            helyszinLista.addAll(HelyszinApi.getHelyszin());
            helyszinekTableView.getItems().clear();
            for (Helyszin helyszin : helyszinLista) {
                helyszinekTableView.getItems().add(helyszin);
            }
        } catch (IOException e) {
            hibaKiir(e);
        }
    }

    @FXML
    public void onTorlesClick(ActionEvent actionEvent) {
        Helyszin torlendo = helyszinekTableView.getSelectionModel().getSelectedItem();
        if (!confirm("Biztos törölni szeretné a(z) " + torlendo.getName())) {
            return;
        }
        try {
            boolean siker = HelyszinApi.deleteHelyszin(torlendo.getId());
            if (siker) {
                alert("Sikeres törlés!");
                inputHelyszinNev.setText("");
            } else {
                alert("Sikertelen törlés!");
            }
            helyszinListaFeltoltes();
        } catch (IOException e) {
            hibaKiir(e);
        }
    }

    @FXML
    public void onModositasClick(ActionEvent actionEvent) {
        String helyszinNev = inputHelyszinNev.getText().trim();

        if (helyszinNev.isEmpty() || helyszinNev.isBlank()) {
            alert("Helyszín nevének megadása kötelező!");
            return;
        }

        Helyszin modositando = (Helyszin) helyszinekTableView.getSelectionModel().getSelectedItem();

        modositando.setName(helyszinNev);

        try {
            helyszinLista.clear();
            Helyszin helyszinModositas = HelyszinApi.updateHelyszin(modositando);
            if (helyszinModositas != null) {
                alertWait("Név módosítva!");
            } else {
                alertWait("Sikertelen módosítás!");
            }
            helyszinListaFeltoltes();
        } catch (IOException e) {
            hibaKiir(e);
        }
    }

    @FXML
    public void onHelyszinClick(MouseEvent event) {
        int selectedIndex = helyszinekTableView.getSelectionModel().getSelectedIndex();
        Helyszin helyszinModositas = helyszinekTableView.getSelectionModel().getSelectedItem();
        if (!(selectedIndex == -1)) {
            inputHelyszinNev.setText(helyszinModositas.getName());
            btnModositas.setDisable(false);
            btnTorles.setDisable(false);
        }
    }
}
