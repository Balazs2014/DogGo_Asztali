package hu.doggo.doggo_admininterface;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.Date;

public class FelhasznalokController {

    @FXML
    private TableColumn<Felhasznalo, Date> regDateCol;
    @FXML
    private TableColumn<Felhasznalo, Boolean> tiltvaCol;
    @FXML
    private TableColumn<Felhasznalo, String> felhnevCol;
    @FXML
    private TableColumn<Felhasznalo, String> emailCol;
    @FXML
    private TableView<Felhasznalo> felhasznalokTableView;
    @FXML
    private TableColumn<Felhasznalo, Boolean> adminCol;

    public void initialize() {
        felhnevCol.setCellValueFactory(new PropertyValueFactory<>("felhasznalonev"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        regDateCol.setCellValueFactory(new PropertyValueFactory<>("regisztracio_datum"));
        adminCol.setCellValueFactory(new PropertyValueFactory<>("admin"));
        tiltvaCol.setCellValueFactory(new PropertyValueFactory<>("tiltva"));
    }

}