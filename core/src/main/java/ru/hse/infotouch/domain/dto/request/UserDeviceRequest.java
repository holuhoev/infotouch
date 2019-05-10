package ru.hse.infotouch.domain.dto.request;

import ru.hse.infotouch.domain.models.enums.AccessRight;

public class UserDeviceRequest {
    private int deviceId;
    private AccessRight accessRight;

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public AccessRight getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(AccessRight accessRight) {
        this.accessRight = accessRight;
    }
}
