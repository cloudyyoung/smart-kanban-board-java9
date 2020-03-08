package application;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class loadingPageController {

    @FXML
    private Circle blueCircle;

    @FXML
    private Circle darkBlueCircle;

    @FXML
    private Circle purpleCircle;

    @FXML
    void loadingComplete(MouseEvent event) {

            System.out.println("Button pressed");
            blueCircle.setOpacity(1);
            blueCircle.setOpacity(0.75);
            darkBlueCircle.setOpacity(1);
            darkBlueCircle.setOpacity(0.75);
            purpleCircle.setOpacity(1);
            purpleCircle.setOpacity(0.75);

    }

}
