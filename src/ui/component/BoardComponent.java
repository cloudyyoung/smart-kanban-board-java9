package ui.component;

import javafx.fxml.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;

import structure.*;
import ui.*;

public class BoardComponent extends Button {

  @FXML private Button button;

  @FXML private SVGPath boardIcon;

  private Board node;

  public BoardComponent(Board node) {
    super();
    this.node = node;
    this.setText(node.getTitle());
    this.setId("board-" + node.getId());
    this.setStyle(HomeController.styleAccent(node.getColor()));
    load();

    SVGPath svg = new SVGPath();
    svg.setContent(
        "M 5 5 L 5 6 L 5 27 L 27 27 L 27 5 L 5 5 z M 7 7 L 25 7 L 25 9 L 7 9 L 7 7 z M 7 11 L 25 11 L 25 25 L 7 25 L 7 11 z");
    HBox hbox = new HBox();
    hbox.getChildren().add(svg);
    this.setGraphic(hbox);
  }

  @FXML
  void initialize() {}

  public Board getNode() {
    return this.node;
  }

  void load() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("board.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (Exception e) {
      // throw new RuntimeException(exception);
      System.out.println(e);
    }
  }
}
