package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import structure.*;

public class homePageController {

  @FXML
  private Label doingButton;

  @FXML
  private BorderPane homePane;

  @FXML
  private Label newBoardButton;

  @FXML
  private Label todaysDate;

  @FXML
  private TextField searchTextField;

  @FXML
  private Label usernameLabel;

  @FXML
  private StackPane newBoardPopUp;

  @FXML
  private Rectangle transRectangle;

  @FXML
  private Label backlogButton;

  @FXML
  private Label todoButton;

  @FXML
  private Label doneButton;

  @FXML
  private VBox searchList;

  @FXML
  private StackPane newTaskPopUp;

  @FXML
  private SVGPath newBoardExitButton;

  @FXML
  private SVGPath newTaskExitButton;

  @FXML
  void backlogNewTask(MouseEvent event) {
    transRectangle.setVisible(true);
    newTaskPopUp.setVisible(true);
  }

  @FXML
  void todoNewTask(MouseEvent event) {
    transRectangle.setVisible(true);
    newTaskPopUp.setVisible(true);
  }

  @FXML
  void doingNewTask(MouseEvent event) {
    transRectangle.setVisible(true);
    newTaskPopUp.setVisible(true);
  }

  @FXML
  void doneNewTask(MouseEvent event) {
    transRectangle.setVisible(true);
    newTaskPopUp.setVisible(true);
  }

  @FXML
  void newBoardClicked(MouseEvent event) {
    transRectangle.setVisible(true);
    newBoardPopUp.setVisible(true);
  }

  @FXML
  void newBoardPopUpExit(MouseEvent event) {
    transRectangle.setVisible(false);
    newBoardPopUp.setVisible(false);
  }

  @FXML
  void createBoard(MouseEvent event) {
    transRectangle.setVisible(false);
    newBoardPopUp.setVisible(false);
  }

  @FXML
  void setDarkMode(ActionEvent event) {
  }

  @FXML
  void showLoginPage(MouseEvent event) throws IOException {
    BorderPane pane = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
    homePane.getChildren().setAll(pane);
  }

  @FXML
  void showSettingsPage(MouseEvent event) throws IOException {
    BorderPane pane = FXMLLoader.load(getClass().getResource("settingsPage.fxml"));
    homePane.getChildren().setAll(pane);
  }

  @FXML
  void newTaskPopUpExit(MouseEvent event) {
    transRectangle.setVisible(false);
    newTaskPopUp.setVisible(false);
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
  void createTask(MouseEvent event) {
    transRectangle.setVisible(false);
    newTaskPopUp.setVisible(false);
  }

  public void initialize() {

    usernameLabel.setText(loginPageController.getUsername());

    String str = (Time.monthName(Integer.parseInt(Time.currentMonth()))).toLowerCase();
    String cap = str.substring(0, 1).toUpperCase() + str.substring(1);

    todaysDate.setText(cap + " " + Time.currentDay() + ", " + Time.currentYear());
  }
}
