package com.bjitgroup.context;

import com.bjitgroup.actions.BrowserActions;
import com.bjitgroup.actions.InputActions;
import com.bjitgroup.pages.AdminPage;
import com.bjitgroup.pages.DashboardPage;
import com.bjitgroup.pages.LoginPage;
import com.microsoft.playwright.Page;

import java.util.Objects;

/**
 * Creates typed page objects for the current test.
 *
 * <p>Each call constructs a new page-object instance backed by the same
 * {@link BrowserActions} and {@link InputActions}, which are themselves
 * bound to the test-scoped {@link Page}.</p>
 */
public final class PageManager {

    private final BrowserActions browserActions;
    private final InputActions inputActions;

    public PageManager(Page page, int timeoutMs) {
        Objects.requireNonNull(page, "Page must not be null");
        if (timeoutMs <= 0) {
            throw new IllegalArgumentException(
                    "Timeout must be greater than zero, got: " + timeoutMs);
        }
        this.browserActions = new BrowserActions(page, timeoutMs);
        this.inputActions   = new InputActions(page);
    }

    public LoginPage loginPage() {
        return new LoginPage(browserActions, inputActions, this);
    }

    public DashboardPage dashboardPage() {
        return new DashboardPage(browserActions, inputActions, this);
    }

    public AdminPage adminPage() {
        return new AdminPage(browserActions, inputActions, this);
    }
}