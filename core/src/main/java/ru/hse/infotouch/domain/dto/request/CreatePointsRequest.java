package ru.hse.infotouch.domain.dto.request;

import java.util.List;

public class CreatePointsRequest {
    private int buildingSchemeId;
    private List<CreatePointDTO> points;

    public List<CreatePointDTO> getPoints() {
        return points;
    }

    public void setPoints(List<CreatePointDTO> points) {
        this.points = points;
    }

    public int getBuildingSchemeId() {
        return buildingSchemeId;
    }

    public void setBuildingSchemeId(int buildingSchemeId) {
        this.buildingSchemeId = buildingSchemeId;
    }
}
