package ru.hse.infotouch.domain.models.map;

import ru.hse.infotouch.domain.models.DomainObject;
import ru.hse.infotouch.domain.models.enums.MapElementType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "map_element")
public class MapElement implements DomainObject {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "element_type")
    private MapElementType elementType;

    @Column(name = "label")
    private String label;

    @Column(name = "building_id")
    private Integer buildingId;

    @Column(name = "coordinates")
    private String coordinates;

    @Column(name = "floor")
    private Integer floor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MapElementType getElementType() {
        return elementType;
    }

    public void setElementType(MapElementType elementType) {
        this.elementType = elementType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
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
        MapElement that = (MapElement) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
