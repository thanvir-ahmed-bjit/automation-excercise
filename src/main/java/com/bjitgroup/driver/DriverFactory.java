package com.bjitgroup.driver;

import com.bjitgroup.config.ConfigManager;
import com.bjitgroup.constants.FrameworkConstants;
import com.bjitgroup.factory.BrowserFactory;
import com.bjitgroup.utils.CustomLogger;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.RecordVideoSize;
import org.slf4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Thread-safe driver factory.
 * <p>
 * One Playwright + Browser + BrowserContext + Page per thread.
 * Call {@link #init()} in {@code @BeforeMethod}, {@link #quit(String)} in {@code @AfterMethod}.
 * </p>
 */
public final class DriverFactory {

    private static final Logger LOG = CustomLogger.getLogger(DriverFactory.class);

    private static final ThreadLocal<Playwright> PLAYWRIGHT = new ThreadLocal<>();
    private static final ThreadLocal<Browser> BROWSER = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> CONTEXT = new ThreadLocal<>();
    private static final ThreadLocal<Page> PAGE = new ThreadLocal<>();

    private DriverFactory() { /* utility */ }

    // Lifecycle

    /** Initialises a full Playwright -> Browser -> Context -> Page stack for this thread. */
    public static void init() {
        ensureDirectories();

        Playwright playwright = Playwright.create();
        Browser browser = BrowserFactory.create(playwright);
        BrowserContext context = newContext(browser);

        // Console-log capture
        context.onConsoleMessage(msg ->
                LOG.info("[BROWSER CONSOLE] [{}] {}", msg.type(), msg.text()));

        // Tracing start (screenshots + snapshots + sources)
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        Page page = context.newPage();

        PLAYWRIGHT.set(playwright);
        BROWSER.set(browser);
        CONTEXT.set(context);
        PAGE.set(page);

        LOG.info("Driver initialised  browser={} headless={} env={}", ConfigManager.getInstance().browser(),
                ConfigManager.getInstance().headless(), ConfigManager.getInstance().environment());
    }

    /** Stops tracing, saves artefacts, and releases all resources for this thread. */
    public static void quit(String testName) {
        try {
            BrowserContext ctx = CONTEXT.get();
            if (ctx != null) {
                Path traceFile = FrameworkConstants.TRACE_DIR.resolve(testName + ".zip");
                ctx.tracing().stop(new Tracing.StopOptions().setPath(traceFile));
                ctx.close();
            }
            Browser br = BROWSER.get();
            if (br != null) br.close();

            Playwright pw = PLAYWRIGHT.get();
            if (pw != null) pw.close();

        } finally {
            PAGE.remove();
            CONTEXT.remove();
            BROWSER.remove();
            PLAYWRIGHT.remove();
        }
        LOG.info("Driver released  test={}", testName);
    }

    // Accessors

    public static Page getPage() { return PAGE.get(); }
    public static BrowserContext getContext() { return CONTEXT.get(); }
    public static Browser getBrowser() { return BROWSER.get(); }

    // Additional contexts / tabs

    /** Creates an independent {@link BrowserContext} (e.g. for multi-user scenarios). */
    public static BrowserContext newBrowserContext() {
        return newContext(getBrowser());
    }

    /** Opens a new tab inside an existing context. */
    public static Page newTab(BrowserContext context) {
        return context.newPage();
    }

    // Internal

    private static BrowserContext newContext(Browser browser) {
        ConfigManager cfg = ConfigManager.getInstance();
        ensureDirectories();
        return browser.newContext(new Browser.NewContextOptions()
                .setBaseURL(cfg.baseUrl())
                .setAcceptDownloads(true)
                .setIgnoreHTTPSErrors(true)
                .setRecordVideoDir(FrameworkConstants.VIDEO_DIR)
                .setRecordVideoSize(new RecordVideoSize(1280, 720)));
    }

    private static void ensureDirectories() {
        for (Path dir : new Path[]{
                FrameworkConstants.SCREENSHOT_DIR,
                FrameworkConstants.TRACE_DIR,
                FrameworkConstants.VIDEO_DIR,
                FrameworkConstants.DOWNLOAD_DIR,
                FrameworkConstants.LOG_DIR}) {
            try { Files.createDirectories(dir); } catch (Exception ignored) { /* best-effort */ }
        }
    }
}
