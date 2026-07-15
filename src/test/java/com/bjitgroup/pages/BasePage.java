package com.bjitgroup.pages;

import com.bjitgroup.actions.BrowserActions;
import com.bjitgroup.actions.InputActions;
import com.bjitgroup.context.PageManager;

import java.util.Objects;

/**
 * Base abstraction for application page objects.
 *
 * <p>Provides access to test-scoped browser and input actions and
 * the page-object manager. Concrete pages remain responsible for
 * their own locators and business-level operations.</p>
 */
public abstract class BasePage {

    protected final BrowserActions browser;
    protected final InputActions input;
    protected final PageManager pages;

    protected BasePage(
            BrowserActions browser,
            InputActions input,
            PageManager pages
    ) {
        this.browser = Objects.requireNonNull(
                browser,
                "BrowserActions must not be null"
        );
        this.input = Objects.requireNonNull(
                input,
                "InputActions must not be null"
        );
        this.pages = Objects.requireNonNull(
                pages,
                "PageManager must not be null"
        );
    }
}