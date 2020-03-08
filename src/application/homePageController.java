package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.input.MouseEvent;

public class homePageController {

  @FXML private Label doingButton;

  @FXML private Label newBoardButton;

  @FXML private StackPane newBoardPopUp;

  @FXML private Rectangle transRectangle;

  @FXML private Label backlogButton;

  @FXML private Label todoButton;

  @FXML private Label doneButton;

  @FXML private StackPane newTaskPopUp;

  @FXML private SVGPath newBoardExitButton;

  @FXML private SVGPath newTaskExitButton;

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
  void newTaskPopUpExit(MouseEvent event) {
    transRectangle.setVisible(false);
    newTaskPopUp.setVisible(false);
  }
}
