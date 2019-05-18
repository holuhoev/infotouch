package ru.hse.infotouch.domain.models.admin;

import ru.hse.infotouch.domain.dto.request.HseUnitRequest;

import javax.persistence.*;

@Entity
@Table(name = "hse_unit")
public class HseUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    /* Привязка к комнате на схеме корпуса. */
    @Column(name = "map_element_id")
    private Integer schemeElementId;

    /* Здание, в котором(или у которого) находится данное подразделение. */
    @Column(name = "building_id")
    private Integer buildingId;

    /* Этаж на котором расположена комната, к которой привязано подразделение. */
    @Transient
    private Integer floor;

    /* Схема к которой привязано подразделение. */
    @Transient
    private Integer buildingSchemeId;

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

    public Integer getSchemeElementId() {
        return schemeElementId;
    }

    public void setSchemeElementId(Integer schemeElementId) {
        this.schemeElementId = schemeElementId;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getBuildingSchemeId() {
        return buildingSchemeId;
    }

    public void setBuildingSchemeId(Integer buildingSchemeId) {
        this.buildingSchemeId = buildingSchemeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void updateFromRequest(HseUnitRequest request) {
        this.setDescription(request.getDescription());
        this.setTitle(request.getTitle());
        this.setBuildingId(request.getBuildingId());
    }
}
