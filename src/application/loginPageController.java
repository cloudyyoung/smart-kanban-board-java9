package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import structure.*;

public class loginPageController {

  private static String username;

  public static String getUsername() {
    return username;
  }

  public static void setUsername(String user) {
    username = user;
  }

  @FXML private BorderPane loginPane;

  @FXML private TextField userNameField;

  @FXML private TextField passwordField;

  @FXML private Label signInBtn;

  @FXML
  void handleForgotAcc(MouseEvent event) throws IOException {
    BorderPane pane = FXMLLoader.load(getClass().getResource("forgotPage1.fxml"));
    loginPane.getChildren().setAll(pane);
  }

  @FXML
  void handleCreatAcc(MouseEvent event) throws IOException {
    BorderPane pane = FXMLLoader.load(getClass().getResource("newaccPage1.fxml"));
    loginPane.getChildren().setAll(pane);
  }

  @FXML
  void handleSignIn(MouseEvent event) throws IOException {
    String username = userNameField.getText();
    String password = passwordField.getText();

    setUsername(username);

    boolean authenticated = true;
    User user = new User();
    HttpRequest req = user.authenticate(username, password);
    authenticated = req.isSucceed();

    if (authenticated == true) {
      BorderPane pane = FXMLLoader.load(getClass().getResource("homePage.fxml"));
      loginPane.getChildren().setAll(pane);
    } else {
      userNameField.setStyle("-fx-text-fill: #bb0000;");
      passwordField.setStyle("-fx-text-fill: #bb0000;");
      userNameField.setText("Incorrect Username");
      passwordField.setText("Incorrect Password");
    }
  }
}
