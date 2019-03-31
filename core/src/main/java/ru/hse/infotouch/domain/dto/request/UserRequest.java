package ru.hse.infotouch.domain.dto.request;

import ru.hse.infotouch.domain.models.enums.UserRole;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UserRequest {
    @NotNull(message = "firstName must be specified")
    private String firstName;
    private String lastName;

    @NotNull(message = "login must be specified")
    private String login;

    @NotNull(message = "role must be specified")
    private UserRole role;

    @NotNull(message = "terminals must be specified")
    private List<UserTerminalRequest> terminals;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<UserTerminalRequest> getTerminals() {
        return terminals;
    }

    public void setTerminals(List<UserTerminalRequest> terminals) {
        this.terminals = terminals;
    }
}
