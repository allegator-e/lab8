package controllers;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import  Object.*;
import java.util.*;

class MapDrawing {

    private double plusSize;
    private GraphicsContext gc;
    private TreeMap<Integer, Flat> localCollection;
    private Canvas mapCanvas;

    MapDrawing(GraphicsContext gc, TreeMap<Integer, Flat> localCollection, Canvas mapCanvas){
        this.gc = gc;
        this.localCollection = localCollection;
        this.mapCanvas = mapCanvas;
    }

    void startDrawMap(TreeMap<Integer, Flat> newCollection) {
        //Удаление объектов
        Set<Integer> set = new LinkedHashSet<>(localCollection.keySet());
        drawMap(0);
        for (Integer a : set) {
            if (!newCollection.containsKey(a))
                removeFlatInMap(a, localCollection.get(a));
        }
        //Добавление новых объектов
        newCollection.keySet().stream()
                .filter(key -> !localCollection.containsKey(key))
                .forEach(key -> addNewFlatInMap(key, newCollection.get(key)));
        //Изменение координат и размера
        newCollection.keySet().stream().filter(key -> newCollection.get(key).getArea() != localCollection.get(key).getArea()
                || newCollection.get(key).getCoordinates().getX() != localCollection.get(key).getCoordinates().getX()
                || !newCollection.get(key).getCoordinates().getY().equals(localCollection.get(key).getCoordinates().getY()))
                .forEach(key -> changeParametrs(localCollection.get(key), newCollection.get(key).getCoordinates().getX(),
                        newCollection.get(key).getCoordinates().getY(), newCollection.get(key).getArea()));
    }

    private void  changeParametrs(Flat oldFlat, float newX, long newY, long newArea){
        float oldX = oldFlat.getCoordinates().getX();
        long oldY = oldFlat.getCoordinates().getY();
        long oldArea = oldFlat.getArea();
        for(int i = 1; i <= 80; i++){
            oldFlat.getCoordinates().setX((newX - oldX) * i / 80 + oldX);
            oldFlat.getCoordinates().setY((newY - oldY) * i / 80 + oldY);
            oldFlat.setArea((newArea - oldArea) * i / 80 + oldArea);
            drawMap(0);
            try {
                Thread.sleep(15);
            }catch (InterruptedException e){}
        }
    }

    // добавление новой квартиры
    private void addNewFlatInMap(Integer key, Flat flat) {
        float xFlat = flat.getCoordinates().getX();
        long yFlat = flat.getCoordinates().getY();
        double lengthFinal = Math.max(Math.abs(xFlat), Math.abs(yFlat));
        double lengthStart = plusSize;
        if (2 * lengthFinal > plusSize){
            for(int i = 0; i <= 50; i++){
                drawMap((lengthFinal - lengthStart / 2) * i / 50 + lengthStart / 2);
                try {
                    Thread.sleep(20);
                }catch (InterruptedException e){}
            }
        }
        double size = setSize(flat);
        double x = (flat.getCoordinates().getX() + plusSize/2.0) * (mapCanvas.getWidth()/plusSize);//  - size*120/2D;
        double y = (flat.getCoordinates().getY() + plusSize/2.0) * (mapCanvas.getWidth()/plusSize);
        animateHammer(x, y, size, lengthFinal);
       // flat.setColor(color.toString());
        localCollection.put(key, flat);
        drawMap(0);
    }

    private void removeFlatInMap(Integer key, Flat flat){
        animateRemove(flat, Color.valueOf(flat.getColor()));
        localCollection.remove(key, flat);
        double pastPlussize = plusSize/2;
        double maxx = localCollection.values().stream().mapToDouble(flat1 -> flat1.getCoordinates().getX()).max().orElse(mapCanvas.getWidth());
        double minx = localCollection.values().stream().mapToDouble(flat1 -> flat1.getCoordinates().getX()).min().orElse(mapCanvas.getHeight());
        double maxy = localCollection.values().stream().mapToDouble(flat1 -> flat1.getCoordinates().getY()).max().orElse(mapCanvas.getHeight());
        double miny = localCollection.values().stream().mapToDouble(flat1 -> flat1.getCoordinates().getY()).min().orElse(mapCanvas.getHeight());
        double localPlusSize = Math.max(maxx, Math.max(-Math.min(minx, miny), maxy));
        if(pastPlussize > localPlusSize){
            for(int i = 50; i >= 0; i--){
                drawMap((pastPlussize - localPlusSize) * i / 50 + localPlusSize);
                try {
                    Thread.sleep(20);
                }catch (InterruptedException e){}
            }
        }
        drawMap(0);
    }

