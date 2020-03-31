package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

public class HomeController {

    @FXML
    private Button sideProfile;

    @FXML
    private Circle profileAvatar;

    @FXML
    private Label profileUsername;

    @FXML
    private Button sideSearch;

    @FXML
    private Button sideToday;

    @FXML
    private Tab boardPane;

    @FXML
    private TextField boardTitle;

    @FXML
    private TextField boardNote;

    @FXML
    private Button boardAddColumn;

    @FXML
    private HBox columnPane;

    @FXML
    private ScrollPane detailPane;

    @FXML
    private TextArea detailTitle;

    @FXML
    private Button detailAdd2Today;

    @FXML
    private Button detailDueDate;

    @FXML
    private Button detailImportance;

    @FXML
    private TextField detailNote;

}
