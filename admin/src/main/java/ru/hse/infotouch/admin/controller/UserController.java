package ru.hse.infotouch.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.infotouch.domain.dto.request.UserCreateRequest;
import ru.hse.infotouch.domain.dto.request.UserUpdateRequest;
import ru.hse.infotouch.domain.models.admin.User;
import ru.hse.infotouch.domain.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getOne(@PathVariable("id") int id) {

        return ResponseEntity.ok(userService.getOneById(id));
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll(@RequestParam(value = "searchString", required = false) String searchString,
                                              @RequestParam(value = "page", required = false) Integer page) {

        return ResponseEntity.ok(userService.findAll(searchString, Objects.nonNull(page) ? page : 0));
    }

    // TODO: require AdminRole
    @PostMapping
    public ResponseEntity<User> createUser(@Valid UserCreateRequest userRequest) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.create(userRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @Valid UserUpdateRequest userRequest) {

        // TODO: require Admin role on change userRole
        return ResponseEntity.ok(userService.update(id, userRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") int id) {

        this.userService.delete(id);
    }
}
