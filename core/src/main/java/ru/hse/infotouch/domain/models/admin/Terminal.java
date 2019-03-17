package ru.hse.infotouch.domain.models.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;
import ru.hse.infotouch.domain.dto.request.TerminalRequest;
import ru.hse.infotouch.util.json.PointToJsonSerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "terminal")
public class Terminal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column
    private String title;

    @Column
    private String description;

    @Column(columnDefinition = "geometry(Point,4326)")
    @JsonSerialize(using = PointToJsonSerializer.class)
    private Point location;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Terminal terminal = (Terminal) o;
        return Objects.equals(id, terminal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void updateFromRequest(TerminalRequest request) {
        this.setTitle(request.getTitle());
        this.setDescription(request.getDescription());
    }
}
