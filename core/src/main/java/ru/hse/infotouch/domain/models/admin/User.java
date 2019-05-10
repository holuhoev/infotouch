package ru.hse.infotouch.domain.models.admin;

import ru.hse.infotouch.domain.dto.UserDeviceDTO;
import ru.hse.infotouch.domain.dto.request.UserCreateRequest;
import ru.hse.infotouch.domain.dto.request.UserUpdateRequest;
import ru.hse.infotouch.domain.models.enums.UserRole;

import javax.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "hse_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "login")
    private String login;

    @Column(name = "role")
    private UserRole userRole;

    @Transient
    private List<UserDeviceDTO> devices;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public List<UserDeviceDTO> getDevices() {
        return devices;
    }

    public void setDevices(List<UserDeviceDTO> devices) {
        this.devices = devices;
    }

    public static User createFromRequest(UserCreateRequest request) {
        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setLogin(request.getLogin());
        user.setUserRole(request.getRole());

        return user;
    }


    public User updateFromRequest(UserUpdateRequest request) {
        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setUserRole(request.getRole());
        user.setLastName(request.getLastName());

        user.setLogin(this.getLogin());
        user.setId(this.getId());

        return user;
    }
}
