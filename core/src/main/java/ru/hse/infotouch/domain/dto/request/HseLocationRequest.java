package ru.hse.infotouch.domain.dto.request;

import ru.hse.infotouch.domain.models.enums.HseLocationType;

public class HseLocationRequest {
    private String title;
    private HseLocationType type;

    private int buildingId;

    private Double gpsX;
    private Double gpsY;

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

    public Double getGpsX() {
        return gpsX;
    }

    public void setGpsX(Double gpsX) {
        this.gpsX = gpsX;
    }

    public Double getGpsY() {
        return gpsY;
    }

    public void setGpsY(Double gpsY) {
        this.gpsY = gpsY;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }
}
