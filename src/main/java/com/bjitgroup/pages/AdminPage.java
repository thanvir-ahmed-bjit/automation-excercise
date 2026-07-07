package com.bjitgroup.pages;

import com.bjitgroup.models.UserData;
import com.microsoft.playwright.Page;

import java.util.Properties;

import static com.bjitgroup.utils.PropertyReader.read;

/**
 * OrangeHRM Admin -> User Management page.
 * Supports create / search / edit / delete user operations.
 */
public class AdminPage extends BasePage {

    private final Properties loc = read("locators/admin-page.properties");

    public AdminPage(Page page) {
        super(page);
    }

    // Navigation

    public AdminPage openUserManagement() {
        navigate("/web/index.php/admin/viewSystemUsers");
        return this;
    }

    // Create User

    public AdminPage clickAddUser() {
        click(loc.getProperty("addButton"));
        return this;
    }

    public AdminPage fillUserForm(UserData user, String password) {
        // User Role dropdown
        click(loc.getProperty("userRoleDropdown"));
        click(String.format(loc.getProperty("dropdownOption"), user.role()));

        // Employee Name auto-complete
        fill(loc.getProperty("employeeNameInput"), user.firstName());
        click(loc.getProperty("firstAutoCompleteOption"));

        // Status dropdown
        click(loc.getProperty("statusDropdown"));
        click(String.format(loc.getProperty("dropdownOption"), user.status()));

        // Username / Password
        fill(loc.getProperty("usernameInput"), user.username());
        fill(loc.getProperty("passwordInput"), password);
        fill(loc.getProperty("confirmPasswordInput"), password);
        return this;
    }

    public AdminPage saveUser() {
        click(loc.getProperty("saveButton"));
        return this;
    }

    /** Full create-user workflow. */
    public AdminPage createUser(UserData user, String password) {
        return clickAddUser().fillUserForm(user, password).saveUser();
    }

    // Search User

    public AdminPage searchByUsername(String username) {
        fill(loc.getProperty("searchUsernameInput"), username);
        click(loc.getProperty("searchButton"));
        return this;
    }

    public boolean isUserInResults(String username) {
        return isVisible(String.format(loc.getProperty("resultRow"), username));
    }

    public int resultCount() {
        return count(loc.getProperty("resultRows"));
    }

    // Edit User

    public AdminPage editFirstResult() {
        click(loc.getProperty("firstEditButton"));
        return this;
    }

    // Delete User

    public AdminPage deleteFirstResult() {
        click(loc.getProperty("firstDeleteButton"));
        click(loc.getProperty("confirmDeleteButton"));
        return this;
    }

    // Reset Search

    public AdminPage resetSearch() {
        click(loc.getProperty("resetButton"));
        return this;
    }
}


