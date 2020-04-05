package ui.component;

import java.time.LocalDate;

import javafx.fxml.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import structure.*;
import ui.*;

public class EventComponent extends Button {

  @FXML private Button event;

  public static SVGPath icon;

  public static VBox promptEvent;

  public static SVGPath promptEventIcon;

  public static TextArea promptEventTitle;

  public static Label promptEventLocationBoard;

  public static Label promptEventLocationColumn;

  public static ComboBox<Integer> promptEventImportanceLevel;

  public static DatePicker promptEventDueDate;

  public static ComboBox<String> promptEventDuration;

  public static TextArea promptEventNote;

  private Event node;
  private String color;

  public EventComponent(Node node, String color) {
    super();
    this.node = (Event) node;
    this.color = color;
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
    if (((Column) this.node.getParent()).getPreset() == Column.DONE) {
      this.setStyle("-fx-accent: -fx-accent-60");
    }
    event.setText(node.getTitle());
    this.setOnAction(
        e -> {
          promptEventTitle.setText(this.node.getTitle());
          promptEventLocationBoard.setText(this.node.getParent().getParent().getTitle());
          promptEventLocationColumn.setText(this.node.getParent().getTitle());
          promptEventImportanceLevel.setValue(this.node.getImportanceLevel());
          promptEventDueDate.setValue(LocalDate.parse(this.node.getDueDateString()));
          // promptEventDuration.setValue(this.node.getDuration());
          promptEventNote.setText(this.node.getNote());
          promptEvent.setStyle(HomeController.styleAccent(this.color));
          promptEvent.setVisible(true);
        });
  }
}
