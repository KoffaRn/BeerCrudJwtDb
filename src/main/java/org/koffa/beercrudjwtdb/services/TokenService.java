package org.koffa.beercrudjwtdb.services;

import lombok.RequiredArgsConstructor;
import org.koffa.beercrudjwtdb.models.Token;
import org.koffa.beercrudjwtdb.repositories.TokenRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtEncoder jwtEncoder;
    private final TokenRepository repository;
    public String generateJwt(Authentication authentication) {
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .subject(authentication.getName())
                .claim("roles", scope)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
    public Token save(Token token) {
        return repository.save(token);
    }
    public List<Token> findAllValidTokensByUserId(Long userId) {
        return repository.findAllValidTokensByUserId(userId);
    }

    public void saveAll(List<Token> tokens) {
        repository.saveAll(tokens);
    }
}
