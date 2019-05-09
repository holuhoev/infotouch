package ru.hse.infotouch.domain.models.admin.relations;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Device2AnnouncementId implements Serializable {
    @Column(name = "deviceId")
    private int deviceId;

    @Column(name = "announcementId")
    private int announcementId;

    public Device2AnnouncementId() {
    }

    public Device2AnnouncementId(int deviceId, int announcementId) {
        this.deviceId = deviceId;
        this.announcementId = announcementId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
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
        return deviceId == that.deviceId &&
                announcementId == that.announcementId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId, announcementId);
    }
}
