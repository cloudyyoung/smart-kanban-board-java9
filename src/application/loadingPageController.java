package application;

import java.io.IOException;
import javafx.util.Duration;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.layout.BorderPane;

public class loadingPageController {

  @FXML private BorderPane loadingPane;

  @FXML private Circle blueCircle;

  @FXML private Circle darkBlueCircle;

  @FXML private Circle purpleCircle;

  @FXML
  void loadingComplete(MouseEvent event) throws IOException {

    BorderPane pane = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
    loadingPane.getChildren().setAll(pane);
  }

  public void initialize() {

    FadeTransition fta = new FadeTransition(Duration.millis(700), blueCircle);
    fta.setFromValue(0.5);
    fta.setToValue(1);
    fta.setCycleCount(2);
    fta.setAutoReverse(true);

    FadeTransition ftb = new FadeTransition(Duration.millis(700), darkBlueCircle);
    ftb.setFromValue(0.5);
    ftb.setToValue(1);
    ftb.setCycleCount(2);
    ftb.setAutoReverse(true);

    FadeTransition ftc = new FadeTransition(Duration.millis(700), purpleCircle);
    ftc.setFromValue(0.5);
    ftc.setToValue(1);
    ftc.setCycleCount(2);
    ftc.setAutoReverse(true);

    FadeTransition ftz = new FadeTransition(Duration.millis(700), blueCircle);
    ftz.setFromValue(0.5);
    ftz.setToValue(0.5);
    ftz.setCycleCount(2);
    ftz.setAutoReverse(true);

    SequentialTransition seqT = new SequentialTransition(ftz, fta, ftb, ftc);

    seqT.setCycleCount(100);
    seqT.play();
  }
}
