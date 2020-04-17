package ui;

import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import structure.*;

/**
 * The JavaFX Controller for component.event.fxml.
 *
 * @author Cloudy Young, Jimschenchen
 * @since 4.0
 * @version 4.0
 */
public class EventComponent extends Button {

  @FXML private Button event;
  @FXML private SVGPath icon;

  private static final String INACTIVE_ICON =
      "M 16.0625 4 L 15.9375 6 L 17.0625 6.0625 L 17.96875 6.1875 L 18.0625 6.21875 L 18.9375 6.4375 L 19 6.46875 L 19.03125 6.46875 L 19.40625 6.625 L 20.09375 4.71875 L 19.625 4.5625 L 19.5625 4.53125 L 19.53125 4.53125 L 18.46875 4.25 L 18.375 4.25 L 17.28125 4.0625 L 17.1875 4.0625 Z M 13.9375 4.1875 L 13.625 4.25 L 13.53125 4.25 L 12.46875 4.53125 L 12.4375 4.53125 L 12.375 4.5625 L 11.375 4.9375 L 11.34375 4.9375 L 11.28125 4.96875 L 10.3125 5.4375 L 10.28125 5.4375 L 10.25 5.46875 L 10 5.625 L 11.03125 7.34375 L 11.28125 7.1875 L 12.0625 6.8125 L 12.96875 6.46875 L 13 6.46875 L 13.0625 6.4375 L 13.9375 6.21875 L 14.03125 6.1875 L 14.25 6.15625 Z M 22 5.625 L 20.96875 7.34375 L 21.5625 7.6875 L 21.625 7.71875 L 22.3125 8.25 L 22.40625 8.3125 L 23.03125 8.90625 L 23.09375 8.96875 L 23.6875 9.59375 L 25.15625 8.25 L 24.53125 7.5625 L 24.5 7.5 L 24.4375 7.46875 L 23.65625 6.78125 L 23.59375 6.71875 L 22.75 6.0625 L 22.71875 6.0625 L 22.65625 6.03125 Z M 8.25 6.84375 L 7.5625 7.46875 L 7.5 7.5 L 7.46875 7.5625 L 6.78125 8.34375 L 6.71875 8.40625 L 6.0625 9.25 L 6.0625 9.28125 L 6.03125 9.34375 L 5.625 10 L 7.34375 11.03125 L 7.6875 10.4375 L 7.71875 10.375 L 8.25 9.6875 L 8.3125 9.59375 L 8.90625 8.96875 L 8.96875 8.90625 L 9.59375 8.3125 Z M 26.375 10 L 24.65625 11.03125 L 24.8125 11.28125 L 25.1875 12.0625 L 25.53125 12.96875 L 25.53125 13 L 25.5625 13.0625 L 25.78125 13.9375 L 25.8125 14.03125 L 25.84375 14.25 L 27.8125 13.9375 L 27.75 13.625 L 27.75 13.53125 L 27.46875 12.46875 L 27.46875 12.4375 L 27.4375 12.375 L 27.0625 11.375 L 27.0625 11.34375 L 27.03125 11.28125 L 26.5625 10.3125 L 26.5625 10.28125 L 26.53125 10.25 Z M 4.71875 11.90625 L 4.5625 12.375 L 4.53125 12.4375 L 4.53125 12.46875 L 4.25 13.53125 L 4.25 13.625 L 4.0625 14.71875 L 4.0625 14.8125 L 4 15.9375 L 6 16.0625 L 6.0625 14.9375 L 6.1875 14.03125 L 6.21875 13.9375 L 6.4375 13.0625 L 6.46875 13 L 6.46875 12.96875 L 6.625 12.59375 Z M 26 15.9375 L 25.9375 17.0625 L 25.8125 17.96875 L 25.78125 18.0625 L 25.5625 18.9375 L 25.53125 19 L 25.53125 19.03125 L 25.375 19.40625 L 27.28125 20.09375 L 27.4375 19.625 L 27.46875 19.5625 L 27.46875 19.53125 L 27.75 18.46875 L 27.75 18.375 L 27.9375 17.28125 L 27.9375 17.1875 L 28 16.0625 Z M 6.15625 17.75 L 4.1875 18.0625 L 4.25 18.375 L 4.25 18.46875 L 4.53125 19.53125 L 4.53125 19.5625 L 4.5625 19.625 L 4.9375 20.625 L 4.9375 20.65625 L 4.96875 20.71875 L 5.4375 21.6875 L 5.4375 21.71875 L 5.46875 21.75 L 5.625 22 L 7.34375 20.96875 L 7.1875 20.71875 L 6.8125 19.9375 L 6.46875 19.03125 L 6.46875 19 L 6.4375 18.9375 L 6.21875 18.0625 L 6.1875 17.96875 Z M 24.65625 20.96875 L 24.3125 21.5625 L 24.28125 21.625 L 23.75 22.3125 L 23.6875 22.40625 L 23.09375 23.03125 L 23.03125 23.09375 L 22.40625 23.6875 L 23.75 25.15625 L 24.4375 24.53125 L 24.5 24.5 L 24.53125 24.4375 L 25.21875 23.65625 L 25.28125 23.59375 L 25.9375 22.75 L 25.9375 22.71875 L 25.96875 22.65625 L 26.375 22 Z M 8.3125 22.40625 L 6.84375 23.75 L 7.46875 24.4375 L 7.5 24.5 L 7.5625 24.53125 L 8.34375 25.21875 L 8.40625 25.28125 L 9.25 25.9375 L 9.28125 25.9375 L 9.34375 25.96875 L 10 26.375 L 11.03125 24.65625 L 10.4375 24.3125 L 10.375 24.28125 L 9.6875 23.75 L 9.59375 23.6875 L 8.96875 23.09375 L 8.90625 23.03125 Z M 20.96875 24.65625 L 20.71875 24.8125 L 19.9375 25.1875 L 19.03125 25.53125 L 19 25.53125 L 18.9375 25.5625 L 18.0625 25.78125 L 17.96875 25.8125 L 17.75 25.84375 L 18.0625 27.8125 L 18.375 27.75 L 18.46875 27.75 L 19.53125 27.46875 L 19.5625 27.46875 L 19.625 27.4375 L 20.625 27.0625 L 20.65625 27.0625 L 20.71875 27.03125 L 21.6875 26.5625 L 21.71875 26.5625 L 21.75 26.53125 L 22 26.375 Z M 12.59375 25.375 L 11.90625 27.28125 L 12.375 27.4375 L 12.4375 27.46875 L 12.46875 27.46875 L 13.53125 27.75 L 13.625 27.75 L 14.71875 27.9375 L 14.8125 27.9375 L 15.9375 28 L 16.0625 26 L 14.9375 25.9375 L 14.03125 25.8125 L 13.9375 25.78125 L 13.0625 25.5625 L 13 25.53125 L 12.96875 25.53125 Z";
  private static final String ACTIVE_ICON =
      "M 16 4 C 9.382813 4 4 9.382813 4 16 C 4 22.617188 9.382813 28 16 28 C 22.617188 28 28 22.617188 28 16 C 28 9.382813 22.617188 4 16 4 Z M 16 6 C 21.535156 6 26 10.464844 26 16 C 26 21.535156 21.535156 26 16 26 C 10.464844 26 6 21.535156 6 16 C 6 10.464844 10.464844 6 16 6 Z";
  private static final String CHECK_ICON =
      "M 16 4 C 9.3844239 4 4 9.3844287 4 16 C 4 22.615571 9.3844239 28 16 28 C 22.615576 28 28 22.615571 28 16 C 28 9.3844287 22.615576 4 16 4 z M 16 6 C 21.534697 6 26 10.465307 26 16 C 26 21.534693 21.534697 26 16 26 C 10.465303 26 6 21.534693 6 16 C 6 10.465307 10.465303 6 16 6 z M 20.949219 12 L 14.699219 18.25 L 11.449219 15 L 10.050781 16.400391 L 14.699219 21.050781 L 22.349609 13.400391 L 20.949219 12 z";
  private static final String OVERDUE_ICON =
      "M 16 3.910156 C 9.332031 3.910156 3.910156 9.332031 3.910156 16 C 3.910156 22.667969 9.332031 28.089844 16 28.089844 C 22.667969 28.089844 28.089844 22.667969 28.089844 16 C 28.089844 9.332031 22.667969 3.910156 16 3.910156 Z M 16 5.769531 C 21.660156 5.769531 26.230469 10.339844 26.230469 16 C 26.230469 21.660156 21.660156 26.230469 16 26.230469 C 10.339844 26.230469 5.769531 21.660156 5.769531 16 C 5.769531 10.339844 10.339844 5.769531 16 5.769531 Z M 15.070312 10.421875 L 15.070312 12.28125 L 16.929688 12.28125 L 16.929688 10.421875 Z M 15.070312 14.140625 L 15.070312 21.578125 L 16.929688 21.578125 L 16.929688 14.140625 Z M 15.070312 14.140625";

