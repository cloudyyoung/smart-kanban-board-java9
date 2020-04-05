package ui;

import java.util.HashSet;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
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

  @FXML private Button boardAddColumn;

  @FXML private HBox columnPane;

  @FXML private Pane dragPane;

  @FXML private VBox promptEvent;

  @FXML private SVGPath promptEventIcon;

  @FXML private Label promptEventPromptTitle;

  @FXML private TextArea promptEventTitle;

  @FXML private Label promptEventLocationBoard;

  @FXML private Label promptEventLocationColumn;

  @FXML private ComboBox<String> promptEventImportanceLevel;

  @FXML private DatePicker promptEventDueDate;

  @FXML private ComboBox<String> promptEventDuration;

  @FXML private TextArea promptEventNote;

  public static EventComponent currentEvent;

  @FXML
  void initialize() {

    BoardComponent.boardPane = boardPane;
    BoardComponent.boardTitle = boardTitle;
    BoardComponent.boardNote = boardNote;
    BoardComponent.columnPane = columnPane;
    BoardComponent.sidePane = sidePane;
    BoardComponent.boardList = boardList;
    BoardComponent.tabPane = tabPane;
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

    promptEvent.getStyleClass().add("hide");

    // Intialize label text values
    profileUsername.setText(User.current.getUsername());
    profileUsername.setTooltip(new Tooltip(User.current.getUsername()));

    // Check out kanban
    Kanban.checkout();
    Kanban.current.generateToday();

    BoardComponent componentToday = null;

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

    // Add list items
    boardList.getChildren().clear();
    for (structure.Node each : Kanban.current.getChildrenNodes()) {
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

    sidePane.requestFocus();
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
    promptEvent.getStyleClass().add("hide");
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
