package ui.component;

import javafx.fxml.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;

import structure.*;

public class Event extends Button {

  @FXML private Button event;

  @FXML private SVGPath icon;

  private Node node;

  public Event() {
    super();
    load();
  }

  public Event(Node node) {
    super();
    this.node = node;
    load();
  }

  void load() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("event.fxml"));
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
    event.setText(node.getTitle());
  }
}
