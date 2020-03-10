package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import structure.*;

public class newAccPage2Controller {

  @FXML private BorderPane newAccPane2;

  @FXML private TextField usernameField;

  @FXML private Label nxtBtn;

  @FXML
  void handleNxtBtn(MouseEvent event) throws IOException {
    // String userName = usernameField.getText();
    // User user = new User();
    // user.setUsername(userName);

    BorderPane pane = FXMLLoader.load(getClass().getResource("newaccPage3.fxml"));
    newAccPane2.getChildren().setAll(pane);
  }
}
