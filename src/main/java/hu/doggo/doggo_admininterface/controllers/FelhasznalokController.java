package hu.doggo.doggo_admininterface.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hu.doggo.doggo_admininterface.Felhasznalo;
import hu.doggo.doggo_admininterface.RequestHandler;
import hu.doggo.doggo_admininterface.Response;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

public class FelhasznalokController {

    @FXML
    private TableColumn<Felhasznalo, LocalDate> created_atCol;
    @FXML
    private TableColumn<Felhasznalo, String> usernameCol;
    @FXML
    private TableColumn<Felhasznalo, String> emailCol;
    @FXML
    private TableView<Felhasznalo> felhasznalokTableView;
    @FXML
    private TableColumn<Felhasznalo, Integer> permissionCol;

    public void initialize() {
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        permissionCol.setCellValueFactory(new PropertyValueFactory<>("permission"));

        felhasznaloListaFeltoltes();

    }

    private void felhasznaloListaFeltoltes() {
        try {
            Response response = RequestHandler.get("http://127.0.0.1:8000/api/users");
            String json = response.getContent();
            if(response.getResponseCode() >= 400) {
                System.out.println(json);
                return;
            }

            Gson jsonConverter = new Gson();
            Type type = new TypeToken<List<Felhasznalo>>(){}.getType();
            List<Felhasznalo> felhasznaloLista = jsonConverter.fromJson(json, type);
            felhasznalokTableView.getItems().clear();
            for (Felhasznalo felhasznalo : felhasznaloLista) {
                felhasznalokTableView.getItems().add(felhasznalo);
            }

        } catch (IOException e) {
            e.getMessage();
        }
    }
}