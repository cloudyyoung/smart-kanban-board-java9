package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import structure.*;

public class newAccPage3Controller extends newAccPage4Controller {
  private String password;

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  @FXML private BorderPane newAccPane3;

  @FXML private TextField passwordField;

  @FXML private Label nextBtn;

  @FXML
  void handleNxtBtn(MouseEvent event) throws IOException {
    String password = passwordField.getText();

    super.setPassword(password);

    BorderPane pane = FXMLLoader.load(getClass().getResource("newaccPage4.fxml"));
    newAccPane3.getChildren().setAll(pane);
  }
}
