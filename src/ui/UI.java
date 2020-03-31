package ui;

// Imports
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class UI extends Application {

  private Stage stage;
  private Scene scene;

  // Main Method
  public static void main(String[] args) {
    ui(args);
  }

  public static void ui(String[] args) {
    launch(args);
  }

  // Start Method
  @Override
  public void start(Stage stage) {
    try {
      this.scene = new Scene(FXMLLoader.load(getClass().getResource("welcome.fxml")), 1020, 680);
      this.scene.getStylesheets().add(getClass().getResource("default.css").toExternalForm());

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
