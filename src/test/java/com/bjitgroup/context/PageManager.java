package com.bjitgroup.context;

import com.bjitgroup.actions.BrowserActions;
import com.bjitgroup.actions.InputActions;
import com.bjitgroup.pages.AdminPage;
import com.bjitgroup.pages.DashboardPage;
import com.bjitgroup.pages.LoginPage;
import com.microsoft.playwright.Page;

public final class PageManager {

    private final BrowserActions browserActions;
    private final InputActions inputActions;

    public PageManager(Page page, int timeoutMs) {
        this.browserActions = new BrowserActions(page, timeoutMs);
        this.inputActions = new InputActions(page);
    }

    public LoginPage loginPage() {
        return new LoginPage(
                browserActions,
                inputActions,
                this
        );
    }

    public DashboardPage dashboardPage() {
        return new DashboardPage(
                browserActions,
                inputActions,
                this
        );
    }

    public AdminPage adminPage() {
        return new AdminPage(
                browserActions,
                inputActions,
                this
        );
    }
}