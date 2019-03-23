package ru.hse.infotouch.domain.models.admin;

import ru.hse.infotouch.domain.dto.request.AnnouncementRequest;

import javax.persistence.*;
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
    private String content;

    @Column
    private byte[] image;

    @Column
    private int priority;

    @Column
    private String link;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "hse_location_id")
    private Integer hseLocationId;

    @Transient
    private HseLocation hseLocation;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public Integer getHseLocationId() {
        return hseLocationId;
    }

    public void setHseLocationId(Integer hseLocationId) {
        this.hseLocationId = hseLocationId;
    }

    public HseLocation getHseLocation() {
        return hseLocation;
    }

    public void setHseLocation(HseLocation hseLocation) {
        this.hseLocation = hseLocation;
    }

    public static Announcement createFromRequest(AnnouncementRequest request) {
        Announcement announcement = new Announcement();

        announcement.setHseLocationId(request.getHseLocationId());
        announcement.setContent(request.getContent());
        announcement.setImage(request.getImage());
        announcement.setLink(request.getLink());
        announcement.setTitle(request.getTitle());
        announcement.setPriority(request.getPriority());

        return announcement;
    }

    public Announcement updateFromRequest(AnnouncementRequest request) {
        Announcement announcement = createFromRequest(request);

        announcement.setId(this.id);
        announcement.setCreatedBy(this.createdBy);

        return announcement;
    }
}
