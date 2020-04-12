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
  private Button eventAdd;
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
    this.load();
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

  private final void load() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("column.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println(fxmlLoader.getLocation());
    System.out.println(fxmlLoader.getController().toString());
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
    if (this.node.getParent().getId() >= 100) {
      columnEdit.getStyleClass().remove("hide");
      eventAdd.getStyleClass().remove("hide");
    } else {
      columnEdit.getStyleClass().add("hide");
      eventAdd.getStyleClass().add("hide");
    }
  }

  @FXML
  void update(ActionEvent e) {
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

  // drag
  @FXML
  void MouseReleased(MouseEvent event) {
    System.out.println("EventMouseDragReleased");
    Button button = HomeController.currentCard;
    this.eventList.getChildren().add(button);
    // set DragPane transparent to 100
    this.parentController.dragPane.setMouseTransparent(true);
  }

}
