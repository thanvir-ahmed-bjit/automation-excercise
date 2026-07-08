package com.bjitgroup.pages;

import com.bjitgroup.utils.WaitUtils;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Reusable page interaction component shared by page objects.
 */
public class PageActions {

    private final Page page;
    private final int timeoutMs;

    public PageActions(Page page, int timeoutMs) {
        this.page = page;
        this.timeoutMs = timeoutMs;
    }

    public Locator loc(String selector) {
        return page.locator(selector);
    }

    public void click(String selector) {
        Locator locator = loc(selector);
        WaitUtils.waitForVisible(locator, timeoutMs);
        locator.click();
    }

    public void fill(String selector, String value) {
        Locator locator = loc(selector);
        WaitUtils.waitForVisible(locator, timeoutMs);
        locator.fill(value);
    }

    public boolean isVisible(String selector) {
        return loc(selector).isVisible();
    }

    public String textOf(String selector) {
        Locator locator = loc(selector);
        WaitUtils.waitForVisible(locator, timeoutMs);
        return locator.textContent().trim();
    }

    public int count(String selector) {
        return loc(selector).count();
    }

    public void navigate(String relativeUrl) {
        page.navigate(relativeUrl);
        WaitUtils.waitForDomContentLoaded(page);
    }

    public void waitForUrlContains(String partialUrl) {
        WaitUtils.waitForUrl(page, partialUrl, timeoutMs);
    }

    public void waitForVisible(String selector) {
        WaitUtils.waitForVisible(loc(selector), timeoutMs);
    }

    public void waitForNetworkIdle() {
        WaitUtils.waitForNetworkIdle(page);
    }
}


