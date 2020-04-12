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

  @FXML private Label eventCount;
  @FXML private TextField columnTitle;
  @FXML private Button eventAdd;
  @FXML private Button columnEdit;
  @FXML private VBox eventList;

  public static VBox promptColumn;
  public static Label promptColumnPromptTitle;
  public static TextArea promptColumnTitle;
  public static ComboBox<String> promptColumnPreset;
  public static Pane dragPane;

  private Column node;
  private BoardComponent parent;

  public ColumnComponent(Column node, BoardComponent parent) {
    super();
    this.node = node;
    this.parent = parent;
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
  }

  public void listEvent() {
    ArrayList<structure.Node> list = null;
    if (this.node.getParent().getId() == 1) {
      list = this.node.getNodes(structure.Node.SORT_BY_PRIORITY, structure.Node.ORDER_BY_ASC);
    } else {
      list = this.node.getNodes();
    }
    // System.out.println(list);

    eventList.getChildren().clear();
    for (Node each : list) {
      EventComponent event = new EventComponent(each, this);
      eventList.getChildren().add(event);
    }
    eventCount.setText(list.size() + "");
  }

  public void update() {
    columnTitle.setText(this.node.getTitle());
  }

  @FXML
  void initialize() {
    this.update();
    this.listEvent();
    if (this.node.getParent().getId() >= 100) {
      columnEdit.getStyleClass().remove("hide");
      eventAdd.getStyleClass().remove("hide");
    } else {
      columnEdit.getStyleClass().add("hide");
      eventAdd.getStyleClass().add("hide");
    }
  }

  @FXML
  void editColumn(ActionEvent e) {
    HomeController.currentColumn = this;
    promptColumn.setStyle(HomeController.styleAccent(this.getColor()));
    promptColumn.getStyleClass().remove("hide");
    promptColumnPromptTitle.setText("Edit column");
    promptColumnTitle.setText(this.node.getTitle());
    promptColumnPreset.getSelectionModel().select(this.node.getPreset());
  }

  // drag
  @FXML
  void EventMouseDragReleased(MouseDragEvent event) {
    System.out.println("EventMouseDragReleased");
    Button button = (Button) event.getGestureSource();
    eventList.getChildren().add(button);
  }
}
