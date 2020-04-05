package ui.component;

import javafx.fxml.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import structure.*;

public class EventComponent extends Button {

  @FXML private Button event;

  @FXML private SVGPath icon;

  public static VBox promptEvent;
  
  private Node node;

  public EventComponent(Node node) {
    super();
    this.node = node;
    System.out.println(promptEvent);
    load();
  }

  void load() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("event.fxml"));
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
    event.setText(node.getTitle());
    this.setOnAction(e -> {
      EventComponent.promptEvent.setVisible(true);
    });
  }
}
