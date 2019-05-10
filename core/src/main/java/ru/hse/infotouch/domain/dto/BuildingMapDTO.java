package ru.hse.infotouch.domain.dto;

import ru.hse.infotouch.domain.models.Room;
import ru.hse.infotouch.domain.models.map.Edge;
import ru.hse.infotouch.domain.models.map.Point;

import java.util.List;

public class BuildingMapDTO {
    private int buildingId;
    private List<Point> points;
    private List<Edge> edges;
    private List<Room> rooms;

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
