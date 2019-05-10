package ru.hse.infotouch.domain.models.admin.relations;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "device2announcement")
public class Device2Announcement {
    @EmbeddedId
    private Device2AnnouncementId id;

    public Device2Announcement() {
    }

    public Device2Announcement(Device2AnnouncementId id) {
        this.id = id;
    }

    public Device2AnnouncementId getId() {
        return id;
    }

    public void setId(Device2AnnouncementId id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device2Announcement that = (Device2Announcement) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static Device2Announcement createOf(int deviceId, int relationId) {
        return new Device2Announcement(new Device2AnnouncementId(deviceId, relationId));
    }
}
