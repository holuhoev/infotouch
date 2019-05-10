package ru.hse.infotouch.domain.models.map;

import javax.persistence.*;

@Entity
@Table(name = "edge")
public class Edge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name = "left_point_id")
    private Integer leftPointId;

    @Column(name = "right_point_id")
    private Integer rightPointId;

    @Column(name = "weight")
    private Integer weight;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLeftPointId() {
        return leftPointId;
    }

    public void setLeftPointId(Integer leftPointId) {
        this.leftPointId = leftPointId;
    }

    public Integer getRightPointId() {
        return rightPointId;
    }

    public void setRightPointId(Integer rightPointId) {
        this.rightPointId = rightPointId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
