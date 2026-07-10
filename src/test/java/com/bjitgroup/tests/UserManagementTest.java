package com.bjitgroup.tests;

import com.bjitgroup.dataproviders.UserDataProvider;
import com.bjitgroup.listeners.RetryAnalyzer;
import com.bjitgroup.listeners.TestListener;
import com.bjitgroup.models.UserData;
import com.bjitgroup.pages.AdminPage;
import com.bjitgroup.pages.DashboardPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.testng.AllureTestNg;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * End-to-end CRUD tests for Admin -> User Management.
 *
 * <p>Each test is independent (its own browser session) to support
 * parallel execution without shared mutable state.</p>
 */
@Feature("User Management")
@Listeners({AllureTestNg.class, TestListener.class})
public class UserManagementTest extends ContextAwareTest {

    private static final String DEFAULT_PASSWORD = "Admin@123";
    // Create User

    @Test(
            dataProvider = "randomUser",
            dataProviderClass = UserDataProvider.class,
            description = "Admin should be able to create a new system user",
            retryAnalyzer = RetryAnalyzer.class
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Create User")
    @Description("Login -> Admin -> Add User -> fill form -> Save -> verify user appears in results.")
    public void adminShouldCreateUser(UserData user) {
        DashboardPage dashboard = loginAsAdmin();
        AdminPage adminPage = dashboard.goToAdmin()
                .openUserManagement()
                .createUser(user, DEFAULT_PASSWORD)
                .searchByUsername(user.username());

        Assertions.assertThat(adminPage.isUserInResults(user.username()))
                .as("Newly created user '%s' should appear in search results", user.username())
                .isTrue();
    }

    // Search User

    @Test(
            description = "Admin should be able to search for an existing user",
            retryAnalyzer = RetryAnalyzer.class
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Search User")
    @Description("Login -> Admin -> search by admin username -> verify result count > 0.")
    public void adminShouldSearchUser() {
        DashboardPage dashboard = loginAsAdmin();
        AdminPage adminPage = dashboard.goToAdmin()
                .openUserManagement()

                .searchByUsername("Admin");

        Assertions.assertThat(adminPage.resultCount())
                .as("There should be at least one result for username 'Admin'")
                .isGreaterThanOrEqualTo(1);
    }

    // Edit User

    @Test(
            dataProvider = "randomUser",
            dataProviderClass = UserDataProvider.class,
            description = "Admin should be able to edit an existing user",
            retryAnalyzer = RetryAnalyzer.class
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Edit User")
    @Description("Create a user -> find in results -> click Edit -> save -> verify still on admin page.")
    public void adminShouldEditUser(UserData user) {
        DashboardPage dashboard = loginAsAdmin();
        AdminPage adminPage = dashboard.goToAdmin()
                .openUserManagement()
                .createUser(user, DEFAULT_PASSWORD)
                .searchByUsername(user.username());

        Assertions.assertThat(adminPage.isUserInResults(user.username()))
                .as("Pre-condition: user must exist before editing")
                .isTrue();

        adminPage.editFirstResult()
                .saveUser();

        Assertions.assertThat(context.page().url())
                .as("After saving, URL should remain on the admin section")
                .contains("/admin");
    }

    // Delete User

    @Test(
            dataProvider = "randomUser",
            dataProviderClass = UserDataProvider.class,
            description = "Admin should be able to delete an existing user",
            retryAnalyzer = RetryAnalyzer.class
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Delete User")
    @Description("Create a user -> search -> delete -> verify user is gone from results.")
    public void adminShouldDeleteUser(UserData user) {
        DashboardPage dashboard = loginAsAdmin();
        AdminPage adminPage = dashboard.goToAdmin()
                .openUserManagement()
                .createUser(user, DEFAULT_PASSWORD)
                .searchByUsername(user.username());

        Assertions.assertThat(adminPage.isUserInResults(user.username()))
                .as("Pre-condition: user must exist before deletion")
                .isTrue();

        adminPage.deleteFirstResult()
                .searchByUsername(user.username());

        Assertions.assertThat(adminPage.isUserInResults(user.username()))
                .as("Deleted user '%s' should NOT appear in search results", user.username())
                .isFalse();
    }

    private DashboardPage loginAsAdmin() {
        return context.pages()
                .loginPage()
                .open()
                .loginAs(context.config().username(), context.config().password());
    }
}
