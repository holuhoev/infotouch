package ru.hse.infotouch.domain.models.map;

import ru.hse.infotouch.domain.models.DomainObject;
import ru.hse.infotouch.domain.models.enums.MapElementType;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "scheme_element")
public class SchemeElement implements DomainObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "element_type")
    private MapElementType type;

    @Column(name = "label")
    private String label;

    @Column(name = "coordinates")
    private String coordinates;

    @Column(name = "building_scheme_id")
    private Integer buildingSchemeId;

    @Column(name = "point_id")
    private Integer centerPointId;

    public Integer getBuildingSchemeId() {
        return buildingSchemeId;
    }

    public void setBuildingSchemeId(Integer buildingSchemeId) {
        this.buildingSchemeId = buildingSchemeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MapElementType getType() {
        return type;
    }

    public void setType(MapElementType type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchemeElement that = (SchemeElement) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MapElement{" +
                "id=" + id +
                ", type=" + type +
                ", label='" + label + '\'' +
                ", coordinates='" + coordinates + '\'' +
                '}';
    }

    public Integer getCenterPointId() {
        return centerPointId;
    }

    public void setCenterPointId(Integer centerPointId) {
        this.centerPointId = centerPointId;
    }
}
