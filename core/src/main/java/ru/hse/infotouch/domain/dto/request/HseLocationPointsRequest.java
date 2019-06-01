package ru.hse.infotouch.domain.dto.request;

public class HseLocationPointsRequest {
    private int serviceId;
    private int pointId;

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }
}
