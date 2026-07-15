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

        browser.waitForUrlContains("/index.php/");
        return pages.dashboardPage();
    }

    /**
     * Returns {@code true} once the login button is visible, waiting up to the
     * configured timeout.  Uses {@link BrowserActions#waitForVisible} because
     * this method is called immediately after navigation and the button may not
     * yet be rendered.
     */
    public boolean isLoginPageDisplayed() {
        browser.waitForVisible(loc.getProperty("loginButton"));
        return true;
    }

    public String getErrorMessage() {
        return browser.textOf(
                loc.getProperty("errorMessage")
        );
    }
}