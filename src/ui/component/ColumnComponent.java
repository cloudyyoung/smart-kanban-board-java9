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

  public ColumnComponent(Column node) {
    super();
    this.node = node;
    load();
  }

  void load() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("column.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void initialize() {
    columnTitle.setText(node.getTitle());
    eventCount.setText(node.getChildrenNodes().size() + "");

    ArrayList<structure.Node> list = null;
    if (this.node.getParent().getId() == 1) {
      list =
          this.node.getChildrenNodes(structure.Node.SORT_BY_PRIORITY, structure.Node.ORDER_BY_ASC);
    } else {
      list = this.node.getChildrenNodes();
    }
    // System.out.println(list);

    for (Node each : list) {
      EventComponent event = new EventComponent(each);
      eventList.getChildren().add(event);
    }
  }
}
