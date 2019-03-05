package ru.hse.infotouch.domain;


import org.apache.commons.lang3.StringUtils;
import ru.hse.infotouch.domain.enums.CityType;
import ru.hse.infotouch.ruz.util.JsonField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Департамент
 *
 * @author Evgeny Kholukhoev
 */
@Entity
@Table(name = "CHAIR")
public class Chair extends RuzObject {
    @Id
    @JsonField(name = "chairOid")
    @Column(name = "ID")
    private Integer Id;

    @JsonField(name = "facultyOid")
    @Column(name = "faculty_id")
    private Integer facultyId;

    @JsonField
    @Column(name = "chair_name")
    private String name;

    @JsonField
    @Column(name = "code")
    private String code;

    @Column(name = "chair_city")
    private CityType city;

    public Chair() {
    }

    public Integer getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        Chair chair = (Chair) o;
        return Objects.equals(Id, chair.Id) &&
                Objects.equals(facultyId, chair.facultyId) &&
                Objects.equals(name, chair.name) &&
                Objects.equals(city, chair.city) &&
                Objects.equals(code, chair.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, facultyId, name, code, city);
    }

    public static String extractChairName(final Chair chair) {
        if (StringUtils.isEmpty(chair.getName()))
            return chair.getName();

        String chairCityShort = chair.getCity().getShortString();

        return chair.getName().substring(chairCityShort.length());
    }

    public static CityType extractChairCity(final Chair chair) {
        return CityType.ofChairName(chair.getName());
    }
}
