package ru.hse.infotouch.domain.dto.request;

import ru.hse.infotouch.domain.models.enums.AccessRight;

public class UserTerminalRequest {
    private int terminalId;
    private AccessRight accessRight;

    public int getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(int terminalId) {
        this.terminalId = terminalId;
    }

    public AccessRight getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(AccessRight accessRight) {
        this.accessRight = accessRight;
    }
}
