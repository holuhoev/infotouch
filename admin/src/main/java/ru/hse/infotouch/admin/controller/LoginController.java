package ru.hse.infotouch.admin.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.security.core.userdetails.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@ApiIgnore
@RestController
public class LoginController {

    @RequestMapping("/login")
    public ResponseEntity<User> login(Authentication authentication) {
        return ResponseEntity.ok((User) authentication.getPrincipal());
    }

    @RequestMapping(value = "/keep-alive", method = RequestMethod.POST)
    public ResponseEntity keepAlive() {
        return ResponseEntity.ok(null);
    }

    @RequestMapping("/logout")
    public ResponseEntity login(HttpServletRequest request) throws ServletException {
        request.logout();
        return ResponseEntity.ok("logged out");
    }
}
