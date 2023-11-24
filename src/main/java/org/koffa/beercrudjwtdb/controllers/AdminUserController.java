package org.koffa.beercrudjwtdb.controllers;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.koffa.beercrudjwtdb.models.User;
import org.koffa.beercrudjwtdb.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/user")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.ok("User deleted");
        } catch (NoResultException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping()
    public ResponseEntity<Object> changeUser(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.changeUser(user));
        } catch (NoResultException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
