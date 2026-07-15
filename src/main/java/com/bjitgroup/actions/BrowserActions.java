package com.bjitgroup.actions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.Objects;
import java.util.regex.Pattern;

import static com.bjitgroup.utils.ValidationUtils.validateSelector;
import static com.bjitgroup.utils.ValidationUtils.validateText;

/**
 * Provides reusable page-level operations.
 *
 * <p>This class is responsible for:</p>
 * <ul>
 *     <li>Page navigation</li>
 *     <li>URL and load-state waiting</li>
 *     <li>Element-state waiting ({@code waitForVisible}, {@code waitForHidden}, …)</li>
 *     <li>Element-state <em>queries</em> ({@code isVisible}, {@code isHidden}, …)</li>
 *     <li>Reading element content and state</li>
 * </ul>
 *
 * <p><strong>isVisible vs waitForVisible:</strong><br>
 * {@link #isVisible(String)} and {@link #isHidden(String)} are <em>immediate</em>
 * snapshot queries — they return the current state without waiting.
 * Use {@link #waitForVisible(String)} / {@link #waitForHidden(String)} when explicit
 * synchronization is needed.</p>
 *
 * <p>User interactions (click, fill, keyboard, upload, drag-and-drop) belong to
 * {@link InputActions}.</p>
 */
public final class BrowserActions {

    private final Page page;
    private final double timeoutMs;

    public BrowserActions(Page page, double timeoutMs) {
        this.page = Objects.requireNonNull(page, "Page must not be null");

        if (timeoutMs <= 0) {
            throw new IllegalArgumentException("Timeout must be greater than zero");
        }

        this.timeoutMs = timeoutMs;
    }

    // -----------------------------------------------------------------------
    // Locator
    // -----------------------------------------------------------------------

    /**
     * Creates a Playwright locator after validating the selector.
     */
    public Locator locator(String selector) {
        validateSelector(selector);
        return page.locator(selector);
    }

    // -----------------------------------------------------------------------
    // Navigation
    // -----------------------------------------------------------------------

    /**
     * Navigates to an absolute URL or a relative path when baseURL is configured.
     */
    public void navigate(String urlOrPath) {
        validateText(urlOrPath, "URL or path");
        page.navigate(urlOrPath, new Page.NavigateOptions().setTimeout(timeoutMs));
    }

    /** Reloads the current page. */
    public void reload() {
        page.reload(new Page.ReloadOptions().setTimeout(timeoutMs));
    }

    /** Navigates to the previous history entry. */
    public void goBack() {
        page.goBack(new Page.GoBackOptions().setTimeout(timeoutMs));
    }

    /** Navigates to the next history entry. */
    public void goForward() {
        page.goForward(new Page.GoForwardOptions().setTimeout(timeoutMs));
    }

    /** Returns the current page URL. */
    public String currentUrl() {
        return page.url();
    }

    /** Returns the current page title. */
    public String title() {
        return page.title();
    }

    // -----------------------------------------------------------------------
    // Page waiting
    // -----------------------------------------------------------------------

    /**
     * Waits until the current URL contains the specified text.
     */
    public void waitForUrlContains(String expectedUrlPart) {
        validateText(expectedUrlPart, "Expected URL part");
        Pattern urlPattern = Pattern.compile(".*" + Pattern.quote(expectedUrlPart) + ".*");
        page.waitForURL(urlPattern, new Page.WaitForURLOptions().setTimeout(timeoutMs));
    }

    /** Waits until DOMContentLoaded is reached. */
    public void waitForDomContentLoaded() {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED,
                new Page.WaitForLoadStateOptions().setTimeout(timeoutMs));
    }

    /** Waits until the page reaches the LOAD state. */
    public void waitForPageLoad() {
        page.waitForLoadState(LoadState.LOAD,
                new Page.WaitForLoadStateOptions().setTimeout(timeoutMs));
    }

    /**
     * Waits for the network to become idle.
     *
     * <p>Use sparingly — many modern applications continuously perform
     * background network requests, which makes this wait unreliable.</p>
     */
    public void waitForNetworkIdle() {
        page.waitForLoadState(LoadState.NETWORKIDLE,
                new Page.WaitForLoadStateOptions().setTimeout(timeoutMs));
    }

    // -----------------------------------------------------------------------
    // Element-state waiting (explicit synchronization)
    // -----------------------------------------------------------------------

    /** Waits until the element matching {@code selector} becomes visible. */
    public void waitForVisible(String selector) {
        waitForState(selector, WaitForSelectorState.VISIBLE);
    }

    /** Waits until the element matching {@code selector} becomes hidden or detached. */
    public void waitForHidden(String selector) {
        waitForState(selector, WaitForSelectorState.HIDDEN);
    }

    /** Waits until the element matching {@code selector} is attached to the DOM. */
    public void waitForAttached(String selector) {
        waitForState(selector, WaitForSelectorState.ATTACHED);
    }

    /** Waits until the element matching {@code selector} is detached from the DOM. */
    public void waitForDetached(String selector) {
        waitForState(selector, WaitForSelectorState.DETACHED);
    }

    private void waitForState(String selector, WaitForSelectorState state) {
        locator(selector).waitFor(
                new Locator.WaitForOptions()
                        .setState(state)
                        .setTimeout(timeoutMs)
        );
    }

    // -----------------------------------------------------------------------
    // Element-state queries (immediate snapshots — no waiting)
    // -----------------------------------------------------------------------

    /**
     * Returns whether the element is currently visible.
     *
     * <p>This is an <em>immediate</em> snapshot — it does not wait for the element
     * to become visible.  Use {@link #waitForVisible(String)} for explicit
     * synchronization before a conditional check.</p>
     */
    public boolean isVisible(String selector) {
        return locator(selector).isVisible();
    }

    /**
     * Returns whether the element is currently hidden or absent from the DOM.
     *
     * <p>This is an <em>immediate</em> snapshot — it does not wait.
     * Use {@link #waitForHidden(String)} for explicit synchronization.</p>
     */
    public boolean isHidden(String selector) {
        return locator(selector).isHidden();
    }

    /** Returns whether the element is currently enabled. */
    public boolean isEnabled(String selector) {
        return locator(selector).isEnabled();
    }

    /** Returns whether the element is currently disabled. */
    public boolean isDisabled(String selector) {
        return locator(selector).isDisabled();
    }

    /** Returns whether the element is currently editable. */
    public boolean isEditable(String selector) {
        return locator(selector).isEditable();
    }

    /** Returns whether the checkbox or radio button is currently checked. */
    public boolean isChecked(String selector) {
        return locator(selector).isChecked();
    }

    /** Returns whether at least one element matching the selector is in the DOM. */
    public boolean isAttached(String selector) {
        return locator(selector).count() > 0;
    }

    // -----------------------------------------------------------------------
    // Reading element content
    // -----------------------------------------------------------------------

    /** Returns the rendered inner text. */
    public String textOf(String selector) {
        return locator(selector).innerText().trim();
    }

    /** Returns the element's text content. */
    public String textContentOf(String selector) {
        String text = locator(selector).textContent();
        return text == null ? "" : text.trim();
    }

    /** Returns the current value of an input, textarea or select control. */
    public String inputValueOf(String selector) {
        return locator(selector).inputValue();
    }

    /**
     * Returns an attribute value, or an empty string when the attribute is absent.
     */
    public String attributeOf(String selector, String attributeName) {
        validateText(attributeName, "Attribute name");
        String value = locator(selector).getAttribute(attributeName);
        return value == null ? "" : value;
    }

    /** Returns the number of elements matching the selector. */
    public int count(String selector) {
        return locator(selector).count();
    }

}