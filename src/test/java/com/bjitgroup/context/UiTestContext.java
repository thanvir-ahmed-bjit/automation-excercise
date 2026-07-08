package com.bjitgroup.context;

import com.bjitgroup.config.ConfigManager;
import com.bjitgroup.runtime.ArtifactManager;
import com.bjitgroup.runtime.BrowserSession;
import com.microsoft.playwright.Page;

import java.nio.file.Path;

/**
 * Per-test execution context containing browser session and framework services.
 */
public class UiTestContext {

    private final ConfigManager config;
    private final BrowserSession session;
    private final ArtifactManager artifactManager;
    private final PageManager pages;

    public UiTestContext(ConfigManager config, BrowserSession session, ArtifactManager artifactManager) {
        this.config = config;
        this.session = session;
        this.artifactManager = artifactManager;
        this.pages = new PageManager(session.page(), config.timeoutMs());
    }

    public ConfigManager config() {
        return config;
    }

    public Page page() {
        return session.page();
    }

    public PageManager pages() {
        return pages;
    }

    public Path captureScreenshot(String screenshotName) {
        Path screenshotPath = artifactManager.screenshotPath(screenshotName);
        page().screenshot(new Page.ScreenshotOptions().setPath(screenshotPath).setFullPage(true));
        return screenshotPath;
    }

    public void close(String testName) {
        session.close(testName);
    }
}

