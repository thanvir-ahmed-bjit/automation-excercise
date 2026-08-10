package com.bjitgroup.pages;

import com.bjitgroup.actions.BrowserActions;
import com.bjitgroup.actions.InputActions;
import com.bjitgroup.context.PageManager;
import com.bjitgroup.models.UserData;

import java.util.Properties;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.bjitgroup.utils.PropertyReader.read;

/**
 * OrangeHRM Admin page.
 */
public final class AdminPage extends BasePage {

    private final Properties loc =
            read("locators/admin-page.properties");

    public AdminPage(
            BrowserActions browser,
            InputActions input,
            PageManager pages
    ) {
        super(browser, input, pages);
    }

    public AdminPage open() {
        browser.navigate("/web/index.php/admin/viewSystemUsers");
        return waitUntilLoaded();
    }

    /**
     * Waits until the admin page readiness element is visible.
     *
     * <p>Throws a Playwright timeout error when the page does not become ready
     * within the configured timeout.</p>
     *
     * @return this admin page
     */
    public AdminPage waitUntilLoaded() {
        browser.waitForVisible(
                loc.getProperty("adminHeader")
        );
        return this;
    }

    /**
     * Backward-compatible alias used by existing tests.
     */
    public AdminPage openUserManagement() {
        return open();
    }

    /**
     * Returns whether the admin page readiness element is currently visible.
     *
     * <p>This is an immediate state query and does not wait. Call
     * {@link #waitUntilLoaded()} when synchronization is required.</p>
     *
     * @return {@code true} when the admin header is visible now; otherwise {@code false}
     */
    public boolean isLoaded() {
        return browser.isVisible(
                loc.getProperty("adminHeader")
        );
    }

    public AdminPage clickAddUser() {
        input.click(loc.getProperty("addUserButton"));
        return this;
    }

    public AdminPage enterEmployeeName(String employeeName) {
        input.fill(
                loc.getProperty("employeeNameInput"),
                employeeName
        );
        return this;
    }

    public AdminPage enterUsername(String username) {
        input.fill(
                loc.getProperty("usernameInput"),
                username
        );
        return this;
    }

    public AdminPage selectRole(String role) {
        input.click(loc.getProperty("roleDropdown"));
        input.click(String.format(
                loc.getProperty("dropdownOptionByText"),
                role
        ));
        return this;
    }

    public AdminPage selectStatus(String status) {
        input.click(loc.getProperty("statusDropdown"));
        input.click(String.format(
                loc.getProperty("dropdownOptionByText"),
                status
        ));
        return this;
    }

    public AdminPage enterPassword(String password) {
        input.fill(
                loc.getProperty("passwordInput"),
                password
        );
        return this;
    }

    public AdminPage confirmPassword(String password) {
        input.fill(
                loc.getProperty("confirmPasswordInput"),
                password
        );
        return this;
    }

    public AdminPage saveUser() {
        input.click(loc.getProperty("saveButton"));
        browser.waitForVisible(loc.getProperty("successMessage"));
        return this;
    }

    public AdminPage searchByUsername(String username) {
        input.fill(
                loc.getProperty("searchUsernameInput"),
                username
        );
        input.click(loc.getProperty("searchButton"));
        browser.waitForVisible(loc.getProperty("resultTable"));
        return this;
    }

    public AdminPage resetSearch() {
        input.click(loc.getProperty("resetButton"));
        assertThat(browser.locator(loc.getProperty("searchUsernameInput"))).hasValue("");
        return this;
    }

    public boolean isUserDisplayed(String username) {
        String userRow = String.format(
                loc.getProperty("userRowByUsername"),
                username
        );
        return browser.isVisible(userRow);
    }

    public boolean isUserInResults(String username) {
        return isUserDisplayed(username);
    }

    public int resultCount() {
        return browser.count(loc.getProperty("resultRows"));
    }

    public String getSuccessMessage() {
        return browser.textOf(
                loc.getProperty("successMessage")
        );
    }

    public AdminPage createUser(
            UserData user,
            String password
    ) {
        clickAddUser()
                .enterEmployeeName(
                        user.firstName() + " " + user.lastName()
                )
                .selectRole(user.role())
                .selectStatus(user.status())
                .enterUsername(user.username())
                .enterPassword(password)
                .confirmPassword(password)
                .saveUser();

        return this;
    }

    public DashboardPage returnToDashboard() {
        input.click(loc.getProperty("dashboardMenuLink"));
        return pages.dashboardPage().waitUntilLoaded();
    }

    public AdminPage editFirstResult() {
        input.click(loc.getProperty("firstEditButton"));
        browser.waitForVisible(loc.getProperty("saveButton"));
        return this;
    }

    public AdminPage deleteFirstResult() {
        // A previous toast can still be visible; ensure the next success wait reflects this delete action.
        browser.waitForHidden(loc.getProperty("successMessage"));

        input.click(loc.getProperty("firstDeleteButton"));
        input.click(loc.getProperty("confirmDeleteButton"));

        browser.waitForVisible(loc.getProperty("successMessage"));
        browser.waitForHidden(loc.getProperty("confirmDeleteButton"));

        return this;
    }
}
