package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.input.MouseEvent;

public class settingsPageController {

  @FXML private TextField availableW;

  @FXML private TextField availableU;

  @FXML private TextField availableT;

  @FXML private ColorPicker iconColorPicker;

  @FXML private TextField availableS;

  @FXML private CheckBox darkModePicker;

  @FXML private Label sideViewSettings;

  @FXML private TextField availableR;

  @FXML private TextField availableM;

  @FXML private TextField searchTextField;

  @FXML private SVGPath searchClick;

  @FXML private Rectangle saveChangeBtn;

  @FXML private TextField availableF;

  @FXML private TextField changeEmailIn;

  @FXML private TextField deleteAllBoards;

  @FXML private Label sideViewBoard;

  @FXML private TextField changeUsernameIn;

  @FXML private BorderPane settingsPane;

  @FXML private TextField changePasswordIn;

  @FXML private Label sideViewToday;

  @FXML
  void changeUsername(ActionEvent event) {}

  @FXML
  void changeEmail(ActionEvent event) {}

  @FXML
  void changePassword(ActionEvent event) {}

  @FXML
  void saveSettingsChanges(ActionEvent event) {}

  @FXML
  void showSearchPop(ActionEvent event) {}

  @FXML
  void showHomePage1(MouseEvent e) throws IOException {
    BorderPane pane = FXMLLoader.load(getClass().getResource("homePage.fxml"));
    settingsPane.GetChildren().setAll(pane);
  }

  @FXML
  void showHomePage(MouseEvent e) throws IOException {
    BorderPane pane = FXMLLoader.load(getClass().getResource("homePage.fxml"));
    settingsPane.GetChildren().setAll(pane);
  }
}
