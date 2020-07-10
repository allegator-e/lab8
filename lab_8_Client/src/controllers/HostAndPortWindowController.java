package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import TCPClient.Authorization;
import TCPClient.DialogMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HostAndPortWindowController {

    private String host;
    private int port;
    private DialogMessage connectionWarning = new DialogMessage();
    @FXML
    private Button connectButton;

    @FXML
    private TextField hostField;

    @FXML
    private TextField portField;

    @FXML
    private Label exceptionLabel;

    @FXML
    void initialize() {
        connectButton.setOnAction(event -> {
            try {
                host = hostField.getText();
                port = Integer.parseInt(portField.getText());
                if (port >= 65535 || port <= 1){
                    exceptionLabel.setText("Значение port должно лежать в интервале от 1 до 65535.");
                }else {
                    Authorization aut = new Authorization(host, port);
                    aut.access("logIn", "", "");
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../Resources/views/enter.fxml"));
                    loader.setController(new EnterController(host, port));
                    loader.load(); //надо ли что-то на IOException особенного написать?
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    connectButton.getScene().getWindow().hide();
                    stage.setTitle("Authorization");
                    stage.setScene(new Scene(root, 483, 326));
                    stage.setResizable(false);
                    stage.show();
                }
            }catch (NumberFormatException e){
                exceptionLabel.setText("Значение поля port должно быть формата int.");
            }catch (NullPointerException e){
                connectionWarning.error("При подключении к серверу возникла ошибка. Проверьте вводимые данные.");
            }catch (IOException e){
                connectionWarning.error("Возникла непредвиденная ошибка в работе приложения.");
                e.printStackTrace();
            }
        });
    }
}