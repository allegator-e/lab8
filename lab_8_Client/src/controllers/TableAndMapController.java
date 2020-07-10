package controllers;

import TCPClient.DialogMessage;
import TCPClient.TCPSender;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import Object.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class TableAndMapController {

    private File file;
    private TreeMap<Integer, Flat> localCollection = new TreeMap<Integer, Flat>();
    private Desktop desktop = Desktop.getDesktop();
    private String host;
    private int port;
    private String login;
    private TCPSender sender;
    private ArrayList<String> loginAndPassword = new ArrayList<>();
    private GraphicsContext gc;
    private String password;
    private javafx.scene.paint.Color myColor;
    private Integer userID;
    private MapDrawing mapDrawing;

    TableAndMapController(String host, int port, String login, String  password, Integer userID){
        this.host = host;
        this.port = port;
        this.login = login;
        this.password = password;
        loginAndPassword.add(login);
        loginAndPassword.add(password);
        sender = new TCPSender(host, port, true, loginAndPassword);
        this.userID = userID;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuBar hightMenu;

    @FXML
    private Menu languageMenu;

    @FXML
    private RadioMenuItem englishMenuItem;

    @FXML
    private ToggleGroup Language;

    @FXML
    private RadioMenuItem RussianMenuItem;

    @FXML
    private RadioMenuItem NorskMenuItem;

    @FXML
    private RadioMenuItem MagyarMenuItem;

    @FXML
    private RadioMenuItem EspanolMenuItem;

    @FXML
    private MenuItem infoItem;

    @FXML
    private MenuItem helpItem;

    @FXML
    private MenuItem averageOfNumberOfRoomsItem;

    @FXML
    private MenuItem groupByCreationDateItem;

    @FXML
    private MenuItem countByTransportItem;

    @FXML
    private MenuItem insertItem;

    @FXML
    private MenuItem updateItem;

    @FXML
    private MenuItem removeKeyItem;

    @FXML
    private MenuItem removeGreaterItem;

    @FXML
    private MenuItem removeGreaterKeyItem;

    @FXML
    private MenuItem clearItem;

    @FXML
    private MenuItem historyItem;

    @FXML
    private MenuItem writeScriptItem;

    @FXML
    private MenuItem chooseFileItem;

    @FXML
    private MenuItem executeScriptItem;

    @FXML
    private Label userName;

    @FXML
    private TabPane choosePlane;

    @FXML
    private Tab tableTab;

    @FXML
    private TableView<Flat> tableView;

    @FXML
    private TableColumn<Flat, Integer> keyColumn;

    @FXML
    private TableColumn<Flat, Integer> idColumn;

    @FXML
    private TableColumn<Flat, String> nameColumn;

    @FXML
    private TableColumn<Flat, Coordinates> xColumn;

    @FXML
    private TableColumn<Flat, Long> yColumn;

    @FXML
    private TableColumn<Flat, LocalDateTime> dateColumn;

    @FXML
    private TableColumn<Flat, Long> areaColumn;

    @FXML
    private TableColumn<Flat, Integer> roomsColumn;

    @FXML
    private TableColumn<Flat, Furnish> furnitureColumn;

    @FXML
    private TableColumn<Flat, View> viewColumn;

    @FXML
    private TableColumn<Flat, Transport> transportColumn;

    @FXML
    private TableColumn<Flat, String> houseNameColumn;

    @FXML
    private TableColumn<Flat, Integer> ageColumn;

    @FXML
    private TableColumn<Flat, Integer> floorsColumn;

    @FXML
    private TableColumn<Flat, Long> flatsColumn;

    @FXML
    private Button filterButton;

    @FXML
    private Tab mapTab;

    @FXML
    private HBox mapBox;

    @FXML
    private Pane mapPane;

    @FXML
    private Canvas mapCanvas;

    @FXML
    private VBox informBox;

    @FXML
    private VBox informPlaceBox;

    @FXML
    private Label nameLabel;

    @FXML
    private Label xLabel;

    @FXML
    private Label yLabel;

    @FXML
    private Label areaLabel;

    @FXML
    private Label roomsLabel;

    @FXML
    private Label furnishLabel;

    @FXML
    private Label viewLabel;

    @FXML
    private Label transportLabel;

    @FXML
    private Label houseNameLabel;

    @FXML
    private Label yearLabel;

    @FXML
    private Label floorsLabel;

    @FXML
    private Label flatsLabel;

    private void initializeColumn(){
        keyColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        xColumn.setCellValueFactory(new PropertyValueFactory<>("coordinatesX"));
        yColumn.setCellValueFactory(new PropertyValueFactory<>("coordinatesY"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        areaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));
        roomsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));
        furnitureColumn.setCellValueFactory(new PropertyValueFactory<>("furnish"));
        viewColumn.setCellValueFactory(new PropertyValueFactory<>("view"));
        transportColumn.setCellValueFactory(new PropertyValueFactory<>("transport"));
        houseNameColumn.setCellValueFactory(new PropertyValueFactory<>("houseName"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        floorsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfFloors"));
        flatsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfFlatsOnFloor"));
    }

    private void printElement(String command){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Resources/views/newElement.fxml"));
        loader.setController(new NewElementController(new TCPSender(host, port, true, loginAndPassword), command));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void openFile(File file) {
        try {
            this.desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void request(String answer){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Resources/views/warningOrResult.fxml"));
        loader.setController(new WarningOrResultController(answer));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void initialize() {
       // String madeColor = (login.hashCode()*54321+123456789)+"";
       // myColor = Color.color(Double.parseDouble("0."+madeColor.substring(0,2)),
        //        Double.parseDouble("0."+madeColor.substring(3, 5)),
         //       Double.parseDouble("0."+madeColor.substring(6, 8)));
        mapDrawing = new MapDrawing(gc, localCollection, mapCanvas);
        initializeColumn();
        userName.setText("User: " + login);

        historyItem.setOnAction(event -> {
            sender.sender("history", "mew");
            request((String) sender.getReturnObjects().get(1));
        });

        countByTransportItem.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../Resources/views/countByTransport.fxml"));
            loader.setController(new CountByTransportController(new TCPSender(host, port, true, loginAndPassword)));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });

        insertItem.setOnAction(event -> {
            printElement("insert");
        });

        updateItem.setOnAction(event -> {
            printElement("update_id");
        });

        removeGreaterItem.setOnAction(event -> {
            printElement("remove_greater");
        });
        averageOfNumberOfRoomsItem.setOnAction(event -> {
            sender.sender("average_of_number_of_rooms", "mew");
            request((String) sender.getReturnObjects().get(1));
        });

        groupByCreationDateItem.setOnAction(event -> {
            sender.sender("group_counting_by_creation_date", "mew");
            request((String) sender.getReturnObjects().get(1));
        });
        removeKeyItem.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../Resources/views/keyForRemove.fxml"));
            loader.setController(new KeyForRemoveController(new TCPSender(host, port, true, loginAndPassword), "remove_key"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
        removeGreaterKeyItem.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../Resources/views/keyForRemove.fxml"));
            loader.setController(new KeyForRemoveController(new TCPSender(host, port, true, loginAndPassword), "remove_greater_key"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
        infoItem.setOnAction(event -> {
            sender.sender("info", "mew");
            request((String) sender.getReturnObjects().get(1));
        });
        helpItem.setOnAction(event -> {
            sender.sender("help", "mew");
            request((String) sender.getReturnObjects().get(1));
        });
        clearItem.setOnAction(event -> {
           if(new DialogMessage().closeWarring("Вы действительно хотите удалить все свои объекты?")){
                sender.sender("clear", "mew");
           }
        });
        filterButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../Resources/views/filter.fxml"));
            loader.setController(new FilterController());
            try{
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        });

        chooseFileItem.setOnAction(event -> {
            //textArea.clear();
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt"));
            file = fileChooser.showOpenDialog(filterButton.getScene().getWindow());
        });

        executeScriptItem.setOnAction(event -> {
            BufferedReader commandReader = null;
            try {
                String str = sender.checker(new String[] {"execute_script", file.getAbsolutePath()}, commandReader);
                request(str + "скрипт успешно выполнен.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        /*
        //TODO:BagFix
        mapCanvas.setOnMouseClicked(event -> {
            double finalCoordX = (event.getSceneX() + 50) * (plusSize / mapCanvas.getWidth()) - plusSize / 2.0;
            double finalCoordY = plusSize / 2.0 - (event.getSceneY() + 90) * (plusSize / mapCanvas.getWidth());

            Flat flat = localCollection.values().stream().filter(flat1 ->
                    Math.abs(flat1.getCoordinates().getX() - finalCoordX) < plusSize * 0.05)
                    .filter(flat1 ->
                            Math.abs(flat1.getCoordinates().getY() - finalCoordY) < plusSize * 0.05)
                    .findAny().orElse(null);
            if (flat != null) new DialogMessage().info(flat.getCoordinates().toString());
            //handleDetailDragon();
        });
        */
        rewriteTable();
    }

    private void rewriteTable() {
        Thread thread = new Thread(() -> {
            TCPSender sender = new TCPSender(host, port, true, loginAndPassword);
            while (true) {
                sender.sender("show", "mew");
                TreeMap<Integer, Flat> newCollection = (TreeMap<Integer, Flat>) sender.getReturnObjects().get(1);
                tableView.getItems().clear();
                newCollection.keySet().forEach(key -> tableView.getItems().add(new LocalFlat(key, newCollection.get(key))));
                tableView.sort();
                mapDrawing.startDrawMap(newCollection);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                localCollection.clear();
                localCollection.putAll(newCollection);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }


    //TODO: у канваса не работает getChildren, чтоб сделать анимацию!
    /*
    public void animateEntry(Flat flat) {
        Pane wrapperMapPane = new Pane();
        double x = ((flat.getCoordinates().getX() + plusSize / 2.0) * (mapCanvas.getWidth() / plusSize));
        double y = ((plusSize / 2.0 - flat.getCoordinates().getY()) * (mapCanvas.getHeight() / plusSize));
        Circle circle = new Circle(x, y, setSize(flat) * 120, Color.PINK);
        wrapperMapPane.getChildren().add(circle);
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1.5), circle);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);
        fadeOut.play();

        fadeOut.setOnFinished(e -> {
            wrapperMapPane.getChildren().remove(circle);
        });

    }
     */

}
