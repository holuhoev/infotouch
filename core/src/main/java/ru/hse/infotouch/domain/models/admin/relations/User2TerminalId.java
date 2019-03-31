package ru.hse.infotouch.domain.models.admin.relations;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class User2TerminalId implements Serializable {
    @Column(name = "userId")
    private int userId;

    @Column(name = "terminalId")
    private int terminalId;

    public User2TerminalId() {
    }

    public User2TerminalId(int userId, int terminalId) {
        this.userId = userId;
        this.terminalId = terminalId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(int terminalId) {
        this.terminalId = terminalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User2TerminalId that = (User2TerminalId) o;
        return userId == that.userId &&
                terminalId == that.terminalId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, terminalId);
    }
}
