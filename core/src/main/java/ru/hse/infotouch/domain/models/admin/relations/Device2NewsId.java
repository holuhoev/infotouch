package ru.hse.infotouch.domain.models.admin.relations;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Device2NewsId implements Serializable {
    @Column(name = "deviceId")
    private int deviceId;

    @Column(name = "newsId")
    private int newsId;

    public Device2NewsId() {
    }

    public Device2NewsId(int deviceId, int newsId) {
        this.deviceId = deviceId;
        this.newsId = newsId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device2NewsId that = (Device2NewsId) o;
        return deviceId == that.deviceId &&
                newsId == that.newsId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId, newsId);
    }
}
