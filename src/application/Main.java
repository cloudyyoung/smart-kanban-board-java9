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
      // forgotPage-1.fxml
      // forgotPage-2.fxml
      // newaccPage-1.fxml
      // newaccPage-2.fxml
      // newaccPage-3.fxml
      // newaccPage-4.fxml
      // homePage.fxml
      // settingsPage.fxml

      // POPUPS:
      // -show new task popup from homePage
      // hierarchy>borderpane>stackpane>furthest rectangle>properties>node>visible
      // hierarchy>borderpane>stackpane>furthest stackpane>properties>node>visible

      // -show new event popup from homePage
      // hierarchy>borderpane>stackpane>furthest rectangle>properties>node>visible
      // hierarchy>borderpane>stackpane>2nd furthest stackpane>properties>node>visible

      // -show search popup from homePage or settingsPage
      // hierarchy>borderpane>stackpane>borderpane>stackpane>furthest vbox>visible

      FXMLLoader loader = new FXMLLoader();
<<<<<<< HEAD
      root = (BorderPane) loader.load(new FileInputStream("src\\application\\settingsPage.fxml"));
=======
      root =
          (BorderPane)
              loader.load(new FileInputStream("src\\application\\homePage(popuptest).fxml"));
>>>>>>> 644f546e0d5dc2932cf23a87ec7d75e598cc3e2c

      // Set Window Size
      Scene scene = new Scene(root, 960, 660);
      primaryStage.setResizable(false);

      // Set CSS Stylesheet
      // TO SWITCH TO DARK MODE, COMMENT/UNCOMMENT LINE OF DARK.CSS
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
