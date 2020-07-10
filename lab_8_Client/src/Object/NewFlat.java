package Object;

import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

public class NewFlat implements Serializable {

    private BufferedReader commandReader;

    public NewFlat(BufferedReader commandReader){
        this.commandReader = commandReader;
    }
    //private Scanner reader = new Scanner(System.in);
 /*   public String readString(String name) throws IOException{
        //System.out.print("Введите " + name +": ");
        return commandReader.readLine();
    }*/

    public String readStringNotNull() throws IOException {
        //System.out.print("Введите " + name +": ");
        String n = commandReader.readLine();
        if (n.equals("")) {
            //System.out.println("Поле не может быть null или пустой строкой ");
            return readStringNotNull();
        } else return n;
    }

    public Number readNumber(String format) throws IOException {
        String n = readStringNotNull();
        try {
            switch (format) {
                case "Float":
                    return Float.parseFloat(n);
                case "Integer":
                    return Integer.parseInt(n);
                case "Long":
                    return Long.parseLong(n);
                default:
                    return null;
            }
        } catch (NumberFormatException ex) {
            //System.out.println("Аргумент не является значением типа " + format);
            return readNumber(format);
        }
    }

    public Enum readEnam(String type) throws IOException {
        String n = commandReader.readLine();
        n = n.toUpperCase();
        try {
            switch (type) {
                case "Furnish":
                    if (n.equals("")) return null;
                    return Furnish.valueOf(n);
                case "View":
                    if (n.equals("")) return null;
                    return View.valueOf(n);
                case "Transport":
                    return Transport.valueOf(n);
                default:
                    return null;
            }
        } catch (IllegalArgumentException ex) {
            //System.out.println("Значение поля неверное");
            return readEnam(type);
        }
    }

    /**
     * Получает значения элемента в коллекции
     */
    public Flat newFlat() throws IOException, NullPointerException {
        String name = readStringNotNull();
        //System.out.println("Введите coordinates: ");
        float x = (Float) readNumber("Float");
        while (x <= -227) {
            //System.out.println("Значение поля должно быть больше -227");
            x = (Float) readNumber("Float");
        }
        Long y = (Long) readNumber("Long");
        while (y > 769) {
           // System.out.println("Значение поля должно быть меньше 769");
            y = (Long) readNumber("Long");
        }
        long area = (Long) readNumber("Long");
        while (area < 0) {
           // System.out.println("Значение поля должно быть больше 0");
            area = (Long) readNumber( "Long");
        }
        Integer numberOfRooms = (Integer) readNumber("Integer");
        while (numberOfRooms < 0) {
            System.out.println("Значение поля должно быть больше 0");
            numberOfRooms = (Integer) readNumber("Integer");
        }
        Furnish furnish = (Furnish) readEnam("Furnish");
        View view = (View) readEnam("View");
        Transport transport = (Transport) readEnam("Transport");
        //System.out.println("Введите House: ");
        String nameHouse = readStringNotNull();
        int year = (Integer) readNumber("Integer");
        while (year < 0) {
            //System.out.println("Значение поля должно быть больше 0");
            year = (Integer) readNumber("Integer");
        }
        int numberOfFloors = (Integer) readNumber("Integer");
        while (numberOfFloors < 0) {
            //System.out.println("Значение поля должно быть больше 0");
            numberOfFloors = (Integer) readNumber("Integer");
        }
        long numberOfFlatsOnFloor = (Long) readNumber("Long");
        while (numberOfFlatsOnFloor < 0) {
            //System.out.println("Значение поля должно быть больше 0");
            numberOfFlatsOnFloor = (Integer) readNumber("Integer");
        }
        int id = 0;
        LocalDateTime creationDate;
        LocalDate d = LocalDate.now();
        LocalTime t = LocalTime.now();
        creationDate = LocalDateTime.of(d,t);
        //System.out.println("Все значения элемента успешно получены");
        //return null;
        return new Flat(id, name, new Coordinates(x, y), creationDate, area, numberOfRooms, furnish, view, transport, new House(nameHouse, year, numberOfFloors, numberOfFlatsOnFloor), 1, Color.BLACK.toString());
    }

}
