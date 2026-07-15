package com.bjitgroup.config;

import com.bjitgroup.constants.BrowserType;
import com.bjitgroup.constants.FrameworkConstants;
import com.bjitgroup.exceptions.AutomationException;
import org.apache.commons.lang3.StringUtils;

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
        this.config = config;
        this.env    = env;
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
        return Boolean.parseBoolean(resolve("headless", "false"));
    }

    public int slowMoMs() {
        return Integer.parseInt(resolve("slowMo",
                String.valueOf(FrameworkConstants.DEFAULT_SLOW_MO_MS)));
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
        return env.getProperty("baseUrl", "");
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
        return Boolean.parseBoolean(resolve("ignoreHttpsErrors", "false"));
    }

    /**
     * Whether to record video for each test session.
     * Default: {@code false}.
     */
    public boolean recordVideo() {
        return Boolean.parseBoolean(resolve("recordVideo", "false"));
    }

    /**
     * Whether to record a Playwright trace for each test session.
     * Default: {@code true}.
     */
    public boolean recordTrace() {
        return Boolean.parseBoolean(resolve("recordTrace", "true"));
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
                                + "' must be greater than zero, got: " + value);
            }
            return value;
        } catch (NumberFormatException ex) {
            throw new AutomationException(
                    "Configuration property '" + propertyName
                            + "' is not a valid integer: '" + raw + "'", ex);
        }
    }
}
