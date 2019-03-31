package ru.hse.infotouch.domain.models.admin.relations;

import ru.hse.infotouch.domain.models.enums.AccessRight;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user2terminal")
public class User2Terminal {
    @EmbeddedId
    private User2TerminalId id;

    @Column(name = "access_right")
    private AccessRight accessRight;

    public User2Terminal() {
    }

    public User2Terminal(User2TerminalId id, AccessRight accessRight) {
        this.id = id;
        this.accessRight = accessRight;
    }

    public AccessRight getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(AccessRight accessRight) {
        this.accessRight = accessRight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User2Terminal that = (User2Terminal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static User2Terminal createOf(int userId, int terminalId, AccessRight right) {
        return new User2Terminal(new User2TerminalId(userId, terminalId), right);
    }
}
