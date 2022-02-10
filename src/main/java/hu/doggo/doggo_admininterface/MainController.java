package hu.doggo.doggo_admininterface;

import hu.doggo.doggo_admininterface.Controller;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController extends Controller {
    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private ImageView maximizeImage;

    private Image newIcon;

    private Stage stage;

    private double x = 0;
    private double y = 0;

    private boolean teljesKepernyo = false;


    public void initialize() throws IOException {
        changeStage("statisztikak-view.fxml");
    }

    @FXML
    public void onFelhasznalokButtonClick(ActionEvent event) throws IOException {
        changeStage("felhasznalok-view.fxml");
    }

    @FXML
    public void onHelyszinekButtonClick(ActionEvent event) throws IOException {
        changeStage("helyszinek-view.fxml");
    }

    @FXML
    public  void onStatisztikakButtonClick(ActionEvent event) throws IOException {
        changeStage("statisztikak-view.fxml");
    }

    @FXML
    public void onVisszajelzesButtonClick(ActionEvent actionEvent) throws IOException {
        changeStage("visszajelzesek-view.fxml");
    }

    @FXML
    public void onBorderPaneTopDragged(MouseEvent event) {
        Stage stage = (Stage)borderPane.getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    @FXML
    public void onBorderPaneTopPressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    public void onCloseClick(Event event) {
        if(!(confirm("Ki szeretne lépni a programból?"))) {
            return;
        }
        System.exit(0);
    }

    @FXML
    public void onMaximizeClick(Event event) {
        stage = (Stage) mainAnchor.getScene().getWindow();
        if (!teljesKepernyo) {
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX(primaryScreenBounds.getMinX());
            stage.setY(primaryScreenBounds.getMinY());

            stage.setMaxWidth(primaryScreenBounds.getWidth());
            stage.setMinWidth(primaryScreenBounds.getWidth());

            stage.setMaxHeight(primaryScreenBounds.getHeight());
            stage.setMinHeight(primaryScreenBounds.getHeight());

            newIcon = new Image(getClass().getResource("/icons/elozo_nezet.png").toExternalForm());
            maximizeImage.setImage(newIcon);

            teljesKepernyo = true;
        } else {
            stage.setMaxWidth(1300);
            stage.setMinWidth(1300);

            stage.setMaxHeight(700);
            stage.setMinHeight(700);

            stage.centerOnScreen();

            newIcon = new Image(getClass().getResource("/icons/teljes_meret.png").toExternalForm());
            maximizeImage.setImage(newIcon);

            teljesKepernyo = false;
        }
    }

    @FXML
    public void onMinimizeClick(Event event) {
        stage = (Stage) mainAnchor.getScene().getWindow();
        minimizeWindow(stage);
    }

    @FXML
    public void onKijelentkezesButtonClick(ActionEvent actionEvent) {
        if(!(confirm("Ki szeretne jelentkezni?"))) {
            return;
        }
        System.exit(0);
    }

    private void changeStage(String fxml) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource(fxml));
        borderPane.setCenter(view);
    }

}
