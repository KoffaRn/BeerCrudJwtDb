package org.koffa.beercrudjwtdb.services;

import lombok.RequiredArgsConstructor;
import org.koffa.beercrudjwtdb.models.*;
import org.koffa.beercrudjwtdb.repositories.RoleRepository;
import org.koffa.beercrudjwtdb.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        User user = User.builder()
                .username(registrationRequest.username())
                .password(passwordEncoder.encode(registrationRequest.password()))
                .roles(new HashSet<>(List.of(roleRepository.findByAuthority("USER").orElseThrow(() -> new RuntimeException("User role not found")))))
                .build();
        System.out.println(user.getPassword());
        Token token = saveUserToken(user);
        user.setTokens(new ArrayList<>(List.of(token)));
        return userRepository.save(user);
    }

    private Token saveUserToken(User user) {
        Token token = tokenService.save(
                Token.builder()
                .user(user)
                .token(tokenService.generateJwt(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())))
                .type(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build());
        return token;
    }

    public LoginResponse loginUser(String username, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        revokeAllUserTokens(user);
        Token token = saveUserToken(user);
        user.setTokens(new ArrayList<>(List.of(token)));
        return new LoginResponse(user);
    }
    private void revokeAllUserTokens(User user) {
        List<Token> tokens = tokenService.findAllValidTokensByUserId(user.getId());
        if(tokens == null || tokens.isEmpty()) {
            return;
        }
        tokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenService.saveAll(tokens);
    }
}
