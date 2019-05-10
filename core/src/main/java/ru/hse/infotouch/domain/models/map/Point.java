package ru.hse.infotouch.domain.models.map;

import ru.hse.infotouch.domain.models.DomainObject;
import ru.hse.infotouch.domain.models.enums.PointType;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "point")
public class Point implements DomainObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name = "x")
    private Integer x;

    @Column(name = "y")
    private Integer y;

    @Column(name = "room_id")
    private Integer roomId;

    @Column(name = "point_type")
    private PointType pointType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public PointType getPointType() {
        return pointType;
    }

    public void setPointType(PointType pointType) {
        this.pointType = pointType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(id, point.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
