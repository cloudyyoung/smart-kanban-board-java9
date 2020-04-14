package ui;

import java.net.*;
import java.util.*;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import structure.*;

public class SettingsController {

  @FXML private ResourceBundle resources;

  @FXML private URL location;

  @FXML private BorderPane settings;

  @FXML private Label accountUsername;

  @FXML private Button accountSignOut;

  @FXML private ComboBox<String> availabilityMonday;

  @FXML private ComboBox<String> availabilityTuesday;

  @FXML private ComboBox<String> availabilityWednesday;

  @FXML private ComboBox<String> availabilityThursday;

  @FXML private ComboBox<String> availabilityFriday;

  @FXML private ComboBox<String> availabilitySaturday;

  @FXML private ComboBox<String> availabilitySunday;

  @FXML private ComboBox<String> themeCombo;

  @FXML private Button settingTitle;

  @FXML
  void initialize() {
    accountUsername.setText(User.getCurrent().getUsername());

    ArrayList<String> hours = new ArrayList<String>();
    hours.add("0 Hour");
    hours.add("1 Hour");
    hours.add("2 Hours");
    hours.add("3 Hours");
    hours.add("4 Hours");
    hours.add("5 Hours");
    hours.add("6 Hours");
    hours.add("7 Hours");
    hours.add("8 Hours");
    hours.add("9 Hours");
    hours.add("10 Hours");
    hours.add("11 Hours");
    hours.add("12 Hours");
    availabilityMonday.getItems().addAll(hours);
    availabilityTuesday.getItems().addAll(hours);
    availabilityWednesday.getItems().addAll(hours);
    availabilityThursday.getItems().addAll(hours);
    availabilityFriday.getItems().addAll(hours);
    availabilitySaturday.getItems().addAll(hours);
    availabilitySunday.getItems().addAll(hours);
    availabilityMonday.getSelectionModel().select(0);
    availabilityTuesday.getSelectionModel().select(0);
    availabilityWednesday.getSelectionModel().select(0);
    availabilityThursday.getSelectionModel().select(0);
    availabilityFriday.getSelectionModel().select(0);
    availabilitySaturday.getSelectionModel().select(0);
    availabilitySunday.getSelectionModel().select(0);

    themeCombo.getItems().addAll("Default", "Dark");
  }

  @FXML
  void back(ActionEvent event) {
    try {
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      Scene oldScene = ((Node) event.getSource()).getScene();
      Scene scene =
          new Scene(
              FXMLLoader.load(getClass().getResource("home.fxml")),
              oldScene.getWidth(),
              oldScene.getHeight());
      scene.getStylesheets().add(getClass().getResource("default.css").toExternalForm());
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void switchTheme(ActionEvent event) {
    Scene scene = ((Node) event.getSource()).getScene();
    scene.getStylesheets().clear();
    scene.getStylesheets().add(getClass().getResource("default.css").toExternalForm());
    if (themeCombo.getSelectionModel().getSelectedItem().toLowerCase().equals("dark")) {
      scene.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
    }
  }

  @FXML
  void signOut(ActionEvent event) {
    User.getCurrent().signOut();
    try {
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      Scene oldScene = ((Node) event.getSource()).getScene();
      Scene scene =
          new Scene(
              FXMLLoader.load(getClass().getResource("welcome.fxml")),
              oldScene.getWidth(),
              oldScene.getHeight());
      scene.getStylesheets().add(getClass().getResource("default.css").toExternalForm());
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
