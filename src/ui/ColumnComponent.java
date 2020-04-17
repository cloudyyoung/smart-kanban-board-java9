package ui;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import structure.*;

/**
 * The JavaFX Controller for component.column.fxml.
 *
 * @author Cloudy Young, Jimschenchen
 * @since 4.0
 * @version 4.0
 */
public class ColumnComponent extends VBox {

  @FXML private Label eventCount;
  @FXML private TextField columnTitle;
  @FXML private Button eventCreate;
  @FXML private Button columnEdit;
  @FXML private VBox eventList;

  /** The paired {@code Node} for the component. */
  private Column node;

  /** The parent of the paired {@code Node} for the component. */
  private BoardComponent parent;

  /** The parent controller instance. */
  private HomeController parentController;

  protected ColumnComponent(Column node, BoardComponent parent, HomeController parentController) {
    super();
    this.node = node;
    this.parent = parent;
    this.parentController = parentController;
    this.loadDisplay();
  }

  /**
   * Gets the paired {@code Column}.
   *
   * @return the paired {@code Column}
   */
  protected Column getNode() {
    return this.node;
  }

  /**
   * Returns the color of the {@code Column}.
   *
   * @return the color of the {@code Column}
   */
  protected String getColor() {
    return this.parent.getColor();
  }

  /**
   * Gets the parent of the paired {@code Column}.
   *
   * @return the parent {@code Column}
   */
  protected BoardComponent getParentComponent() {
    return this.parent;
  }

  /** Returns the event list component. */
  protected VBox getEventList() {
    return this.eventList;
  }

  /** Loads the fxml of the compoennt. */
  private final void loadDisplay() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/component.column.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** Lists the children nodes of the paired {@code Node} */
  protected void list() {
    ArrayList<structure.Node> list = null;
    if (this.node.getParent().isSpecialized()) {
      list = this.node.getNodes(structure.Node.SORT_BY_PRIORITY, structure.Node.ORDER_BY_ASC);
    } else {
      list = this.node.getNodes();
    }

    eventList.getChildren().clear();

    for (structure.Node each : list) {
      EventComponent event = new EventComponent(each, this, this.parentController);
      eventList.getChildren().add(event);
      System.out.println(event);
    }
    eventCount.setText(list.size() + "");
  }

  /** Displays the component according to the paired {@code Node}. */
  protected void display() {
    columnTitle.setText(this.node.getTitle());
  }

  @FXML
  void initialize() {
    this.display();
    this.list();
    if (this.node.getParent().isSpecialized()) {
      this.parentController.hide(this.columnEdit, this.eventCreate);
    } else {
      this.parentController.show(this.columnEdit, this.eventCreate);
    }
  }

  @FXML
  void update(ActionEvent e) {
    this.displayPrompt();
    this.parentController.hide(this.parentController.columnCreate);
    if (this.node.isOnlyPreset()) {
      this.parentController.hide(this.parentController.columnDelete);
    } else {
      this.parentController.show(this.parentController.columnDelete);
    }
    this.parentController.promptColumnPromptTitle.setText("Edit column");
  }

  @FXML
  void create(ActionEvent e) {
    this.displayPrompt();
    this.parentController.hide(this.parentController.columnDelete);
    this.parentController.show(this.parentController.columnCreate);
    this.parentController.promptColumnPromptTitle.setText("Create column");
  }

  /** Displays the prompt. */
  private void displayPrompt() {
    this.parentController.currentColumn = this;
    this.parentController.promptColumn.setStyle(HomeController.styleAccent(this.getColor()));
    this.parentController.show(this.parentController.promptColumn);
    this.parentController.promptColumnTitle.setText(this.node.getTitle());
    this.parentController.promptColumnPreset.getSelectionModel().select(this.node.getPreset());
  }

  @FXML
  void createChild(ActionEvent e) {
    Event event = new Event("Untitled Event", "", null, null, 0, this.node);
    EventComponent eventComponent = new EventComponent(event, this, this.parentController);
    eventComponent.create(e);
  }

  // drag
  @FXML
  void MouseDragReleased(MouseDragEvent event) {
    EventComponent button = (EventComponent) event.getGestureSource();
    ColumnComponent oldColumn = (ColumnComponent) button.getParentComponent();
    ColumnComponent newColumn = (ColumnComponent) event.getSource();

    this.parentController.originalParent = null;

    if (this.getNode().getParent().isSpecialized()) {
      // Node node = button.getNode().getParent().getParent();
      // Board originBoard = (Board) node;
      // int nextColumnPreset = newColumn.getNode().getPreset();
      // for (Node node_col : originBoard.getNodes()) {
      // Column column = (Column) node_col;
      // if (column.getPreset() == nextColumnPreset) {
      // button.getNode().setParentRequest(column);
      // newColumn.getEventList().getChildren().add(button);
      // break;
      // }
      // }

      newColumn.getEventList().getChildren().add(button);
      oldColumn.list();
      newColumn.list();
    } else {
      newColumn.getEventList().getChildren().add(button);
      button.getNode().setParentRequest(newColumn.getNode());
      oldColumn.list();
      newColumn.list();
    }
  }
}
