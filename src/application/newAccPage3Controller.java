package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import structure.*;

public class newAccPage3Controller {

    @FXML
    private TextField passwordField;

    @FXML
    private Label nextBtn;

    @FXML
    void handleNxtBtn(MouseEvent event) {
        String password = passwordField.getText();
        User user = new User();
        user.setPassword(password);
    }

}