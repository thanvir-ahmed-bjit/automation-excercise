package com.bjitgroup.utils;

import com.bjitgroup.config.PropertyLoader;

import java.util.Properties;

/**
 * Classpath property-file reader - thin wrapper around {@link PropertyLoader}.
 */
public final class PropertyReader {

    private PropertyReader() { /* utility */ }

    public static Properties read(String resourcePath) {
        return PropertyLoader.load(resourcePath);
    }

    public static String get(String resourcePath, String key) {
        return PropertyLoader.load(resourcePath).getProperty(key, "");
    }
}


