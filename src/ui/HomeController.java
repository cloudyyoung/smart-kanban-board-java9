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

  @FXML
  public Button sideProfile;
  @FXML
  public Circle profileAvatar;
  @FXML
  public Label profileUsername;
  @FXML
  public VBox sidePane;
  @FXML
  public VBox operationList;
  @FXML
  public Button sideSearch;
  @FXML
  public VBox boardList;
  @FXML
  public TabPane tabPane;
  @FXML
  public VBox boardPane;
  @FXML
  public TextField boardTitle;
  @FXML
  public TextField boardNote;
  @FXML
  public Button boardEdit;
  @FXML
  public HBox columnPane;
  @FXML
  public TextField inputSearch;
  @FXML
  public VBox searchList;
  @FXML
  public Pane dragPane;
  @FXML
  public VBox promptEvent;
  @FXML
  public SVGPath promptEventIcon;
  @FXML
  public Label promptEventPromptTitle;
  @FXML
  public VBox promptEventTitleWrapper;
  @FXML
  public TextArea promptEventTitle;
  @FXML
  public Label promptEventLocationBoard;
  @FXML
  public Label promptEventLocationColumn;
  @FXML
  public ComboBox<String> promptEventImportanceLevel;
  @FXML
  public DatePicker promptEventDueDate;
  @FXML
  public ComboBox<String> promptEventDuration;
  @FXML
  public TextArea promptEventNote;
  @FXML
  public Pane extraPane;
  @FXML
  public VBox promptBoard;
  @FXML
  public SVGPath promptBoardIcon;
  @FXML
  public Label promptBoardPromptTitle;
  @FXML
  public TextArea promptBoardTitle;
  @FXML
  public TextArea promptBoardNote;
  @FXML
  public VBox promptColumn;
  @FXML
  public SVGPath promptColumnIcon;
  @FXML
  public Button columnDelete;
  @FXML
  public Label promptColumnPromptTitle;
  @FXML
  public TextArea promptColumnTitle;
  @FXML
  public ComboBox<String> promptColumnPreset;
  @FXML
  public Text textHolder = new Text();
  @FXML
  public Button eventDelete;
  @FXML
  public Button eventCreate;
  @FXML
  public Button columnCreate;
  @FXML
  public Button boardDelete;
  @FXML
  public Button boardChildCreate;
  @FXML
  public Button boardCreate;

  public EventComponent currentEvent;
  public BoardComponent currentBoard;
  public ColumnComponent currentColumn;
  public ColumnComponent originalParent;
  public Button currentDragButton;
  public BoardComponent componentToday;

  @FXML
  void initialize() {

    this.closePrompt();
    this.extraPane.setVisible(false);

    // Intialize label text values
    this.profileUsername.setText(User.getCurrent().getUsername());
    this.profileUsername.setTooltip(new Tooltip(User.getCurrent().getUsername()));

    // Check out kanban
    Kanban.checkout();
    Kanban.getCurrent().generateToday();
    Kanban.getCurrent().generateOverview();

    // Initialize Event panel
    this.promptEventImportanceLevel.getItems().addAll("", "Level 1", "Level 2", "Level 3");
    this.promptEventDuration.getItems().addAll("", "1 Hour", "2 Hours", "3 Hours", "4 Hours", "5 Hours", "6 Hours",
        "7 Hours", "8 Hours", "9 Hours", "10 Hours", "11 Hours", "12 Hours");

    this.promptEventDueDate.valueProperty().addListener((observable, oldDate, newDate) -> {
      Long timestamp = null;
      if (newDate != null) {
        timestamp = Timestamp.valueOf(newDate.atTime(LocalTime.MAX)).getTime() / 1000;
      }
      this.currentEvent.getNode().setDueDateRequest(timestamp);
    });

    this.searchList.getChildren().clear();
    this.inputSearch.textProperty().addListener((observable, oldText, newText) -> {
      this.searchList.getChildren().clear();
      if (!newText.equals("")) {
        ArrayList<structure.Node> list = Kanban.getCurrent().search(newText);
        for (structure.Node each : list) {
          EventComponent event = new EventComponent(each, null, this);
          this.searchList.getChildren().add(event);
        }
      }
    });

    this.promptEventTitle.focusedProperty().addListener((observable, oldFocus, newFocus) -> {
      if (!newFocus) {
        this.currentEvent.getNode().setTitleRequest(this.promptEventTitle.getText());
      }
    });

    this.promptEventTitle.textProperty().addListener((observable, oldText, newText) -> {
      this.currentEvent.setText(this.promptEventTitle.getText());
    });

    // The TextArea internally does not use the onKeyPressed property to handle
    // keyboard input.
    // Therefore, setting onKeyPressed does not remove the original event handler.
    // To prevent TextArea's internal handler for the Enter key, you need to add an
    // event filter that consumes the event.
    // @link:
    // https://stackoverflow.com/questions/26752924/how-to-stop-cursor-from-moving-to-a-new-line-in-a-textarea-when-enter-is-pressed
    this.promptEventTitle.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
      if (e.getCode() == KeyCode.ENTER) {
        e.consume();
        this.promptEvent.requestFocus();
      }
    });

    this.textHolder.getStyleClass().addAll(this.promptEventTitle.getStyleClass());
    this.textHolder.setStyle(promptEventTitle.getStyle());
    this.textHolder.setWrappingWidth(450);
    this.textHolder.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
      this.promptEventTitle.setPrefHeight(this.textHolder.getLayoutBounds().getHeight() + 23);
      this.promptBoardTitle.setPrefHeight(this.textHolder.getLayoutBounds().getHeight() + 23);
    });

    this.extraPane.getChildren().add(this.textHolder);

    this.promptEventImportanceLevel.valueProperty().addListener((observable, oldValue, newValue) -> {
      this.currentEvent.getNode()
          .setImportanceLevelRequest(this.promptEventImportanceLevel.getSelectionModel().getSelectedIndex());
    });

    this.promptEventDuration.valueProperty().addListener((observable, oldValue, newValue) -> {
      this.currentEvent.getNode()
          .setDurationRequest(this.promptEventDuration.getSelectionModel().getSelectedIndex() * 3600L);
    });

    this.promptEventNote.focusedProperty().addListener((observable, oldFocus, newFocus) -> {
      if (!newFocus)
        this.currentEvent.getNode().setNoteRequest(this.promptEventNote.getText());
    });

    this.promptBoardTitle.focusedProperty().addListener((observable, oldFocus, newFocus) -> {
      if (!newFocus)
        this.currentBoard.getNode().setTitleRequest(this.promptBoardTitle.getText());
    });

    this.promptColumnPreset.getItems().addAll("To Do", "In Progress", "Done");

    this.promptColumnPreset.valueProperty().addListener((observable, oldValue, newValue) -> {
      this.currentColumn.getNode().setPresetRequest(this.promptColumnPreset.getSelectionModel().getSelectedIndex());
    });

    this.promptColumnTitle.focusedProperty().addListener((observable, oldFocus, newFocus) -> {
      if (!newFocus)
        this.currentColumn.getNode().setTitleRequest(this.promptColumnTitle.getText());
    });

    this.dragPane.setMouseTransparent(true);

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
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("settings.fxml")), oldScene.getWidth(),
            oldScene.getHeight());
        scene.getStylesheets().add(getClass().getResource("default.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
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

  public static String styleAccent(String hex) {
    String style = "";
    if (hex == null || hex.equals(""))
      hex = "#242424";
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
    if (btn instanceof EventComponent && originalParent != null) {
      EventComponent current = (EventComponent) btn;
      originalParent.getEventList().getChildren().add(current);
    }
    this.currentDragButton = null;
    this.originalParent = null;
  }

  public void hide(Node... nodes) {
    for (Node each : nodes) {
      if (!each.getStyleClass().contains("hide")) {
        each.getStyleClass().add("hide");
      }
    }
  }

  public void show(Node... nodes) {
    for (Node each : nodes) {
      while (each.getStyleClass().contains("hide")) {
        each.getStyleClass().remove("hide");
      }
    }
  }
}
