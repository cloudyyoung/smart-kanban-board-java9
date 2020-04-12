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
  private Button columnCreate;

  public static EventComponent currentEvent;
  public static BoardComponent currentBoard;
  public static ColumnComponent currentColumn;
  public static VBox originalParent;
  public static Button currentDragButton;

  @FXML
  void initialize() {

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
    promptEventDuration.getItems().addAll("", "1 Hour", "2 Hours", "3 Hours", "4 Hours", "5 Hours", "6 Hours",
        "7 Hours", "8 Hours", "9 Hours", "10 Hours", "11 Hours", "12 Hours");

    promptEventDueDate.valueProperty().addListener((observable, oldDate, newDate) -> {
      Long timestamp = null;
      if (newDate != null) {
        timestamp = Timestamp.valueOf(newDate.atTime(LocalTime.MAX)).getTime() / 1000;
      }
      currentEvent.getNode().setDueDateRequest(timestamp);
    });

    searchList.getChildren().clear();
    inputSearch.textProperty().addListener((observable, oldText, newText) -> {
      searchList.getChildren().clear();
      if (!newText.equals("")) {
        ArrayList<structure.Node> list = Kanban.getCurrent().search(newText);
        for (structure.Node each : list) {
          EventComponent event = new EventComponent(each, null, this);
          searchList.getChildren().add(event);
        }
      }
    });

    promptEventTitle.focusedProperty().addListener((observable, oldFocus, newFocus) -> {
      if (!newFocus) {
        currentEvent.getNode().setTitleRequest(promptEventTitle.getText());
      }
    });

    promptEventTitle.textProperty().addListener((observable, oldText, newText) -> {
      currentEvent.setText(promptEventTitle.getText());
    });

    // The TextArea internally does not use the onKeyPressed property to handle
    // keyboard input.
    // Therefore, setting onKeyPressed does not remove the original event handler.
    // To prevent TextArea's internal handler for the Enter key, you need to add an
    // event filter that consumes the event.
    // @link:
    // https://stackoverflow.com/questions/26752924/how-to-stop-cursor-from-moving-to-a-new-line-in-a-textarea-when-enter-is-pressed
    promptEventTitle.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
      if (e.getCode() == KeyCode.ENTER) {
        e.consume();
        promptEvent.requestFocus();
      }
    });

    textHolder.getStyleClass().addAll(promptEventTitle.getStyleClass());
    textHolder.setStyle(promptEventTitle.getStyle());
    textHolder.setWrappingWidth(450);
    textHolder.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
      promptEventTitle.setPrefHeight(textHolder.getLayoutBounds().getHeight() + 23);
      promptBoardTitle.setPrefHeight(textHolder.getLayoutBounds().getHeight() + 23);
    });

    extraPane.getChildren().add(textHolder);

    promptEventImportanceLevel.valueProperty().addListener((observable, oldValue, newValue) -> {
      currentEvent.getNode()
          .setImportanceLevelRequest(promptEventImportanceLevel.getSelectionModel().getSelectedIndex());
    });

    promptEventDuration.valueProperty().addListener((observable, oldValue, newValue) -> {
      currentEvent.getNode().setDurationRequest(promptEventDuration.getSelectionModel().getSelectedIndex() * 3600L);
    });

    promptEventNote.focusedProperty().addListener((observable, oldFocus, newFocus) -> {
      if (!newFocus)
        currentEvent.getNode().setNoteRequest(promptEventNote.getText());
    });

    promptBoardTitle.focusedProperty().addListener((observable, oldFocus, newFocus) -> {
      if (!newFocus)
        currentColumn.getNode().setTitleRequest(promptColumnTitle.getText());
    });

    promptColumnPreset.getItems().addAll("To Do", "In Progress", "Done");

    promptColumnPreset.valueProperty().addListener((observable, oldValue, newValue) -> {
      currentColumn.getNode().setPresetRequest(promptColumnPreset.getSelectionModel().getSelectedIndex());
    });

    promptColumnTitle.focusedProperty().addListener((observable, oldFocus, newFocus) -> {
      if (!newFocus)
        currentColumn.getNode().setTitleRequest(promptColumnTitle.getText());
    });

    sidePane.requestFocus();
    listBoard();

    // set transparent to 100
    dragPane.setMouseTransparent(true);
  }

  @FXML
  void listBoard() {
    BoardComponent componentToday = null;

    // Add list items
    operationList.getChildren().clear();
    boardList.getChildren().clear();
    for (structure.Node each : Kanban.getCurrent().getNodes()) {
      // Add to list
      BoardComponent node = new BoardComponent((Board) each, this);
      if (!each.isSpecialized()) {
        boardList.getChildren().add(node);
      } else {
        operationList.getChildren().add(node);
        if (each.getId() == 1) {
          componentToday = node;
        }
      }
    }
    if (componentToday != null) {
      componentToday.fire();
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
      tabPane.getSelectionModel().select(0);
    }
  }

  @FXML
  void closePrompt() {
    promptEvent.getStyleClass().setAll("prompt-cover", "hide");
    promptBoard.getStyleClass().setAll("prompt-cover", "hide");
    promptColumn.getStyleClass().setAll("prompt-cover", "hide");
    if (currentEvent != null) {
      currentEvent.display();
      currentEvent = null;
    }
    if (currentBoard != null) {
      currentBoard.display();
      currentBoard.fire();
      currentBoard = null;
    }
    if (currentColumn != null) {
      currentColumn.display();
      currentColumn = null;
    }
  }

  @FXML
  void updateBoardRequest() {
    textHolder.textProperty().bind(promptBoardTitle.textProperty());
    promptBoard.getStyleClass().remove("hide");
    promptBoardTitle.setText(currentBoard.getNode().getTitle());
    promptBoardNote.setText(currentBoard.getNode().getNote());
  }

  @FXML
  void deleteBoardRequest() {
    currentBoard.getNode().removeRequest();
    this.listBoard();
    this.closePrompt();
  }

  @FXML
  void deleteEventRequest() {
    currentEvent.getNode().removeRequest();
    Kanban.getCurrent().generateToday();
    currentEvent.getParentComponent().list();
    this.closePrompt();
  }

  @FXML
  void deleteColumnRequest() {
    currentColumn.getNode().removeRequest();
    currentColumn.getParentComponent().list();
    this.closePrompt();
  }

  @FXML
  void createEventRequest(){
    Result res = currentEvent.getNode().createRequest();
    System.out.println(res);
  }

  @FXML
  void createColumnRequest(){
    Result res = currentColumn.getNode().createRequest();
    System.out.println(res);
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
    if (btn.getParent().getClass() == VBox.class) {
    } else {
      originalParent.getChildren().add(btn);
    } 
    currentDragButton = null;
    originalParent = null;
  }
}
