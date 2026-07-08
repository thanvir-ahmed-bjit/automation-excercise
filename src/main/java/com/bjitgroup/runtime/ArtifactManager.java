package com.bjitgroup.runtime;

import com.bjitgroup.constants.FrameworkConstants;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Manages framework artifact paths and directories.
 */
public class ArtifactManager {

    public void ensureDirectories() {
        for (Path dir : new Path[]{
                FrameworkConstants.SCREENSHOT_DIR,
                FrameworkConstants.TRACE_DIR,
                FrameworkConstants.VIDEO_DIR,
                FrameworkConstants.DOWNLOAD_DIR,
                FrameworkConstants.LOG_DIR}) {
            try {
                Files.createDirectories(dir);
            } catch (Exception ignored) {
                // Best effort - tests should continue even if artifact setup partially fails.
            }
        }
    }

    public Path screenshotPath(String fileNameWithoutExtension) {
        return FrameworkConstants.SCREENSHOT_DIR.resolve(safeName(fileNameWithoutExtension) + ".png");
    }

    public Path tracePath(String fileNameWithoutExtension) {
        return FrameworkConstants.TRACE_DIR.resolve(safeName(fileNameWithoutExtension) + ".zip");
    }

    private String safeName(String value) {
        return value.replaceAll("[^A-Za-z0-9._-]", "_");
    }
}

