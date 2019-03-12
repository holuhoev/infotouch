package ru.hse.infotouch.domain.models.admin;

import ru.hse.infotouch.domain.dto.request.NewsRequest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "news")
public class News {
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

    @Column(name = "topic_id")
    private Integer topicId;

    @Column
    private LocalDate start;

    @Column
    private LocalDate end;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "created_by")
    private Integer createdBy;

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

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals(id, news.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public static News createFromRequest(NewsRequest request) {
        News news = new News();

        news.setTitle(request.getTitle());
        news.setContent(request.getContent());
        news.setEnd(request.getEnd());
        news.setEndTime(request.getEndTime());
        news.setImage(request.getImage());
        news.setTopicId(request.getTopicId());
        news.setStart(request.getStart());
        news.setStartTime(request.getStartTime());

        return news;
    }

    public void updateFromRequest(NewsRequest request) {
        this.setImage(request.getImage());
        this.setTopicId(request.getTopicId());
        this.setContent(request.getContent());

        this.setEnd(request.getEnd());
        this.setEndTime(request.getEndTime());
        this.setStart(request.getStart());
        this.setStartTime(request.getStartTime());
    }
}
