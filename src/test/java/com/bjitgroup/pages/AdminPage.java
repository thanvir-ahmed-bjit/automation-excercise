package com.bjitgroup.pages;

import com.bjitgroup.context.PageManager;
import com.bjitgroup.models.UserData;

import java.util.Properties;

import static com.bjitgroup.utils.PropertyReader.read;

/**
 * OrangeHRM Admin -> User Management page.
 * Supports create / search / edit / delete user operations.
 */
public class AdminPage {

    private final PageActions actions;
    @SuppressWarnings("unused")
    private final PageManager pages;
    private final Properties loc = read("locators/admin-page.properties");

    public AdminPage(PageActions actions, PageManager pages) {
        this.actions = actions;
        this.pages = pages;
    }

    // Navigation

    public AdminPage openUserManagement() {
        actions.navigate("/web/index.php/admin/viewSystemUsers");
        actions.waitForVisible(loc.getProperty("searchUsernameInput"));
        return this;
    }

    // Create User

    public AdminPage clickAddUser() {
        actions.click(loc.getProperty("addButton"));
        return this;
    }

    public AdminPage fillUserForm(UserData user, String password) {
        // User Role dropdown
        actions.click(loc.getProperty("userRoleDropdown"));
        actions.click(String.format(loc.getProperty("dropdownOption"), user.role()));

        // Employee Name auto-complete
        actions.fill(loc.getProperty("employeeNameInput"), "Test Ahmed");
        actions.waitForVisible(loc.getProperty("firstAutoCompleteOption"));
        actions.click(loc.getProperty("firstAutoCompleteOption"));

        // Status dropdown
        actions.click(loc.getProperty("statusDropdown"));
        actions.click(String.format(loc.getProperty("dropdownOption"), user.status()));

        // Username / Password
        actions.fill(loc.getProperty("usernameInput"), user.username());
        actions.fill(loc.getProperty("passwordInput"), password);
        actions.fill(loc.getProperty("confirmPasswordInput"), password);
        return this;
    }

    public AdminPage saveUser() {
        actions.click(loc.getProperty("saveButton"));
        actions.waitForNetworkIdle();
        return this;
    }

    /** Full create-user workflow. */
    public AdminPage createUser(UserData user, String password) {
        return clickAddUser().fillUserForm(user, password).saveUser();
    }

    // Search User

    public AdminPage searchByUsername(String username) {
        actions.fill(loc.getProperty("searchUsernameInput"), username);
        actions.click(loc.getProperty("searchButton"));
        actions.waitForVisible(loc.getProperty("resultTable"));
        return this;
    }

    public boolean isUserInResults(String username) {
        return actions.isVisible(String.format(loc.getProperty("resultRow"), username));
    }

    public int resultCount() {
        return actions.count(loc.getProperty("resultRows"));
    }

    // Edit User

    public AdminPage editFirstResult() {
        actions.click(loc.getProperty("firstEditButton"));
        actions.waitForNetworkIdle(); // wait for edit form to load
        return this;
    }

    // Delete User

    public AdminPage deleteFirstResult() {
        actions.click(loc.getProperty("firstDeleteButton"));
        actions.click(loc.getProperty("confirmDeleteButton"));
        return this;
    }

    // Reset Search

    public AdminPage resetSearch() {
        actions.click(loc.getProperty("resetButton"));
        return this;
    }
}
