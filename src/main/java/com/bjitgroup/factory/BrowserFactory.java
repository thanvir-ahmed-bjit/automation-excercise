package com.bjitgroup.factory;

import com.bjitgroup.config.ConfigManager;
import com.bjitgroup.constants.BrowserType;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright;

/**
 * Creates a {@link Browser} instance based on the active configuration.
 * Supports Chrome, Edge, Firefox, and Chromium in both headed / headless modes.
 */
public final class BrowserFactory {

    private BrowserFactory() { /* utility */ }

    /**
     * Launch and return a browser according to {@link ConfigManager} settings.
     *
     * @param playwright the Playwright instance for this thread
     * @return a launched {@link Browser}
     */
    public static Browser create(Playwright playwright) {
        ConfigManager cfg = ConfigManager.getInstance();
        BrowserType type  = cfg.browser();

        LaunchOptions options = new LaunchOptions()
                .setHeadless(cfg.headless())
                .setSlowMo(cfg.slowMoMs());

        return switch (type) {
            case CHROMIUM -> playwright.chromium().launch(options);
            case FIREFOX  -> playwright.firefox().launch(options);
            case EDGE     -> playwright.chromium().launch(new LaunchOptions()
                    .setHeadless(cfg.headless())
                    .setSlowMo(cfg.slowMoMs())
                    .setChannel("msedge"));
            case CHROME   -> playwright.chromium().launch(new LaunchOptions()
                    .setHeadless(cfg.headless())
                    .setSlowMo(cfg.slowMoMs())
                    .setChannel("chrome"));
        };
    }
}


