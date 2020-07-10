package TCPClient;

import Object.*;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Класс для отправки данных на сервер.
 */

public class TCPSender {

    BufferedReader commandReader;
    private NewFlat newFlat;
    private String str = "";
    private String hostname;
    private boolean access;
    private ArrayList<String> loginAndPassword;
    private int port;
    private ArrayList<File> scripts = new ArrayList<>();
    private ArrayList<Object> returnObjects;

    public TCPSender(String hostname, int port, boolean access, ArrayList<String> loginAndPassword) {
        this.hostname = hostname;
        this.port = port;
        this.access = access;
        this.loginAndPassword = loginAndPassword;
    }

    public boolean isAccess() {
        return (boolean)returnObjects.get(0);
    }

    public ArrayList<Object> getReturnObjects() {
        return returnObjects;
    }

    public String checker(String[] command, BufferedReader commandReader) throws IOException {
        int key;
        boolean correct = true;
        Flat flat;
        newFlat = new NewFlat(commandReader);
        switch (command[0]) {
            case "insert":
            case "update_id":
                if (command.length == 2) {
                    try {
                        key = Integer.parseInt(command[1]);
                        flat = newFlat.newFlat();
                        sender(command[0] + " " + command[1], flat);
                        return (String) getReturnObjects().get(1) + "\n";
                    } catch (NumberFormatException | IOException ex) {
                        return "Команда некорректна. \n";
                    } catch (NullPointerException e){
                        //System.out.println("kkeeeeek");
                        return "Поля объекта введены неполностью. Команда не может быть выполнена. \n";
                    }
                } else return "Команда некорректна. \n";
            case "remove_greater":
                try {
                    flat = newFlat.newFlat();
                    sender(command[0], flat);
                    return (String) getReturnObjects().get(1) + "\n";
                }catch(NullPointerException e){
                    return "Поля объекта введены неполностью. Команда не может быть выполнена.\n";
                }
            case "remove_greater_key":
            case "remove_key":
                if (command.length == 2) {
                    try {
                        key = Integer.parseInt(command[1]);
                        sender(command[0], key);
                        return (String) getReturnObjects().get(1) + "\n";
                    } catch (NumberFormatException ex) {
                        return "Аргумент не является значением типа Integer. Команда некорректна.\n";
                    }
                } return "Команда некорректна. \n";
            case "count_by_transport":
                if (command.length == 2) {
                    try {
                        command[1] = command[1].toUpperCase();
                        Transport transport = Transport.valueOf(command[1]);
                        sender(command[0], transport);
                        return (String) getReturnObjects().get(1) + "\n";
                    } catch (IllegalArgumentException | NullPointerException ex) {
                        return "Значение поля Transport некорректно. Возможные значения: FEW, NONE, LITTLE, NORMAL, ENOUGH.";
                    }
                } return "Комманда некорректна.";
            case "show": return "";
            case "execute_script":
                if (command.length == 2) {
                    if (!command[1].equals("")) {
                        DialogMessage error = new DialogMessage();
                        File file1 = new File(command[1]);
                        if (!file1.exists())
                            return "Файла с таким названием не существует. \n";
                        else if (!file1.canRead())
                            return "Файл защищён от чтения. Невозможно выполнить скрипт.\n";
                        else if (scripts.contains(file1)) {
                            return "Могло произойти зацикливание при исполнении скрипта: " + command[1] + "\nКоманда не будет выполнена. Переход к следующей команде \n";
                        } else {
                            sender(command[0], "mew");
                            scripts.add(file1);
                            try {
                                String resultNow = "";
                                commandReader = new BufferedReader(new FileReader(file1));
                                String line = commandReader.readLine();
                                while (line != null) {
                                    resultNow = resultNow + checker(line.split(" "), commandReader);
                                    line = commandReader.readLine();
                                }
                                scripts.remove(scripts.size() - 1);
                                return resultNow;
                            } catch (IOException ex) {
                                scripts.remove(scripts.size() - 1);
                                return "Невозможно считать скрипт \n";
                            }
                        }
                    }
                } return "Команда некорректна. \n";
            default:
                sender(command[0], "mew");
                return (String) getReturnObjects().get(1) + "\n";
        }
    }

    public void sender(Object object, Object argument){
        try {
            ArrayList <Object> listObject = new ArrayList<>();
            listObject.add(access);
            listObject.add(loginAndPassword);
            listObject.add(object);
            listObject.add(argument);
            System.out.println(listObject);
            Socket socket = new Socket(hostname, port);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(listObject);
            byte[] outcoming = baos.toByteArray();
            socket.getOutputStream().write(outcoming);
            oos.close();
            baos.close();
            TCPReceiver receiver = new TCPReceiver(socket);
            returnObjects = receiver.receiver();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
            if(object != "logIn") {
                DialogMessage dialogMessage = new DialogMessage();
                dialogMessage.error("Проблемы с передачей на сервер...");
            }

        }
    }
}
