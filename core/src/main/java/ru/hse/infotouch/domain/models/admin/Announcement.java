package ru.hse.infotouch.domain.models.admin;

import ru.hse.infotouch.domain.dto.request.AnnouncementRequest;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "announcement")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String title;

    @Column
    private Integer createdBy;

    @Column
    private int deviceId;

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Announcement that = (Announcement) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public static Announcement createFromRequest(AnnouncementRequest request) {
        Announcement announcement = new Announcement();

        announcement.setTitle(request.getTitle());
        announcement.setDeviceId(request.getDeviceId());

        return announcement;
    }

    public Announcement updateFromRequest(AnnouncementRequest request) {
        Announcement announcement = new Announcement();

        announcement.setId(this.id);
        announcement.setDeviceId(this.deviceId);
        announcement.setCreatedBy(this.createdBy);

        announcement.setTitle(request.getTitle());

        return announcement;
    }
}
