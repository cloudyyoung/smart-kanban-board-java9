package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;

public class settingsPageController {

  @FXML
  private TextField availableW;

  @FXML
  private TextField availableU;

  @FXML
  private TextField availableT;

  @FXML
  private ColorPicker iconColorPicker;

  @FXML
  private TextField availableS;

  @FXML
  private CheckBox darkModePicker;

  @FXML
  private Label sideViewSettings;

  @FXML
  private TextField availableR;

  @FXML
  private TextField availableM;

  @FXML
  private TextField searchTextField;

  @FXML
  private SVGPath searchClick;

  @FXML
  private HBox SmartKanbanBtn;

  @FXML
  private StackPane saveChangeBtn;

  @FXML
  private TextField availableF;

  @FXML
  private TextField changeEmailIn;

  @FXML
  private TextField deleteAllBoards;

  @FXML
  private Label sideViewBoard;

  @FXML
  private TextField changeUsernameIn;

  @FXML
  private BorderPane settingsPane;

  @FXML
  private TextField changePasswordIn;

  @FXML
  private Label sideViewToday;

  @FXML
  void changeUsername(ActionEvent event) {

  }

  @FXML
  void changeEmail(ActionEvent event) {

  }

  @FXML
  void changePassword(ActionEvent event) {

  }

  @FXML
  void saveSettingsChanges(MouseEvent event) throws IOException {
    BorderPane pane = FXMLLoader.load(getClass().getResource("homePage.fxml"));
    settingsPane.getChildren().setAll(pane);
  }

  @FXML
  void showSearchPop(ActionEvent event) {

  }

  @FXML
  void showSettingsPage(MouseEvent event) throws IOException {

  }

  @FXML
  void showLoginPage(MouseEvent event) throws IOException {
    // TODO : auth = false when clicked
    BorderPane pane = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
    settingsPane.getChildren().setAll(pane);
  }

  @FXML
  void showHomePage(MouseEvent event) throws IOException {
    BorderPane pane = FXMLLoader.load(getClass().getResource("homePage.fxml"));
    settingsPane.getChildren().setAll(pane);
  }

}