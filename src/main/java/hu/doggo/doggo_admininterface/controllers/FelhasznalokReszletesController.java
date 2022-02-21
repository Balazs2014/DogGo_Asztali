package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.api.ErtekelesApi;
import hu.doggo.doggo_admininterface.api.FelhasznaloApi;
import hu.doggo.doggo_admininterface.classes.Ertekeles;
import hu.doggo.doggo_admininterface.classes.Felhasznalo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class FelhasznalokReszletesController extends Controller {
    @FXML
    private Label felhEmail;
    @FXML
    private Label felhReg;
    @FXML
    private Label felhnev;
    @FXML
    private TableColumn<Ertekeles, Integer> starsCol;
    @FXML
    private TableColumn<Ertekeles, String> descriptionCol;
    @FXML
    private TableView<Ertekeles> ertekelesekTableView;
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label felhJog;
    @FXML
    private Button btnTiltas;
    @FXML
    private Pane paneFelh;

    private Felhasznalo reszletes;
    private ObservableList<Ertekeles> ertekelesLista = FXCollections.observableArrayList();

    private Stage stage;

    private double x = 0;
    private double y = 0;



    public Felhasznalo getReszletes() {
        return reszletes;
    }

    public void setReszletes(Felhasznalo reszletes) {
        this.reszletes = reszletes;
        adatokKiirasa();
    }

    private void adatokKiirasa() {
        felhAdatokBetoltese();

        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        starsCol.setCellValueFactory(new PropertyValueFactory<>("stars"));

        int permission = reszletes.getPermission();
        if (permission == 0) {
            btnTiltas.setText("Tiltás");
        } else if (permission == 1) {
            btnTiltas.setText("Feloldás");
        }

        ertekelesListaFeltoltes();
    }

    public void felhAdatokBetoltese() {
        felhnev.setText(reszletes.getUsername());
        felhEmail.setText(reszletes.getEmail());
        felhReg.setText(reszletes.getCreated_at() + "");
        if (reszletes.getPermission() == 0) {
            felhJog.setText("default");
        } else if (reszletes.getPermission()  == 1) {
            felhJog.setText("tiltva");
        } else if (reszletes.getPermission() == 2) {
            felhJog.setText("admin");
        } else {
            felhJog.setText("szuperadmin");
        }
    }

    private void ertekelesListaFeltoltes() {
        try {
            ertekelesLista.clear();
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
            alertWait("A módosításhoz előbb válasszon ki egy elemet a táblázatból");
            return;
        }
        Ertekeles modositando = (Ertekeles) ertekelesekTableView.getSelectionModel().getSelectedItem();

        if (!confirm("Biztos hogy törölni szeretné az alábbi leírást: " + modositando.getDescription())) {
            return;
        }

        modositando.setDescription("");

        try {
            Ertekeles modositandoErtekeles = ErtekelesApi.updateLeiras(modositando);
            if (modositandoErtekeles != null) {
                alertWait("Sikeres törlés");
            } else {
                alertWait("Sikertelen törlés");
            }
            adatokKiirasa();
        } catch (IOException e) {
            hibaKiir(e);
        }
    }

    @FXML
    public void onBorderPaneTopDragged(MouseEvent event) {
        Stage stage = (Stage)borderPane.getScene().getWindow();
        dragWindow(stage, event, x, y);
    }

    @FXML
    public void onBorderPaneTopPressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    public void onCloseClick(Event event) {
        ((Stage)mainAnchor.getScene().getWindow()).close();
    }

    @FXML
    public void onMinimizeClick(Event event) {
        ((Stage)mainAnchor.getScene().getWindow()).setIconified(true);
    }

    @FXML
    public void onTiltasClick(ActionEvent actionEvent) {
        int permission = reszletes.getPermission();
        if (permission == 0) {
            kitiltas();
            btnTiltas.setText("Feloldás");
        } else if (permission == 1) {
            feloldas();
            btnTiltas.setText("Tiltás");
        }
    }

    public void kitiltas() {
        reszletes.setPermission(1);

        try {
            ertekelesLista.clear();
            Felhasznalo felhTiltasa = FelhasznaloApi.updateFelhasznalo(reszletes);
            if (felhTiltasa != null) {
                alertWait("Felhasználó tiltva!");
            } else {
                alertWait("Sikertelen tiltás!");
            }
            felhAdatokBetoltese();
        } catch (IOException e) {
            hibaKiir(e);
        }
    }

    public void feloldas() {
        reszletes.setPermission(0);

        try {
            ertekelesLista.clear();
            Felhasznalo felhTiltasa = FelhasznaloApi.updateFelhasznalo(reszletes);
            if (felhTiltasa != null) {
                alertWait("Felhasználó feloldva!");
            } else {
                alertWait("Sikertelen feloldás!");
            }
            felhAdatokBetoltese();
        } catch (IOException e) {
            hibaKiir(e);
        }
    }
}