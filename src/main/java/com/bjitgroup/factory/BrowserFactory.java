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
public class BrowserFactory {

    private final ConfigManager config;

    public BrowserFactory(ConfigManager config) {
        this.config = config;
    }

    /**
     * Launch and return a browser according to {@link ConfigManager} settings.
     *
     * @param playwright the Playwright instance for this thread
     * @return a launched {@link Browser}
     */
    public Browser launch(Playwright playwright) {
        BrowserType type  = config.browser();

        LaunchOptions options = new LaunchOptions()
                .setHeadless(config.headless())
                .setSlowMo(config.slowMoMs());

        return switch (type) {
            case CHROMIUM -> playwright.chromium().launch(options);
            case FIREFOX  -> playwright.firefox().launch(options);
            case EDGE     -> playwright.chromium().launch(new LaunchOptions()
                    .setHeadless(config.headless())
                    .setSlowMo(config.slowMoMs())
                    .setChannel("msedge"));
            case CHROME   -> playwright.chromium().launch(new LaunchOptions()
                    .setHeadless(config.headless())
                    .setSlowMo(config.slowMoMs())
                    .setChannel("chrome"));
        };
    }

    /**
     * Backward-compatible static entry point for legacy code paths.
     */
    public static Browser create(Playwright playwright) {
        return new BrowserFactory(ConfigManager.getInstance()).launch(playwright);
    }
}


