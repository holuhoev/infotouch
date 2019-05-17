package ru.hse.infotouch.domain.models.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;
import ru.hse.infotouch.domain.dto.request.DeviceRequest;
import ru.hse.infotouch.util.json.PointToJsonSerializer;

import javax.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "device")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column
    private String title;

    @Column
    private String description;

    @Column(columnDefinition = "geometry(Point,4326)")
    @JsonSerialize(using = PointToJsonSerializer.class)
    private Point location;

    @Column(name = "point_id")
    private Integer pointId;

    @Transient
    private Integer buildingId;

    public Integer getPointId() {
        return pointId;
    }

    public void setPointId(Integer pointId) {
        this.pointId = pointId;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
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
        Device device = (Device) o;
        return Objects.equals(id, device.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void updateFromRequest(DeviceRequest request) {
        this.setTitle(request.getTitle());
        this.setDescription(request.getDescription());
    }
}
