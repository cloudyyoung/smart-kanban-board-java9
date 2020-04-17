package ui;

import java.sql.Timestamp;
import java.time.*;
import java.util.*;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;
import structure.*;

/**
 * The JavaFX Controller for home.fxml.
 *
 * @author Cloudy Young, Jimschenchen
 * @since 3.0
 * @version 4.0
 */
public class HomeController {

  @FXML protected Button sideProfile;
  @FXML protected Circle profileAvatar;
  @FXML protected Label profileUsername;
  @FXML protected VBox sidePane;
  @FXML protected VBox operationList;
  @FXML protected Button sideSearch;
  @FXML protected VBox boardList;
  @FXML protected TabPane tabPane;
  @FXML protected VBox boardPane;
  @FXML protected TextField boardTitle;
  @FXML protected TextField boardNote;
  @FXML protected Button boardEdit;
  @FXML protected HBox columnPane;
  @FXML protected TextField inputSearch;
  @FXML protected VBox searchList;
  @FXML protected Pane dragPane;
  @FXML protected VBox promptEvent;
  @FXML protected SVGPath promptEventIcon;
  @FXML protected Label promptEventPromptTitle;
  @FXML protected VBox promptEventTitleWrapper;
  @FXML protected TextArea promptEventTitle;
  @FXML protected Label promptEventLocationBoard;
  @FXML protected Label promptEventLocationColumn;
  @FXML protected ComboBox<String> promptEventImportanceLevel;
  @FXML protected DatePicker promptEventDueDate;
  @FXML protected ComboBox<String> promptEventDuration;
  @FXML protected TextArea promptEventNote;
  @FXML protected Pane extraPane;
  @FXML protected VBox promptBoard;
  @FXML protected SVGPath promptBoardIcon;
  @FXML protected Label promptBoardPromptTitle;
  @FXML protected TextArea promptBoardTitle;
  @FXML protected TextArea promptBoardNote;
  @FXML protected VBox promptColumn;
  @FXML protected SVGPath promptColumnIcon;
  @FXML protected Button columnDelete;
  @FXML protected Label promptColumnPromptTitle;
  @FXML protected TextArea promptColumnTitle;
  @FXML protected ComboBox<String> promptColumnPreset;
  @FXML protected Text textHolder = new Text();
  @FXML protected Button eventDelete;
  @FXML protected Button eventCreate;
  @FXML protected Button columnCreate;
  @FXML protected Button boardDelete;
  @FXML protected Button boardChildCreate;
  @FXML protected Button boardCreate;

  protected EventComponent currentEvent;
  protected BoardComponent currentBoard;
  protected ColumnComponent currentColumn;
  protected ColumnComponent originalColumn;
  protected Button currentDragCard;
  protected BoardComponent componentToday;

