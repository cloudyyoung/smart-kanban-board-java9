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
import ui.component.*;

public class HomeController {

  @FXML private Button sideProfile;
  @FXML private Circle profileAvatar;
  @FXML private Label profileUsername;
  @FXML private VBox sidePane;
  @FXML private VBox operationList;
  @FXML private Button sideSearch;
  @FXML private VBox boardList;
  @FXML private TabPane tabPane;
  @FXML private VBox boardPane;
  @FXML private TextField boardTitle;
  @FXML private TextField boardNote;
  @FXML private Button boardEdit;
  @FXML private HBox columnPane;
  @FXML private TextField inputSearch;
  @FXML private VBox searchList;
  @FXML private Pane dragPane;
  @FXML private VBox promptEvent;
  @FXML private SVGPath promptEventIcon;
  @FXML private Label promptEventPromptTitle;
  @FXML private VBox promptEventTitleWrapper;
  @FXML private TextArea promptEventTitle;
  @FXML private Label promptEventLocationBoard;
  @FXML private Label promptEventLocationColumn;
  @FXML private ComboBox<String> promptEventImportanceLevel;
  @FXML private DatePicker promptEventDueDate;
  @FXML private ComboBox<String> promptEventDuration;
  @FXML private TextArea promptEventNote;
  @FXML private Pane extraPane;
  @FXML private VBox promptBoard;
  @FXML private SVGPath promptBoardIcon;
  @FXML private Label promptBoardPromptTitle;
  @FXML private TextArea promptBoardTitle;
  @FXML private TextArea promptBoardNote;
  @FXML private VBox promptColumn;
  @FXML private SVGPath promptColumnIcon;
  @FXML private Label promptColumnPromptTitle;
  @FXML private TextArea promptColumnTitle;
  @FXML private ComboBox<String> promptColumnPreset;

  public static EventComponent currentEvent;
  public static BoardComponent currentBoard;
  public static ColumnComponent currentColumn;

  private Text textHolder = new Text();

