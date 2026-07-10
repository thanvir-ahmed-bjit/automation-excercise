package com.bjitgroup.pages;

import com.bjitgroup.actions.BrowserActions;
import com.bjitgroup.actions.InputActions;
import com.bjitgroup.context.PageManager;

import java.util.Properties;

import static com.bjitgroup.utils.PropertyReader.read;

/**
 * OrangeHRM Dashboard page.
 */
public final class DashboardPage {

    private final BrowserActions browser;
    private final InputActions input;
    private final PageManager pages;
    private final Properties loc =
            read("locators/dashboard-page.properties");

    public DashboardPage(
            BrowserActions browser,
            InputActions input,
            PageManager pages
    ) {
        this.browser = browser;
        this.input = input;
        this.pages = pages;
    }

    public boolean isLoaded() {
        return browser.isVisible(loc.getProperty("dashboardHeader"));
    }

    public LoginPage logout() {
        input.click(loc.getProperty("userDropdown"));
        input.click(loc.getProperty("logoutLink"));

        browser.waitForUrlContains("/auth/login");
        browser.waitForDomContentLoaded();

        return pages.loginPage();
    }

    public AdminPage goToAdmin() {
        input.click(loc.getProperty("adminMenuLink"));
        return pages.adminPage();
    }
}