package ru.hse.infotouch.domain.dto.request;

public class EventUrlRequest {
    private int deviceId;
    private String url;

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
