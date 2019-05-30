package ru.hse.infotouch.domain.models.map;

import ru.hse.infotouch.domain.dto.request.CreatePointDTO;
import ru.hse.infotouch.domain.models.DomainObject;
import ru.hse.infotouch.domain.models.enums.PointType;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "point")
public class Point implements DomainObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name = "x")
    private Integer x;

    @Column(name = "y")
    private Integer y;

    @Column(name = "scheme_element_id")
    private Integer schemeElementId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(id, point.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static Point createFromRequest(CreatePointDTO createPointDTO) {
        Point point = new Point();

        point.setX(createPointDTO.getX());
        point.setY(createPointDTO.getY());
        point.setSchemeElementId(createPointDTO.getSchemeElementId());

        return point;
    }

    public Integer getSchemeElementId() {
        return schemeElementId;
    }

    public void setSchemeElementId(Integer schemeElementId) {
        this.schemeElementId = schemeElementId;
    }
}
