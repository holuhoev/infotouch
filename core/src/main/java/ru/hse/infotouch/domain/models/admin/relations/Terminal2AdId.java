package ru.hse.infotouch.domain.models.admin.relations;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Terminal2AdId implements Serializable {
    @Column(name = "terminalId")
    private int terminalId;

    @Column(name = "adId")
    private int adId;

    public Terminal2AdId() {
    }

    public Terminal2AdId(int terminalId, int adId) {
        this.terminalId = terminalId;
        this.adId = adId;
    }

    public int getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(int terminalId) {
        this.terminalId = terminalId;
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
        Terminal2AdId that = (Terminal2AdId) o;
        return terminalId == that.terminalId &&
                adId == that.adId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(terminalId, adId);
    }
}
