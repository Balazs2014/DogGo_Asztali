package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.AdminInterface;
import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.api.ErtekelesApi;
import hu.doggo.doggo_admininterface.api.FelhasznaloApi;
import hu.doggo.doggo_admininterface.classes.Ertekeles;
import hu.doggo.doggo_admininterface.classes.Felhasznalo;
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
            banButton.setText("Felhasználó tiltása");
        } else if (permission == 1) {
            banButton.setText("Felhasználó feloldása");
            adminPermissionButton.setDisable(true);
        } else if (permission == 2) {
            adminPermissionButton.setText("Admin jog elvétele");
            banButton.setVisible(false);
        }  else {
            banButton.setVisible(false);
            descriptionDeleteButton.setVisible(false);
            adminPermissionButton.setVisible(false);
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
        try {
            ratingList.clear();
            ratingList.addAll(ErtekelesApi.getUserRatings(reszletes.getId()));
        } catch (IOException e) {
            error(e);
        }
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
                alert("Felhasználó tiltva!");
                banButton.setText("Felhasználó felodása");
                adminPermissionButton.setDisable(true);
            } else {
                alert("Sikertelen művelet!");
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
                alert("Felhasználó feloldva!");
                banButton.setText("Felhasználó tiltása");
                adminPermissionButton.setDisable(false);
            } else {
                alert("Sikertelen művelet!");
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
                alert("Sikeres művelet");
                adminPermissionButton.setText("Admin jog elvétele");
                banButton.setDisable(true);
            } else {
                alert("Sikertelen művelet!");
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
                alert("Sikeres művelet");
                adminPermissionButton.setText("Admin jog");
                banButton.setDisable(false);
            } else {
                alert("Sikertelen művelet!");
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
            alert("A módosításhoz előbb válasszon ki egy elemet a táblázatból");
            return;
        }
        Ertekeles ertekeles = ratingsTableView.getSelectionModel().getSelectedItem();

        if (!confirmation("Biztos hogy törölni szeretné az alábbi leírást: " + ertekeles.getDescription())) {
            return;
        }

        ertekeles.setDescription("");

        try {
            Ertekeles toroltErtekeles = ErtekelesApi.deleteDescription(ertekeles);
            if (toroltErtekeles != null) {
                alert("Sikeres törlés");
                descriptionDeleteButton.setDisable(true);
                descriptionTextArea.setText("");
                ratingsTableView.getSelectionModel().select(null);
            } else {
                alert("Sikertelen törlés");
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
            case 0:
                ban();
                break;
            case 1:
                unban();
                break;
        }
    }

    @FXML
    public void onAdminClick(ActionEvent actionEvent) {
        int permission = reszletes.getPermission();
        switch (permission) {
            case 0:
                admin();
                break;
            case 2:
                unadmin();
                break;
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
        Stage stage = (Stage) borderPane.getScene().getWindow();
        dragWindow(stage, mouseEvent, x, y);
    }

    @FXML
    public void onBorderPaneTopPressed(MouseEvent mouseEvent) {
        x = mouseEvent.getSceneX();
        y = mouseEvent.getSceneY();
    }
}