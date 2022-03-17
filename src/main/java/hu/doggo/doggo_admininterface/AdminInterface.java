package hu.doggo.doggo_admininterface;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AdminInterface extends Application {

    public static boolean superAdmin = false;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/bejelentkezes-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 420);
        stage.initStyle(StageStyle.UNDECORATED);
        Image icon = new Image(getClass().getResourceAsStream("icons/logo.png"));
        stage.getIcons().add(icon);
        stage.setTitle("DogGo - Bejelentkezés");
        stage.setScene(scene);
        stage.setMinWidth(550);
        stage.setMinHeight(420);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}