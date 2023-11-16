package org.koffa.beercrudjwtdb.services;

import lombok.RequiredArgsConstructor;
import org.koffa.beercrudjwtdb.models.LoginResponse;
import org.koffa.beercrudjwtdb.models.User;
import org.koffa.beercrudjwtdb.repositories.RoleRepository;
import org.koffa.beercrudjwtdb.repositories.UserRepository;
import org.koffa.beercrudjwtdb.models.RegistrationRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    public User register(RegistrationRequest registrationRequest) {
        return userRepository.save(User.builder()
                .username(registrationRequest.username())
                .password(passwordEncoder.encode(registrationRequest.password()))
                .roles(new HashSet<>(List.of(roleRepository.findByAuthority("USER").orElseThrow(() -> new RuntimeException("User role not found")))))
                .build());
    }

    public LoginResponse loginUser(String username, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        String token = tokenService.generateJwt(auth);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword("");
        return new LoginResponse(user, token);
    }
}
