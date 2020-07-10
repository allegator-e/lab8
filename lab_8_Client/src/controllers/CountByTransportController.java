package controllers;

import TCPClient.TCPSender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CountByTransportController {
    TCPSender sender;

    CountByTransportController(TCPSender sender){
        this.sender = sender;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> transportBox;

    @FXML
    private Button okButton;

    @FXML
    private Label answerLabel;

    @FXML
    void initialize() {
        transportBox.getItems().addAll("NONE",
                "LITTLE",
                "FEW",
                "NORMAL",
                "ENOUGH");
        transportBox.getSelectionModel().selectFirst();
        answerLabel.setMaxWidth(340);
        answerLabel.setWrapText(true);
        okButton.setOnAction(event -> {
            sender.sender("count_by_transport", transportBox.getSelectionModel().getSelectedItem());
            String answer = (String) sender.getReturnObjects().get(1);
            answerLabel.setText(answer);
        });
    }
}
