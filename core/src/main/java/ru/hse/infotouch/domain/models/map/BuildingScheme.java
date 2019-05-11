package ru.hse.infotouch.domain.models.map;

import ru.hse.infotouch.domain.models.DomainObject;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "building_scheme")
public class BuildingScheme implements DomainObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "building_id")
    private Integer buildingId;

    @Column(name = "floor")
    private Integer floor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildingScheme that = (BuildingScheme) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
