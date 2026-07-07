package com.bjitgroup.utils;

import com.bjitgroup.constants.FrameworkConstants;
import com.microsoft.playwright.Page;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Captures full-page PNG screenshots and stores them under
 * {@link FrameworkConstants#SCREENSHOT_DIR}.
 */
public final class ScreenshotUtils {

    private ScreenshotUtils() { /* utility */ }

    /**
     * Take a full-page screenshot.
     *
     * @param page the Playwright page
     * @param name file name (without extension)
     * @return path to the saved screenshot file
     */
    public static Path take(Page page, String name) {
        ensureDir();
        Path path = FrameworkConstants.SCREENSHOT_DIR.resolve(name + ".png");
        page.screenshot(new Page.ScreenshotOptions().setPath(path).setFullPage(true));
        return path;
    }

    private static void ensureDir() {
        try {
            Files.createDirectories(FrameworkConstants.SCREENSHOT_DIR);
        } catch (IOException ignored) { /* best-effort */ }
    }
}