  @FXML
  void initialize() {

    BoardComponent.boardPane = boardPane;
    BoardComponent.boardTitle = boardTitle;
    BoardComponent.boardNote = boardNote;
    BoardComponent.columnPane = columnPane;
    BoardComponent.sidePane = sidePane;
    BoardComponent.boardList = boardList;
    BoardComponent.tabPane = tabPane;
    BoardComponent.boardEdit = boardEdit;
    EventComponent.promptEvent = promptEvent;
    EventComponent.promptEventIcon = promptEventIcon;
    EventComponent.promptEventPromptTitle = promptEventPromptTitle;
    EventComponent.promptEventTitle = promptEventTitle;
    EventComponent.promptEventLocationBoard = promptEventLocationBoard;
    EventComponent.promptEventLocationColumn = promptEventLocationColumn;
    EventComponent.promptEventImportanceLevel = promptEventImportanceLevel;
    EventComponent.promptEventDueDate = promptEventDueDate;
    EventComponent.promptEventDuration = promptEventDuration;
    EventComponent.promptEventNote = promptEventNote;
    EventComponent.textHolder = textHolder;
    ColumnComponent.promptColumn = promptColumn;
    ColumnComponent.promptColumnPromptTitle = promptColumnPromptTitle;
    ColumnComponent.promptColumnTitle = promptColumnTitle;
    ColumnComponent.promptColumnPreset = promptColumnPreset;

    this.closePrompt();
    extraPane.setVisible(false);

    // Intialize label text values
    profileUsername.setText(User.getCurrent().getUsername());
    profileUsername.setTooltip(new Tooltip(User.getCurrent().getUsername()));

    // Check out kanban
    Kanban.checkout();
    Kanban.getCurrent().generateToday();

    // Initialize Event panel
    promptEventImportanceLevel.getItems().addAll("", "Level 1", "Level 2", "Level 3");
    promptEventDuration
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

    promptEventDueDate
        .valueProperty()
        .addListener(
            (observable, oldDate, newDate) -> {
              Long timestamp = null;
              if (newDate != null) {
                timestamp = Timestamp.valueOf(newDate.atTime(LocalTime.MAX)).getTime() / 1000;
              }
              currentEvent.getNode().setDueDate(timestamp);
            });

    searchList.getChildren().clear();
    inputSearch
        .textProperty()
        .addListener(
            (observable, oldText, newText) -> {
              searchList.getChildren().clear();
              if (!newText.equals("")) {
                ArrayList<structure.Node> list = Kanban.getCurrent().search(newText);
                for (structure.Node each : list) {
                  EventComponent event = new EventComponent(each, null);
                  searchList.getChildren().add(event);
                }
              }
            });

    promptEventTitle
        .focusedProperty()
        .addListener(
            (observable, oldFocus, newFocus) -> {
              if (!newFocus) {
                currentEvent.getNode().setTitleRequest(promptEventTitle.getText());
              }
            });

    promptEventTitle
        .textProperty()
        .addListener(
            (observable, oldText, newText) -> {
              currentEvent.setText(promptEventTitle.getText());
            });

    // The TextArea internally does not use the onKeyPressed property to handle
    // keyboard input.
    // Therefore, setting onKeyPressed does not remove the original event handler.
    // To prevent TextArea's internal handler for the Enter key, you need to add an
    // event filter that consumes the event.
    // @link:
    // https://stackoverflow.com/questions/26752924/how-to-stop-cursor-from-moving-to-a-new-line-in-a-textarea-when-enter-is-pressed
    promptEventTitle.addEventFilter(
        KeyEvent.KEY_PRESSED,
        e -> {
          if (e.getCode() == KeyCode.ENTER) {
            e.consume();
            promptEvent.requestFocus();
          }
        });

    textHolder.getStyleClass().addAll(promptEventTitle.getStyleClass());
    textHolder.setStyle(promptEventTitle.getStyle());
    textHolder.setWrappingWidth(450);
    textHolder
        .layoutBoundsProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              promptEventTitle.setPrefHeight(textHolder.getLayoutBounds().getHeight() + 23);
              promptBoardTitle.setPrefHeight(textHolder.getLayoutBounds().getHeight() + 23);
            });

    extraPane.getChildren().add(textHolder);

    promptEventImportanceLevel
        .valueProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              currentEvent
                  .getNode()
                  .setImportanceLevelRequest(
                      promptEventImportanceLevel.getSelectionModel().getSelectedIndex());
            });

    promptEventDuration
        .valueProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              currentEvent
                  .getNode()
                  .setDurationRequest(
                      promptEventDuration.getSelectionModel().getSelectedIndex() * 3600L);
            });

    promptEventNote
        .focusedProperty()
        .addListener(
            (observable, oldFocus, newFocus) -> {
              if (!newFocus) currentEvent.getNode().setNoteRequest(promptEventNote.getText());
            });

    sidePane.requestFocus();
    listBoard();
  }

  @FXML
  void listBoard() {
    BoardComponent componentToday = null;

    // Add list items
    operationList.getChildren().clear();
    boardList.getChildren().clear();
    for (structure.Node each : Kanban.getCurrent().getNodes()) {
      // Add to list
      BoardComponent node = new BoardComponent((Board) each);
      if (each.getId() >= 100) {
        boardList.getChildren().add(node);
      } else {
        operationList.getChildren().add(node);
        if (each.getId() == 1) {
          componentToday = node;
        }
      }
    }
    componentToday.fire();
  }

  @FXML
  void switchTab(ActionEvent event) {

    HashSet<Node> sideButtons = new HashSet<Node>();
    sideButtons.addAll(sidePane.lookupAll(".button"));
    sideButtons.addAll(boardList.lookupAll(".button"));

    for (Node each : sideButtons) {
      each.getStyleClass().remove("selected");
    }

    Button button = (Button) event.getSource();
    button.getStyleClass().add("selected");

    if (button.equals(sideSearch)) {
      tabPane.getSelectionModel().select(1);
    } else if (button.equals(sideProfile)) {
      try {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene oldScene = ((Node) event.getSource()).getScene();
        Scene scene =
            new Scene(
                FXMLLoader.load(getClass().getResource("settings.fxml")),
                oldScene.getWidth(),
                oldScene.getHeight());
        scene.getStylesheets().add(getClass().getResource("default.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      tabPane.getSelectionModel().select(0);
    }
  }

  @FXML
  void closePrompt() {
    promptEvent.getStyleClass().setAll("prompt-cover", "hide");
    promptBoard.getStyleClass().setAll("prompt-cover", "hide");
    promptColumn.getStyleClass().setAll("prompt-cover", "hide");
    if (currentEvent != null) {
      currentEvent.update();
      currentEvent = null;
    }
    if (currentBoard != null) {
      currentBoard.update();
      currentBoard.fire();
      currentBoard = null;
    }
    if (currentColumn != null) {
      currentColumn.update();
      currentColumn = null;
    }
  }

  @FXML
  void editBoard() {
    textHolder.textProperty().bind(promptBoardTitle.textProperty());
    promptBoard.getStyleClass().remove("hide");
    promptBoardTitle.setText(currentBoard.getNode().getTitle());
    promptBoardNote.setText(currentBoard.getNode().getNote());
  }

  @FXML
  void deleteBoard() {
    currentBoard.getNode().removeRequest();
    this.listBoard();
    this.closePrompt();
  }

  @FXML
  void deleteEvent() {
    currentEvent.getNode().removeRequest();
    Kanban.getCurrent().generateToday();
    currentEvent.getParentComponent().listEvent();
    this.closePrompt();
  }

  @FXML
  void deleteColumn() {
    currentColumn.getNode().removeRequest();
    currentColumn.getParentComponent().listColumn();
    this.closePrompt();
  }

  public static String styleAccent(String hex) {
    String style = "";
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
    return style;
  }
}
