package TCPClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class TCPReceiver {
    private Socket socket;
    public TCPReceiver(Socket socket){
        this.socket = socket;
    }
    /**
     * Класс для получения данных с сервера
     */
    public ArrayList<Object> receiver() {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ArrayList<Object> accessAndStroka = (ArrayList<Object>) ois.readObject();
            ois.close();
            return accessAndStroka;
        }catch (IOException|ClassNotFoundException e) {
            return null;
        }
    }
}
