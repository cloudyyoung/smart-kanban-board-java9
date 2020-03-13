package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
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
  void userTextFillReset(KeyEvent event) {
    userNameField.setStyle("-fx-text-fill: #2F2E2E;");
    // Comment/ uncomment for dark mode
    // userNameField.setStyle("-fx-text-fill: #ffffff;");
  }

  @FXML
  void passTextFillReset(KeyEvent event) {
    passwordField.setStyle("-fx-text-fill: #2F2E2E;");
    // Comment/ uncomment for dark mode
    // passwordField.setStyle("-fx-text-fill: #ffffff;");
  }

  @FXML
  void handleSignIn(MouseEvent event) throws IOException {
    String username = userNameField.getText();
    String password = passwordField.getText();

    // -fx-text-fill: #2F2E2E;

    setUsername(username);

    boolean authenticated = true;
    User user = new User();
    Result req = user.authenticate(username, password);
    authenticated = req.isSucceeded();

    if (authenticated == true) {
      BorderPane pane = FXMLLoader.load(getClass().getResource("homePage.fxml"));
      loginPane.getChildren().setAll(pane);
    } else {
      userNameField.setStyle("-fx-prompt-text-fill: #bb0000;");
      passwordField.setStyle("-fx-prompt-text-fill: #bb0000;");
      userNameField.setText("");
      passwordField.setText("");
      userNameField.setPromptText("Incorrect Username");
      passwordField.setPromptText("Incorrect Password");
    }
  }

  public void initialize() {}
}
