package ru.hse.infotouch.domain.dto.request;

import java.util.List;

public class CreatePointsRequest {
    private List<CreatePointDTO> points;

    public List<CreatePointDTO> getPoints() {
        return points;
    }

    public void setPoints(List<CreatePointDTO> points) {
        this.points = points;
    }
}
