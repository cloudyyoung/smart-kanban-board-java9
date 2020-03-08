package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;
import structure.*;

public class newAccPage4Controller {

    @FXML
    private TextField motherBornField;

    @FXML
    private TextField graduateField;

    @FXML
    private Label nextBtn;

    @FXML
    void handleNextBtn(MouseEvent event) {
        String momBorn = motherBornField.getText();
        String graduate = graduateField.getText();
    }

}