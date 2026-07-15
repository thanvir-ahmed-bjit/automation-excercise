package com.bjitgroup.runtime;

import com.bjitgroup.constants.FrameworkConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Manages framework artifact paths and directories.
 */
public class ArtifactManager {

    private static final Logger LOG = LoggerFactory.getLogger(ArtifactManager.class);

    public void ensureDirectories() {
        for (Path dir : new Path[]{
                FrameworkConstants.SCREENSHOT_DIR,
                FrameworkConstants.TRACE_DIR,
                FrameworkConstants.VIDEO_DIR,
                FrameworkConstants.DOWNLOAD_DIR,
                FrameworkConstants.LOG_DIR}) {
            try {
                Files.createDirectories(dir);
            } catch (Exception ex) {
                LOG.warn("Failed to create artifact directory: {}", dir.toAbsolutePath(), ex);
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

