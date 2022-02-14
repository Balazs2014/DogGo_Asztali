package hu.doggo.doggo_admininterface;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    protected Stage stage;

    public Stage getStage() {
        return stage;
    }

    protected boolean confirm(String uzenet) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Biztos?");
        alert.setContentText(uzenet);
        Optional<ButtonType> result = alert.showAndWait();
        return  result.get() == ButtonType.OK;
    }

    protected void hibaKiir(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(e.getClass().toString());
        alert.setContentText(e.getMessage());
        Timer alertTimer = new Timer();
        alertTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> alert.show());
            }
        }, 500);
    }

    public static Controller ujAblak(String fxml, String title, int width, int height) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(AdminInterface.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle(title);
        stage.setScene(scene);
        Controller controller = fxmlLoader.getController();
        controller.stage = stage;
        return controller;
    }

    protected void alert(String uzenet) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setContentText(uzenet);
        alert.getButtonTypes().add(ButtonType.OK);
        alert.show();
    }

    protected void alertWait(String uzenet) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setContentText(uzenet);
        alert.getButtonTypes().add(ButtonType.OK);
        alert.showAndWait();
    }

    protected void minimizeWindow(Stage stage) {
        stage.setIconified(true);
    }

    protected void dragWindow(Stage stage, MouseEvent event, double x, double y) {
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }
}
