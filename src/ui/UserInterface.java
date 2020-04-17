package ui;

// Imports
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * The entrance class of user interface.
 * 
 * @author Cloudy Young
 * @since 3.0
 * @version 4.0
 */

public class UserInterface extends Application {

  /** The stage object of the instance. */
  private Stage stage;

  /** The current scene of the instance. */
  private Scene scene;

  /**
   * Starts the ui.
   * 
   * @param args the input arguments from OS
   */
  public static void ui(String[] args) {
    launch(args);
  }

  /**
   * Starts the ui.
   * 
   * @param stage the stage object of the instance
   */
  @Override
  public void start(Stage stage) {
    try {
      this.scene = new Scene(FXMLLoader.load(getClass().getResource("view/welcome.fxml")), 1020, 680);
      this.scene.getStylesheets().add(getClass().getResource("view/default.css").toExternalForm());

      this.stage = stage;
      this.stage.setResizable(true);
      this.stage.setScene(this.scene);
      this.stage.setTitle("Smart Kanban");
      this.stage.show();
      this.stage.setMinHeight(600);
      this.stage.setMinWidth(700);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
