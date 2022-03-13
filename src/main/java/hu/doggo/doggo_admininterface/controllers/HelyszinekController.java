package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.api.FelhasznaloApi;
import hu.doggo.doggo_admininterface.api.HelyszinApi;
import hu.doggo.doggo_admininterface.classes.Helyszin;
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
import java.util.Timer;
import java.util.TimerTask;

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
    private TableColumn<Helyszin, String> descriptionCol;
    @FXML
    private TableColumn<Helyszin, String> statusCol;
    @FXML
    private TextField textFieldHelyszinKereses;
    @FXML
    private Button btnTorles;
    @FXML
    private ChoiceBox<String> choiceBoxHelyszin;

    private ObservableList<Helyszin> helyszinLista = FXCollections.observableArrayList();
    private Timer timer = new Timer();

    public void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        latCol.setCellValueFactory(new PropertyValueFactory<>("lat"));
        lngCol.setCellValueFactory(new PropertyValueFactory<>("lng"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("formattedAllowed"));

        megkotesKivalasztas();
        helyszinListaFeltoltes();
        kereses();
    }

    private void kereses() {
        FilteredList<Helyszin> filteredList = new FilteredList<>(helyszinLista, b -> true);
        textFieldHelyszinKereses.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(helyszin -> {
                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }

                btnTorles.setDisable(true);
                helyszinekTableView.getSelectionModel().select(null);
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

        sortedList.comparatorProperty().bind(helyszinekTableView.comparatorProperty());

        helyszinekTableView.setItems(sortedList);
    }

    private void helyszinListaFeltoltes() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    if (choiceBoxHelyszin.getSelectionModel().getSelectedItem().equals("összes")) {
                        helyszinLista.clear();
                        helyszinLista.addAll(HelyszinApi.getHelyszin());
                    } else if (choiceBoxHelyszin.getSelectionModel().getSelectedItem().equals("engedélyezésre vár")) {
                        helyszinLista.clear();
                        helyszinLista.addAll(HelyszinApi.getNemEngedelyezettHelyszin());
                    } else {
                        helyszinLista.clear();
                        helyszinLista.addAll(HelyszinApi.getEngedelyezettHelyszin());
                    }
                } catch (IOException e) {
                    hibaKiir(e);
                }
            }
        };
        timer.schedule(timerTask, 1);
    }

    private void megkotesKivalasztas() {
        choiceBoxHelyszin.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            btnTorles.setDisable(true);
            try {
                if (newValue.equals("engedélyezésre vár")) {
                    helyszinLista.clear();
                    helyszinLista.addAll(HelyszinApi.getNemEngedelyezettHelyszin());
                } else if (newValue.equals("engedélyezve")) {
                    helyszinLista.clear();
                    helyszinLista.addAll(HelyszinApi.getEngedelyezettHelyszin());
                } else {
                    helyszinListaFeltoltes();
                }
            } catch (IOException e) {
                hibaKiir(e);
            }
        });
    }

    @FXML
    public void onTorlesClick(ActionEvent actionEvent) {
        Helyszin torlendo = helyszinekTableView.getSelectionModel().getSelectedItem();
        if (!megerosites("Biztos törölni szeretné a(z) " + torlendo.getName())) {
            return;
        }
        try {
            boolean siker = HelyszinApi.deleteHelyszin(torlendo.getId());
            if (siker) {
                alert("Sikeres törlés!");
                helyszinListaFeltoltes();
                btnTorles.setDisable(true);
            } else {
                alert("Sikertelen törlés!");
            }
        } catch (IOException e) {
            hibaKiir(e);
        }
    }

    @FXML
    public void onHelyszinDoubleClick(MouseEvent mouseEvent) {
        int selectedIndex = helyszinekTableView.getSelectionModel().getSelectedIndex();
        Helyszin reszletesHelyszin = helyszinekTableView.getSelectionModel().getSelectedItem();
        if (!(selectedIndex == -1) && mouseEvent.getClickCount() == 2) {
            try {
                HelyszinMuveletekController reszletes = (HelyszinMuveletekController) ujAblak("fxml/helyszin-muveletek-view.fxml", "Helyszín", 365, 400);
                reszletes.setReszletes(reszletesHelyszin);
                reszletes.getStage().setOnHiding(event -> helyszinekTableView.refresh());
                reszletes.getStage().show();
            } catch (Exception e) {
                hibaKiir(e);
            }
        } else if (!(selectedIndex == -1)) {
            btnTorles.setDisable(false);
        }
    }
}