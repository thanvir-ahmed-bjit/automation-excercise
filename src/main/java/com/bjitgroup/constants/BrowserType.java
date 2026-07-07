package com.bjitgroup.constants;

/**
 * Supported browser types for cross-browser execution.
 */
public enum BrowserType {
    CHROMIUM,
    CHROME,
    FIREFOX,
    EDGE;

    public static BrowserType from(String name) {
        if (name == null || name.isBlank()) return CHROME;
        return valueOf(name.trim().toUpperCase());
    }
}


