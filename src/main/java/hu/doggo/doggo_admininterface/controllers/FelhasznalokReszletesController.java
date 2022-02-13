package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.api.ErtekelesApi;
import hu.doggo.doggo_admininterface.classes.Ertekeles;
import hu.doggo.doggo_admininterface.classes.Felhasznalo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class FelhasznalokReszletesController extends Controller {
    @FXML
    private Label felhEmail;
    @FXML
    private Label felJog;
    @FXML
    private Label felhReg;
    @FXML
    private Label felhnev;
    @FXML
    private TableColumn<Felhasznalo, Integer> starsCol;
    @FXML
    private TableColumn<Felhasznalo, String> descriptionCol;
    @FXML
    private TableView ertekelesekTableView;

    private Felhasznalo reszletes;
    private ObservableList<Ertekeles> ertekelesLista = FXCollections.observableArrayList();

    public Felhasznalo getReszletes() {
        return reszletes;
    }

    public void setReszletes(Felhasznalo reszletes) {
        this.reszletes = reszletes;
        adatokKiirasa();
    }

    private void adatokKiirasa() {
        felhnev.setText(reszletes.getUsername());
        felhEmail.setText(reszletes.getEmail());
        felhReg.setText(reszletes.getCreated_at() + "");
        felJog.setText(reszletes.getPermission() + "");

        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        starsCol.setCellValueFactory(new PropertyValueFactory<>("stars"));

        ertekelesListaFeltoltes();

    }

    private void ertekelesListaFeltoltes() {
        try {
            ertekelesLista.addAll(ErtekelesApi.getErtekelesek());
            ertekelesekTableView.getItems().clear();
            for (Ertekeles ertekeles : ertekelesLista) {
                if (reszletes.getId() == ertekeles.getUser_id()) {
                    ertekelesekTableView.getItems().add(ertekeles);
                }
            }
        } catch (IOException e) {
            hibaKiir(e);
        }
    }

    @FXML
    public void onHozzaszolasTorlesClick(ActionEvent actionEvent) {
        int selectedIndex = ertekelesekTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("A módosításhoz előbb válasszon ki egy elemet a táblázatból");
            return;
        }
        Ertekeles modositando = (Ertekeles) ertekelesekTableView.getSelectionModel().getSelectedItem();

        if (!confirm("Biztos hogy törölni szeretné az alábbi leírást: " + modositando.getDescription())) {
            return;
        }


        try {
            modositando.setDescription("");
            Ertekeles modositandoErtekeles = ErtekelesApi.updateLeiras(modositando);
            if (modositandoErtekeles != null) {
                alertWait("Sikeres törlés");
            } else {
                alert("Sikertelen törlés");
            }

            adatokKiirasa();
        } catch (IOException e) {
            hibaKiir(e);
        }
    }
}