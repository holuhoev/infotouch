package ru.hse.infotouch.domain.dto.request;

import ru.hse.infotouch.domain.models.enums.HseLocationType;

public class HseLocationRequest {
    private String title;
    private HseLocationType type;

    // TODO: may be replaced when start front
    private double x;
    private double y;

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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
