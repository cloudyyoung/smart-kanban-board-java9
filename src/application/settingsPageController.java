package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;

public class settingsPageController {

  boolean searchPopUpOpen;

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
  private Label usernameLabel;

  @FXML
  private TextField searchTextField;

  @FXML
  private SVGPath searchClick;

  @FXML
  private Rectangle saveChangeBtn;

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
  private VBox searchList;

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
  void checkInput(KeyEvent event) {

    String str = searchTextField.getText();
    String search = "Search";

    if (str.length() != 0) {
      searchList.setVisible(true);
    } else {
      searchList.setVisible(false);
    }
  }

  @FXML
  void showHomePage(MouseEvent event) throws IOException {
    BorderPane pane = FXMLLoader.load(getClass().getResource("homePage.fxml"));
    settingsPane.getChildren().setAll(pane);
  }

  @FXML
  void showLogin(MouseEvent event) throws IOException {
    BorderPane pane = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
    settingsPane.getChildren().setAll(pane);
  }

  public void initialize() {

    usernameLabel.setText(loginPageController.getUsername());
  }

}
