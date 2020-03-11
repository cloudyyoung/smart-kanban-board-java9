package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import structure.*;

public class newAccPage4Controller {
  private String momBorn;
  private String graduate;
  private String username;
  private String password;

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return this.username;
  }

  public void setMomBorn(String momBorn) {
    this.momBorn = momBorn;
  }

  public String getMomBorn() {
    return momBorn;
  }

  public void setGraduate(String graduate) {
    this.graduate = graduate;
  }

  public String getGraduate() {
    return graduate;
  }

  @FXML private BorderPane newAccPane4;

  @FXML private TextField motherBornField;

  @FXML private TextField graduateField;

  @FXML private Label nextBtn;

  @FXML
  void handleNextBtn(MouseEvent event) throws IOException {

    String momBorn = motherBornField.getText();

    String graduate = graduateField.getText();

    setGraduate(graduate);
    setMomBorn(momBorn);

    User user = new User();
    // user.resgister(getUsername(), getPassword(), getMomBorn(), getGraduate());
    System.out.println(this.username + this.password + getMomBorn() + getGraduate());

    BorderPane pane = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
    newAccPane4.getChildren().setAll(pane);
  }
}
