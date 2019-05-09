package ru.hse.infotouch.domain.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public class NewsRequest {

    private String title;

    private String content;

    private byte[] image;

    private Integer topicId;

    private LocalDate start;

    private LocalDate end;

    private LocalTime startTime;

    private LocalTime endTime;

    private int[] tagIds;

    private int[] deviceIds;

    private String imageUrl;

    private LocalDate eventDate;

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

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
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

    public int[] getTagIds() {
        return tagIds;
    }

    public void setTagIds(int[] tagIds) {
        this.tagIds = tagIds;
    }

    public int[] getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(int[] deviceIds) {
        this.deviceIds = deviceIds;
    }
}
