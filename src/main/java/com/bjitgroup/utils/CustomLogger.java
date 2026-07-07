package com.bjitgroup.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thin wrapper around SLF4J so every class gets a consistently-configured logger.
 */
public final class CustomLogger {

    private CustomLogger() { /* utility */ }

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}


