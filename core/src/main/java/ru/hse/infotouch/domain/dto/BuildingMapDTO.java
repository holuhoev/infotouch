package ru.hse.infotouch.domain.dto;

import ru.hse.infotouch.domain.models.map.BuildingScheme;
import ru.hse.infotouch.domain.models.map.Edge;
import ru.hse.infotouch.domain.models.map.SchemeElement;
import ru.hse.infotouch.domain.models.map.Point;

import java.util.List;

public class BuildingMapDTO {
    private int buildingId;
    private List<BuildingScheme> schemes;
    private List<Point> points;
    private List<Edge> edges;
    private List<SchemeElement> elements;

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

    public List<SchemeElement> getElements() {
        return elements;
    }

    public void setElements(List<SchemeElement> elements) {
        this.elements = elements;
    }

    public List<BuildingScheme> getSchemes() {
        return schemes;
    }

    public void setSchemes(List<BuildingScheme> schemes) {
        this.schemes = schemes;
    }
}
