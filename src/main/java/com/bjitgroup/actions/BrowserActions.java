package com.bjitgroup.actions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Provides reusable page-level operations.
 *
 * <p>This class is responsible for:</p>
 * <ul>
 *     <li>Page navigation</li>
 *     <li>URL and load-state waiting</li>
 *     <li>Element-state waiting</li>
 *     <li>Reading element content and state</li>
 * </ul>
 *
 * <p>User interactions such as click, fill, check, keyboard input,
 * file upload and drag-and-drop belong to {@link InputActions}.</p>
 */
public final class BrowserActions {

    private final Page page;
    private final double timeoutMs;

    public BrowserActions(Page page, double timeoutMs) {
        this.page = Objects.requireNonNull(page, "Page must not be null");

        if (timeoutMs <= 0) {
            throw new IllegalArgumentException(
                    "Timeout must be greater than zero"
            );
        }

        this.timeoutMs = timeoutMs;
    }

    // -------------------------------------------------------------------------
    // Locator
    // -------------------------------------------------------------------------

    /**
     * Creates a Playwright locator after validating the selector.
     */
    public Locator locator(String selector) {
        validateSelector(selector);
        return page.locator(selector);
    }

    // -------------------------------------------------------------------------
    // Navigation
    // -------------------------------------------------------------------------

    /**
     * Navigates to an absolute URL or a relative path when baseURL is configured.
     */
    public void navigate(String urlOrPath) {
        validateText(urlOrPath, "URL or path");

        page.navigate(
                urlOrPath,
                new Page.NavigateOptions()
                        .setTimeout(timeoutMs)
        );
    }

    /**
     * Reloads the current page.
     */
    public void reload() {
        page.reload(
                new Page.ReloadOptions()
                        .setTimeout(timeoutMs)
        );
    }

    /**
     * Navigates to the previous history entry.
     */
    public void goBack() {
        page.goBack(
                new Page.GoBackOptions()
                        .setTimeout(timeoutMs)
        );
    }

    /**
     * Navigates to the next history entry.
     */
    public void goForward() {
        page.goForward(
                new Page.GoForwardOptions()
                        .setTimeout(timeoutMs)
        );
    }

    /**
     * Returns the current page URL.
     */
    public String currentUrl() {
        return page.url();
    }

    /**
     * Returns the current page title.
     */
    public String title() {
        return page.title();
    }

    // -------------------------------------------------------------------------
    // Page waiting
    // -------------------------------------------------------------------------

    /**
     * Waits until the current URL contains the specified text.
     */
    public void waitForUrlContains(String expectedUrlPart) {
        validateText(expectedUrlPart, "Expected URL part");

        Pattern urlPattern = Pattern.compile(
                ".*" + Pattern.quote(expectedUrlPart) + ".*"
        );

        page.waitForURL(
                urlPattern,
                new Page.WaitForURLOptions()
                        .setTimeout(timeoutMs)
        );
    }

    /**
     * Waits until DOMContentLoaded is reached.
     */
    public void waitForDomContentLoaded() {
        page.waitForLoadState(
                LoadState.DOMCONTENTLOADED,
                new Page.WaitForLoadStateOptions()
                        .setTimeout(timeoutMs)
        );
    }

    /**
     * Waits until the page reaches the LOAD state.
     */
    public void waitForPageLoad() {
        page.waitForLoadState(
                LoadState.LOAD,
                new Page.WaitForLoadStateOptions()
                        .setTimeout(timeoutMs)
        );
    }

    /**
     * Waits for the network to become idle.
     *
     * Use sparingly because many modern applications continuously perform
     * background network requests.
     */
    public void waitForNetworkIdle() {
        page.waitForLoadState(
                LoadState.NETWORKIDLE,
                new Page.WaitForLoadStateOptions()
                        .setTimeout(timeoutMs)
        );
    }

    // -------------------------------------------------------------------------
    // Element-state waiting
    // -------------------------------------------------------------------------

    public void waitForVisible(String selector) {
        waitForState(selector, WaitForSelectorState.VISIBLE);
    }

    public void waitForHidden(String selector) {
        waitForState(selector, WaitForSelectorState.HIDDEN);
    }

    public void waitForAttached(String selector) {
        waitForState(selector, WaitForSelectorState.ATTACHED);
    }

    public void waitForDetached(String selector) {
        waitForState(selector, WaitForSelectorState.DETACHED);
    }

    private void waitForState(
            String selector,
            WaitForSelectorState state
    ) {
        locator(selector).waitFor(
                new Locator.WaitForOptions()
                        .setState(state)
                        .setTimeout(timeoutMs)
        );
    }

    // -------------------------------------------------------------------------
    // Element-state queries
    // -------------------------------------------------------------------------

    public boolean isVisible(String selector) {
        waitForState(selector, WaitForSelectorState.VISIBLE);
        return locator(selector).isVisible();
    }

    public boolean isHidden(String selector) {
        return locator(selector).isHidden();
    }

    public boolean isEnabled(String selector) {
        return locator(selector).isEnabled();
    }

    public boolean isDisabled(String selector) {
        return locator(selector).isDisabled();
    }

    public boolean isEditable(String selector) {
        return locator(selector).isEditable();
    }

    public boolean isChecked(String selector) {
        return locator(selector).isChecked();
    }

    public boolean isAttached(String selector) {
        return locator(selector).count() > 0;
    }

    // -------------------------------------------------------------------------
    // Reading element content
    // -------------------------------------------------------------------------

    /**
     * Returns the rendered inner text.
     */
    public String textOf(String selector) {
        return locator(selector).innerText().trim();
    }

    /**
     * Returns the element's text content.
     */
    public String textContentOf(String selector) {
        String text = locator(selector).textContent();
        return text == null ? "" : text.trim();
    }

    /**
     * Returns the current value of an input, textarea or select control.
     */
    public String inputValueOf(String selector) {
        return locator(selector).inputValue();
    }

    /**
     * Returns an attribute value or an empty string when it is absent.
     */
    public String attributeOf(
            String selector,
            String attributeName
    ) {
        validateText(attributeName, "Attribute name");

        String value = locator(selector).getAttribute(attributeName);
        return value == null ? "" : value;
    }

    /**
     * Returns the number of elements matching the selector.
     */
    public int count(String selector) {
        return locator(selector).count();
    }

    // -------------------------------------------------------------------------
    // Validation
    // -------------------------------------------------------------------------

    private void validateSelector(String selector) {
        validateText(selector, "Selector");
    }

    private void validateText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " must not be null or blank"
            );
        }
    }
}