  /** The paired {@code Node} for the component. */
  private Event node;

  /** The parent of the paired {@code Node} for the component. */
  private ColumnComponent parent;

  /** The parent controller instance. */
  private HomeController parentController;

  protected EventComponent(Node node, ColumnComponent parent, HomeController parentController) {
    super();
    this.node = (Event) node;
    this.parent = parent;
    this.parentController = parentController;
    this.loadDisplay();
  }

  /**
   * Gets the paired {@code Event}.
   *
   * @return the paired {@code Event}
   */
  protected Event getNode() {
    return this.node;
  }

  /**
   * Gets the parent of the paired {@code Event}.
   *
   * @return the parent {@code Node}
   */
  protected ColumnComponent getParentComponent() {
    return this.parent;
  }

  /**
   * Returns the color of the {@code Event}.
   *
   * @return the color of the {@code Event}
   */
  protected String getColor() {
    if (this.parent == null) {
      return ((Board) this.node.getParent().getParent()).getColor();
    }
    return this.parent.getColor();
  }

  /** Loads the fxml of the compoennt. */
  private final void loadDisplay() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/component.event.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** Displays the component according to the paired {@code Node}. */
  protected void display() {
    if (this.node == null || this.node.getParent() == null) {
      return;
    }
    if (((Column) this.node.getParent()).getPreset() == Column.DONE) {
      this.setStyle("-fx-accent: -fx-accent-light-40 !important;");
      icon.setContent(CHECK_ICON);
    } else if (this.node.isOverdue()) {
      icon.setContent(OVERDUE_ICON);
    } else if (((Column) this.node.getParent()).getPreset() == Column.TO_DO) {
      icon.setContent(INACTIVE_ICON);
    } else if (((Column) this.node.getParent()).getPreset() == Column.IN_PROGRESS) {
      icon.setContent(ACTIVE_ICON);
    }
    event.setText(node.getTitle());
    this.setStyle(this.getStyle() + HomeController.styleAccent(this.getColor()));
  }

