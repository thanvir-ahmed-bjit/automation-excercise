package com.bjitgroup.context;

import com.bjitgroup.actions.BrowserActions;
import com.bjitgroup.actions.InputActions;
import com.bjitgroup.pages.AdminPage;
import com.bjitgroup.pages.DashboardPage;
import com.bjitgroup.pages.LoginPage;
import com.bjitgroup.pages.SignupPage;
import com.microsoft.playwright.Page;

import java.util.Objects;

/**
 * Creates typed page objects for the current test.
 *
 * <p>Page objects are created lazily and cached per PageManager instance,
 * while sharing the same {@link BrowserActions} and {@link InputActions}
 * bound to the test-scoped {@link Page}.</p>
 */
public final class PageManager {

    private final BrowserActions browserActions;
    private final InputActions inputActions;

    // Lazily created and cached per PageManager instance (which is scoped to one UiTestContext)
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private AdminPage adminPage;
    private SignupPage signupPage;

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
        if (loginPage == null) {
            loginPage = new LoginPage(browserActions, inputActions, this);
        }
        return loginPage;
    }

    public DashboardPage dashboardPage() {
        if (dashboardPage == null) {
            dashboardPage = new DashboardPage(browserActions, inputActions, this);
        }
        return dashboardPage;
    }

    public AdminPage adminPage() {
        if (adminPage == null) {
            adminPage = new AdminPage(browserActions, inputActions, this);
        }
        return adminPage;
    }

    public SignupPage signupPage() {
        if (signupPage == null) {
            signupPage = new SignupPage(browserActions, inputActions, this);
        }
        return signupPage;
    }
}