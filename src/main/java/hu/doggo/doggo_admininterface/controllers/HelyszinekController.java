package hu.doggo.doggo_admininterface.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hu.doggo.doggo_admininterface.Felhasznalo;
import hu.doggo.doggo_admininterface.Helyszin;
import hu.doggo.doggo_admininterface.RequestHandler;
import hu.doggo.doggo_admininterface.Response;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class HelyszinekController {

    @FXML
    private TableColumn<Helyszin, Double> lngCol;
    @FXML
    private TableView<Helyszin> helyszinekTableView;
    @FXML
    private TableColumn<Helyszin, String> nameCol;
    @FXML
    private TableColumn<Helyszin, Double> latCol;

    public void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        latCol.setCellValueFactory(new PropertyValueFactory<>("lat"));
        lngCol.setCellValueFactory(new PropertyValueFactory<>("lng"));

        helyszinListaFeltoltes();

    }

    private void helyszinListaFeltoltes() {
        try {
            Response response = RequestHandler.get("http://127.0.0.1:8000/api/locations");
            String json = response.getContent();
            if(response.getResponseCode() >= 400) {
                System.out.println(json);
                return;
            }

            Gson jsonConverter = new Gson();
            Type type = new TypeToken<List<Helyszin>>(){}.getType();
            List<Helyszin> helyszinLista = jsonConverter.fromJson(json, type);
            helyszinekTableView.getItems().clear();
            for (Helyszin helyszin : helyszinLista) {
                helyszinekTableView.getItems().add(helyszin);
            }

        } catch (IOException e) {
            e.getMessage();
        }
    }
}
