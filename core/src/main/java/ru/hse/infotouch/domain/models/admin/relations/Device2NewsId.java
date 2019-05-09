package ru.hse.infotouch.domain.models.admin.relations;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Device2NewsId implements Serializable {
    @Column(name = "terminalId")
    private int terminalId;

    @Column(name = "newsId")
    private int newsId;

    public Device2NewsId() {
    }

    public Device2NewsId(int terminalId, int newsId) {
        this.terminalId = terminalId;
        this.newsId = newsId;
    }

    public int getDeviceId() {
        return terminalId;
    }

    public void setDeviceId(int terminalId) {
        this.terminalId = terminalId;
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
        return terminalId == that.terminalId &&
                newsId == that.newsId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(terminalId, newsId);
    }
}
