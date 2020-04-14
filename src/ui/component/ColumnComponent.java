package ui.component;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import structure.*;
import ui.HomeController;

public class ColumnComponent extends VBox {

  @FXML
  private Label eventCount;
  @FXML
  private TextField columnTitle;
  @FXML
  private Button eventCreate;
  @FXML
  private Button columnEdit;
  @FXML
  private VBox eventList;

  private Column node;
  private BoardComponent parent;
  private HomeController parentController;

  public ColumnComponent(Column node, BoardComponent parent, HomeController parentController) {
    super();
    this.node = node;
    this.parent = parent;
    this.parentController = parentController;
    this.loadDisplay();
  }

  public Column getNode() {
    return this.node;
  }

  public String getColor() {
    return this.parent.getColor();
  }

  public BoardComponent getParentComponent() {
    return this.parent;
  }

  public VBox getEventList() {
    return this.eventList;
  }

  private final void loadDisplay() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("column.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void list() {
    ArrayList<structure.Node> list = null;
    if (this.node.getParent().getId() == 1) {
      list = this.node.getNodes(structure.Node.SORT_BY_PRIORITY, structure.Node.ORDER_BY_ASC);
    } else {
      list = this.node.getNodes();
    }
    // System.out.println(list);

    eventList.getChildren().clear();

    // System.out.println(eventList.getChildren());

    for (structure.Node each : list) {
      EventComponent event = new EventComponent(each, this, this.parentController);
      eventList.getChildren().add(event);
      System.out.println(event);
    }
    eventCount.setText(list.size() + "");
  }

  public void display() {
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
  void create(ActionEvent e){
    this.displayPrompt();
    this.parentController.hide(this.parentController.columnDelete);
    this.parentController.show(this.parentController.columnCreate);
    this.parentController.promptColumnPromptTitle.setText("Create column");
  }

  private void displayPrompt(){
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
    ColumnComponent nextColumn = (ColumnComponent) event.getSource();
    EventComponent button = (EventComponent) event.getGestureSource();
    ColumnComponent oldColumn = (ColumnComponent) button.getParentComponent();
    // nextColumn.getEventList().getChildren().add(button);

    System.out.println(oldColumn.getNode().getTitle());
    System.out.println(nextColumn.getNode().getTitle());
    // System.out.println(button.getNode());
    
    if (this.getNode().getParent().isSpecialized()) {
      Node node = button.getNode().getParent().getParent();
      Board originBoard = (Board) node;
      int nextColumnPreset = nextColumn.getNode().getPreset();
      for (Node node_col : originBoard.getNodes()) {
        Column column = (Column) node_col;
        if (column.getPreset() == nextColumnPreset) {
          button.getNode().setParentRequest(column);
          nextColumn.getEventList().getChildren().add(button);
          break;
        }
      }
      button.getNode().updateGeneratedDate();
    } else {
      button.getNode().setParentRequest(nextColumn.getNode());
      nextColumn.getEventList().getChildren().add(button);
    }

    // Kanban.getCurrent().generateToday();
  }
}
