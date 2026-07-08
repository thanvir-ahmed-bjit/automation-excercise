package com.bjitgroup.pages;

import com.bjitgroup.context.PageManager;
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
public class LoginPage {

    private final Page page;
    private final PageActions actions;
    private final PageManager pages;
    private final Properties loc = read("locators/login-page.properties");

    public LoginPage(Page page, PageActions actions, PageManager pages) {
        this.page = page;
        this.actions = actions;
        this.pages = pages;
    }

    // Navigation

    public LoginPage open() {
        actions.navigate("/web/index.php/auth/login");
        return this;
    }

    // Actions

    public LoginPage enterUsername(String username) {
        actions.fill(loc.getProperty("usernameInput"), username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        actions.fill(loc.getProperty("passwordInput"), password);
        return this;
    }

    public LoginPage clickLogin() {
        actions.click(loc.getProperty("loginButton"));
        com.bjitgroup.utils.WaitUtils.waitForDomContentLoaded(page);
        return this;
    }

    /** Convenience: enter credentials and submit in one call. */
    public DashboardPage loginAs(String username, String password) {
        enterUsername(username)
                .enterPassword(password)
                .clickLogin();
        actions.waitForUrlContains("/index.php/");
        return pages.dashboardPage();
    }

    // Assertions

    public boolean isLoginPageDisplayed() {
        try {
            actions.waitForVisible(loc.getProperty("loginButton"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        return actions.textOf(loc.getProperty("errorMessage"));
    }
}


