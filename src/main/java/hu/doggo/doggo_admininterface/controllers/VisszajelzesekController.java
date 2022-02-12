package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.api.VisszajelzesApi;
import hu.doggo.doggo_admininterface.classes.Visszajelzes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class VisszajelzesekController extends Controller {
    @FXML
    private TableColumn<Visszajelzes, String> commentCol;
    @FXML
    private TableView<Visszajelzes> visszajelzesekTableView;
    @FXML
    private TextField textFieldVisszajelzesekKereses;
    @FXML
    private TableColumn<Visszajelzes, Date> created_atCol;

    public void initialize() {
        commentCol.setCellValueFactory(new PropertyValueFactory<>("comment"));
        created_atCol.setCellValueFactory(new PropertyValueFactory<>("created_at"));

        visszajelzesekListaFeltoltese();
    }

    private void visszajelzesekListaFeltoltese() {
        try {
            List<Visszajelzes> visszajelzesLista = VisszajelzesApi.getVisszajelzesek();
            visszajelzesekTableView.getItems().clear();
            for (Visszajelzes visszajelzes : visszajelzesLista) {
                visszajelzesekTableView.getItems().add(visszajelzes);
            }
        } catch (IOException e) {
            hibaKiir(e);
        }
    }

    @FXML
    public void onVisszajelzesekKeresesClick(ActionEvent actionEvent) {
    }
}
