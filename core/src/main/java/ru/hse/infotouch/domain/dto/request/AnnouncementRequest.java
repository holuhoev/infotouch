package ru.hse.infotouch.domain.dto.request;

import java.time.LocalDate;

public class AnnouncementRequest {

    private String title;
    private String content;
    private int priority = 0;
    private String link;
    private Integer createdBy;
    private int[] terminalIds;
    private LocalDate startDate;
    private LocalDate endDate;

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

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }


    public int[] getTerminalIds() {
        return terminalIds;
    }

    public void setTerminalIds(int[] terminalIds) {
        this.terminalIds = terminalIds;
    }
}
