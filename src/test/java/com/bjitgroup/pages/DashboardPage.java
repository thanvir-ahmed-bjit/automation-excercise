package com.bjitgroup.pages;

import com.bjitgroup.actions.BrowserActions;
import com.bjitgroup.actions.InputActions;
import com.bjitgroup.context.PageManager;

import java.util.Properties;

import static com.bjitgroup.utils.PropertyReader.read;

/**
 * OrangeHRM Dashboard page.
 */
public final class DashboardPage extends BasePage {

    private final Properties loc =
            read("locators/dashboard-page.properties");

    public DashboardPage(
            BrowserActions browser,
            InputActions input,
            PageManager pages
    ) {
        super(browser, input, pages);
    }

    /**
     * Returns {@code true} once the dashboard header is visible, waiting up to the
     * configured timeout.  Uses {@link BrowserActions#waitForVisible} rather than
     * an instant snapshot because this method is called immediately after navigation
     * and the header may not yet be in the DOM.
     */
    public boolean isLoaded() {
        browser.waitForVisible(loc.getProperty("dashboardHeader"));
        return true;
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