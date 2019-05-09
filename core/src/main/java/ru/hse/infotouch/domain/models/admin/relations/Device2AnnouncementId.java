package ru.hse.infotouch.domain.models.admin.relations;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Device2AnnouncementId implements Serializable {
    @Column(name = "terminalId")
    private int terminalId;

    @Column(name = "announcementId")
    private int announcementId;

    public Device2AnnouncementId() {
    }

    public Device2AnnouncementId(int terminalId, int announcementId) {
        this.terminalId = terminalId;
        this.announcementId = announcementId;
    }

    public int getDeviceId() {
        return terminalId;
    }

    public void setDeviceId(int terminalId) {
        this.terminalId = terminalId;
    }

    public int getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(int announcementId) {
        this.announcementId = announcementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device2AnnouncementId that = (Device2AnnouncementId) o;
        return terminalId == that.terminalId &&
                announcementId == that.announcementId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(terminalId, announcementId);
    }
}
