package ui;

// Imports
import java.io.FileInputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

private 

public class UI extends Application {

  // Main Method
  public static void main(String[] args) {
    ui(args);
  }

  public static void ui(String[] args) {
    launch(args);
  }

  // Start Method
  @Override
  public void start(Stage primaryStage) {
    try {
      FXMLLoader loader = new FXMLLoader();
      BorderPane root = (BorderPane) loader.load(getClass().getResource("welcome.fxml"));
      Scene scene = new Scene(root, 960, 660);
      scene.getStylesheets().add(getClass().getResource("default.css").toExternalForm());
      
      primaryStage.setResizable(true);
      primaryStage.setScene(scene);
      primaryStage.setTitle("Smart Kanban");
      primaryStage.show();
      primaryStage.setMinHeight(680);
      primaryStage.setMinWidth(1020);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
