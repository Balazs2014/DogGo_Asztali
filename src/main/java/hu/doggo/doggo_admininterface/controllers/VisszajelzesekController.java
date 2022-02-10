package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.classes.Visszajelzesek;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class VisszajelzesekController {
    @FXML
    private TableColumn<Visszajelzesek, String> commentCol;
    @FXML
    private TableView<Visszajelzesek> visszajelzesekTableView;
    @FXML
    private TextField textFieldVisszajelzesekKereses;

    @FXML
    public void onVisszajelzesekKeresesClick(ActionEvent actionEvent) {
    }
}
