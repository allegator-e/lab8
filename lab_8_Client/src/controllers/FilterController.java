package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import Object.*;

public class FilterController {

    private ObservableList<Flat> masterData = FXCollections.observableArrayList();

    @FXML
    private TextField xFromField;

    @FXML
    private TextField xToField;

    @FXML
    private TextField yFromField;

    @FXML
    private TextField yToField;

    @FXML
    private TextField areaFromField;

    @FXML
    private TextField areaToField;

    @FXML
    private TextField roomsFromField;

    @FXML
    private TextField roomsToField;

    @FXML
    private ComboBox<String> furnishBox;

    @FXML
    private ComboBox<String> viewBox;

    @FXML
    private ComboBox<String> transportBox;

    @FXML
    private TextField yearFromField;

    @FXML
    private TextField yearToField;

    @FXML
    private TextField floorsFromField;

    @FXML
    private TextField floorsToField;

    @FXML
    private TextField flatsFromfield;

    @FXML
    private TextField flatsToField;

    @FXML
    private CheckBox elementsCheckBox;

    @FXML
    private Button applyButton;

    @FXML
    private Button clearButton;

    @FXML
    void initialize() {
        viewBox.getItems().addAll("all",
                "PARK",
                "STREET",
                "BAD",
                "null");
        viewBox.getSelectionModel().selectFirst();
        furnishBox.getItems().addAll("all",
                "DESIGNER",
                "FINE",
                "LITTLE",
                "BAD",
                "NONE",
                "null");
        furnishBox.getSelectionModel().selectFirst();
        transportBox.getItems().addAll("all",
                "NONE",
                "LITTLE",
                "FEW",
                "NORMAL",
                "ENOUGH");
        transportBox.getSelectionModel().selectFirst();
        clearButton.setOnAction(event -> {
            xToField.setText("");
            xFromField.setText("");
            yFromField.setText("");
            yToField.setText("");
            areaFromField.setText("");
            areaToField.setText("");
            roomsFromField.setText("");
            roomsToField.setText("");
            yearFromField.setText("");
            floorsFromField.setText("");
            floorsToField.setText("");
            flatsFromfield.setText("");
            flatsToField.setText("");
            elementsCheckBox.setSelected(false);
            furnishBox.getSelectionModel().selectFirst();
            viewBox.getSelectionModel().selectFirst();
            transportBox.getSelectionModel().selectFirst();
        });
        applyButton.setOnAction(event -> {
            FilteredList<Flat> filteredData = new FilteredList<>(masterData, p -> true);

        });
    }
}
