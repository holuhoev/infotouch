package ru.hse.infotouch.domain.models.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;
import ru.hse.infotouch.domain.dto.request.HseLocationRequest;
import ru.hse.infotouch.domain.models.enums.HseLocationType;
import ru.hse.infotouch.util.json.PointToJsonSerializer;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "hse_location")
public class HseLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String title;

    @Column
    private HseLocationType type;

    /* Имеется у тех услуг, которые находятся вне здания. */
    @Column(columnDefinition = "geometry(Point,4326)")
    @JsonSerialize(using = PointToJsonSerializer.class)
    private Point location;

    /* Привязка к точке на схеме корпуса. */
    @Column(name = "point_id")
    private Integer pointId;

    /* Здание, в котором(или у которого) находится данная услуга. */
    @Column(name = "building_id")
    private Integer buildingId;

    /* Этаж на котором расположена точка, к которой привязана услуга. */
    @Transient
    private Integer floor;

    /* Cхема к которой привязана точка. */
    @Transient
    private Integer buildingSchemeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HseLocationType getType() {
        return type;
    }

    public void setType(HseLocationType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HseLocation that = (HseLocation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public HseLocation updateFromRequest(HseLocationRequest request) {
        this.setType(request.getType());
        this.setTitle(request.getTitle());
        this.setBuildingId(request.getBuildingId());

        return this;
    }

    public Integer getPointId() {
        return pointId;
    }

    public void setPointId(Integer pointId) {
        this.pointId = pointId;
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

    public Integer getBuildingSchemeId() {
        return buildingSchemeId;
    }

    public void setBuildingSchemeId(Integer buildingSchemeId) {
        this.buildingSchemeId = buildingSchemeId;
    }
}
