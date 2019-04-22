package ru.hse.infotouch.domain.models.admin;

import ru.hse.infotouch.domain.dto.request.NewsRequest;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "topic_id")
    private Integer topicId;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "created_by")
    private Integer createdBy;

    @Transient
    private List<Tag> tags;

    @Transient
    private Topic topic;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
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


    public static News createFromRequest(NewsRequest request) {
        News news = new News();

        news.setTopicId(request.getTopicId());

        news.setTitle(request.getTitle());
        news.setContent(request.getContent());
        news.setEndDate(request.getEnd());
        news.setEndTime(request.getEndTime());
        news.setImage(request.getImage());
        news.setImageUrl(request.getImageUrl());
        news.setEventDate(request.getEventDate());

        news.setStartTime(request.getEndTime() != null ? request.getEndTime() : LocalTime.of(0, 0));
        news.setEndTime(request.getEndTime() != null ? request.getEndTime() : LocalTime.of(23, 0));

        return news;
    }

    public void updateFromRequest(NewsRequest request) {
        this.setTopicId(request.getTopicId());

        this.setImage(request.getImage());
        this.setContent(request.getContent());

        this.setEndDate(request.getEnd());
        this.setStartDate(request.getStart());

        this.setEventDate(request.getEventDate());
        this.setImageUrl(request.getImageUrl());

        this.setStartTime(request.getEndTime() != null ? request.getEndTime() : LocalTime.of(0, 0));
        this.setEndTime(request.getEndTime() != null ? request.getEndTime() : LocalTime.of(23, 0));
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
