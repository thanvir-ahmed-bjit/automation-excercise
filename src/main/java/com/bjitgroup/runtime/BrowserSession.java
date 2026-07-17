package com.bjitgroup.runtime;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Encapsulates one Playwright browser stack for a single test invocation.
 *
 * <p>Manages the lifecycle of Playwright, Browser, BrowserContext, and Page.
 * Each cleanup step runs independently so that a failure in one step does not
 * prevent subsequent steps from executing.</p>
 *
 * <p>This class is idempotent: calling {@link #close} more than once is safe.</p>
 */
public class BrowserSession {

    private final Playwright playwright;
    private final Browser browser;
    private final BrowserContext context;
    private final Page page;
    private final ArtifactManager artifactManager;
    private final Logger logger;
    /** Whether tracing was started during session creation. */
    private final boolean tracingEnabled;
    /** Whether video recording was enabled during session creation. */
    private final boolean videoEnabled;
    private final AtomicBoolean closed = new AtomicBoolean(false);

    public BrowserSession(
            Playwright playwright,
            Browser browser,
            BrowserContext context,
            Page page,
            ArtifactManager artifactManager,
            Logger logger,
            boolean tracingEnabled,
            boolean videoEnabled
    ) {
        this.playwright      = Objects.requireNonNull(playwright,      "Playwright must not be null");
        this.browser         = Objects.requireNonNull(browser,         "Browser must not be null");
        this.context         = Objects.requireNonNull(context,         "BrowserContext must not be null");
        this.page            = Objects.requireNonNull(page,            "Page must not be null");
        this.artifactManager = Objects.requireNonNull(artifactManager, "ArtifactManager must not be null");
        this.logger          = Objects.requireNonNull(logger,          "Logger must not be null");
        this.tracingEnabled  = tracingEnabled;
        this.videoEnabled    = videoEnabled;
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
     *
     * <p>Stops tracing (only if it was started), then closes the BrowserContext,
     * Browser, and Playwright instance in order. Each step runs independently.
     * This method is idempotent — additional calls are no-ops.</p>
     *
     * @param executionName unique name used for trace and artifact file naming;
     *                      must not be {@code null}
     */
    public void close(String executionName) {
        if (!closed.compareAndSet(false, true)) {
            return;
        }
        stopTracingSafely(executionName);
        saveVideoSafely(executionName);
        closeBrowserSafely();
        closePlaywrightSafely();
    }

    // -----------------------------------------------------------------------
    // Independent cleanup steps
    // -----------------------------------------------------------------------

    private void stopTracingSafely(String executionName) {
        if (!tracingEnabled) {
            logger.info("Trace disabled");
            return;
        }
        try {
            var tracePath = artifactManager.tracePath(executionName);
            context.tracing().stop(
                    new Tracing.StopOptions().setPath(tracePath)
            );
            logger.info("Trace saved: {}", tracePath.toAbsolutePath());
        } catch (Exception ex) {
            logger.warn("Error stopping tracing for '{}'", executionName, ex);
        }
    }

    private void saveVideoSafely(String executionName) {
        com.microsoft.playwright.Video video = null;

        if (videoEnabled) {
            try {
                // Capture video reference before closing context.
                video = page.video();
            } catch (Exception ex) {
                logger.warn("Error saving video for '{}'", executionName, ex);
            }
        }

        try {
            // Context must always close, regardless of video retrieval outcome.
            context.close();
        } catch (Exception ex) {
            logger.warn("Error closing browser context", ex);
        }

        if (videoEnabled && video != null) {
            try {
                var videoPath = artifactManager.videoPath(executionName);
                video.saveAs(videoPath);
                video.delete();
                logger.info("Video saved: {}", videoPath.toAbsolutePath());
            } catch (Exception ex) {
                logger.warn("Error saving video for '{}'", executionName, ex);
            }
        }
    }

    private void closeBrowserSafely() {
        try {
            browser.close();
        } catch (Exception ex) {
            logger.warn("Error closing browser", ex);
        }
    }

    private void closePlaywrightSafely() {
        try {
            playwright.close();
        } catch (Exception ex) {
            logger.warn("Error closing Playwright", ex);
        }
    }
}
