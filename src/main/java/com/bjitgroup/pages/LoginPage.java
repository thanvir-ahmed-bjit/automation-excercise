package com.bjitgroup.pages;

import com.microsoft.playwright.Page;

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
public class LoginPage extends BasePage {

    private final Properties loc = read("locators/login-page.properties");

    public LoginPage(Page page) {
        super(page);
    }

    // Navigation

    public LoginPage open() {
        navigate("/web/index.php/auth/login");
        return this;
    }

    // Actions

    public LoginPage enterUsername(String username) {
        fill(loc.getProperty("usernameInput"), username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        fill(loc.getProperty("passwordInput"), password);
        return this;
    }

    public DashboardPage clickLogin() {
        click(loc.getProperty("loginButton"));
        // Wait until the browser has left the login page (URL no longer contains /auth/login)
        com.bjitgroup.utils.WaitUtils.waitForUrl(page, "/index.php/", timeoutMs);
        com.bjitgroup.utils.WaitUtils.waitForDomContentLoaded(page);
        return new DashboardPage(page);
    }

    /** Convenience: enter credentials and submit in one call. */
    public DashboardPage loginAs(String username, String password) {
        return enterUsername(username)
                .enterPassword(password)
                .clickLogin();
    }

    // Assertions

    public boolean isLoginPageDisplayed() {
        try {
            com.bjitgroup.utils.WaitUtils.waitForVisible(loc(loc.getProperty("loginButton")), timeoutMs);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        return textOf(loc.getProperty("errorMessage"));
    }
}


