package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import structure.*;

public class newAccPage1Controller {

  @FXML private BorderPane newAccPane1;

  @FXML private TextField emailTextField;

  @FXML private Label nextBtn;

  @FXML
  void handleNextClick(MouseEvent event) throws IOException {
    // String email = emailTextField.getText();
    // User user = new User();
    // HttpRequest req = user.s

    BorderPane pane = FXMLLoader.load(getClass().getResource("newaccPage2.fxml"));
    newAccPane1.getChildren().setAll(pane);
  }
}
