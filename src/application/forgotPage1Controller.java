package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import structure.*;

public class forgotPage1Controller {

  @FXML private BorderPane forgotPagePane1;

  @FXML
  void handleForgot1(MouseEvent event) throws IOException {
    System.out.println("again");

    BorderPane pane = FXMLLoader.load(getClass().getResource("forgotPage2.fxml"));
    forgotPagePane1.getChildren().setAll(pane);
  }
}
