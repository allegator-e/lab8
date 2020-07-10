package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class WarningOrResultController {

    private String answer;

    WarningOrResultController(String answer){
        this.answer = answer;
    }
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ScrollPane answerScrollPane;

    @FXML
    private Button okButton;

    @FXML
    void initialize() {
        //answerScrollPane.setFitToHeight(false);
        answerScrollPane.setFitToWidth(true);
        Text text = new Text(answer);
        text.setWrappingWidth(450);
        answerScrollPane.setContent(text);
        okButton.setOnAction(event -> {
            okButton.getScene().getWindow().hide();
        });
    }
}
