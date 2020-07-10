package TCPClient;

import controllers.EnterController;
import controllers.HostAndPortWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Scanner;

//TODO: filter
//TODO: Clicker
//TODO: languages
//TODO: проверка при NewElement каждого поля

/**
 * Класс для подключения к серверу
 */
public class TCPClientConnect extends Application {
    private static String host;
    private static int port;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Resources/views/hostAnPortWindow.fxml"));
        loader.setController(new HostAndPortWindowController());
        loader.load();
        Parent root = loader.getRoot();
        primaryStage.setTitle("Connection");
        primaryStage.setScene(new Scene(root, 483, 326));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        //initialization();
        launch(args);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("Работа программы завершена!")));
    }

  /* static void initialization() {
        Scanner commandReader = new Scanner(System.in);
        System.out.print("Введите hostname:");
        //host = commandReader.nextLine();
        host = "localhost";
        while (true) {
            try {
                System.out.print("Введите порт:");
                port = Integer.parseInt(commandReader.nextLine());
                if (port <= 65535 && port >= 1) break;
            } catch (NumberFormatException e) {
                System.out.println("Порт должен принимать целочисленные значения от 1 до 65535.");
            }
        }
    }*/
}

