package ru.hse.infotouch.domain.dto.request;

public class CreatePointDTO {
    private int x;
    private int y;
    private Integer schemeElementId;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Integer getSchemeElementId() {
        return schemeElementId;
    }

    public void setSchemeElementId(Integer schemeElementId) {
        this.schemeElementId = schemeElementId;
    }
}
