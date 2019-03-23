package ru.hse.infotouch.domain.models.admin.relations;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "terminal2ad")
public class Terminal2Ad {
    @EmbeddedId
    private Terminal2AdId id;

    public Terminal2Ad() {
    }

    public Terminal2Ad(Terminal2AdId id) {
        this.id = id;
    }

    public Terminal2AdId getId() {
        return id;
    }

    public void setId(Terminal2AdId id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Terminal2Ad that = (Terminal2Ad) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static Terminal2Ad createOf(int terminalId, int relationId) {
        return new Terminal2Ad(new Terminal2AdId(terminalId, relationId));
    }
}
