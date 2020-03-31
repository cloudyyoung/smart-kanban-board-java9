package ui;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.*;

public class WelcomeController {

  @FXML
  private Button buttonSignIn;

  @FXML
  private Button buttonSignUp;

  @FXML
  void sign(ActionEvent event) {
    Button button = (Button) event.getSource();
    String id = button.getId();
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = ((Node) event.getSource()).getScene();

    if (id.equals("signIn")) {
      this.switchScene("signin.fxml", stage, scene);
    } else if (id.equals("signUp")) {
      this.switchScene("signup.fxml", stage, scene);
    }
  }

  void switchScene(String file, Stage stage, Scene oldScene) {
    try {
      Scene scene = new Scene(FXMLLoader.load(getClass().getResource(file)), oldScene.getWidth(), oldScene.getHeight());
      scene.getStylesheets().add(getClass().getResource("default.css").toExternalForm());
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      // System.out.println(e);
    }
  }
}
