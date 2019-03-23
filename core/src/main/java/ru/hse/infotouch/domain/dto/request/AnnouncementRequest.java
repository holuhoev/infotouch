package ru.hse.infotouch.domain.dto.request;

public class AnnouncementRequest {

    private String title;

    private String content;

    private byte[] image;

    private int priority = 0;

    private String link;

    private Integer createdBy;

    private Integer hseLocationId;

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

    public Integer getHseLocationId() {
        return hseLocationId;
    }

    public void setHseLocationId(Integer hseLocationId) {
        this.hseLocationId = hseLocationId;
    }

    public int[] getTerminalIds() {
        return terminalIds;
    }

    public void setTerminalIds(int[] terminalIds) {
        this.terminalIds = terminalIds;
    }
}
