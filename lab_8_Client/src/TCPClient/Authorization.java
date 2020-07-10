package TCPClient;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;

public class Authorization {

    //private Console console = System.console();
    private boolean access = false;
    private String hostName;
    private int port;
    private ArrayList<String> loginAndPassword = new ArrayList<>();
    private String answer;

    public Authorization(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    public boolean access(String param, String login, String password) throws IOException {
        loginAndPassword.clear();
        loginAndPassword.add(login);
        loginAndPassword.add(password);
        TCPSender sender = new TCPSender(hostName, port, false, loginAndPassword);
        sender.sender(param, "mew");
        access = sender.isAccess();
        answer = (String) sender.getReturnObjects().get(1);
        return access;
    }

    public String getAnswer() {
        return answer;
    }
}
