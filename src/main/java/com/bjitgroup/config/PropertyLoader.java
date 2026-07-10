package com.bjitgroup.config;

import com.bjitgroup.exceptions.AutomationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads Java {@link Properties} from classpath resources.
 * Stateless utility - call {@link #load(String)} wherever needed.
 */
public final class PropertyLoader {

    private PropertyLoader() { /* utility */ }

    /**
     * Load a properties file from the test-class-path.
     *
     * @param resourcePath path relative to classpath root (e.g. "config/config.properties")
     * @return loaded {@link Properties}
     * @throws AutomationException if the resource is missing or unreadable
     */
    public static Properties load(String resourcePath) {
        try (InputStream in = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(resourcePath)) {

            if (in == null) {
                throw new AutomationException("Classpath resource not found: " + resourcePath);
            }
            Properties props = new Properties();
            props.load(in);
            return props;

        } catch (IOException e) {
            throw new AutomationException("Failed to load resource: " + resourcePath, e);
        }
    }
}


