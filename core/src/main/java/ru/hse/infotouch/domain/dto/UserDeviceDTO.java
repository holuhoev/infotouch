package ru.hse.infotouch.domain.dto;

import ru.hse.infotouch.domain.models.admin.Device;
import ru.hse.infotouch.domain.models.enums.AccessRight;

import java.util.Objects;

public class UserDeviceDTO {
    private int deviceId;
    private String title;
    private String description;
    private AccessRight accessRight;

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AccessRight getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(AccessRight accessRight) {
        this.accessRight = accessRight;
    }

    public UserDeviceDTO() {
    }

    private UserDeviceDTO(int deviceId, String title, String description, AccessRight accessRight) {
        this.deviceId = deviceId;
        this.title = title;
        this.description = description;
        this.accessRight = accessRight;
    }

    public static UserDeviceDTO createOf(Device device, AccessRight accessRight) {
        Objects.requireNonNull(accessRight);
        Objects.requireNonNull(device.getId());
        return new UserDeviceDTO(device.getId(), device.getTitle(), device.getDescription(), accessRight);
    }
}
