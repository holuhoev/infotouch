package ru.hse.infotouch.domain.dto.request;

import java.util.Map;

public class HseLocationPointsRequest {
   private Map<Integer, Integer> hseLocationToPoint;

    public Map<Integer, Integer> getHseLocationToPoint() {
        return hseLocationToPoint;
    }

    public void setHseLocationToPoint(Map<Integer, Integer> hseLocationToPoint) {
        this.hseLocationToPoint = hseLocationToPoint;
    }
}
