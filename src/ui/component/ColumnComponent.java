package ui.component;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
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
    for (structure.Node each : list) {
      EventComponent event = new EventComponent(each, this, this.parentController);
      eventList.getChildren().add(event);
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
      columnEdit.getStyleClass().add("hide");
      eventCreate.getStyleClass().add("hide");
    } else {
      columnEdit.getStyleClass().remove("hide");
      eventCreate.getStyleClass().remove("hide");
    }
  }

  @FXML
  void update(ActionEvent e) {
    this.displayPrompt();
    this.parentController.columnDelete.getStyleClass().remove("hide");
    this.parentController.columnCreate.getStyleClass().add("hide");
  }

  @FXML
  void create(ActionEvent e){
    this.displayPrompt();
    this.parentController.columnDelete.getStyleClass().add("hide");
    this.parentController.columnCreate.getStyleClass().remove("hide");
  }

  private void displayPrompt(){
    HomeController.currentColumn = this;
    this.parentController.promptColumn.setStyle(HomeController.styleAccent(this.getColor()));
    this.parentController.promptColumn.getStyleClass().remove("hide");
    this.parentController.promptColumnPromptTitle.setText("Edit column");
    this.parentController.promptColumnTitle.setText(this.node.getTitle());
    this.parentController.promptColumnPreset.getSelectionModel().select(this.node.getPreset());
    if (this.node.isOnlyPreset()) {
      this.parentController.columnDelete.getStyleClass().add("hide");
    } else {
      this.parentController.columnDelete.getStyleClass().remove("hide");
    }
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
    System.out.println("MouseDragReleased");
    ColumnComponent current = (ColumnComponent) event.getSource();
    Button button = (Button) event.getGestureSource();
    current.getEventList().getChildren().add(button);
  }
}
