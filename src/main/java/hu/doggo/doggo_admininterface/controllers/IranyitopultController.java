package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.api.FelhasznaloApi;
import hu.doggo.doggo_admininterface.api.HelyszinApi;
import hu.doggo.doggo_admininterface.api.VisszajelzesApi;
import hu.doggo.doggo_admininterface.classes.HelyszinErtekeles;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class IranyitopultController extends Controller {
    @FXML
    private Label bestRatingLabel;
    @FXML
    private TextArea bestLocationTextArea;
    @FXML
    private Label worstRatingLabel;
    @FXML
    private TextArea worstLocationTextArea;
    @FXML
    private Label allUserLabel;
    @FXML
    private Label adminUserLabel;
    @FXML
    private Label bannedUserLabel;
    @FXML
    private Label newLocationLabel;
    @FXML
    private Label allowedLocationLabel;
    @FXML
    private Label readFeedbackLabel;
    @FXML
    private Label newFeedbackLabel;

    private HelyszinErtekeles legjobb;
    private HelyszinErtekeles legrosszabb;
    private Timer timer = new Timer();
    private int osszFelh;
    private int adminFelh;
    private int kitiltottFelh;
    private int ujHely;
    private int engedelyezettHely;
    private int ujVisszajelzes;
    private int olvasottVisszajelzes;
    @FXML
    private PieChart locationsPieChart;
    @FXML
    private PieChart feedbacksPieChart;


    public void initialize() {
        Platform.runLater(() -> {
            try {
                ujHely = HelyszinApi.getNewCount();
                engedelyezettHely = HelyszinApi.getAllowedCount();
                ObservableList<PieChart.Data> locationPieChart = FXCollections.observableArrayList(
                        new PieChart.Data("új", ujHely),
                        new PieChart.Data("engedélyezett", engedelyezettHely)
                );
                locationsPieChart.setData(locationPieChart);
                locationsPieChart.setStartAngle(70);

                ujVisszajelzes = VisszajelzesApi.getNewCount();
                olvasottVisszajelzes = VisszajelzesApi.getReadCount();
                ObservableList<PieChart.Data> feedbackPieChart = FXCollections.observableArrayList(
                        new PieChart.Data("új", ujVisszajelzes),
                        new PieChart.Data("olvasott", olvasottVisszajelzes)
                );
                feedbacksPieChart.setData(feedbackPieChart);
                feedbacksPieChart.setStartAngle(70);
            } catch (IOException e) {
                error(e);
            }
        });

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    legjobb = HelyszinApi.getBestRating();

                    osszFelh = FelhasznaloApi.getUsersCount();
                } catch (IOException e) {
                    error(e);
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        bestRatingLabel.setText(legjobb.getAtlag() + "");
                        bestLocationTextArea.setText(legjobb.getName());

                        allUserLabel.setText(osszFelh + "");

                        newLocationLabel.setText(ujHely + "");

                        newFeedbackLabel.setText(ujVisszajelzes + "");
                    }
                });
            }
        };
        timer.schedule(timerTask, 1);

        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {
                try {
                    legrosszabb = HelyszinApi.getWorstRating();

                    kitiltottFelh = FelhasznaloApi.getBannedCount();
                    adminFelh = FelhasznaloApi.getAdminCount();
                } catch (IOException e) {
                    error(e);
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        worstRatingLabel.setText(legrosszabb.getAtlag() + "");
                        worstLocationTextArea.setText(legrosszabb.getName());

                        bannedUserLabel.setText(kitiltottFelh + "");
                        adminUserLabel.setText(adminFelh + "");

                        allowedLocationLabel.setText(engedelyezettHely + "");

                        readFeedbackLabel.setText(olvasottVisszajelzes + "");
                    }
                });
            }
        };
        timer.schedule(timerTask2, 1);
    }
}
