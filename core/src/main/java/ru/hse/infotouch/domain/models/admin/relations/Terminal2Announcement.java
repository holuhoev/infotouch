package ru.hse.infotouch.domain.models.admin.relations;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "terminal2announcement")
public class Terminal2Announcement {
    @EmbeddedId
    private Terminal2AnnouncementId id;

    public Terminal2Announcement() {
    }

    public Terminal2Announcement(Terminal2AnnouncementId id) {
        this.id = id;
    }

    public Terminal2AnnouncementId getId() {
        return id;
    }

    public void setId(Terminal2AnnouncementId id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Terminal2Announcement that = (Terminal2Announcement) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static Terminal2Announcement createOf(int terminalId, int relationId) {
        return new Terminal2Announcement(new Terminal2AnnouncementId(terminalId, relationId));
    }
}
