package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class forgotPage2Controller {

  @FXML
  private BorderPane forgotPagePane2;

  @FXML
  void handleForgot2(MouseEvent event) throws IOException {
    BorderPane pane = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
    forgotPagePane2.getChildren().setAll(pane);
  }
}
