package ru.hse.infotouch.domain.models.admin.relations;

import ru.hse.infotouch.domain.models.enums.AccessRight;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user2device")
public class User2Device {
    @EmbeddedId
    private User2DeviceId id;

    @Column(name = "access_right")
    private AccessRight accessRight;

    public User2Device() {
    }

    public User2Device(User2DeviceId id, AccessRight accessRight) {
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
        User2Device that = (User2Device) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static User2Device createOf(int userId, int deviceId, AccessRight right) {
        return new User2Device(new User2DeviceId(userId, deviceId), right);
    }
}
