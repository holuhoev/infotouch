package ru.hse.infotouch.domain.models.admin.relations;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "device2ad")
public class Device2Ad {
    @EmbeddedId
    private Device2AdId id;

    public Device2Ad() {
    }

    public Device2Ad(Device2AdId id) {
        this.id = id;
    }

    public Device2AdId getId() {
        return id;
    }

    public void setId(Device2AdId id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device2Ad that = (Device2Ad) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static Device2Ad createOf(int terminalId, int relationId) {
        return new Device2Ad(new Device2AdId(terminalId, relationId));
    }
}
