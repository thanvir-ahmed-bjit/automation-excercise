package com.bjitgroup.config;

import com.bjitgroup.constants.BrowserType;
import com.bjitgroup.constants.FrameworkConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

/**
 * Singleton configuration manager.
 * <p>
 * Priority order (highest -> lowest):
 *  1. JVM system property  (-Dkey=value on CLI)
 *  2. config/config.properties
 *  3. environments/{environment}.properties
 * </p>
 *
 * Centralises all framework config so nothing else reads raw properties.
 */
public final class ConfigManager {

    private static final ConfigManager INSTANCE = loadDefault();

    private final Properties config;
    private final Properties env;

    public ConfigManager(Properties config, Properties env) {
        this.config = config;
        this.env = env;
    }

    public static ConfigManager loadDefault() {
        Properties config = PropertyLoader.load(FrameworkConstants.CONFIG_FILE);
        String environment = resolve(config, "environment", "qa");
        Properties env = PropertyLoader.load(FrameworkConstants.ENVIRONMENTS_DIR + "/" + environment + ".properties");
        return new ConfigManager(config, env);
    }

    public static ConfigManager getInstance() {
        return INSTANCE;
    }

    // Browser

    public BrowserType browser() {
        return BrowserType.from(resolve("browser", "chrome"));
    }

    public boolean headless() {
        return Boolean.parseBoolean(resolve("headless", "false"));
    }

    public int slowMoMs() {
        return Integer.parseInt(resolve("slowMo", String.valueOf(FrameworkConstants.DEFAULT_SLOW_MO_MS)));
    }

    // Timing

    public int timeoutMs() {
        return Integer.parseInt(resolve("timeout", String.valueOf(FrameworkConstants.DEFAULT_TIMEOUT_MS)));
    }

    // Environment

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

    // Generic getter

    public String get(String key) {
        return resolve(key, "");
    }

    // Internal

    private String resolve(String key, String defaultValue) {
        return resolve(config, key, defaultValue);
    }

    private static String resolve(Properties props, String key, String defaultValue) {
        String sys = System.getProperty(key);
        if (StringUtils.isNotBlank(sys)) return sys;
        return props.getProperty(key, defaultValue);
    }
}


