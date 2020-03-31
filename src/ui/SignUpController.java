package ui;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.*;

public class SignUpController {

  @FXML private TabPane tabPane;

  @FXML private TextField inputUsername;

  @FXML private Label labelErrorUsername;

  @FXML private TextField inputPassword;

  @FXML private Label labelErrorPassword;

  @FXML private Label profileUsername;

  private int tab = 0;

  @FXML
  void initialize() {
    // Intialize label text values
    labelErrorUsername.setText("");
    labelErrorPassword.setText("");
    profileUsername.setText("");
  }

  @FXML
  void next(ActionEvent event) {
    Button button = (Button) event.getSource();
    String id = button.getId();

    System.out.println(id);

    if (id.contains("Back")) tab--;
    if (id.contains("Next")) tab++;

    if (id.equals("buttonNextStart")) {
      try {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene oldScene = ((Node) event.getSource()).getScene();
        Scene scene =
            new Scene(
                FXMLLoader.load(getClass().getResource("home.fxml")),
                oldScene.getWidth(),
                oldScene.getHeight());
        scene.getStylesheets().add(getClass().getResource("default.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
      } catch (Exception e) {
        System.out.println(e);
      }
    }

    tabPane.getSelectionModel().select(tab);

    tabPane.requestFocus();
  }
}
