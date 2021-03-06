package Object;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates implements Serializable, Comparable<Coordinates> {
    private float x; //Значение поля должно быть больше -227
    private Long y; //Максимальное значение поля: 769, Поле не может быть null

    public Coordinates(float x, Long y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }
    public Long getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(Long y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates {" +
                "x:" + x + ", y:" + y +  "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;
        Coordinates coordinates = (Coordinates) o;
        return x == coordinates.getX() &&
                y.equals(coordinates.getY());
    }

    @Override
    public int hashCode() {
        return Objects.hash(x,y);
    }

    @Override
    public int compareTo(Coordinates o) {
        return (int) (x*x + y*y - o.getX()*o.getX() - o.getY()*o.getY());
    }
}