package ru.hse.infotouch.domain.models.admin.relations;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "terminal2news")
public class Terminal2News {
    @EmbeddedId
    private Terminal2NewsId id;

    public Terminal2News() {
    }

    public Terminal2News(Terminal2NewsId id) {
        this.id = id;
    }

    public Terminal2NewsId getId() {
        return id;
    }

    public void setId(Terminal2NewsId id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Terminal2News that = (Terminal2News) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
