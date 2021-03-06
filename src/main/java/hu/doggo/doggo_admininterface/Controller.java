package hu.doggo.doggo_admininterface;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    protected DialogPane dialogPane;
    protected Stage stage;
    private static Image icon = new Image(Controller.class.getResourceAsStream("/hu/doggo/doggo_admininterface/icons/logo.png"));

    public Stage getStage() {
        return stage;
    }

    public static Controller newWindow(String fxml, String title, int width, int height) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(AdminInterface.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(icon);
        stage.setTitle(title);
        stage.setScene(scene);
        Controller controller = fxmlLoader.getController();
        controller.stage = stage;

        return controller;
    }

    protected boolean confirmation(String uzenet) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Megerősítés");
        alert.setHeaderText("Biztos?");
        alert.setContentText(uzenet);
        dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/hu/doggo/doggo_admininterface/style/alertStyle.css").toExternalForm()
        );
        dialogPane.getStyleClass().add("alert");
        Stage stage = (Stage) dialogPane.getScene().getWindow();
        stage.getIcons().add(icon);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    protected void error(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setContentText(e.getMessage());
        dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/hu/doggo/doggo_admininterface/style/alertStyle.css").toExternalForm()
        );
        dialogPane.getStyleClass().add("alert");
        Stage stage = (Stage) dialogPane.getScene().getWindow();
        stage.getIcons().add(icon);
        Timer alertTimer = new Timer();
        alertTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(alert::show);
            }
        }, 500);
    }

    protected void alert(String uzenet) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Alert");
        alert.setHeaderText(" ");
        alert.setContentText(uzenet);
        alert.getButtonTypes().add(ButtonType.OK);
        dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/hu/doggo/doggo_admininterface/style/alertStyle.css").toExternalForm()
        );
        dialogPane.getStyleClass().add("alert");
        Stage stage = (Stage) dialogPane.getScene().getWindow();
        stage.getIcons().add(icon);
        alert.showAndWait();
    }

    protected void dragWindow(Stage stage, MouseEvent event, double x, double y) {
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }
}
