package org.koffa.beercrudjwtdb.services;

import lombok.RequiredArgsConstructor;
import org.koffa.beercrudjwtdb.models.AuthRequest;
import org.koffa.beercrudjwtdb.models.AuthResponse;
import org.koffa.beercrudjwtdb.models.User;
import org.koffa.beercrudjwtdb.repositories.RoleRepository;
import org.koffa.beercrudjwtdb.repositories.UserRepository;
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
    public AuthResponse register(AuthRequest registrationRequest) {
        User user = userRepository.save(User.builder()
                .username(registrationRequest.username())
                .password(passwordEncoder.encode(registrationRequest.password()))
                .roles(new HashSet<>(List.of(roleRepository.findByAuthority("USER").orElseThrow(() -> new RuntimeException("User role not found")))))
                .build());
        return new AuthResponse(user, tokenService.generateJwt(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())));
    }

    public AuthResponse loginUser(String username, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        String token = tokenService.generateJwt(auth);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return new AuthResponse(user, token);
    }
}
