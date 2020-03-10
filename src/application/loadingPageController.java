package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class loadingPageController {

  @FXML
  private BorderPane loadingPane;

  @FXML
  private Circle blueCircle;

  @FXML
  private Circle darkBlueCircle;

  @FXML
  private Circle purpleCircle;

  @FXML
  void loadingComplete(MouseEvent event) throws IOException {

    blueCircle.setOpacity(1);
    blueCircle.setOpacity(0.75);
    darkBlueCircle.setOpacity(1);
    darkBlueCircle.setOpacity(0.75);
    purpleCircle.setOpacity(1);
    purpleCircle.setOpacity(0.75);

    BorderPane pane = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
    loadingPane.getChildren().setAll(pane);
  }
}
