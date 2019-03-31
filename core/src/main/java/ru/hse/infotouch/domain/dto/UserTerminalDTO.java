package ru.hse.infotouch.domain.dto;

import ru.hse.infotouch.domain.models.admin.Terminal;
import ru.hse.infotouch.domain.models.enums.AccessRight;

import java.util.Objects;

public class UserTerminalDTO {
    private int terminalId;
    private String title;
    private String description;
    private AccessRight accessRight;

    public int getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(int terminalId) {
        this.terminalId = terminalId;
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

    public UserTerminalDTO() {
    }

    private UserTerminalDTO(int terminalId, String title, String description, AccessRight accessRight) {
        this.terminalId = terminalId;
        this.title = title;
        this.description = description;
        this.accessRight = accessRight;
    }

    public static UserTerminalDTO createOf(Terminal terminal, AccessRight accessRight) {
        Objects.requireNonNull(accessRight);
        Objects.requireNonNull(terminal.getId());
        return new UserTerminalDTO(terminal.getId(), terminal.getTitle(), terminal.getDescription(), accessRight);
    }
}
