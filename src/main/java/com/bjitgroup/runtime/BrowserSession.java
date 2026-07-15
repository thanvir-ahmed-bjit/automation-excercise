package com.bjitgroup.runtime;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Encapsulates one Playwright browser stack for a single test.
 * <p>
 * Manages lifecycle of Playwright, Browser, BrowserContext, and Page.
 * Cleanup operations are independent and idempotent to ensure all resources close
 * even if individual operations fail.
 * </p>
 */
public class BrowserSession {

    private final Playwright playwright;
    private final Browser browser;
    private final BrowserContext context;
    private final Page page;
    private final ArtifactManager artifactManager;
    private final Logger logger;
    private final AtomicBoolean closed = new AtomicBoolean(false);

    public BrowserSession(
            Playwright playwright,
            Browser browser,
            BrowserContext context,
            Page page,
            ArtifactManager artifactManager,
            Logger logger
    ) {
        this.playwright = playwright;
        this.browser = browser;
        this.context = context;
        this.page = page;
        this.artifactManager = artifactManager;
        this.logger = logger;
    }

    public Page page() {
        return page;
    }

    public BrowserContext context() {
        return context;
    }

    public Browser browser() {
        return browser;
    }

    /**
     * Closes the browser session.
     * Stops tracing, saves artifacts, and releases all resources.
     * Each cleanup operation runs independently to ensure completion even if errors occur.
     *
     * @param testName the test name (used for artifact file naming)
     */
    public void close(String testName) {
        if (!closed.compareAndSet(false, true)) {
            return;
        }
        stopTracingSafely(testName);
        closeContextSafely();
        closeBrowserSafely();
        closePlaywrightSafely();
    }

    private void stopTracingSafely(String testName) {
        try {
            if (context != null) {
                context.tracing().stop(new Tracing.StopOptions().setPath(artifactManager.tracePath(testName)));
            }
        } catch (Exception ex) {
            logger.warn("Error stopping tracing for test '{}'", testName, ex);
        }
    }

    private void closeContextSafely() {
        try {
            if (context != null) {
                context.close();
            }
        } catch (Exception ex) {
            logger.warn("Error closing browser context", ex);
        }
    }

    private void closeBrowserSafely() {
        try {
            if (browser != null) {
                browser.close();
            }
        } catch (Exception ex) {
            logger.warn("Error closing browser", ex);
        }
    }

    private void closePlaywrightSafely() {
        try {
            if (playwright != null) {
                playwright.close();
            }
        } catch (Exception ex) {
            logger.warn("Error closing Playwright", ex);
        }
    }
}

