package com.bjitgroup.runtime;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import org.slf4j.Logger;

/**
 * Encapsulates one Playwright browser stack for a single test.
 */
public class BrowserSession {

    private final Playwright playwright;
    private final Browser browser;
    private final BrowserContext context;
    private final Page page;
    private final ArtifactManager artifactManager;
    private final Logger logger;

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

    public void close(String testName) {
        try {
            if (context != null) {
                context.tracing().stop(new Tracing.StopOptions().setPath(artifactManager.tracePath(testName)));
                context.close();
            }
            if (browser != null) {
                browser.close();
            }
            if (playwright != null) {
                playwright.close();
            }
        } catch (Exception ex) {
            logger.warn("Error while closing browser session for test '{}': {}", testName, ex.getMessage());
        }
    }
}

