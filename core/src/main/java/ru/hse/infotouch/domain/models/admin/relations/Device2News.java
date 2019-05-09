package ru.hse.infotouch.domain.models.admin.relations;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "device2news")
public class Device2News {
    @EmbeddedId
    private Device2NewsId id;

    public Device2News() {
    }

    public Device2News(Device2NewsId id) {
        this.id = id;
    }

    public Device2NewsId getId() {
        return id;
    }

    public void setId(Device2NewsId id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device2News that = (Device2News) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static Device2News createOf(int deviceId, int relationId) {
        return new Device2News(new Device2NewsId(deviceId, relationId));
    }
}
