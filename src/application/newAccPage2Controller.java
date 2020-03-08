package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import structure.*;

public class newAccPage2Controller {

  @FXML private TextField usernameField;

  @FXML private Label nxtBtn;

  @FXML
  void handleNxtBtn(MouseEvent event) {
    String userName = usernameField.getText();
    User user = new User();
    user.setUsername(userName);
  }
}
