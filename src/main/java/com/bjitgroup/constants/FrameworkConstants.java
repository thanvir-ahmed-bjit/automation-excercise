package com.bjitgroup.constants;

import java.nio.file.Path;

/**
 * Central constants for paths, timeouts and resource locations.
 */
public final class FrameworkConstants {

    // Classpath resource paths
    public static final String CONFIG_FILE      = "config/config.properties";
    public static final String ENVIRONMENTS_DIR = "environments";
    public static final String LOCATORS_DIR     = "locators";
    public static final String TEST_DATA_DIR    = "testdata";

    // Default timing
    public static final int DEFAULT_TIMEOUT_MS = 20_000;
    public static final int DEFAULT_SLOW_MO_MS = 0;

    // Output artifact directories
    public static final Path ARTIFACTS_DIR  = Path.of("target", "artifacts");
    public static final Path SCREENSHOT_DIR = ARTIFACTS_DIR.resolve("screenshots");
    public static final Path TRACE_DIR      = ARTIFACTS_DIR.resolve("traces");
    public static final Path VIDEO_DIR      = ARTIFACTS_DIR.resolve("videos");
    public static final Path DOWNLOAD_DIR   = ARTIFACTS_DIR.resolve("downloads");
    public static final Path LOG_DIR        = ARTIFACTS_DIR.resolve("logs");

    private FrameworkConstants() { /* utility */ }
}


