package com.bjitgroup.context;

import com.bjitgroup.pages.AdminPage;
import com.bjitgroup.pages.DashboardPage;
import com.bjitgroup.pages.LoginPage;
import com.bjitgroup.pages.PageActions;
import com.microsoft.playwright.Page;

/**
 * Central factory for app-specific page objects.
 */
public class PageManager {

    private final Page page;
    private final PageActions actions;

    public PageManager(Page page, int timeoutMs) {
        this.page = page;
        this.actions = new PageActions(page, timeoutMs);
    }

    public LoginPage loginPage() {
        return new LoginPage(page, actions, this);
    }

    public DashboardPage dashboardPage() {
        return new DashboardPage(page, actions, this);
    }

    public AdminPage adminPage() {
        return new AdminPage(actions, this);
    }
}