    private void animateRemove(Flat flat, Color color){
        for(int i = 100; i >= 0; i--) {
            drawMap(0);
            flat.setColor(Color.color(color.getRed(), color.getGreen(), color.getBlue(), i* 1.0/100).toString());
            try {
                Thread.sleep(10);
            }catch(InterruptedException e){}
        }
       // changeParametrs(flat, 0, 0, Integer.MAX_VALUE);
    }

    // отрисовка карты
    private void drawMap(double length){
        gc = mapCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());

        double maxx = localCollection.values().stream().mapToDouble(flat -> flat.getCoordinates().getX()).max().orElse(mapCanvas.getWidth());
        double minx = localCollection.values().stream().mapToDouble(flat -> flat.getCoordinates().getX()).min().orElse(mapCanvas.getHeight());
        double maxy = localCollection.values().stream().mapToDouble(flat -> flat.getCoordinates().getY()).max().orElse(mapCanvas.getHeight());
        double miny = localCollection.values().stream().mapToDouble(flat -> flat.getCoordinates().getY()).min().orElse(mapCanvas.getHeight());
        plusSize = 2 * Math.max(Math.max(maxx, Math.max(-Math.min(minx, miny), maxy)), length);

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());

        gc.setStroke(Color.BLACK);
        gc.setFill(Color.BLACK);
        gc.strokeLine(0,mapCanvas.getHeight()/2, mapCanvas.getWidth(), mapCanvas.getHeight() / 2);
        gc.strokeLine(mapCanvas.getWidth()/2, 0, mapCanvas.getWidth()/2, mapCanvas.getHeight());
        gc.fillText("0.0", mapCanvas.getWidth()/2, mapCanvas.getHeight()/2 + 20);
        gc.fillText(String.valueOf((int)(-plusSize*2/2.2 / 4)), mapCanvas.getWidth() / 4, mapCanvas.getHeight() / 2 + 20);
        gc.fillText(String.valueOf((int)(plusSize*2/2.2 / 4)), mapCanvas.getWidth() * 3.0 / 4.0, mapCanvas.getHeight() / 2 + 20);

        localCollection.values().stream().sorted(new Comparator<Flat>() {
            @Override
            public int compare(Flat o1, Flat o2) {
                return (int) (o2.getArea() - o1.getArea());
            }
        }).forEach(this::drawFlat);
    }

    // определение параметров дома относительно карты
    private void drawFlat(Flat flat)  {
        double size = setSize(flat);
        double x = (flat.getCoordinates().getX() + plusSize/2.0) * (mapCanvas.getWidth()/plusSize);//  - size*120/2D;
        double y = (flat.getCoordinates().getY() + plusSize/2.0) * (mapCanvas.getWidth()/plusSize);// - size*120/2D;
        //TODO: COLOR
        drawElement(x, y,size, 1.0, 1.0, Color.valueOf(flat.getColor()));
    }

    // определение размера дома
    private double setSize(Flat flat) {
        if (flat.getArea()<15) return 0.05D*mapCanvas.getWidth()/500;
        if (flat.getArea() > 50) {
            return 1D*mapCanvas.getWidth()/1800;
        }
        return flat.getArea()*mapCanvas.getWidth()/120000D;
    }

    //анимация молотка при появлении
    private void animateHammer(double x, double y, double size, double length) {
        for(int i = 0; i < 5; i++){
            drawHammer(gc, x, y + 10, size);
            drawMap(length);
            drawHammer(gc, x, y + 5, size);
            drawMap(length);
            drawHammer(gc, x, y , size);
            drawMap(length);
            drawHammer(gc, x, y + 5, size);
            drawMap(length);
            i++;
        }
    }

    //отрисовка молотка
    private void drawHammer(GraphicsContext gc, double x, double y, double size) {
        x = x - 340 * size / 2;
        y = y - 100 * size/2;
        gc.setLineWidth(0);
        gc.setFill(Color.BLACK);
        //gc.setFill(Color.color(0.1234, 0.567, 0.765));
        gc.fillRect(x, y - 20, 30, 8);
        gc.setFill(Color.BROWN);
        gc.fillRect(x - 5, y - 30 , 10, 30);
        try {
            Thread.sleep(75);
        }catch (InterruptedException ignored) {
        }
        gc.setFill(Color.WHITE);
        gc.fillRect(x - 1, y - 21, 32, 10);
        gc.fillRect(x - 6, y - 31 , 12, 32);
    }

    //отрисовка дома
    private void drawElement(double x, double y, double size, double opacity, double britness, Color color) {
        x = x - 340 * size / 2;
        y = y - 100 * size / 2;
        gc.setStroke(Color.rgb(0,0,0, color.getOpacity()));
        gc.setFill(Color.color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity()));
        gc.setLineWidth(1.0);
        // контур дома
        gc.beginPath();
        gc.moveTo(x, y);
        //85, 255
        gc.lineTo(x + 115 * size,y - 90 * size);
        gc.lineTo(x + 115 * size, y - 165 * size);
        gc.lineTo(x + 135 * size, y - 180 * size);
        gc.lineTo(x + 170 * size, y - 180 * size);
        gc.lineTo(x + 170 * size, y - 130 * size);
        gc.lineTo(x + 225 * size, y - 175 * size);
        gc.lineTo(x + 340 * size, y - 85 * size);
        gc.lineTo(x + 340 * size, y + 85 * size);
        gc.lineTo(x + 225 * size, y + 175*size);
        gc.lineTo(x, y + 175*size);
        gc.lineTo(x, y);
        gc.stroke();
        gc.fill();
        gc.closePath();
        //доп линии
        gc.strokeLine(x,y,x + 225 * size,y);
        gc.strokeLine(x + 225 * size,y,x + 225 * size,y + 175*size);
        gc.strokeLine(x + 225 * size,y,x + 340 * size,y - 85*size);
        gc.strokeLine(x + 225 * size,y,x + 115 * size,y - 90*size);