  @FXML
  void update(ActionEvent e) {
    this.displayPrompt();
    this.parentController.show(this.parentController.eventDelete);
    this.parentController.hide(this.parentController.eventCreate);
    this.parentController.promptEventPromptTitle.setText("Edit event");
  }

  @FXML
  void create(ActionEvent e) {
    this.displayPrompt();
    this.parentController.hide(this.parentController.eventDelete);
    this.parentController.show(this.parentController.eventCreate);
    this.parentController.promptEventPromptTitle.setText("Create event");
  }

  /** Displays the prompt. */
  private void displayPrompt() {
    this.parentController.currentEvent = this;
    this.parentController
        .textHolder
        .textProperty()
        .bind(this.parentController.promptEventTitle.textProperty());

    this.parentController.promptEventTitle.setText(this.node.getTitle());
    this.parentController.promptEventLocationBoard.setText(
        this.node.getParent().getParent().getTitle());
    this.parentController.promptEventLocationColumn.setText(this.node.getParent().getTitle());
    this.parentController
        .promptEventImportanceLevel
        .getSelectionModel()
        .select(this.node.getImportanceLevel());
    this.parentController.promptEventDueDate.setValue(
        (this.node.getDueDate() != null) ? LocalDate.parse(this.node.getDueDateString()) : null);

    this.parentController
        .promptEventDuration
        .getSelectionModel()
        .select(this.node.getDurationValue().intValue() / 3600);
    this.parentController.promptEventNote.setText(this.node.getNote());
    this.parentController.promptEventIcon.setContent(icon.getContent());
    this.parentController.promptEvent.setStyle(HomeController.styleAccent(this.getColor()));
    this.parentController.show(this.parentController.promptEvent);
  }

  @FXML
  void initialize() {
    this.display();
  }

  @FXML
  void EventDragDetected(MouseEvent event) { // The card dragging detection
    // Cleans card
    this.parentController.currentDragCard = null;
    this.parentController.dragPane.getChildren().clear();

    // Adds current card to drag pane
    EventComponent card = (EventComponent) event.getSource();
    if (card instanceof EventComponent) {
      card.setLayoutX(event.getSceneX());
      card.setLayoutY(event.getSceneY());
      card.startFullDrag();
      this.parentController.originalColumn = (ColumnComponent) card.getParentComponent();
      this.parentController.currentDragCard = card;
      this.parentController.dragPane.getChildren().add(card);
    }
  }
}
