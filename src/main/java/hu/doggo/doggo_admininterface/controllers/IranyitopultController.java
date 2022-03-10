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

    public void initialize() {
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        HelyszinErtekeles legjobb = HelyszinApi.getLegjobbErtekeles();
                        HelyszinErtekeles legrosszabb = HelyszinApi.getLegrosszabbErtekeles();

                        int osszFelh = FelhasznaloApi.getFelhasznalokCount();
                        int kitiltottFelh = FelhasznaloApi.getKitiltottCount();
                        int adminFelh = FelhasznaloApi.getAdminCount();

                        int engedelyezettHely = HelyszinApi.getAllowedCount();
                        int ujHely = HelyszinApi.getNewCount();

                        int olvasottVisszajelzes = VisszajelzesApi.getReadCount();
                        int ujVisszajelzes = VisszajelzesApi.getNewCount();
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    lblLegjobbHely.setText(legjobb.getAtlag() + "");
                                    textareaLegjobbHelyNev.setText(legjobb.getName());
                                    lblLegrosszabbHely.setText(legrosszabb.getAtlag() + "");
                                    textareaLegrosszabbHelyNev.setText(legrosszabb.getName());

                                    lblOsszFelh.setText(osszFelh + "");
                                    lblKitiltottFelh.setText(kitiltottFelh + "");
                                    lblAdminFelh.setText(adminFelh + "");

                                    lblEngedelyezettHely.setText(engedelyezettHely + "");
                                    lblUjHely.setText(ujHely + "");

                                    lblOlvasottVisszajelzes.setText(olvasottVisszajelzes + "");
                                    lblUjVisszajelzes.setText(ujVisszajelzes + "");
                                }finally{
                                    latch.countDown();
                                }
                            }
                        });
                        latch.await();
                        return null;
                    }
                };
            }
        };
        service.start();
    }
}