// труба
        gc.rect(x + 115 * size,y - 165*size,35* size,10* size);
        gc.strokeLine(x + 135 * size,y - 180*size,x + 135 * size,y - 165*size);
        gc.strokeLine(x + 150 * size,y - 165*size,x + 170 * size,y - 180*size);
        gc.strokeLine(x + 150 * size,y - 155*size,x + 170 * size,y - 170*size);
        gc.strokeLine(x + 150 * size,y - 155*size,x + 150 * size,y - 115*size);
        gc.strokeLine(x + 115 * size,y - 90*size,x + 170 * size,y - 130*size);
        gc.stroke();

        gc.setFill(Color.WHITE);

// окно 1
        gc.beginPath();
        gc.rect(x + 70 * size,y + 45*size,85*size,70*size);
        gc.fill();
        gc.stroke();
        gc.closePath();
        gc.strokeLine(x + 70 * size,y + 65*size,x + 155 * size,y + 65*size);
        gc.strokeLine(x + 110 * size,y + 65*size,x + 110 * size,y + 115*size);
        gc.stroke();

// окно 2
        gc.fillOval(x + 86 * size,y - 54*size,48*size,48*size);
        gc.strokeOval(x + 85 * size,y - 55*size,50*size,50*size);
        gc.strokeLine(x + 85 * size,y - 30*size,x + 135 * size,y - 30*size);
        gc.strokeLine(x + 110 * size,y - 55*size,x + 110 * size,y - 5*size);
        gc.stroke();

// окно 3
        gc.beginPath();
        gc.moveTo(x + 260 * size,y + 10*size);
        gc.lineTo(x + 300 * size,y - 20*size);
        gc.lineTo(x + 300 * size,y + 45*size);
        gc.lineTo(x + 260 * size,y + 75*size);
        gc.lineTo(x + 260 * size,y + 10*size);
        gc.fill();
        gc.stroke();
        gc.closePath();
        gc.strokeLine(x + 260 * size,y + 45*size, x + 300 * size, y + 15*size);
        gc.strokeLine(x + 280 * size,y - 5*size,x + 280 * size,y + 60*size);

// окно 4
        gc.beginPath();
        gc.moveTo(x +185 * size,y - 105*size);
        gc.lineTo(x + 250 * size,y - 50*size);
        gc.lineTo(x + 275 * size,y - 70*size);
        gc.lineTo(x + 210 * size,y - 125*size);
        gc.lineTo(x + 185 * size,y - 105*size);
        gc.stroke();
        gc.closePath();
        gc.beginPath();
        gc.moveTo(x + 195 * size,y - 105*size);
        gc.lineTo(x + 250 * size,y - 60*size);
        gc.lineTo(x + 265 * size,y - 70*size);
        gc.lineTo(x + 210 * size,y - 115*size);
        gc.lineTo(x + 195 * size,y - 105*size);
        gc.fill();
        gc.stroke();
        gc.closePath();
    }
}
