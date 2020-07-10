package TCPClient;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class DialogMessage {

    public void error(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(text);
        alert.showAndWait();
    }
    public boolean closeWarring(String text){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Waring");
        alert.setHeaderText(text);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.CANCEL);
        final Optional<ButtonType> buttonType = alert.showAndWait();
        return buttonType.get() == ButtonType.YES;
    }
    public void warning(String text){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(text);
        alert.showAndWait();
    }
    public void info(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(text);
        alert.show();
    }
}
