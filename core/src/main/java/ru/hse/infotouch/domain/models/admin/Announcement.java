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
    private String content;

    @Column
    private int priority;

    @Column
    private String link;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

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


    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public static Announcement createFromRequest(AnnouncementRequest request) {
        Announcement announcement = new Announcement();

        announcement.setContent(request.getContent());
        announcement.setLink(request.getLink());
        announcement.setTitle(request.getTitle());
        announcement.setPriority(request.getPriority());
        announcement.setStartDate(request.getStartDate());
        announcement.setEndDate(request.getEndDate());

        return announcement;
    }

    public Announcement updateFromRequest(AnnouncementRequest request) {
        Announcement announcement = createFromRequest(request);

        announcement.setId(this.id);
        announcement.setCreatedBy(this.createdBy);

        return announcement;
    }
}
