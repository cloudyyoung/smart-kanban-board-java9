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
    showError("");
    profileUsername.setText("");
    if (comboSecurityQuestion != null)
      comboSecurityQuestion
          .getItems()
          .addAll(
              "What school did you attend for sixth grade?",
              "In what city or town was your first job?",
              "What is your oldest sibling's middle name?",
              "In what city does your nearest sibling live?",
              "What is the last name of the teacher who gave you your first failing grade?",
              "What was the last name of your third grade teacher?",
              "What was the name of the hospital where you were born?",
              "What was the name of the company where you had your first job?",
              "What is the first name of the boy or girl that you first kissed?",
              "What was the make and model of your first car?",
              "What was your favorite food as a child?",
              "What is the name of your favorite childhood friend?",
              "Where were you when you had your first alcoholic drink (or cigarette)?",
              "What was the name of your second dog/cat/goldfish/etc?",
              "Who was your childhood hero?",
              "In what town or city did your mother and father meet?",
              "What time of the day was your first child born? (hh:mm)",
              "What were the last four digits of your childhood telephone number?",
              "What was the house number and street name you lived in as a child?",
              "What is your grandmother's (on your mother's side) maiden name?");
  }

  @FXML
  void next(ActionEvent event) {
    Button button = (Button) event.getSource();
    String id = button.getId();
    tabPane.setDisable(true);

    boolean next = true;
    showError("");

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
        e.printStackTrace();
      }

    } else if (id.equals("buttonNextSecQues-SignUp")) {
      labelSecurityAnswerQuestion.setText(
          comboSecurityQuestion.getSelectionModel().getSelectedItem());
    } else {
      Result res;
      int totalField = 0;

      if (id.contains("SignIn")) {
        totalField = 2;
        res = User.authentication(inputUsername.getText(), inputPassword.getText());
      } else {
        totalField = 4;
        res =
            User.registration(
                inputUsername.getText(),
                inputPassword.getText(),
                comboSecurityQuestion.getSelectionModel().getSelectedItem(),
                inputSecurityAnswer.getText());
      }

      if (res.isFailed()) {
        int statusCode = res.getFailError().getInt("code");
        HttpBody body = res.getFailError().getHttpBody("details");
        System.out.println(statusCode);
        if (statusCode == 406 && totalField - body.size() > tab) {
          next = true;
        } else {
          next = false;
          showError(res.getFailError().getString("message"));
        }
      } else if (res.isExcepted()) {
        next = false;
        String errorText = "Unexpected error occured";
        showError(errorText);
      } else {
        profileUsername.setText(User.current.getUsername());
      }
    }

    tabPane.setDisable(false);

    if (next) {
      if (id.contains("Back")) tab--;
      if (id.contains("Next")) tab++;
      tabPane.getSelectionModel().select(tab);
      tabPane.requestFocus();
    }
  }

  void showError(String error) {
    if (labelErrorUsername != null) labelErrorUsername.setText(error);
    if (labelErrorPassword != null) labelErrorPassword.setText(error);
    if (labelErrorSecQues != null) labelErrorSecQues.setText(error);
    if (labelErrorSecAns != null) labelErrorSecAns.setText(error);
  }
}
