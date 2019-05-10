package ru.hse.infotouch.domain.dto.request;

import ru.hse.infotouch.domain.models.enums.UserRole;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UserUpdateRequest {
    @NotNull(message = "firstName must be specified")
    private String firstName;
    private String lastName;

    @NotNull(message = "role must be specified")
    private UserRole role;

    @NotNull(message = "devices must be specified")
    private List<UserDeviceRequest> devices;

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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<UserDeviceRequest> getDevices() {
        return devices;
    }

    public void setDevices(List<UserDeviceRequest> devices) {
        this.devices = devices;
    }
}
