package ru.hse.infotouch.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.infotouch.domain.dto.request.UserTerminalRequest;
import ru.hse.infotouch.domain.models.admin.relations.User2Terminal;
import ru.hse.infotouch.domain.service.UserService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("user/terminal/{id}")
    public ResponseEntity<User2Terminal> createUserTerminal(@PathVariable("id") int id, UserTerminalRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.addTerminal(id, request));
    }

    @GetMapping
    public ResponseEntity<List<User2Terminal>> findAllUserWithTerminals(@RequestParam(name = "searchString", required = false) String searchString,
                                                                        @RequestParam(name = "page", required = false) Integer page) {

        return ResponseEntity.ok(
                userService.findAllWithTerminals(searchString, Objects.nonNull(page) ? page : 0)
        );
    }


}
