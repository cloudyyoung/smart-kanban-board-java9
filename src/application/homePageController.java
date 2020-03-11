package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import structure.*;

// Cards:
// The card system is layed out 2 layers with A-D rows and 1-8 columns. Each card has 4 FXIDs:
// For title strings > 26 char & <= 52
//  dblCardSP<insert coord> : for visibility of the card
//  dblCardText<insert coord> : for title text
//  dblCardTime<insert coord> : for time text
//  dblCardBG<insert coord> : : for the card BG fill

// For title strings <= 26 char
//  sinCardSP<insert coord> : for visibility of the card
//  sinCardText<insert coord> : for title text
//  sinCardTime<insert coord> : for time text
//  sinCardBG<insert coord> : : for the card BG fill

public class homePageController {

  @FXML
  private Label dblCardTimeC1;

  @FXML
  private Label dblCardTimeC2;

  @FXML
  private Circle sideBoard5C;

  @FXML
  private Label dblCardTimeC3;

  @FXML
  private Label dblCardTimeC4;

  @FXML
  private Label dblCardTimeC5;

  @FXML
  private Label dblCardTimeC6;

  @FXML
  private Label dblCardTimeC7;

  @FXML
  private Rectangle transRectangle;

  @FXML
  private Label dblCardTimeB7;

  @FXML
  private SVGPath newTaskButtonExitButton;

  @FXML
  private Label todoButton;

  @FXML
  private Label dblCardTimeD1;

  @FXML
  private StackPane dblCardD6;

  @FXML
  private Label dblCardTimeD2;

  @FXML
  private StackPane dblCardD7;

  @FXML
  private Circle sideBoard4C;

  @FXML
  private Label dblCardTimeD3;

  @FXML
  private Label dblCardTimeD4;

  @FXML
  private StackPane newBoardPopUp;

  @FXML
  private Label dblCardTimeD5;

  @FXML
  private Label dblCardTimeD6;

  @FXML
  private Label dblCardTimeD7;

  @FXML
  private Label backlogButton1;

  @FXML
  private Label newBoardButton;

  @FXML
  private Label todaysDate;

  @FXML
  private StackPane dblCardD1;

  @FXML
  private StackPane dblCardD2;

  @FXML
  private StackPane dblCardD3;

  @FXML
  private StackPane dblCardD4;

  @FXML
  private StackPane dblCardD5;

  @FXML
  private StackPane dblCardC5;

  @FXML
  private StackPane dblCardC6;

  @FXML
  private StackPane dblCardC7;

  @FXML
  private Label dblCardTimeA1;

  @FXML
  private Label dblCardTimeA2;

  @FXML
  private Circle sideBoard7C;

  @FXML
  private Label dblCardTimeA3;

  @FXML
  private Label dblCardTimeA4;

  @FXML
  private Label dblCardTimeA5;

  @FXML
  private TextField searchTextField;

  @FXML
  private Rectangle dblCardBGA3;

  @FXML
  private Rectangle dblCardBGA2;

  @FXML
  private Rectangle dblCardBGA5;

  @FXML
  private Rectangle dblCardBGA4;

  @FXML
  private Rectangle dblCardBGA1;

  @FXML
  private Label dblCardTextA3;

  @FXML
  private Label dblCardTextA2;

  @FXML
  private Label dblCardTextA5;

  @FXML
  private Label doneButton1;

  @FXML
  private Label dblCardTextA4;

  @FXML
  private Rectangle dblCardBGA7;

  @FXML
  private StackPane dblCardC1;

  @FXML
  private Rectangle dblCardBGA6;

  @FXML
  private StackPane dblCardC2;

  @FXML
  private Label dblCardTextA1;

  @FXML
  private StackPane dblCardC3;

  @FXML
  private StackPane dblCardC4;

  @FXML
  private Label doingButton;

  @FXML
  private StackPane dblCardB4;

  @FXML
  private StackPane dblCardB5;

  @FXML
  private Label dblCardTimeB1;

  @FXML
  private StackPane dblCardB6;

  @FXML
  private Label dblCardTimeB2;

  @FXML
  private StackPane dblCardB7;

  @FXML
  private Circle sideBoard6C;

  @FXML
  private Label dblCardTimeB3;

  @FXML
  private Label dblCardTimeB4;

  @FXML
  private Label dblCardTimeB5;

  @FXML
  private Label dblCardTimeB6;

  @FXML
  private Label todoButton1;

  @FXML
  private Label doneButton;

  @FXML
  private Label dblCardTimeA6;

  @FXML
  private Label dblCardTimeA7;

  @FXML
  private StackPane dblCardB1;

  @FXML
  private StackPane dblCardB2;

  @FXML
  private StackPane dblCardB3;

  @FXML
  private StackPane dblCardA3;

  @FXML
  private HBox sideBoard3;

  @FXML
  private StackPane dblCardA4;

  @FXML
  private Label doingButton1;

  @FXML
  private HBox sideBoard4;

  @FXML
  private StackPane dblCardA5;

  @FXML
  private HBox sideBoard5;

  @FXML
  private HBox sideBoard6;

  @FXML
  private StackPane dblCardA7;

  @FXML
  private Label dblCardTextB7;

  @FXML
  private HBox sideBoard1;

  @FXML
  private HBox sideBoard2;

  @FXML
  private Label backlogButton;

  @FXML
  private HBox sideBoard7;

  @FXML
  private HBox sideBoard8;

  @FXML
  private Label dblCardTextC5;

  @FXML
  private Label dblCardTextC4;

  @FXML
  private Label dblCardTextC7;

  @FXML
  private Label dblCardTextC6;

  @FXML
  private Label dblCardTextC1;

  @FXML
  private StackPane dblCardA1;

  @FXML
  private Label dblCardTextC3;

  @FXML
  private StackPane dblCardA2;

  @FXML
  private Label dblCardTextC2;

  @FXML
  private SVGPath newBoardExitButton;

  @FXML
  private StackPane newTaskPopUp;

  @FXML
  private Label dblCardTextA7;

  @FXML
  private Label dblCardTextA6;

  @FXML
  private Circle sideBoard8C;

  @FXML
  private Label usernameLabel;

  @FXML
  private Label dblCardTextB4;

  @FXML
  private Label dblCardTextB3;

  @FXML
  private Label dblCardTextB5;

  @FXML
  private Label dblCardTextB2;

  @FXML
  private Label dblCardTextB1;

  @FXML
  private Label bigBoardTitle;

  @FXML
  private Circle sideBoard3C;

  @FXML
  private VBox searchList;

  @FXML
  private Circle sideBoard2C;

  @FXML
  private BorderPane homePane;

  @FXML
  private Label dblCardTextD6;

  @FXML
  private Label dblCardTextD5;

  @FXML
  private Label dblCardTextD7;

  @FXML
  private Label dblCardTextD2;

  @FXML
  private Label dblCardTextD1;

  @FXML
  private Label dblCardTextD4;

  @FXML
  private Circle sideBoard1C;

  @FXML
  private Label dblCardTextD3;

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
