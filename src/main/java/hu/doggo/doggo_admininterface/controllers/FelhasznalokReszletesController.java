package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.AdminInterface;
import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.api.ErtekelesApi;
import hu.doggo.doggo_admininterface.api.FelhasznaloApi;
import hu.doggo.doggo_admininterface.classes.Ertekeles;
import hu.doggo.doggo_admininterface.classes.Felhasznalo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class FelhasznalokReszletesController extends Controller {
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label regDateLabel;
    @FXML
    private Label permissionLabel;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Button descriptionDeleteButton;
    @FXML
    private Button banButton;
    @FXML
    private Button adminPermissionButton;
    @FXML
    private TextField ratingSearchField;
    @FXML
    private TableView<Ertekeles> ratingsTableView;
    @FXML
    private TableColumn<Ertekeles, String> descriptionCol;
    @FXML
    private TableColumn<Ertekeles, Integer> starsCol;

    private Stage stage;
    private Felhasznalo reszletes;
    private ObservableList<Ertekeles> ratingList = FXCollections.observableArrayList();
    private double x = 0;
    private double y = 0;


    public Felhasznalo getReszletes() {
        return reszletes;
    }

    public void setReszletes(Felhasznalo reszletes) {
        this.reszletes = reszletes;

        writeData();
    }

    private void writeData() {
        loadUserData();


        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        starsCol.setCellValueFactory(new PropertyValueFactory<>("stars"));

        boolean superAdmin = AdminInterface.superAdmin;
        adminPermissionButton.setVisible(superAdmin);

        int permission = reszletes.getPermission();
        if (permission == 0) {
            banButton.setText("Felhaszn??l?? tilt??sa");
            banButton.setDisable(false);
        } else if (permission == 1) {
            banButton.setText("Felhaszn??l?? felold??sa");
            banButton.setDisable(false);
            adminPermissionButton.setDisable(true);
        } else if (permission == 2) {
            adminPermissionButton.setText("Admin jog elv??tele");
            banButton.setVisible(false);
        } else {
            banButton.setVisible(false);
            descriptionDeleteButton.setVisible(false);
            adminPermissionButton.setVisible(false);
        }
        if (superAdmin && permission != 3) {
            banButton.setVisible(true);
        }

        ratingListUpload();
        search();
    }

    private void loadUserData() {
        usernameLabel.setText(reszletes.getUsername());
        emailLabel.setText(reszletes.getEmail());
        regDateLabel.setText(reszletes.getFormattedDate());
        if (reszletes.getPermission() == 0) {
            permissionLabel.setText("default");
        } else if (reszletes.getPermission() == 1) {
            permissionLabel.setText("tiltva");
        } else if (reszletes.getPermission() == 2) {
            permissionLabel.setText("admin");
        } else {
            permissionLabel.setText("szuperadmin");
        }
    }

    private void ratingListUpload() {
        Platform.runLater(() -> {
            try {
                ratingList.clear();
                ratingList.addAll(ErtekelesApi.getUserRatings(reszletes.getId()));
            } catch (IOException e) {
                error(e);
            }
        });
    }

    private void search() {
        FilteredList<Ertekeles> filteredList = new FilteredList<>(ratingList, b -> true);
        ratingSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(ertekeles -> {
                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }

                descriptionDeleteButton.setDisable(true);
                descriptionTextArea.setText("");
                ratingsTableView.getSelectionModel().select(null);
                String kereses = newValue.toLowerCase();

                if (ertekeles.getDescription() == null) {
                    return false;
                } else if (ertekeles.getDescription().toLowerCase().contains(kereses)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Ertekeles> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(ratingsTableView.comparatorProperty());

        ratingsTableView.setItems(sortedList);
    }

    private void ban() {
        reszletes.setPermission(1);
        try {
            Felhasznalo felhTiltasa = FelhasznaloApi.updateUserPermission(reszletes);
            if (felhTiltasa != null) {
                alert("Felhaszn??l?? tiltva!");
                banButton.setText("Felhaszn??l?? felod??sa");
                adminPermissionButton.setDisable(true);
            } else {
                alert("Sikertelen m??velet!");
            }
            loadUserData();
        } catch (IOException e) {
            error(e);
        }
    }

    private void unban() {
        reszletes.setPermission(0);
        try {
            Felhasznalo felhTiltasa = FelhasznaloApi.updateUserPermission(reszletes);
            if (felhTiltasa != null) {
                alert("Felhaszn??l?? feloldva!");
                banButton.setText("Felhaszn??l?? tilt??sa");
                adminPermissionButton.setDisable(false);
            } else {
                alert("Sikertelen m??velet!");
            }
            loadUserData();
        } catch (IOException e) {
            error(e);
        }
    }

    private void admin() {
        reszletes.setPermission(2);
        try {
            Felhasznalo felhAdmin = FelhasznaloApi.updateUserPermission(reszletes);
            if (felhAdmin != null) {
                alert("Sikeres m??velet");
                adminPermissionButton.setText("Admin jog elv??tele");
                banButton.setDisable(true);
            } else {
                alert("Sikertelen m??velet!");
            }
            loadUserData();
        } catch (IOException e) {
            error(e);
        }
    }

    private void unadmin() {
        reszletes.setPermission(0);
        try {
            Felhasznalo felhAdmin = FelhasznaloApi.updateUserPermission(reszletes);
            if (felhAdmin != null) {
                alert("Sikeres m??velet");
                adminPermissionButton.setText("Admin");
                banButton.setDisable(false);
            } else {
                alert("Sikertelen m??velet!");
            }
            loadUserData();
        } catch (IOException e) {
            error(e);
        }
    }

    @FXML
    public void onDescriptionClick(MouseEvent mouseEvent) {
        int selectedIndex = ratingsTableView.getSelectionModel().getSelectedIndex();
        Ertekeles ertekeles = ratingsTableView.getSelectionModel().getSelectedItem();
        if (!(selectedIndex == -1) && ertekeles.getDescription() != null) {
            descriptionDeleteButton.setDisable(false);
            descriptionTextArea.setText(ertekeles.getDescription());
        } else {
            descriptionDeleteButton.setDisable(true);
            descriptionTextArea.setText("");
        }
    }

    @FXML
    public void onDescriptionDeleteClick(ActionEvent actionEvent) {
        int selectedIndex = ratingsTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("A m??dos??t??shoz el??bb v??lasszon ki egy elemet a t??bl??zatb??l");
            return;
        }
        Ertekeles ertekeles = ratingsTableView.getSelectionModel().getSelectedItem();

        if (!confirmation("Biztos hogy t??r??lni szeretn?? az al??bbi le??r??st: " + ertekeles.getDescription())) {
            return;
        }

        ertekeles.setDescription("");

        try {
            Ertekeles toroltErtekeles = ErtekelesApi.deleteDescription(ertekeles);
            if (toroltErtekeles != null) {
                alert("Sikeres t??rl??s");
                descriptionDeleteButton.setDisable(true);
                descriptionTextArea.setText("");
                ratingsTableView.getSelectionModel().select(null);
            } else {
                alert("Sikertelen t??rl??s");
            }
            loadUserData();
            ratingsTableView.refresh();
        } catch (IOException e) {
            error(e);
        }
    }

    @FXML
    public void onUserBanClick(ActionEvent actionEvent) {
        int permission = reszletes.getPermission();
        switch (permission) {
            case 0 -> ban();
            case 1 -> unban();
        }
    }

    @FXML
    public void onAdminClick(ActionEvent actionEvent) {
        int permission = reszletes.getPermission();
        switch (permission) {
            case 0 -> admin();
            case 2 -> unadmin();
        }
    }

    @FXML
    public void onCloseClick(Event event) {
        ((Stage) mainAnchor.getScene().getWindow()).close();
    }

    @FXML
    public void onMinimizeClick(Event event) {
        ((Stage) mainAnchor.getScene().getWindow()).setIconified(true);
    }

    @FXML
    public void onBorderPaneTopDragged(MouseEvent mouseEvent) {
        stage = (Stage) borderPane.getScene().getWindow();
        dragWindow(stage, mouseEvent, x, y);
    }

    @FXML
    public void onBorderPaneTopPressed(MouseEvent mouseEvent) {
        x = mouseEvent.getSceneX();
        y = mouseEvent.getSceneY();
    }
}