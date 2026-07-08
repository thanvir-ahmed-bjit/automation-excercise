package com.bjitgroup.pages;

import com.bjitgroup.context.PageManager;
import com.microsoft.playwright.Page;

import java.util.Properties;

import static com.bjitgroup.utils.PropertyReader.read;

/**
 * OrangeHRM Dashboard / home page.
 */
public class DashboardPage {

    private final Page page;
    private final PageActions actions;
    private final PageManager pages;
    private final Properties loc = read("locators/dashboard-page.properties");

    public DashboardPage(Page page, PageActions actions, PageManager pages) {
        this.page = page;
        this.actions = actions;
        this.pages = pages;
    }

    // Assertions

    public boolean isLoaded() {
        try {
            actions.waitForVisible(loc.getProperty("dashboardHeader"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Actions

    public LoginPage logout() {
        actions.click(loc.getProperty("userDropdown"));
        actions.click(loc.getProperty("logoutLink"));
        actions.waitForUrlContains("/auth/login");
        com.bjitgroup.utils.WaitUtils.waitForDomContentLoaded(page);
        return pages.loginPage();
    }

    public AdminPage goToAdmin() {
        actions.click(loc.getProperty("adminMenuLink"));
        return pages.adminPage();
    }
}


