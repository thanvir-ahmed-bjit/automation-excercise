package com.bjitgroup.context;

import java.util.Objects;

/**
 * Stores one runtime-created account for cross-test reuse in the same suite run.
 */
public final class TestAccountStore {

    private static String name;
    private static String email;
    private static String password;

    private TestAccountStore() {
    }

    public static synchronized void save(String savedName, String savedEmail, String savedPassword) {
        name = Objects.requireNonNull(savedName, "name must not be null");
        email = Objects.requireNonNull(savedEmail, "email must not be null");
        password = Objects.requireNonNull(savedPassword, "password must not be null");
    }

    public static synchronized String name() {
        ensureReady();
        return name;
    }

    public static synchronized String email() {
        ensureReady();
        return email;
    }

    public static synchronized String password() {
        ensureReady();
        return password;
    }

    private static void ensureReady() {
        if (name == null || email == null || password == null) {
            throw new IllegalStateException("No created user exists yet. Run signup test first.");
        }
    }
}
