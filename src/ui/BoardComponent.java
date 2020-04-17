package ui;

import java.util.HashSet;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;

import structure.*;

/**
 * The JavaFX Controller for component.board.fxml.
 *
 * @author Cloudy Young
 * @since 4.0
 * @version 4.0
 */
public class BoardComponent extends Button {

  @FXML private Button button;

  /** The paired {@code Node} for the component. */
  private Board node;

  /** The parent controller instance. */
  private HomeController parentController;

  protected BoardComponent(Board node, HomeController parentController) {
    this.node = node;
    this.parentController = parentController;

    this.loadDisplay();
    if (this.node.isExisting()) {
      this.display();
    }
  }

  @FXML
  void open(ActionEvent e) {
    if (this.equals(this.parentController.componentToday)) {
      Kanban.getCurrent().generateToday();
      Kanban.getCurrent().generateOverview();
    }

    this.parentController.currentBoard = this;
    this.parentController.boardEdit.setVisible(!this.node.isSpecialized());

    HashSet<Node> sideButtons = new HashSet<Node>();
    sideButtons.addAll(this.parentController.sidePane.lookupAll(".button"));
    sideButtons.addAll(this.parentController.boardList.lookupAll(".button"));

    for (Node each : sideButtons) {
      each.getStyleClass().remove("selected");
    }

    this.getStyleClass().add("selected");
    this.display();
    this.list();
  }

  /**
   * Displays the component according to the paired {@code Node}.
   */
  protected void display() {
    this.setText(this.node.getTitle());
    this.setId("board-" + this.node.getId());
    this.setStyle(HomeController.styleAccent(this.node.getColor()));

    SVGPath svg = new SVGPath();
    svg.setContent(
        "M 5 5 L 5 6 L 5 27 L 27 27 L 27 5 L 5 5 z M 7 7 L 25 7 L 25 9 L 7 9 L 7 7 z M 7 11 L 25 11 L 25 25 L 7 25 L 7 11 z");
    HBox hbox = new HBox();
    hbox.getChildren().add(svg);
    this.setGraphic(hbox);

    this.parentController.tabPane.getSelectionModel().select(0);

    this.parentController.boardPane.setStyle(HomeController.styleAccent(this.node.getColor()));
    this.parentController.boardTitle.setText(this.node.getTitle());
    this.parentController.boardNote.setText(
        !this.node.getNote().equals("") ? this.node.getNote() : "No description");
  }

  /**
   * Lists the children nodes of the paired {@code Node}
   */
  protected void list() {
    this.parentController.columnPane.getChildren().clear();
    for (structure.Node each : this.node.getNodes()) {
      Node col = new ColumnComponent((Column) each, this, this.parentController);
      this.parentController.columnPane.getChildren().add(col);
    }
  }

  /**
   * Gets the paired {@code Column}.
   * 
   * @return the paired {@code Column}
   */
  protected Board getNode() {
    return this.node;
  }

  /**
   * Returns the color of the {@code Column}.
   * 
   * @return the color of the {@code Column}
   */
  protected String getColor() {
    return this.node.getColor();
  }

  @FXML
  protected void update(ActionEvent e) {
    this.displayPrompt();
    this.parentController.promptBoardPromptTitle.setText("Edit Board");
    this.parentController.show(
        this.parentController.boardDelete, this.parentController.boardChildCreate);
    this.parentController.hide(this.parentController.boardCreate);
  }

  @FXML
  protected void create(ActionEvent e) {
    this.displayPrompt();
    this.parentController.promptBoardPromptTitle.setText("Create Board");
    this.parentController.hide(
        this.parentController.boardDelete, this.parentController.boardChildCreate);
    this.parentController.show(this.parentController.boardCreate);
  }

  /** 
   * Displays the prompt.
   */
  private void displayPrompt() {
    this.parentController.currentBoard = this;
    this.parentController
        .textHolder
        .textProperty()
        .bind(this.parentController.promptBoardTitle.textProperty());
    this.parentController.promptBoardTitle.setText(
        this.parentController.currentBoard.getNode().getTitle());
    this.parentController.promptBoardNote.setText(
        this.parentController.currentBoard.getNode().getNote());
    this.parentController.show(this.parentController.promptBoard);
  }

  @FXML
  protected void createChild(ActionEvent e) {
    Column column = new Column("Untitled Column", "", 0, this.node);
    ColumnComponent ColumnComponent = new ColumnComponent(column, this, this.parentController);
    ColumnComponent.create(e);
  }

  /**
   * Loads the fxml of the compoennt.
   */
  private final void loadDisplay() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/component.board.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
