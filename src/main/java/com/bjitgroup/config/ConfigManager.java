package com.bjitgroup.config;

import com.bjitgroup.constants.BrowserType;
import com.bjitgroup.constants.FrameworkConstants;
import com.bjitgroup.exceptions.AutomationException;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Properties;

/**
 * Singleton configuration manager.
 *
 * <p>Priority order (highest → lowest):</p>
 * <ol>
 *   <li>JVM system property ({@code -Dkey=value} on CLI)</li>
 *   <li>{@code config/config.properties}</li>
 *   <li>{@code environments/{environment}.properties}</li>
 * </ol>
 *
 * <p>Centralises all framework config so no other class reads raw properties.
 * Numeric properties that must be positive are validated at read time; an
 * invalid value throws {@link AutomationException} at framework startup.</p>
 */
public final class ConfigManager {

    private static final ConfigManager INSTANCE = loadDefault();

    private final Properties config;
    private final Properties env;

    public ConfigManager(Properties config, Properties env) {
        this.config = Objects.requireNonNull(config, "Config properties must not be null");
        this.env    = Objects.requireNonNull(env, "Environment properties must not be null");
    }

    public static ConfigManager loadDefault() {
        Properties config = PropertyLoader.load(FrameworkConstants.CONFIG_FILE);
        String environment = resolve(config, "environment", "qa");
        Properties env = PropertyLoader.load(
                FrameworkConstants.ENVIRONMENTS_DIR + "/" + environment + ".properties");
        return new ConfigManager(config, env);
    }

    public static ConfigManager getInstance() {
        return INSTANCE;
    }

    // -----------------------------------------------------------------------
    // Browser
    // -----------------------------------------------------------------------

    public BrowserType browser() {
        return BrowserType.from(resolve("browser", "chrome"));
    }

    public boolean headless() {
        return parseBooleanStrict(resolve("headless", "false"), "headless");
    }

    public int slowMoMs() {
        return parseNonNegativeInt(
                resolve("slowMo", String.valueOf(FrameworkConstants.DEFAULT_SLOW_MO_MS)),
                "slowMo");
    }

    // -----------------------------------------------------------------------
    // Timing
    // -----------------------------------------------------------------------

    /** Element-interaction timeout in milliseconds.  Must be &gt; 0. */
    public int timeoutMs() {
        return parsePositiveInt(
                resolve("timeout", String.valueOf(FrameworkConstants.DEFAULT_TIMEOUT_MS)),
                "timeout");
    }

    /** Page-navigation timeout in milliseconds.  Must be &gt; 0. */
    public int navigationTimeoutMs() {
        return parsePositiveInt(
                resolve("navigationTimeout",
                        String.valueOf(FrameworkConstants.DEFAULT_NAVIGATION_TIMEOUT_MS)),
                "navigationTimeout");
    }

    // -----------------------------------------------------------------------
    // Environment
    // -----------------------------------------------------------------------

    public String environment() {
        return resolve("environment", "qa");
    }

    public String baseUrl() {
        String fromSystem = System.getProperty("baseUrl");
        if (StringUtils.isNotBlank(fromSystem)) return fromSystem;

        String value = env.getProperty("baseUrl", "");
        if (StringUtils.isBlank(value)) {
            throw new AutomationException(
                    "Configuration property 'baseUrl' must not be blank, got: '" + value + "'");
        }
        return value;
    }

    public String username() {
        return env.getProperty("username", "");
    }

    public String password() {
        return env.getProperty("password", "");
    }

    // -----------------------------------------------------------------------
    // Browser-context options
    // -----------------------------------------------------------------------

    /**
     * Whether to ignore HTTPS certificate errors.
     * Default: {@code false} — production environments must never ignore cert errors.
     */
    public boolean ignoreHttpsErrors() {
        return parseBooleanStrict(resolve("ignoreHttpsErrors", "false"), "ignoreHttpsErrors");
    }

    /**
     * Whether to record video for each test session.
     * Default: {@code false}.
     */
    public boolean recordVideo() {
        return parseBooleanStrict(resolve("recordVideo", "false"), "recordVideo");
    }

    /**
     * Whether to record a Playwright trace for each test session.
     * Default: {@code true}.
     */
    public boolean recordTrace() {
        return parseBooleanStrict(resolve("recordTrace", "true"), "recordTrace");
    }

    // -----------------------------------------------------------------------
    // Generic getter
    // -----------------------------------------------------------------------

    public String get(String key) {
        return resolve(key, "");
    }

    // -----------------------------------------------------------------------
    // Internal helpers
    // -----------------------------------------------------------------------

    private String resolve(String key, String defaultValue) {
        return resolve(config, key, defaultValue);
    }

    private static String resolve(Properties props, String key, String defaultValue) {
        String sys = System.getProperty(key);
        if (StringUtils.isNotBlank(sys)) return sys;
        return props.getProperty(key, defaultValue);
    }

    /**
     * Parses {@code raw} as a positive integer.
     *
     * @throws AutomationException if the value is not a valid integer or is ≤ 0
     */
    private static int parsePositiveInt(String raw, String propertyName) {
        try {
            int value = Integer.parseInt(raw.trim());
            if (value <= 0) {
                throw new AutomationException(
                        "Configuration property '" + propertyName
                                + "' must be greater than zero, got: '" + raw + "'");
            }
            return value;
        } catch (NumberFormatException ex) {
            throw new AutomationException(
                    "Configuration property '" + propertyName
                            + "' is not a valid integer: '" + raw + "'", ex);
        }
    }

    /**
     * Parses {@code raw} as a non-negative integer.
     *
     * @throws AutomationException if the value is not a valid integer or is < 0
     */
    private static int parseNonNegativeInt(String raw, String propertyName) {
        try {
            int value = Integer.parseInt(raw.trim());
            if (value < 0) {
                throw new AutomationException(
                        "Configuration property '" + propertyName
                                + "' must be zero or greater, got: '" + raw + "'");
            }
            return value;
        } catch (NumberFormatException ex) {
            throw new AutomationException(
                    "Configuration property '" + propertyName
                            + "' is not a valid integer: '" + raw + "'", ex);
        }
    }

    /**
     * Parses a strict boolean property that only accepts literal "true" or "false".
     *
     * @throws AutomationException if value is anything else
     */
    private static boolean parseBooleanStrict(String raw, String propertyName) {
        String normalized = raw == null ? "" : raw.trim();
        if ("true".equalsIgnoreCase(normalized)) {
            return true;
        }
        if ("false".equalsIgnoreCase(normalized)) {
            return false;
        }
        throw new AutomationException(
                "Configuration property '" + propertyName
                        + "' must be 'true' or 'false', got: '" + raw + "'");
    }
}
