package com.bjitgroup.pages;

import com.microsoft.playwright.Page;

import java.util.Properties;

import static com.bjitgroup.utils.PropertyReader.read;

/**
 * OrangeHRM Dashboard / home page.
 */
public class DashboardPage extends BasePage {

    private final Properties loc = read("locators/dashboard-page.properties");

    public DashboardPage(Page page) {
        super(page);
    }

    // Assertions

    public boolean isLoaded() {
        try {
            com.bjitgroup.utils.WaitUtils.waitForVisible(loc(loc.getProperty("dashboardHeader")), timeoutMs);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Actions

    public LoginPage logout() {
        click(loc.getProperty("userDropdown"));
        click(loc.getProperty("logoutLink"));
        com.bjitgroup.utils.WaitUtils.waitForUrl(page, "/auth/login", timeoutMs);
        com.bjitgroup.utils.WaitUtils.waitForDomContentLoaded(page);
        return new LoginPage(page);
    }

    public AdminPage goToAdmin() {
        click(loc.getProperty("adminMenuLink"));
        return new AdminPage(page);
    }
}


