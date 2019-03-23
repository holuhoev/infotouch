package ru.hse.infotouch.domain.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public class AdRequest {
    
    private String title;

    private String content;

    private byte[] image;

    private byte[] video;
    
    private LocalDate startDate;

    private LocalDate endDate;

    private Integer createdBy;

    private LocalTime startTime;

    private LocalTime endTime;

    private int[] terminalIds;

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

    public byte[] getVideo() {
        return video;
    }

    public void setVideo(byte[] video) {
        this.video = video;
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

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
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

    public int[] getTerminalIds() {
        return terminalIds;
    }

    public void setTerminalIds(int[] terminalIds) {
        this.terminalIds = terminalIds;
    }
}
