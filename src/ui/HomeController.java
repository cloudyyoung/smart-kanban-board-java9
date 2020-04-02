package ui;

import java.util.HashSet;
import java.util.Set;

import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;

import structure.*;
import ui.component.*;

public class HomeController {

  @FXML private Button sideProfile;

  @FXML private Circle profileAvatar;

  @FXML private Label profileUsername;

  @FXML private VBox sidePane;

  @FXML private Button sideSearch;

  @FXML private Button sideToday;

  @FXML private VBox boardList;

  @FXML private TabPane tabPane;

  @FXML private VBox boardPane;

  @FXML private TextField boardTitle;

  @FXML private TextField boardNote;

  @FXML private Button boardAddColumn;

  @FXML private HBox columnPane;

  @FXML private ScrollPane detailPane;

  @FXML private TextArea detailTitle;

  @FXML private Button detailAdd2Today;

  @FXML private Button detailDueDate;

  @FXML private Button detailImportance;

  @FXML private TextField detailNote;

  @FXML private Pane dragPane;

  @FXML
  void initialize() {
    // Intialize label text values
    profileUsername.setText(User.current.getUsername());
    profileUsername.setTooltip(new Tooltip(User.current.getUsername()));

    // Hide detailPane
    detailPane.getStyleClass().add("hide");

    // Check out kanban
    Kanban.checkout();
    Kanban.generateToday();

    // Add list items
    boardList.getChildren().clear();
    for (structure.Node each : Kanban.current.getChildrenNodes()) {
      if (each.getId() >= 100) {
        // Add to list
        BoardComponent node = new BoardComponent((Board) each);
        boardList.getChildren().add(node);
      } else if (each.getId() == 1) {
        sideToday.setStyle(styleAccent(((Board) each).getColor()));
      }
    }

    // Bind button action event for sidePane buttons
    Set<Node> sideButtons = new HashSet<Node>();
    sideButtons.addAll(sidePane.lookupAll(".button"));
    sideButtons.addAll(boardList.lookupAll(".button"));

    for (Node each : sideButtons) {
      System.out.println(each.getId());
      Button btn = (Button) each;
      btn.setOnAction(
          event -> {
            for (Node eachBtn : sideButtons) {
              eachBtn.getStyleClass().remove("selected");
            }
            Node current = ((Node) event.getSource());
            current.getStyleClass().add("selected");
            if (current.equals(sideSearch)) {
              tabPane.getSelectionModel().select(1);
            } else {
              sideSelectDisplay(current);
            }
          });
    }

    sidePane.requestFocus();
    sideToday.fire();
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

  void sideSelectDisplay(Node btn) {
    String idRaw = btn.getId();
    tabPane.getSelectionModel().select(0);
    if (!idRaw.contains("board-")) {
      return;
    }
    String idStr = idRaw.split("-")[1];
    Integer id = Integer.parseInt(idStr);
    Board node = (Board) Kanban.current.getNode(id);
    boardPane.setStyle(styleAccent(node.getColor()));

    boardTitle.setText(node.getTitle());
    boardNote.setText(!node.getNote().equals("") ? node.getNote() : "No description");

    boardTitle.setDisable(id < 100);
    boardNote.setDisable(id < 100);

    columnPane.getChildren().clear();
    for (structure.Node each : node.getChildrenNodes()) {
      Node col = new ColumnComponent((Column) each);
      columnPane.getChildren().add(col);
    }
  }
}
