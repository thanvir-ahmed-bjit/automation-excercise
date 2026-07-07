package com.bjitgroup.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

/**
 * Explicit-wait helpers built on Playwright's own async primitives.
 * Never uses {@code Thread.sleep()}.
 */
public final class WaitUtils {

    private WaitUtils() { /* utility */ }

    /**
     * Wait until a locator becomes visible.
     *
     * @param locator   the target locator
     * @param timeoutMs maximum time to wait in milliseconds
     */
    public static void waitForVisible(Locator locator, int timeoutMs) {
        locator.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(timeoutMs));
    }

    /**
     * Wait until a locator becomes hidden or detached.
     */
    public static void waitForHidden(Locator locator, int timeoutMs) {
        locator.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.HIDDEN)
                .setTimeout(timeoutMs));
    }

    /**
     * Wait until the page URL contains {@code partialUrl}.
     */
    public static void waitForUrl(Page page, String partialUrl, int timeoutMs) {
        page.waitForURL(url -> url.contains(partialUrl),
                new Page.WaitForURLOptions().setTimeout(timeoutMs));
    }

    /**
     * Wait for the page network to become idle (DOM + network).
     */
    public static void waitForNetworkIdle(Page page) {
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    /**
     * Wait for the DOM content to be loaded.
     */
    public static void waitForDomContentLoaded(Page page) {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }
}


