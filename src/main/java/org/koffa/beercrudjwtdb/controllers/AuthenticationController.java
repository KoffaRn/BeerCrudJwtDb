package org.koffa.beercrudjwtdb.controllers;

import lombok.RequiredArgsConstructor;
import org.koffa.beercrudjwtdb.models.*;
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
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest registrationRequest) {
        try {
            return ResponseEntity.ok(authenticationService.register(registrationRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest loginRequest) {
            try {
                return ResponseEntity.ok(authenticationService.loginUser(loginRequest.username(), loginRequest.password()));
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
    }
}