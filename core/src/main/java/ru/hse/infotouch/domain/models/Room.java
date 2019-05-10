package ru.hse.infotouch.domain.models;

import ru.hse.infotouch.ruz.util.JsonField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "auditorium")
public class Room extends RuzObject implements DomainObject {
    @Id
    @JsonField(name = "auditoriumOid")
    @Column(name = "ID")
    private Integer Id;

    @Column(name = "auditorium_type")
    @JsonField(name = "typeOfAuditorium")
    private String auditoriumType;

    @Column(name = "number")
    @JsonField
    private String number;

    @Column(name = "building_id")
    @JsonField(name = "buildingOid")
    private Integer buildingId;

    private String coordinates;

    private Integer floor;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getAuditoriumType() {
        return auditoriumType;
    }

    public void setAuditoriumType(String auditoriumType) {
        this.auditoriumType = auditoriumType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room that = (Room) o;
        return Objects.equals(Id, that.Id) &&
                Objects.equals(auditoriumType, that.auditoriumType) &&
                Objects.equals(number, that.number) &&
                Objects.equals(buildingId, that.buildingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, auditoriumType, number, buildingId);
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
