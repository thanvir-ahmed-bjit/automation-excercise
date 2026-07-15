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
     * Waits until the dashboard readiness element is visible.
     *
     * <p>Throws a Playwright timeout error when the page does not become ready
     * within the configured timeout.</p>
     *
     * @return this dashboard page
     */
    public DashboardPage waitUntilLoaded() {
        browser.waitForVisible(
                loc.getProperty("dashboardHeader")
        );
        return this;
    }

    /**
     * Returns whether the dashboard readiness element is currently visible.
     *
     * <p>This is an immediate state query and does not wait. Call
     * {@link #waitUntilLoaded()} when synchronization is required.</p>
     *
     * @return {@code true} when the dashboard header is visible now; otherwise {@code false}
     */
    public boolean isLoaded() {
        return browser.isVisible(
                loc.getProperty("dashboardHeader")
        );
    }

    public LoginPage logout() {
        input.click(loc.getProperty("userDropdown"));
        input.click(loc.getProperty("logoutLink"));

        return pages.loginPage().waitUntilLoaded();
    }

    public AdminPage goToAdmin() {
        input.click(loc.getProperty("adminMenuLink"));
        return pages.adminPage().waitUntilLoaded();
    }
}