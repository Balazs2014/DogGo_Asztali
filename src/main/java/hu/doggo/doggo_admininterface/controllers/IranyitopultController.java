package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.api.FelhasznaloApi;
import hu.doggo.doggo_admininterface.api.HelyszinApi;
import hu.doggo.doggo_admininterface.api.VisszajelzesApi;
import hu.doggo.doggo_admininterface.classes.HelyszinErtekeles;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

public class IranyitopultController extends Controller {
    @FXML
    private Label lblKitiltottFelh;
    @FXML
    private Label lblAdminFelh;
    @FXML
    private Label lblOsszFelh;
    @FXML
    private Label lblLegrosszabbHely;
    @FXML
    private Label lblUjVisszajelzes;
    @FXML
    private Label lblLegjobbHely;
    @FXML
    private Label lblUjHely;
    @FXML
    private TextArea textareaLegjobbHelyNev;
    @FXML
    private TextArea textareaLegrosszabbHelyNev;
    @FXML
    private Label lblEngedelyezettHely;
    @FXML
    private Label lblOlvasottVisszajelzes;

    private HelyszinErtekeles legjobb;
    private HelyszinErtekeles legrosszabb;
    private int osszFelh;
    private int kitiltottFelh;
    private int adminFelh;
    private int engedelyezettHely;
    private int ujHely;
    private int olvasottVisszajelzes;
    private int ujVisszajelzes;
    private Timer timer = new Timer();

    public void initialize() {

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    legjobb = HelyszinApi.getLegjobbErtekeles();

                    osszFelh = FelhasznaloApi.getFelhasznalokCount();

                    engedelyezettHely = HelyszinApi.getAllowedCount();

                    olvasottVisszajelzes = VisszajelzesApi.getReadCount();
                } catch (IOException e) {
                    hibaKiir(e);
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lblLegjobbHely.setText(legjobb.getAtlag() + "");
                        textareaLegjobbHelyNev.setText(legjobb.getName());

                        lblOsszFelh.setText(osszFelh + "");

                        lblEngedelyezettHely.setText(engedelyezettHely + "");

                        lblOlvasottVisszajelzes.setText(olvasottVisszajelzes + "");
                    }
                });
            }
        };
        timer.schedule(timerTask, 1);

        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {
                try {
                    legrosszabb = HelyszinApi.getLegrosszabbErtekeles();

                    kitiltottFelh = FelhasznaloApi.getKitiltottCount();
                    adminFelh = FelhasznaloApi.getAdminCount();

                    ujHely = HelyszinApi.getNewCount();

                    ujVisszajelzes = VisszajelzesApi.getNewCount();
                } catch (IOException e) {
                    hibaKiir(e);
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lblLegrosszabbHely.setText(legrosszabb.getAtlag() + "");
                        textareaLegrosszabbHelyNev.setText(legrosszabb.getName());

                        lblKitiltottFelh.setText(kitiltottFelh + "");
                        lblAdminFelh.setText(adminFelh + "");

                        lblUjHely.setText(ujHely + "");

                        lblUjVisszajelzes.setText(ujVisszajelzes + "");
                    }
                });
            }
        };
        timer.schedule(timerTask2, 1);
    }
}
