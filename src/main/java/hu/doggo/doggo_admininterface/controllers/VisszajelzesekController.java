package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.api.HelyszinApi;
import hu.doggo.doggo_admininterface.api.VisszajelzesApi;
import hu.doggo.doggo_admininterface.classes.Visszajelzes;
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

public class VisszajelzesekController extends Controller {
    @FXML
    private TableColumn<Visszajelzes, String> commentCol;
    @FXML
    private TableView<Visszajelzes> visszajelzesekTableView;
    @FXML
    private TextField textFieldVisszajelzesKereses;
    @FXML
    private TableColumn<Visszajelzes, String> created_atCol;
    @FXML
    private TableColumn<Visszajelzes, String> statusCol;
    @FXML
    private Button btnTorles;
    @FXML
    private ChoiceBox<String> choiceBoxVisszajelzes;

    private ObservableList<Visszajelzes> visszajelzesLista = FXCollections.observableArrayList();
    private Timer timer = new Timer();

    public void initialize() {
        commentCol.setCellValueFactory(new PropertyValueFactory<>("comment"));
        created_atCol.setCellValueFactory(new PropertyValueFactory<>("formattedDate"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("formattedRead"));

        visszajelzesekListaFeltoltese();
        kereses();
        megkotesKivalasztas();
    }

    private void kereses() {
        FilteredList<Visszajelzes> filteredList = new FilteredList<>(visszajelzesLista, b -> true);
        textFieldVisszajelzesKereses.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(visszajelzes -> {
                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }

                btnTorles.setDisable(true);
                visszajelzesekTableView.getSelectionModel().select(null);
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
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    if (choiceBoxVisszajelzes.getSelectionModel().getSelectedItem().equals("összes")) {
                        visszajelzesLista.clear();
                        visszajelzesLista.addAll(VisszajelzesApi.getVisszajelzesek());
                    } else if (choiceBoxVisszajelzes.getSelectionModel().getSelectedItem().equals("olvasatlan")) {
                        visszajelzesLista.clear();
                        visszajelzesLista.addAll(VisszajelzesApi.getOlvasatlan());
                    } else if (choiceBoxVisszajelzes.getSelectionModel().getSelectedItem().equals("olvasott")) {
                        visszajelzesLista.clear();
                        visszajelzesLista.addAll(VisszajelzesApi.getOlvasott());
                    }
                } catch (IOException e) {
                    hibaKiir(e);
                }
            }
        };
        timer.schedule(timerTask, 1);
    }

    private void megkotesKivalasztas() {
        choiceBoxVisszajelzes.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            btnTorles.setDisable(true);
            try {
                if (newValue.equals("olvasatlan")) {
                    visszajelzesLista.clear();
                    visszajelzesLista.addAll(VisszajelzesApi.getOlvasatlan());
                } else if (newValue.equals("olvasott")) {
                    visszajelzesLista.clear();
                    visszajelzesLista.addAll(VisszajelzesApi.getOlvasott());
                } else {
                    visszajelzesekListaFeltoltese();
                }
            } catch (IOException e) {
                hibaKiir(e);
            }
        });
    }

    @FXML
    public void onTorlesClick(ActionEvent actionEvent) {
        Visszajelzes torlendo = visszajelzesekTableView.getSelectionModel().getSelectedItem();
        if (!megerosites("Biztos törölni szeretné?")) {
            return;
        }
        try {
            boolean siker = VisszajelzesApi.deleteVisszajelzes(torlendo.getId());
            if (siker) {
                alert("Sikeres törlés!");
                btnTorles.setDisable(true);
                visszajelzesekListaFeltoltese();
            } else {
                alert("Sikertelen törlés!");
            }
        } catch (IOException e) {
            hibaKiir(e);
        }
    }

    @FXML
    public void onVisszajelzesDoubleClick(MouseEvent mouseEvent) {
        int selectedIndex = visszajelzesekTableView.getSelectionModel().getSelectedIndex();
        Visszajelzes visszajelzesLeiras = visszajelzesekTableView.getSelectionModel().getSelectedItem();
        if (!(selectedIndex == -1) && mouseEvent.getClickCount() == 2) {
            try {
                VisszajelzesMuveletekController leiras = (VisszajelzesMuveletekController) ujAblak("fxml/visszajelzes-muveletek-view.fxml", "Visszajelzés", 365, 400);
                leiras.setReszletes(visszajelzesLeiras);
                leiras.getStage().setOnHiding(event -> visszajelzesekTableView.refresh());
                leiras.getStage().show();
            } catch (Exception e) {
                hibaKiir(e);
            }
        } else if (!(selectedIndex == -1)){
            btnTorles.setDisable(false);
        }
    }


}