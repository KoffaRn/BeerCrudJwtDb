package org.koffa.beercrudjwtdb;

import org.koffa.beercrudjwtdb.models.Role;
import org.koffa.beercrudjwtdb.models.User;
import org.koffa.beercrudjwtdb.repositories.RoleRepository;
import org.koffa.beercrudjwtdb.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;

@SpringBootApplication
public class BeerCrudJwtDbApplication {
    public static void main(String[] args) {
        SpringApplication.run(BeerCrudJwtDbApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if(roleRepository.findByAuthority("ADMIN").isPresent())
                return;
            Role adminRole = roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("USER"));
            userRepository.save(User.builder()
                    .id(1L)
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .roles(new HashSet<>(List.of(adminRole)))
                    .build());
        };
    }
}