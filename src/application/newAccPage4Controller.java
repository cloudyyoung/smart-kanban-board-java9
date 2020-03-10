package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import structure.*;

public class newAccPage4Controller {

  @FXML
  private BorderPane newAccPane4;

  @FXML
  private TextField motherBornField;

  @FXML
  private TextField graduateField;

  @FXML
  private Label nextBtn;

  @FXML
  void handleNextBtn(MouseEvent event) throws IOException {
    String momBorn = motherBornField.getText();
    String graduate = graduateField.getText();

    BorderPane pane = FXMLLoader.load(getClass().getResource("homePage.fxml"));
    newAccPane4.getChildren().setAll(pane);
  }
}
