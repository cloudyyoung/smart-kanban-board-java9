package ui.component;

import java.util.HashSet;

import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;

import structure.*;
import ui.*;

public class BoardComponent extends Button{

  @FXML private Button button;

  private Board node;
  private VBox boardPane;
  private TextField boardTitle;
  private TextField boardNote;
  private HBox columnPane;
  private VBox sidePane;
  private VBox boardList;
  private TabPane tabPane;


  public BoardComponent(Board node, VBox boardPane, TextField boardTitle, TextField boardNote, HBox columnPane, VBox sidePane, VBox boardList, TabPane tabPane) {
    this.node = node;
    this.boardPane = boardPane;
    this.boardTitle = boardTitle;
    this.boardNote = boardNote;
    this.columnPane = columnPane;
    this.sidePane = sidePane;
    this.boardList = boardList;

    load();

    this.setText(node.getTitle());
    this.setId("board-" + node.getId());
    this.setStyle(HomeController.styleAccent(node.getColor()));

    SVGPath svg = new SVGPath();
    svg.setContent(
        "M 5 5 L 5 6 L 5 27 L 27 27 L 27 5 L 5 5 z M 7 7 L 25 7 L 25 9 L 7 9 L 7 7 z M 7 11 L 25 11 L 25 25 L 7 25 L 7 11 z");
    HBox hbox = new HBox();
    hbox.getChildren().add(svg);
    this.setGraphic(hbox);

    this.setOnAction(e -> {
      
      HashSet<Node> sideButtons = new HashSet<Node>();
      sideButtons.addAll(sidePane.lookupAll(".button"));
      sideButtons.addAll(boardList.lookupAll(".button"));

      for(Node each : sideButtons){
        each.getStyleClass().remove("selected");
      }

      this.getStyleClass().add("selected");

      this.tabPane.getSelectionModel().select(0);

      boardPane.setStyle(HomeController.styleAccent(node.getColor()));
      boardTitle.setText(this.node.getTitle());
      boardNote.setText(!this.node.getNote().equals("") ? node.getNote() : "No description");

      boardTitle.setDisable(this.node.getId() < 100);
      boardNote.setDisable(this.node.getId() < 100);

      columnPane.getChildren().clear();
      for (structure.Node each : node.getChildrenNodes()) {
        Node col = new ColumnComponent((Column) each);
        columnPane.getChildren().add(col);
      }
    });
  }

  public Board getNode() {
    return this.node;
  }

  void load() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("board.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
