package com.bjitgroup.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Immutable model representing a system user (for test data).
 * Uses Jackson-compatible record so it can be deserialised from JSON.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record UserData(
        @NotBlank(message = "is required")
        @Size(max = 40, message = "must not exceed 40 characters")
        String username,
        @NotBlank(message = "is required")
        String firstName,
        @NotBlank(message = "is required")
        String lastName,
        @NotBlank(message = "is required")
        @Email(message = "must be a well-formed email address")
        String email,
        @Pattern(regexp = "Enabled|Disabled", message = "must be either Enabled or Disabled")
        String status,
        @Pattern(regexp = "ESS|Admin", message = "must be either ESS or Admin")
        String role
) {
    /** Convenience builder-style copy with a different username. */
    public UserData withUsername(String newUsername) {
        return new UserData(newUsername, firstName, lastName, email, status, role);
    }
}


