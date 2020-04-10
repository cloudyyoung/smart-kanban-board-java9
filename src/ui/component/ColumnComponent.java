package ui.component;

import java.util.ArrayList;

import javafx.fxml.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import structure.*;

public class ColumnComponent extends VBox {

  @FXML private Label eventCount;
  @FXML private TextField columnTitle;
  @FXML private Button eventAdd;
  @FXML private VBox eventList;

  private Column node;
  private BoardComponent parent;

  public ColumnComponent(Column node, BoardComponent parent) {
    super();
    this.node = node;
    this.parent = parent;
    this.load();
  }

  public Column getNode(){
    return this.node;
  }

  public String getColor(){
    return this.parent.getColor();
  }
  
  public BoardComponent getParentComponent(){
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

  public void listEvent(){
    ArrayList<structure.Node> list = null;
    if (this.node.getParent().getId() == 1) {
      list =
          this.node.getNodes(structure.Node.SORT_BY_PRIORITY, structure.Node.ORDER_BY_ASC);
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

  @FXML
  void initialize() {
    columnTitle.setText(this.node.getTitle());
    this.listEvent();
  }
}
