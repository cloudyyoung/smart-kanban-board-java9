package ui;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.*;

public class WelcomeController {

  @FXML private Button buttonSignIn;

  @FXML private Button buttonSignUp;

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

  void switchScene(String file, Stage stage, Scene oldScene) {
    try {
      Scene scene =
          new Scene(
              FXMLLoader.load(getClass().getResource(file)),
              oldScene.getWidth(),
              oldScene.getHeight());
      scene.getStylesheets().add(getClass().getResource("view/default.css").toExternalForm());
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      // e.printStackTrace();
    }
  }
}
