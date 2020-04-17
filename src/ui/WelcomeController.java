package ui;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.*;

/**
 * The JavaFX Controller for Welcome.fxml.
 * 
 * @author Cloudy Young
 * @since 3.0
 * @version 3.0
 */

public class WelcomeController {

  /** The sign in button */
  @FXML private Button buttonSignIn;

  /** The sign up button */
  @FXML private Button buttonSignUp;

  /**
   * Authenticates user account
   * 
   * @param event the {@code ActionEvent} instance
   */
  @FXML
  void sign(ActionEvent event) {
    Button button = (Button) event.getSource();
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = ((Node) event.getSource()).getScene();

    if (button.equals(buttonSignIn)) {
      this.switchScene("view/signin.fxml", stage, scene);
    } else if (button.equals(buttonSignUp)) {
      this.switchScene("view/signup.fxml", stage, scene);
    }
  }

  /** 
   * Switches the scene of the window
   * 
   * @param file the fxml file name
   * @param stage the stage instance
   * @param prevScene the previous scene
   */
  private void switchScene(String file, Stage stage, Scene prevScene) {
    try {
      Scene scene =
          new Scene(
              FXMLLoader.load(getClass().getResource(file)),
              prevScene.getWidth(),
              prevScene.getHeight());
      scene.getStylesheets().add(getClass().getResource("view/default.css").toExternalForm());
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      // e.printStackTrace();
    }
  }
}
