package ru.hse.infotouch.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.infotouch.domain.dto.request.UserRequest;
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
    public ResponseEntity<User> createUser(@Valid UserRequest userRequest) {

        return ResponseEntity.ok(userService.create(userRequest));
    }

    // TODO: require AdminRole
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @Valid UserRequest userRequest) {

        return ResponseEntity.ok(userService.update(id, userRequest));
    }
}
