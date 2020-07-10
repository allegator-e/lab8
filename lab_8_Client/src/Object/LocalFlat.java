package Object;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class LocalFlat extends Flat{

    private Integer key;

    public LocalFlat(Integer key, Flat flat) {
        super(flat.getId(),
                flat.getName(),
                flat.getCoordinates(),
                flat.getCreationDate(),
                flat.getArea(),
                flat.getNumberOfRooms(),
                flat.getFurnish(),
                flat.getView(),
                flat.getTransport(),
                flat.getHouse(),
                flat.getUserID(),
                flat.getColor());
        this.key = key;
    }

    public Integer getKey() { return key; }
    public Float getCoordinatesX() {return getCoordinates().getX(); }
    public Long getCoordinatesY() {return getCoordinates().getY(); }
    public String getHouseName() {return getHouse().getName(); }
    public Integer getYear() {return getHouse().getYear(); }
    public Integer getNumberOfFloors() {return getHouse().getNumberOfFloors(); }
    public Long getNumberOfFlatsOnFloor() {return getHouse().getNumberOfFlatsOnFloor(); }
}