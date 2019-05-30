package ru.hse.infotouch.domain.dto.request;

import javax.validation.constraints.NotNull;

public class AnnouncementRequest {

    @NotNull(message = "title must be specified")
    private String title;

    @NotNull(message = "deviceId must be specified")
    private int deviceId;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }
}
