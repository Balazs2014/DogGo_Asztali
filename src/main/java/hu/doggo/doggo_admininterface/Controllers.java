package hu.doggo.doggo_admininterface;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Optional;

public class Controllers {
    protected boolean confirm(String uzenet) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Biztos?");
        alert.setContentText(uzenet);
        Optional<ButtonType> result = alert.showAndWait();
        return  result.get() == ButtonType.OK;
    }

    protected void minimizeWindow(Stage stage) {
        stage.setIconified(true);
    }
}
