package ui.component;

import java.util.HashSet;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;

import structure.*;
import ui.*;

public class BoardComponent extends Button {

  @FXML private Button button;

  private Board node;
  public static VBox boardPane;
  public static TextField boardTitle;
  public static TextField boardNote;
  public static HBox columnPane;
  public static VBox sidePane;
  public static VBox boardList;
  public static TabPane tabPane;
  public static Button boardEdit;

  public BoardComponent(Board node) {
    this.node = node;

    this.load();
    this.display();
  }

  @FXML
  void open(ActionEvent e){
    HomeController.currentBoard = this;
    boardEdit.setVisible(this.node.getId() >= 100);

    HashSet<Node> sideButtons = new HashSet<Node>();
    sideButtons.addAll(sidePane.lookupAll(".button"));
    sideButtons.addAll(boardList.lookupAll(".button"));

    for (Node each : sideButtons) {
      each.getStyleClass().remove("selected");
    }

    this.getStyleClass().add("selected");
    this.display();
    this.list();
  }

  public void display() {
    this.setText(this.node.getTitle());
    this.setId("board-" + this.node.getId());
    this.setStyle(HomeController.styleAccent(this.node.getColor()));

    SVGPath svg = new SVGPath();
    svg.setContent(
        "M 5 5 L 5 6 L 5 27 L 27 27 L 27 5 L 5 5 z M 7 7 L 25 7 L 25 9 L 7 9 L 7 7 z M 7 11 L 25 11 L 25 25 L 7 25 L 7 11 z");
    HBox hbox = new HBox();
    hbox.getChildren().add(svg);
    this.setGraphic(hbox);

    tabPane.getSelectionModel().select(0);

    boardPane.setStyle(HomeController.styleAccent(this.node.getColor()));
    boardTitle.setText(this.node.getTitle());
    boardNote.setText(!this.node.getNote().equals("") ? this.node.getNote() : "No description");
  }

  public void list() {
    columnPane.getChildren().clear();
    for (structure.Node each : this.node.getNodes()) {
      Node col = new ColumnComponent((Column) each, this);
      columnPane.getChildren().add(col);
    }
  }

  public Board getNode() {
    return this.node;
  }

  public String getColor() {
    return this.node.getColor();
  }

  private final void load() {
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
