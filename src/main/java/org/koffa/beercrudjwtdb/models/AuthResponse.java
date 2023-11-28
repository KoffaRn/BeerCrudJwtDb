package org.koffa.beercrudjwtdb.models;

public record AuthResponse(User user, String jwt) {
}
