package com.bjitgroup.utils;

import com.github.javafaker.Faker;

/**
 * Random test-data generator backed by {@link Faker}.
 */
public final class RandomDataUtils {

    private static final Faker FAKER = new Faker();

    private RandomDataUtils() { /* utility */ }

    public static String firstName()  { return FAKER.name().firstName(); }
    public static String lastName()   { return FAKER.name().lastName(); }
    public static String fullName()   { return FAKER.name().fullName(); }
    public static String phone()      { return FAKER.numerify("###-###-####"); }
    public static String address()    { return FAKER.address().streetAddress(); }
    public static String city()       { return FAKER.address().city(); }
    public static String country()    { return FAKER.address().country(); }
    public static String company()    { return FAKER.company().name(); }

    /** Unique email safe for test environments. */
    public static String email() {
        return "qa." + System.currentTimeMillis() + "@test.example.com";
    }

    /** Unique username safe for test environments. */
    public static String username() {
        return "usr_" + System.currentTimeMillis();
    }

    /** Password meeting common complexity rules. */
    public static String password() {
        return "Auto@" + FAKER.number().digits(6);
    }
}


