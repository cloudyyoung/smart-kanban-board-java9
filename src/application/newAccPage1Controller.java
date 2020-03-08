package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import structure.*;

public class newAccPage1Controller {

    @FXML
    private TextField emailTextField;

    @FXML
    private Label nextBtn;

    @FXML
    void handleNextClick(MouseEvent event) {
        String email = emailTextField.getText();
       // User user = new User();
        //HttpRequest req = user.s
    }

}