package com.bjitgroup.utils;

/**
 * Shared input validation helpers.
 */
public final class ValidationUtils {

    private ValidationUtils() {
    }

    public static void validateSelector(String selector) {
        validateText(selector, "Selector");
    }

    public static void validateText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " must not be null or blank"
            );
        }
    }
}

