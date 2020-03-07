package application;

// Imports
import java.io.FileInputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;

public class Main extends Application {

  // Main Method
  public static void main(String[] args) {
    launch(args);
  }

  // Start Method
  @Override
  public void start(Stage primaryStage) {

    try {

      // Launch BorderPane Root
      BorderPane root = new BorderPane();

      // TO SWITCH PANES CHANGE ROOT PATH
      // PANES:
      // loadingPage.fxml
      // loginPage.fxml
      // newaccPage.fxml
      // homePage.fxml
      // settingsPage.fxml

      FXMLLoader loader = new FXMLLoader();
      root = (BorderPane) loader.load(new FileInputStream("src/application/loginPage.fxml"));

      // Set Window Size
      Scene scene = new Scene(root, 960, 660);
      primaryStage.setResizable(false);

      // Set CSS Stylesheet
      // TO SWITCH TO DARK MODE, CHANGE PATH TO 'dark.css'
      scene.getStylesheets().add(getClass().getResource("default.css").toExternalForm());
      scene.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
      primaryStage.setScene(scene);

      // Set Favicon and Title
      primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("windowIcon.png")));
      primaryStage.setTitle("	 Home - Smart Kanban");

      // Show Scene
      primaryStage.show();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
