package ru.hse.infotouch.domain.models;


import ru.hse.infotouch.domain.models.enums.CityType;
import ru.hse.infotouch.ruz.util.JsonField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "BUILDING")
public class Building extends RuzObject {
    @Id
    @JsonField(name = "buildingOid")
    @Column(name = "ID")
    private Integer Id;

    @JsonField()
    @Column(name = "address")
    private String address;

    @JsonField()
    @Column(name = "building_name")
    private String name;

    @Column(name = "city")
    private CityType city;

    public Building() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CityType getCity() {
        return city;
    }

    public void setCity(CityType city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Building building = (Building) o;
        return Objects.equals(Id, building.Id) &&
                Objects.equals(address, building.address) &&
                Objects.equals(name, building.name) &&
                city == building.city;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, address, name, city);
    }
}
