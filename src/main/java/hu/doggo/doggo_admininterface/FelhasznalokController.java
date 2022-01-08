package hu.doggo.doggo_admininterface;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;

public class FelhasznalokController extends SceneController {

    @FXML
    private TableColumn<Felhasznalo, LocalDate> regDateCol;
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

        felhasznalokTableView.getItems().add(new Felhasznalo(1, "Test", "test@example.com",
                LocalDate.of(2022, 1, 8), false, false));
    }

}