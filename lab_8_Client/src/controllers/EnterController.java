package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import TCPClient.DialogMessage;
import TCPClient.Authorization;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EnterController {

    private String host;
    private int port;
    private Authorization authorization;
    private DialogMessage authorizationWarning = new DialogMessage();

    public EnterController(String host, int port) {
        this.host = host;
        this.port = port;
        authorization = new Authorization(host, port);
    }

    @FXML
    private Button enter;

    @FXML
    private Button register;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    void initialize() {
        enter.setOnAction(event -> {
            try {
                if (authorization.access("logIn", login.getText(), password.getText())) {
                    try {
                        Integer userID = Integer.parseInt(authorization.getAnswer());
                        register.getScene().getWindow().hide();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../Resources/views/tableAndMap.fxml"));
                        loader.setController(new TableAndMapController(host, port, login.getText(), password.getText(), userID));
                        loader.load();
                        Parent root = loader.getRoot();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                    }catch (NumberFormatException e){
                        e.printStackTrace();
                        new DialogMessage().error("Произошла странная ошибка. Обратитесь к разработчикам.");
                    }
                } else
                    authorizationWarning.warning("Неправильный логин/пароль. Попробуйте еще раз.");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                authorizationWarning.error("В процессе получения данных с сервера возникла ошибка.");
            }
        });

        register.setOnAction(event -> {
            if (login.getText().equals(""))
                authorizationWarning.warning("Логин не может быть пустым.");
            else {
                try {
                    if (authorization.access("reg", login.getText(), password.getText())) {
                        authorizationWarning.info("Вы успешно зарегистрировались. Для входа введите пароль еще раз.");
                        password.setText("");
                    } else
                        authorizationWarning.warning("Пользователь с данным логином уже существует. Придумайте другой.");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    authorizationWarning.error("В процессе получения данных с сервера возникла ошибка.");
                }
            }
        });
    }
}