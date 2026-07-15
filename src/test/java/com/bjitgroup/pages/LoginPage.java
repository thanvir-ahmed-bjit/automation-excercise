package com.bjitgroup.pages;

import com.bjitgroup.actions.InputActions;
import com.bjitgroup.actions.BrowserActions;
import com.bjitgroup.context.PageManager;

import java.util.Properties;

import static com.bjitgroup.utils.PropertyReader.read;

/**
 * Login page for OrangeHRM.
 *
 * <h3>Locators</h3>
 * All CSS/XPath selectors are externalised in
 * {@code src/test/resources/locators/login-page.properties}.
 *
 * <h3>Fluent interface</h3>
 * Methods that stay on the same page return {@code this};
 * navigation methods return the target page object.
 */
public final class LoginPage extends BasePage {

    private final Properties loc =
            read("locators/login-page.properties");

    public LoginPage(
            BrowserActions browser,
            InputActions input,
            PageManager pages
    ) {
        super(browser, input, pages);
    }

    public LoginPage open() {
        browser.navigate("/web/index.php/auth/login");
        return waitUntilLoaded();
    }

    /**
     * Waits until the login page readiness element is visible.
     *
     * <p>Throws a Playwright timeout error when the page does not become ready
     * within the configured timeout.</p>
     *
     * @return this login page
     */
    public LoginPage waitUntilLoaded() {
        browser.waitForVisible(
                loc.getProperty("loginButton")
        );
        return this;
    }

    public LoginPage enterUsername(String username) {
        input.fill(
                loc.getProperty("usernameInput"),
                username
        );
        return this;
    }

    public LoginPage enterPassword(String password) {
        input.fill(
                loc.getProperty("passwordInput"),
                password
        );
        return this;
    }

    public LoginPage clickLogin() {
        input.click(
                loc.getProperty("loginButton")
        );
        return this;
    }

    public DashboardPage loginAs(
            String username,
            String password
    ) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();

        return pages.dashboardPage().waitUntilLoaded();
    }

    /**
     * Returns whether the login page readiness element is currently visible.
     *
     * <p>This is an immediate state query and does not wait. Call
     * {@link #waitUntilLoaded()} when synchronization is required.</p>
     *
     * @return {@code true} when the login button is visible now; otherwise {@code false}
     */
    public boolean isLoaded() {
        return browser.isVisible(
                loc.getProperty("loginButton")
        );
    }

    /** Backward-compatible alias for existing tests. */
    public boolean isLoginPageDisplayed() {
        return isLoaded();
    }

    public String getErrorMessage() {
        return browser.textOf(
                loc.getProperty("errorMessage")
        );
    }
}