  @FXML
  void initialize() {

    this.closePrompt();
    this.extraPane.setVisible(false);

    // Intializes label text values
    this.profileUsername.setText(User.getCurrent().getUsername());
    this.profileUsername.setTooltip(new Tooltip(User.getCurrent().getUsername()));

    // Checks out kanban
    Kanban.checkout();
    Kanban.getCurrent().generateToday();
    Kanban.getCurrent().generateOverview();

    // Initializes Event prompt
    this.promptEventImportanceLevel.getItems().addAll("", "Level 1", "Level 2", "Level 3");
    this.promptEventDuration
        .getItems()
        .addAll(
            "",
            "1 Hour",
            "2 Hours",
            "3 Hours",
            "4 Hours",
            "5 Hours",
            "6 Hours",
            "7 Hours",
            "8 Hours",
            "9 Hours",
            "10 Hours",
            "11 Hours",
            "12 Hours");

    // Sets listeners
    this.promptEventDueDate
        .valueProperty()
        .addListener(
            (observable, oldDate, newDate) -> {
              Long timestamp = null;
              if (newDate != null) {
                timestamp = Timestamp.valueOf(newDate.atTime(LocalTime.MAX)).getTime() / 1000;
              }
              this.currentEvent.getNode().setDueDateRequest(timestamp);
            });

    this.searchList.getChildren().clear();
    this.inputSearch
        .textProperty()
        .addListener(
            (observable, oldText, newText) -> {
              this.searchList.getChildren().clear();
              if (!newText.equals("")) {
                ArrayList<structure.Node> list = Kanban.getCurrent().search(newText);
                for (structure.Node each : list) {
                  EventComponent event = new EventComponent(each, null, this);
                  this.searchList.getChildren().add(event);
                }
              }
            });

    this.promptEventTitle
        .focusedProperty()
        .addListener(
            (observable, oldFocus, newFocus) -> {
              if (!newFocus) {
                this.currentEvent.getNode().setTitleRequest(this.promptEventTitle.getText());
              }
            });

    this.promptEventTitle
        .textProperty()
        .addListener(
            (observable, oldText, newText) -> {
              this.currentEvent.setText(this.promptEventTitle.getText());
            });

    /* The TextArea internally does not use the onKeyPressed property to handle
     * keyboard input.
     * Therefore, setting onKeyPressed does not remove the original event handler.
     * To prevent TextArea's internal handler for the Enter key, you need to add an
     * event filter that consumes the event.
     * @link: https://stackoverflow.com/questions/26752924/how-to-stop-cursor-from-moving-to-a-new-line-in-a-textarea-when-enter-is-pressed
     */
    this.promptEventTitle.addEventFilter(
        KeyEvent.KEY_PRESSED,
        e -> {
          if (e.getCode() == KeyCode.ENTER) {
            e.consume();
            this.promptEvent.requestFocus();
          }
        });

    this.promptColumnTitle.addEventFilter(
        KeyEvent.KEY_PRESSED,
        e -> {
          if (e.getCode() == KeyCode.ENTER) {
            e.consume();
            this.promptColumn.requestFocus();
          }
        });

    this.promptBoardTitle.addEventFilter(
        KeyEvent.KEY_PRESSED,
        e -> {
          if (e.getCode() == KeyCode.ENTER) {
            e.consume();
            this.promptBoard.requestFocus();
          }
        });

    this.textHolder.getStyleClass().addAll(this.promptEventTitle.getStyleClass());
    this.textHolder.setStyle(promptEventTitle.getStyle());
    this.textHolder.setWrappingWidth(450);
    this.textHolder
        .layoutBoundsProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              this.promptEventTitle.setPrefHeight(
                  this.textHolder.getLayoutBounds().getHeight() + 23);
              this.promptBoardTitle.setPrefHeight(
                  this.textHolder.getLayoutBounds().getHeight() + 23);
            });

    this.promptEventImportanceLevel
        .valueProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              this.currentEvent
                  .getNode()
                  .setImportanceLevelRequest(
                      this.promptEventImportanceLevel.getSelectionModel().getSelectedIndex());
            });

    this.promptEventDuration
        .valueProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              this.currentEvent
                  .getNode()
                  .setDurationRequest(
                      this.promptEventDuration.getSelectionModel().getSelectedIndex() * 3600L);
            });

    this.promptEventNote
        .focusedProperty()
        .addListener(
            (observable, oldFocus, newFocus) -> {
              if (!newFocus)
                this.currentEvent.getNode().setNoteRequest(this.promptEventNote.getText());
            });

    this.promptBoardTitle
        .focusedProperty()
        .addListener(
            (observable, oldFocus, newFocus) -> {
              if (!newFocus)
                this.currentBoard.getNode().setTitleRequest(this.promptBoardTitle.getText());
            });

    this.promptColumnPreset.getItems().addAll("To Do", "In Progress", "Done");

    this.promptColumnPreset
        .valueProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              this.currentColumn
                  .getNode()
                  .setPresetRequest(this.promptColumnPreset.getSelectionModel().getSelectedIndex());
            });

    this.promptColumnTitle
        .focusedProperty()
        .addListener(
            (observable, oldFocus, newFocus) -> {
              if (!newFocus)
                this.currentColumn.getNode().setTitleRequest(this.promptColumnTitle.getText());
            });

    this.dragPane.setMouseTransparent(true);
    this.extraPane.getChildren().add(this.textHolder);

    this.list();
    this.tabPane.requestFocus();
  }

  @FXML
  void list() {
    this.componentToday = null;

    // Add list items
    this.operationList.getChildren().clear();
    this.boardList.getChildren().clear();
    for (structure.Node each : Kanban.getCurrent().getNodes()) {
      // Add to list
      BoardComponent node = new BoardComponent((Board) each, this);
      if (!each.isSpecialized()) {
        this.boardList.getChildren().add(node);
      } else {
        this.operationList.getChildren().add(node);
        if (each.getId() == 1) {
          this.componentToday = node;
        }
      }
    }
    if (componentToday != null) {
      this.componentToday.fire();
    }
  }

  @FXML
  void switchTab(ActionEvent event) {

    // Clears previous selected board
    HashSet<Node> sideButtons = new HashSet<Node>();
    sideButtons.addAll(sidePane.lookupAll(".button"));
    sideButtons.addAll(boardList.lookupAll(".button"));
    for (Node each : sideButtons) {
      each.getStyleClass().remove("selected");
    }

    // Selectes current board
    Button button = (Button) event.getSource();
    button.getStyleClass().add("selected");

    if (button.equals(sideSearch)) {
      // If clicks search
      tabPane.getSelectionModel().select(1);
    } else if (button.equals(sideProfile)) {
      // If clicks settings
      try {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene oldScene = ((Node) event.getSource()).getScene();
        Scene scene =
            new Scene(
                FXMLLoader.load(getClass().getResource("view/settings.fxml")),
                oldScene.getWidth(),
                oldScene.getHeight());
        scene.getStylesheets().add(getClass().getResource("view/default.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      // If clicks board button
      this.tabPane.getSelectionModel().select(0);
    }
  }

  @FXML
  void closePrompt() {
    this.hide(this.promptEvent, this.promptBoard, this.promptColumn);
    if (this.currentEvent != null && this.currentEvent.getNode().isExisting()) {
      this.currentEvent.display();
      this.currentEvent = null;
    }
    if (this.currentBoard != null && this.currentBoard.getNode().isExisting()) {
      this.currentBoard.display();
      this.currentBoard.fire();
    }
    if (this.currentColumn != null && this.currentColumn.getNode().isExisting()) {
      this.currentColumn.display();
      this.currentColumn = null;
    }
    this.tabPane.requestFocus();
  }

  @FXML
  void updateBoard(ActionEvent e) {
    currentBoard.update(e);
  }

  @FXML
  void createBoard(ActionEvent e) {
    Board event = new Board("Untitled Board", "", "", Kanban.getCurrent());
    BoardComponent boardComponent = new BoardComponent(event, this);
    boardComponent.create(e);
  }

  @FXML
  void createBoardRequest(ActionEvent e) {
    this.currentBoard.getNode().createRequest();
    Kanban.getCurrent().generateToday();
    Kanban.getCurrent().generateOverview();
    this.list();
    this.closePrompt();
  }

  @FXML
  void deleteBoardRequest() {
    this.currentBoard.getNode().deleteRequest();
    this.list();
    this.currentBoard = null;
    this.closePrompt();
  }

  @FXML
  void deleteEventRequest() {
    this.currentEvent.getNode().deleteRequest();
    this.currentEvent.getParentComponent().list();
    Kanban.getCurrent().generateToday();
    Kanban.getCurrent().generateOverview();
    this.currentEvent = null;
    this.closePrompt();
  }

  @FXML
  void deleteColumnRequest() {
    this.currentColumn.getNode().deleteRequest();
    this.currentColumn.getParentComponent().list();
    this.currentColumn = null;
    this.closePrompt();
  }

  @FXML
  void createEventRequest() {
    this.currentEvent.getNode().createRequest();
    Kanban.getCurrent().generateToday();
    Kanban.getCurrent().generateOverview();
    this.currentEvent.getParentComponent().list();
    this.closePrompt();
  }

  @FXML
  void createBoardChild(ActionEvent e) {
    this.closePrompt();
    this.currentBoard.createChild(e);
  }

  @FXML
  void createColumnRequest() {
    this.currentColumn.getNode().createRequest();
    Kanban.getCurrent().generateToday();
    Kanban.getCurrent().generateOverview();
    this.currentColumn.getParentComponent().list();
    this.closePrompt();
  }

  /**
   * Returns the inline style for custom accent color.
   *
   * @version 4.0
   * @param hex the color string in HEX, eg #242424
   * @return the inline style string
   */
  protected static String styleAccent(String hex) {
    String style = "";
    if (hex == null || hex.equals("")) hex = "#242424";
    style += "-fx-accent: " + hex + ";";
    style += "-fx-accent-90: " + hex + "e6;";
    style += "-fx-accent-80: " + hex + "cc;";
    style += "-fx-accent-70: " + hex + "b3;";
    style += "-fx-accent-60: " + hex + "99;";
    style += "-fx-accent-50: " + hex + "80;";
    style += "-fx-accent-40: " + hex + "66;";
    style += "-fx-accent-30: " + hex + "4d;";
    style += "-fx-accent-20: " + hex + "33;";
    style += "-fx-accent-10: " + hex + "1a;";
    style += "-fx-accent-5: " + hex + "0d;";
    style += "-fx-accent-light-40: derive(" + hex + ", 40%);";
    return style;
  }

  // drag and drop
  @FXML
  void PanelMouseDragOver(MouseDragEvent event) {
    Button button = (Button) event.getGestureSource();
    button.setLayoutX(event.getSceneX());
    button.setLayoutY(event.getSceneY());
  }

  @FXML
  void MouseDragReleased(MouseDragEvent event) {

    Button btn = (Button) event.getGestureSource();
    if (btn instanceof EventComponent && originalColumn != null) {
      this.originalColumn.list();
    }
    this.currentDragCard = null;
    this.originalColumn = null;

    this.tabPane.requestFocus();
  }

  @FXML
  void onMouseExited() {
    // Cleans card
    if (this.originalColumn != null) {
      this.originalColumn.list();
    }
    this.currentDragCard = null;
    this.dragPane.getChildren().clear();
  }

  /**
   * Sets the given component to invisible
   *
   * @param nodes the nodes to set
   */
  protected void hide(Node... nodes) {
    for (Node each : nodes) {
      if (!each.getStyleClass().contains("hide")) {
        each.getStyleClass().add("hide");
      }
    }
  }

  /**
   * Sets the given component to visible
   *
   * @param nodes the nodes to set
   */
  protected void show(Node... nodes) {
    for (Node each : nodes) {
      while (each.getStyleClass().contains("hide")) {
        each.getStyleClass().remove("hide");
      }
    }
  }
}
