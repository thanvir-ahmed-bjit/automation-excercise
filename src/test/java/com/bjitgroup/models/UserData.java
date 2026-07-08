package com.bjitgroup.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Immutable model representing a system user (for test data).
 * Uses Jackson-compatible record so it can be deserialised from JSON.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record UserData(
        String username,
        String firstName,
        String lastName,
        String email,
        String status,
        String role
) {
    /** Convenience builder-style copy with a different username. */
    public UserData withUsername(String newUsername) {
        return new UserData(newUsername, firstName, lastName, email, status, role);
    }
}


