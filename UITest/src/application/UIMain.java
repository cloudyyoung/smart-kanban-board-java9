package application;

import java.io.FileInputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;

public class UIMain extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {

    try {
      BorderPane root = new BorderPane();
      FXMLLoader loader = new FXMLLoader();

      // TO SWITCH PANES CHANGE ROOT PATH
      root = (BorderPane) loader.load(new FileInputStream("src/application/homePage.fxml"));

      Scene scene = new Scene(root, 960, 660);
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      primaryStage.setScene(scene);

      // HOUSE KEEPING
      primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("windowIcon.png")));
      primaryStage.setTitle("	 Smart Kanban");
      primaryStage.show();
      primaryStage.setResizable(false);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
