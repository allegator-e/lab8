package controllers;

import TCPClient.DialogMessage;
import TCPClient.TCPSender;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import Object.*;
import javafx.scene.paint.Color;

public class NewElementController {

    String command;
    TCPSender sender;

    NewElementController(TCPSender sender, String command){
        this.sender = sender;
        this.command = command;
    }

    @FXML
    private Label keyIdLabel;

    @FXML
    private TextField keyIdField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField xLabel;

    @FXML
    private TextField yLabel;

    @FXML
    private TextField areaLabel;

    @FXML
    private TextField roomsLabel;

    @FXML
    private ComboBox<String> furnishBox;

    @FXML
    private ComboBox<String> viewBox;

    @FXML
    private ComboBox<String> transportBox;

    @FXML
    private TextField nameOfHouseField;

    @FXML
    private TextField yearLabel;

    @FXML
    private TextField floorsLabel;

    @FXML
    private TextField flatsLabel;

    @FXML
    private Button okButton;

    @FXML
    void initialize() {;
        furnishBox.getItems().addAll("DESIGNER",
                "FINE",
                "LITTLE",
                "BAD",
                "NONE",
                "null");
        furnishBox.getSelectionModel().selectFirst();

        viewBox.getItems().addAll("PARK",
                "STREET",
                "BAD",
                "null");
        viewBox.getSelectionModel().selectFirst();

        transportBox.getItems().addAll("NONE",
                "LITTLE",
                "FEW",
                "NORMAL",
                "ENOUGH");
        transportBox.getSelectionModel().selectFirst();
        if(command.equals("remove_greater")) {
            keyIdLabel.setVisible(false);
            keyIdField.setVisible(false);
            keyIdField.setText("0");
        }else if(command.equals("update_id")){
            keyIdLabel.setText("Id:");
        }
        okButton.setOnAction(event -> {
            try {
                sender.sender(command + " " + Integer.parseInt(keyIdField.getText()), new Flat(0, nameField.getText(), new Coordinates(Float.parseFloat(xLabel.getText()),
                        Long.parseLong(yLabel.getText())), LocalDateTime.of(LocalDate.now(), LocalTime.now()), Long.parseLong(areaLabel.getText()), Integer.parseInt(roomsLabel.getText()),
                        furnishBox.getSelectionModel().getSelectedItem().equals("null") ? null : Furnish.valueOf((furnishBox.getSelectionModel().getSelectedItem() )),
                        viewBox.getSelectionModel().getSelectedItem().equals("null") ? null : View.valueOf(viewBox.getSelectionModel().getSelectedItem()),
                        Transport.valueOf(transportBox.getSelectionModel().getSelectedItem()),
                        new House(nameOfHouseField.getText(), Integer.parseInt(yearLabel.getText()), Integer.parseInt(floorsLabel.getText()), Long.parseLong(flatsLabel.getText()))
                        , 11, Color.BLACK.toString()));
                String answer = (String) sender.getReturnObjects().get(1);
                new DialogMessage().info(answer);
            } catch (Exception e) {
                e.printStackTrace();
                new DialogMessage().warning("Неправильный формат введенных данных");
            }
        });
    }
}