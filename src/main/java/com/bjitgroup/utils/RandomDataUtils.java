package com.bjitgroup.utils;

import com.github.javafaker.Faker;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Random test-data generator backed by {@link Faker}.
 */
public final class RandomDataUtils {

    private static final ThreadLocal<Faker> FAKER = ThreadLocal.withInitial(Faker::new);
    private static final AtomicLong UNIQUE = new AtomicLong(System.currentTimeMillis());

    private RandomDataUtils() { /* utility */ }

    public static String firstName()  { return faker().name().firstName(); }
    public static String lastName()   { return faker().name().lastName(); }
    public static String fullName()   { return faker().name().fullName(); }
    public static String phone()      { return faker().numerify("###-###-####"); }
    public static String address()    { return faker().address().streetAddress(); }
    public static String city()       { return faker().address().city(); }
    public static String country()    { return faker().address().country(); }
    public static String company()    { return faker().company().name(); }

    /** Unique email safe for test environments. */
    public static String email() {
        return "qa." + uniqueSuffix() + "@test.example.com";
    }

    /** Unique username safe for test environments. */
    public static String username() {
        return "usr_" + uniqueSuffix();
    }

    /** Password meeting common complexity rules. */
    public static String password() {
        return "Auto@" + faker().number().digits(6);
    }

    private static Faker faker() {
        return FAKER.get();
    }

    private static long uniqueSuffix() {
        return UNIQUE.incrementAndGet();
    }
}


