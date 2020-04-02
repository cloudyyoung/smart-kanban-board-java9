package ui.component;

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
      // throw new RuntimeException(exception);
      System.out.println(e);
    }
  }

  @FXML
  void initialize() {
    columnTitle.setText(node.getTitle());
    eventCount.setText(node.getChildrenNodes().size() + "");

    for (Node each : node.getChildrenNodes()) {
      EventComponent event = new EventComponent(each);
      eventList.getChildren().add(event);
    }
  }
}
