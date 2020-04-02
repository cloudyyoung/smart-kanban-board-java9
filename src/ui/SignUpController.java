package ui;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.*;

import structure.User;
import structure.HttpBody;
import structure.Result;

public class SignUpController {

  @FXML private TabPane tabPane;

  @FXML private TextField inputUsername;

  @FXML private Label labelErrorUsername;

  @FXML private PasswordField inputPassword;

  @FXML private Label labelErrorPassword;

  @FXML private ComboBox<String> comboSecurityQuestion;

  @FXML private Label labelErrorSecQues;

  @FXML private Label labelSecurityAnswerQuestion;

  @FXML private TextField inputSecurityAnswer;

  @FXML private Label labelErrorSecAns;

  @FXML private Label profileUsername;

  private int tab = 0;

  @FXML
  void initialize() {
    // Intialize label text values
    clearErrorLabel();
    profileUsername.setText("");
    if(comboSecurityQuestion != null) comboSecurityQuestion.getItems().addAll(
      "What school did you attend for sixth grade?", 
      "In what city or town was your first job?",
      "What is your oldest sibling's middle name?",
      "In what city does your nearest sibling live?",
      "What is the last name of the teacher who gave you your first failing grade?"
      );
  }

  void clearErrorLabel() {
    if (labelErrorUsername != null) labelErrorUsername.setText("");
    if (labelErrorPassword != null) labelErrorPassword.setText("");
    if (labelErrorSecQues != null) labelErrorSecQues.setText("");
    if (labelErrorSecAns != null) labelErrorSecAns.setText("");
  }

  @FXML
  void next(ActionEvent event) {
    Button button = (Button) event.getSource();
    String id = button.getId();

    boolean next = true;
    clearErrorLabel();

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
        // e.printStackTrace();
      }

    } else if(id.equals("buttonNextSecQues-SignUp")){
      labelSecurityAnswerQuestion.setText(comboSecurityQuestion.getSelectionModel().getSelectedItem());
    } else if (id.contains("SignIn")) {
      String username = inputUsername.getText();
      String password = inputPassword.getText();
      int totalField = 2;
      Result res = User.authentication(username, password);

      if (res.isFailed()) {
        int statusCode = res.getFailError().getInt("code");
        HttpBody body = res.getFailError().getHttpBody("details");
        System.out.println(statusCode);
        if (statusCode == 406 && totalField - body.size() > tab) {
          next = true;
        } else {
          next = false;
          String errorText = res.getFailError().getString("message");
          labelErrorUsername.setText(errorText);
          labelErrorPassword.setText(errorText);
        }
      } else if (res.isExcepted()) {
        next = false;
        String errorText = "Unexpected error occured";
        labelErrorUsername.setText(errorText);
        labelErrorPassword.setText(errorText);
      } else {
        profileUsername.setText(username);
      }
    } else if (id.contains("SignUp")) {
      String username = inputUsername.getText();
      String password = inputPassword.getText();
      String sec_ques = comboSecurityQuestion.getSelectionModel().getSelectedItem();
      String sec_ans = inputSecurityAnswer.getText();
      int totalField = 4;
      Result res = User.registration(username, password, sec_ques, sec_ans);

      if (res.isFailed()) {
        int statusCode = res.getFailError().getInt("code");
        HttpBody body = res.getFailError().getHttpBody("details");
        System.out.println(statusCode);
        if (statusCode == 406 && totalField - body.size() > tab) {
          next = true;
        } else {
          next = false;
          String errorText = res.getFailError().getString("message");
          labelErrorUsername.setText(errorText);
          labelErrorPassword.setText(errorText);
          labelErrorSecQues.setText(errorText);
          labelErrorSecAns.setText(errorText);
        }
      } else if (res.isExcepted()) {
        next = false;
        String errorText = "Unexpected error occured";
        labelErrorUsername.setText(errorText);
        labelErrorPassword.setText(errorText);
        labelErrorSecQues.setText(errorText);
        labelErrorSecAns.setText(errorText);
      } else {
        profileUsername.setText(username);
        User.authentication(username, password);
      }
    }

    if (next) {
      if (id.contains("Back")) tab--;
      if (id.contains("Next")) tab++;
      tabPane.getSelectionModel().select(tab);
      tabPane.requestFocus();
    }
  }
}
