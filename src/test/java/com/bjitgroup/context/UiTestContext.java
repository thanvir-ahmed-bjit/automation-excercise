package com.bjitgroup.context;

import com.bjitgroup.config.ConfigManager;
import com.bjitgroup.runtime.ArtifactManager;
import com.bjitgroup.runtime.BrowserSession;
import com.microsoft.playwright.Page;

import java.nio.file.Path;
import java.util.Objects;

/**
 * Per-test execution context containing browser session and framework services.
 * <p>
 * Wraps a {@link BrowserSession} and provides convenient access to config, page,
 * and page objects. Delegates cleanup to the session.
 * </p>
 */
public class UiTestContext {

    private final ConfigManager config;
    private final BrowserSession session;
    private final ArtifactManager artifactManager;
    private final PageManager pages;

    public UiTestContext(ConfigManager config, BrowserSession session, ArtifactManager artifactManager) {
        this.config = Objects.requireNonNull(config, "ConfigManager must not be null");
        this.session = Objects.requireNonNull(session, "BrowserSession must not be null");
        this.artifactManager = Objects.requireNonNull(artifactManager, "ArtifactManager must not be null");
        this.pages = new PageManager(session.page(), config.timeoutMs());
    }

    /**
     * Get the configuration manager.
     * @return the ConfigManager
     */
    public ConfigManager config() {
        return config;
    }

    /**
     * Get the current page.
     * @return the Playwright Page
     */
    public Page page() {
        return session.page();
    }

    /**
     * Get the page object manager for creating typed page objects.
     * @return the PageManager
     */
    public PageManager pages() {
        return pages;
    }

    /**
     * Capture a full-page screenshot.
     * @param screenshotName the screenshot name (without extension)
     * @return the path to the saved screenshot
     */
    public Path captureScreenshot(String screenshotName) {
        Path screenshotPath = artifactManager.screenshotPath(screenshotName);
        page().screenshot(new Page.ScreenshotOptions().setPath(screenshotPath).setFullPage(true));
        return screenshotPath;
    }

    /**
     * Close the browser session.
     * @param testName the test name (used for artifact file naming)
     */
    public void close(String testName) {
        session.close(testName);
    }
}

