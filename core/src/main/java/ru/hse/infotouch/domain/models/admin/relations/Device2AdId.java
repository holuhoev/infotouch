package ru.hse.infotouch.domain.models.admin.relations;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Device2AdId implements Serializable {
    @Column(name = "deviceId")
    private int deviceId;

    @Column(name = "adId")
    private int adId;

    public Device2AdId() {
    }

    public Device2AdId(int deviceId, int adId) {
        this.deviceId = deviceId;
        this.adId = adId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device2AdId that = (Device2AdId) o;
        return deviceId == that.deviceId &&
                adId == that.adId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId, adId);
    }
}
