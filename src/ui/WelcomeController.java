
package ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class WelcomeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonSignIn;

    @FXML
    private Button buttonSignUp;

    @FXML
    void initialize() {
        assert buttonSignIn != null : "fx:id=\"buttonSignIn\" was not injected: check your FXML file 'welcome.fxml'.";
        assert buttonSignUp != null : "fx:id=\"buttonSignUp\" was not injected: check your FXML file 'welcome.fxml'.";

    }
    
    @FXML
    public void signIn(MouseEvent event){
        System.out.println(111);
    }

}
