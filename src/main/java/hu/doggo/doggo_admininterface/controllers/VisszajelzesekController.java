package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.api.VisszajelzesApi;
import hu.doggo.doggo_admininterface.classes.Visszajelzes;
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

public class VisszajelzesekController extends Controller {
    @FXML
    private TextField feedbackSearchField;
    @FXML
    private ChoiceBox<String> feedbackStatusChoiceBox;
    @FXML
    private Button feedbackDeleteButton;
    @FXML
    private TableView<Visszajelzes> feedbacksTableView;
    @FXML
    private TableColumn<Visszajelzes, String> commentCol;
    @FXML
    private TableColumn<Visszajelzes, String> created_atCol;
    @FXML
    private TableColumn<Visszajelzes, String> statusCol;

    private ObservableList<Visszajelzes> feedbackList = FXCollections.observableArrayList();


    public void initialize() {
        commentCol.setCellValueFactory(new PropertyValueFactory<>("comment"));
        created_atCol.setCellValueFactory(new PropertyValueFactory<>("formattedDate"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("formattedStatus"));

        feedbackListUpload();
        search();
        filter();
    }

    private void search() {
        FilteredList<Visszajelzes> filteredList = new FilteredList<>(feedbackList, b -> true);
        feedbackSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(visszajelzes -> {
                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }

                feedbackDeleteButton.setDisable(true);
                feedbacksTableView.getSelectionModel().select(null);
                String kereses = newValue.toLowerCase();

                if (visszajelzes.getComment().toLowerCase().contains(kereses)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Visszajelzes> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(feedbacksTableView.comparatorProperty());

        feedbacksTableView.setItems(sortedList);
    }

    private void feedbackListUpload() {
        Platform.runLater(() -> {
            try {
                feedbackList.clear();
                feedbackList.addAll(VisszajelzesApi.getFeedbacks());
                feedbackStatusChoiceBox.setValue("??sszes");
            } catch (IOException e) {
                error(e);
            }
        });
    }

    private void filter() {
        feedbackStatusChoiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            feedbackDeleteButton.setDisable(true);
            try {
                if (newValue.equals("??j")) {
                    feedbackList.clear();
                    feedbackList.addAll(VisszajelzesApi.getNewFeedbacks());
                } else if (newValue.equals("olvasott")) {
                    feedbackList.clear();
                    feedbackList.addAll(VisszajelzesApi.getReadFeedbacks());
                } else {
                    feedbackListUpload();
                }
            } catch (IOException e) {
                error(e);
            }
        });
    }

    @FXML
    public void onFeedbackDeleteClick(ActionEvent actionEvent) {
        Visszajelzes torlendo = feedbacksTableView.getSelectionModel().getSelectedItem();
        if (!confirmation("Biztos t??r??lni szeretn???")) {
            return;
        }
        try {
            boolean siker = VisszajelzesApi.deleteFeedback(torlendo.getId());
            if (siker) {
                alert("Sikeres t??rl??s!");
                feedbackDeleteButton.setDisable(true);
                feedbackListUpload();
            } else {
                alert("Sikertelen t??rl??s!");
            }
        } catch (IOException e) {
            error(e);
        }
    }

    @FXML
    public void onFeedbackDoubleClick(MouseEvent mouseEvent) {
        int selectedIndex = feedbacksTableView.getSelectionModel().getSelectedIndex();
        Visszajelzes visszajelzesLeiras = feedbacksTableView.getSelectionModel().getSelectedItem();
        if (!(selectedIndex == -1) && mouseEvent.getClickCount() == 2) {
            try {
                VisszajelzesMuveletekController leiras = (VisszajelzesMuveletekController) newWindow("fxml/visszajelzes-muveletek-view.fxml", "Visszajelz??s", 365, 400);
                leiras.setReszletes(visszajelzesLeiras);
                leiras.getStage().setOnHiding(event -> feedbacksTableView.refresh());
                leiras.getStage().show();
            } catch (Exception e) {
                error(e);
            }
        } else if (!(selectedIndex == -1)) {
            feedbackDeleteButton.setDisable(false);
        }
    }
}