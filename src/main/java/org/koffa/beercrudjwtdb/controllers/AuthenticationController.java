package org.koffa.beercrudjwtdb.controllers;

import lombok.RequiredArgsConstructor;
import org.koffa.beercrudjwtdb.models.LoginRequest;
import org.koffa.beercrudjwtdb.models.LoginResponse;
import org.koffa.beercrudjwtdb.models.User;
import org.koffa.beercrudjwtdb.models.RegistrationRequest;
import org.koffa.beercrudjwtdb.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegistrationRequest registrationRequest) {
        try {
            User registeredUser = authenticationService.register(registrationRequest);
            registeredUser.setPassword("");
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
            try {
                return ResponseEntity.ok(authenticationService.loginUser(loginRequest.username(), loginRequest.password()));
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
    }
}