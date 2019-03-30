package ru.hse.infotouch.domain.models.admin.relations;

import ru.hse.infotouch.domain.models.admin.User;
import ru.hse.infotouch.domain.models.enums.AccessRight;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user2terminal")
public class User2Terminal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "terminal_id")
    private Integer terminalId;

    @Column(name = "access_right")
    private AccessRight accessRight;

    @Transient
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Integer terminalId) {
        this.terminalId = terminalId;
    }

    public AccessRight getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(AccessRight accessRight) {
        this.accessRight = accessRight;
    }

    public User2Terminal(Integer userId, Integer terminalId, AccessRight accessRight) {
        this.userId = userId;
        this.terminalId = terminalId;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
