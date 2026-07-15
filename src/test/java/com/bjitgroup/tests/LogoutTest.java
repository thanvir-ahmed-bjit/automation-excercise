package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import com.bjitgroup.listeners.RetryAnalyzer;
import com.bjitgroup.pages.DashboardPage;
import com.bjitgroup.pages.LoginPage;
import io.qameta.allure.*;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/**
 * <p>Listeners are declared once on {@link com.bjitgroup.base.BaseTest}.</p>
 */
@Feature("Authentication")
public class LogoutTest extends BaseTest {

    @Test(
            description = "Authenticated user should be able to log out",
            retryAnalyzer = RetryAnalyzer.class
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Logout")
    @Description("Login with valid credentials, click Logout, and verify redirect to Login page.")
    public void loggedInUserShouldLogoutSuccessfully() {

        LoginPage loginPage = pages().loginPage();

        Allure.step("Open Login page", loginPage::open);
        Allure.step("Enter username", () ->
                loginPage.enterUsername(context().config().username()));
        Allure.step("Enter password", () ->
                loginPage.enterPassword(context().config().password()));
        Allure.step("Click Login button", loginPage::clickLogin);

        DashboardPage dashboard = pages().dashboardPage();

        Allure.step("Verify Dashboard page is loaded", () ->
                Assertions.assertThat(dashboard.isLoaded())
                        .as("Pre-condition: dashboard must be visible")
                        .isTrue());

        LoginPage loginPageAfterLogout = Allure.step(
                "Logout from application",
                dashboard::logout
        );

        Allure.step("Verify Login page is displayed after logout", () ->
                Assertions.assertThat(loginPageAfterLogout.isLoginPageDisplayed())
                        .as("Login page should be displayed after logout")
                        .isTrue());

        Allure.step("Verify URL redirects to Login page", () ->
                Assertions.assertThat(page().url())
                        .as("URL should redirect back to the login page")
                        .contains("/auth/login"));
    }
}