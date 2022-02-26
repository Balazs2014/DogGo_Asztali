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
    private TableColumn<Helyszin, Boolean> allowedCol;
    @FXML
    private TextField textFieldHelyszinKereses;
    @FXML
    private TextField inputHelyszinNev;
    @FXML
    private JFXButton btnModositas;
    @FXML
    private JFXButton btnTorles;
    @FXML
    private JFXButton btnEngedelyezes;

    private ObservableList<Helyszin> helyszinLista = FXCollections.observableArrayList();

    public void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        latCol.setCellValueFactory(new PropertyValueFactory<>("lat"));
        lngCol.setCellValueFactory(new PropertyValueFactory<>("lng"));
        allowedCol.setCellValueFactory(new PropertyValueFactory<>("allowed"));

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
        try {
            helyszinLista.clear();
            helyszinLista.addAll(HelyszinApi.getHelyszin());
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
                alertWait("Sikeres törlés!");
                inputHelyszinNev.setText("");
                btnModositas.setDisable(true);
                btnTorles.setDisable(true);
                helyszinListaFeltoltes();
            } else {
                alert("Sikertelen törlés!");
            }
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
            Helyszin helyszinModositas = HelyszinApi.updateHelyszin(modositando);
            if (helyszinModositas != null) {
                alertWait("Név módosítva!");
                inputHelyszinNev.setText("");
                helyszinekTableView.refresh();
                helyszinekTableView.getSelectionModel().select(null);
                btnModositas.setDisable(true);
                btnTorles.setDisable(true);
            } else {
                alertWait("Sikertelen módosítás!");
            }
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
            if (!helyszinModositas.isAllowed()) {
                btnEngedelyezes.setDisable(false);
            } else {
                btnEngedelyezes.setDisable(true);
            }
        }
    }

    @FXML
    public void onEngedelyezesClick(ActionEvent actionEvent) {
        Helyszin modositando = (Helyszin) helyszinekTableView.getSelectionModel().getSelectedItem();

        modositando.setAllowed(true);

        try {
            Helyszin helyszinModositas = HelyszinApi.updateHelyszin(modositando);
            if (helyszinModositas != null) {
                alertWait("Sikeres engedélyezés!");
                helyszinekTableView.refresh();
                btnEngedelyezes.setDisable(true);
            } else {
                alertWait("Sikertelen engedélyezés!");
            }
        } catch (IOException e) {
            hibaKiir(e);
        }
    }
}
