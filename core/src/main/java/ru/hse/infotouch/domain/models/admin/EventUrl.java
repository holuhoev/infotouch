package ru.hse.infotouch.domain.models.admin;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "event_url")
public class EventUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name = "device_id")
    private int deviceId;

    @Column(name = "url")
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventUrl eventUrl = (EventUrl) o;
        return id.equals(eventUrl.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
