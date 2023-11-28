package org.koffa.beercrudjwtdb.models;

public record RegistrationResponse(User user, String jwt) {
}
