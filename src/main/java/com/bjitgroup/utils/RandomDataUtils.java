package com.bjitgroup.utils;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Random test-data generator backed by {@link Faker}.
 */
public final class RandomDataUtils {

    private static final Logger LOG = LoggerFactory.getLogger(RandomDataUtils.class);
    private static final String TEST_DATA_SEED_PROPERTY = "testDataSeed";
    private static final Long CONFIGURED_SEED = readConfiguredSeed();
    private static final ThreadLocal<Faker> FAKER = ThreadLocal.withInitial(RandomDataUtils::createFaker);
    private static final AtomicLong UNIQUE = new AtomicLong(initialUniqueValue());

    static {
        if (CONFIGURED_SEED != null) {
            LOG.info("Test-data generation seed: {}", CONFIGURED_SEED);
        }
    }

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

    private static Faker createFaker() {
        if (CONFIGURED_SEED != null) {
            return new Faker(new Random(CONFIGURED_SEED));
        }
        return new Faker();
    }

    private static long initialUniqueValue() {
        return CONFIGURED_SEED != null ? CONFIGURED_SEED : System.currentTimeMillis();
    }

    private static Long readConfiguredSeed() {
        String rawSeed = System.getProperty(TEST_DATA_SEED_PROPERTY);
        if (rawSeed == null || rawSeed.isBlank()) {
            return null;
        }
        try {
            return Long.parseLong(rawSeed.trim());
        } catch (NumberFormatException ex) {
            LOG.warn("Ignoring invalid '{}' value '{}'; falling back to non-deterministic mode",
                    TEST_DATA_SEED_PROPERTY, rawSeed);
            return null;
        }
    }

    private static long uniqueSuffix() {
        return UNIQUE.incrementAndGet();
    }
}


