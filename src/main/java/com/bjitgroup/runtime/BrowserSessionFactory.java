package com.bjitgroup.runtime;

import com.bjitgroup.config.ConfigManager;
import com.bjitgroup.constants.FrameworkConstants;
import com.bjitgroup.factory.BrowserFactory;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.RecordVideoSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Creates a fully-initialised {@link BrowserSession} per test invocation.
 *
 * <p>All browser-context options (video, tracing, HTTPS errors, timeouts)
 * are driven by {@link ConfigManager} so they can be changed without touching
 * production code.</p>
 *
 * <p>If initialization fails at any step, all partially created Playwright
 * resources are closed before re-throwing the original exception.</p>
 */
public class BrowserSessionFactory {

    private static final Logger LOG = LoggerFactory.getLogger(BrowserSessionFactory.class);

    private final ConfigManager config;
    private final BrowserFactory browserFactory;
    private final ArtifactManager artifactManager;

    public BrowserSessionFactory(
            ConfigManager config,
            BrowserFactory browserFactory,
            ArtifactManager artifactManager
    ) {
        this.config          = Objects.requireNonNull(config,          "ConfigManager must not be null");
        this.browserFactory  = Objects.requireNonNull(browserFactory,  "BrowserFactory must not be null");
        this.artifactManager = Objects.requireNonNull(artifactManager, "ArtifactManager must not be null");
    }

    /**
     * Creates and returns a new {@link BrowserSession}.
     *
     * <p>On any failure, all partially created resources are released and the
     * original exception is re-thrown unchanged.</p>
     */
    public BrowserSession create() {
        artifactManager.ensureDirectories();

        Playwright playwright = null;
        Browser browser = null;
        BrowserContext context = null;

        try {
            playwright = Playwright.create();
            browser    = browserFactory.launch(playwright);

            // Build context options from configuration
            Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                    .setBaseURL(config.baseUrl())
                    .setAcceptDownloads(true)
                    .setIgnoreHTTPSErrors(config.ignoreHttpsErrors());

            if (config.recordVideo()) {
                contextOptions
                        .setRecordVideoDir(FrameworkConstants.VIDEO_DIR)
                        .setRecordVideoSize(new RecordVideoSize(1280, 720));
            }

            context = browser.newContext(contextOptions);

            // Apply framework-wide timeouts so individual action calls never
            // need to re-specify them.
            context.setDefaultTimeout(config.timeoutMs());
            context.setDefaultNavigationTimeout(config.navigationTimeoutMs());

            context.onConsoleMessage(msg ->
                    LOG.info("[BROWSER CONSOLE] [{}] {}", msg.type(), msg.text()));

            // Start tracing only when configured — BrowserSession records this
            // flag so it can skip stopTracing() when tracing was never started.
            boolean traceEnabled = config.recordTrace();
            if (traceEnabled) {
                context.tracing().start(new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSnapshots(true)
                        .setSources(true));
            }

            Page page = context.newPage();

            LOG.info("Session created  browser={}  headless={}  env={}  trace={}  video={}",
                    config.browser(), config.headless(), config.environment(),
                    traceEnabled, config.recordVideo());

            return new BrowserSession(playwright, browser, context, page,
                    artifactManager, LOG, traceEnabled);

        } catch (RuntimeException ex) {
            // Roll back in reverse creation order
            safelyCloseContext(context);
            safelyCloseBrowser(browser);
            safelyClosePlaywright(playwright);
            throw ex;
        }
    }

    // -----------------------------------------------------------------------
    // Rollback helpers
    // -----------------------------------------------------------------------

    private void safelyCloseContext(BrowserContext context) {
        if (context == null) return;
        try { context.close(); }
        catch (Exception ex) {
            LOG.warn("Failed to close browser context during session creation rollback", ex);
        }
    }

    private void safelyCloseBrowser(Browser browser) {
        if (browser == null) return;
        try { browser.close(); }
        catch (Exception ex) {
            LOG.warn("Failed to close browser during session creation rollback", ex);
        }
    }

    private void safelyClosePlaywright(Playwright playwright) {
        if (playwright == null) return;
        try { playwright.close(); }
        catch (Exception ex) {
            LOG.warn("Failed to close Playwright during session creation rollback", ex);
        }
    }
}
