package application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import structure.*;

public class loginPageController {
  @FXML private TextField userNameField;

  @FXML private TextField passwordField;

  @FXML private Label signInBtn;

  @FXML
  void handleCreatAcc(MouseEvent e) {}

  @FXML
  void handleSignIn(MouseEvent e) {
    String username = userNameField.getText();
    String password = passwordField.getText();

    boolean authenticated = true;
    User user = new User();
    HttpRequest req = user.authenticate(username, password);
    authenticated = req.isSucceed();
    System.out.println(authenticated);
  }
}
