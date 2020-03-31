package ui;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.*;

import structure.User;
import structure.Result;

public class SignUpController {

  @FXML
  private TabPane tabPane;

  @FXML
  private TextField inputUsername;

  @FXML
  private Label labelErrorUsername;

  @FXML
  private PasswordField inputPassword;

  @FXML
  private Label labelErrorPassword;

  @FXML
  private Label labelErrorSecQues;

  @FXML
  private Label labelErrorSecAns;

  @FXML
  private Label profileUsername;

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

    boolean next = true;

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
    }else if(id.equals("buttonNextPassword-SignIn")){

      tabPane.setDisable(true);
      String username = inputUsername.getText();
      String password = inputPassword.getText();
      Result res = User.authentication(username, password);
      tabPane.setDisable(false);

      if(res.isSucceeded()){
        profileUsername.setText(username);
      }else if(res.isFailed()){
        next = false;
        String errorText = res.getFail().getResponseBody().getHttpBody("error").getString("message");
        labelErrorUsername.setText(errorText);
        labelErrorPassword.setText(errorText);
      }
    }
    
    if(next){
      if (id.contains("Back")) tab--;
      if (id.contains("Next")) tab++;
      tabPane.getSelectionModel().select(tab);
      tabPane.requestFocus();
    }

  }
}
