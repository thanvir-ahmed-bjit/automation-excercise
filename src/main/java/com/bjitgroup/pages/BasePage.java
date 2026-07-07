package com.bjitgroup.pages;

import com.bjitgroup.config.ConfigManager;
import com.bjitgroup.utils.WaitUtils;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Abstract base for all Page Objects.
 * <p>
 * Provides protected helpers for click, fill, text, visibility and other
 * common Playwright interactions with built-in explicit waits.
 * Subclasses should ONLY expose public business-level methods; internal
 * selector strings must remain private / package-private.
 * </p>
 */
public abstract class BasePage {

    protected final Page page;
    protected final int  timeoutMs;

    protected BasePage(Page page) {
        this.page      = page;
        this.timeoutMs = ConfigManager.getInstance().timeoutMs();
    }

    // Locator helpers

    protected Locator loc(String selector) {
        return page.locator(selector);
    }

    // Interaction helpers

    protected void click(String selector) {
        Locator l = loc(selector);
        WaitUtils.waitForVisible(l, timeoutMs);
        l.click();
    }

    protected void fill(String selector, String value) {
        Locator l = loc(selector);
        WaitUtils.waitForVisible(l, timeoutMs);
        l.fill(value);
    }

    protected void clear(String selector) {
        loc(selector).clear();
    }

    protected void selectOption(String selector, String value) {
        loc(selector).selectOption(value);
    }

    protected void check(String selector) {
        loc(selector).check();
    }

    protected void uncheck(String selector) {
        loc(selector).uncheck();
    }

    // Assertion helpers

    protected String textOf(String selector) {
        Locator l = loc(selector);
        WaitUtils.waitForVisible(l, timeoutMs);
        return l.textContent().trim();
    }

    protected String valueOf(String selector) {
        return loc(selector).inputValue();
    }

    protected boolean isVisible(String selector) {
        return loc(selector).isVisible();
    }

    protected boolean isEnabled(String selector) {
        return loc(selector).isEnabled();
    }

    protected int count(String selector) {
        return loc(selector).count();
    }

    // Navigation helpers

    protected void navigate(String relativeUrl) {
        page.navigate(relativeUrl);
        WaitUtils.waitForDomContentLoaded(page);
    }

    protected String currentUrl() {
        return page.url();
    }
}


