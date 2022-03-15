package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.api.FelhasznaloApi;
import hu.doggo.doggo_admininterface.api.HelyszinApi;
import hu.doggo.doggo_admininterface.api.VisszajelzesApi;
import hu.doggo.doggo_admininterface.classes.HelyszinErtekeles;
import javafx.application.Platform;
import javafx.fxml.FXML;
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


    public void initialize() {

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    legjobb = HelyszinApi.getBestRating();

                    osszFelh = FelhasznaloApi.getUsersCount();

                    ujHely = HelyszinApi.getNewCount();

                    ujVisszajelzes = VisszajelzesApi.getNewCount();
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

                    engedelyezettHely = HelyszinApi.getAllowedCount();

                    olvasottVisszajelzes = VisszajelzesApi.getReadCount();
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
