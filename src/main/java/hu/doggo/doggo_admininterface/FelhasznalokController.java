package hu.doggo.doggo_admininterface;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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

}