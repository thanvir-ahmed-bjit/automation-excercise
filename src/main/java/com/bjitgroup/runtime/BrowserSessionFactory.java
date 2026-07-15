package com.bjitgroup.runtime;

import com.bjitgroup.config.ConfigManager;
import com.bjitgroup.factory.BrowserFactory;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.RecordVideoSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates browser sessions using composition-friendly dependencies.
 */
public class BrowserSessionFactory {

    private static final Logger LOG = LoggerFactory.getLogger(BrowserSessionFactory.class);

    private final ConfigManager config;
    private final BrowserFactory browserFactory;
    private final ArtifactManager artifactManager;

    public BrowserSessionFactory(ConfigManager config, BrowserFactory browserFactory, ArtifactManager artifactManager) {
        this.config = config;
        this.browserFactory = browserFactory;
        this.artifactManager = artifactManager;
    }

    public BrowserSession create() {
        artifactManager.ensureDirectories();

        Playwright playwright = null;
        Browser browser = null;
        BrowserContext context = null;

        try {
            playwright = Playwright.create();
            browser = browserFactory.launch(playwright);
            context = browser.newContext(new Browser.NewContextOptions()
                    .setBaseURL(config.baseUrl())
                    .setAcceptDownloads(true)
                    .setIgnoreHTTPSErrors(true)
                    .setRecordVideoDir(com.bjitgroup.constants.FrameworkConstants.VIDEO_DIR)
                    .setRecordVideoSize(new RecordVideoSize(1280, 720)));

            context.onConsoleMessage(msg -> LOG.info("[BROWSER CONSOLE] [{}] {}", msg.type(), msg.text()));

            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));

            Page page = context.newPage();

            LOG.info("Driver initialised browser={} headless={} env={}"
                    , config.browser(), config.headless(), config.environment());

            return new BrowserSession(playwright, browser, context, page, artifactManager, LOG);
        } catch (RuntimeException ex) {
            safelyCloseContext(context);
            safelyCloseBrowser(browser);
            safelyClosePlaywright(playwright);
            throw ex;
        }
    }

    private void safelyCloseContext(BrowserContext context) {
        if (context == null) {
            return;
        }
        try {
            context.close();
        } catch (Exception closeEx) {
            LOG.warn("Failed to close browser context during session creation rollback", closeEx);
        }
    }

    private void safelyCloseBrowser(Browser browser) {
        if (browser == null) {
            return;
        }
        try {
            browser.close();
        } catch (Exception closeEx) {
            LOG.warn("Failed to close browser during session creation rollback", closeEx);
        }
    }

    private void safelyClosePlaywright(Playwright playwright) {
        if (playwright == null) {
            return;
        }
        try {
            playwright.close();
        } catch (Exception closeEx) {
            LOG.warn("Failed to close playwright during session creation rollback", closeEx);
        }
    }
}

