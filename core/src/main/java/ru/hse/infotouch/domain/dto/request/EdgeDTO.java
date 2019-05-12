package ru.hse.infotouch.domain.dto.request;

public class EdgeDTO {
    private int leftPointId;
    private int rightPointId;
    private int weight;

    public int getLeftPointId() {
        return leftPointId;
    }

    public void setLeftPointId(int leftPointId) {
        this.leftPointId = leftPointId;
    }

    public int getRightPointId() {
        return rightPointId;
    }

    public void setRightPointId(int rightPointId) {
        this.rightPointId = rightPointId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
