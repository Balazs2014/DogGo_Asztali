package hu.doggo.doggo_admininterface.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Ablakok {
    protected boolean confirm(String uzenet) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Biztos?");
        alert.setContentText(uzenet);
        Optional<ButtonType> result = alert.showAndWait();
        return  result.get() == ButtonType.OK;
    }
}
