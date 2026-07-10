package com.bjitgroup.tests;

import com.bjitgroup.listeners.RetryAnalyzer;
import com.bjitgroup.listeners.TestListener;
import com.bjitgroup.pages.DashboardPage;
import com.bjitgroup.pages.LoginPage;
import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Feature("Authentication")
@Listeners({AllureTestNg.class, TestListener.class})
public class LogoutTest extends ContextAwareTest {

    @Test(
            description = "Authenticated user should be able to log out",
            retryAnalyzer = RetryAnalyzer.class
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Logout")
    @Description("Login with valid credentials, click Logout, and verify redirect to Login page.")
    public void loggedInUserShouldLogoutSuccessfully() {

        LoginPage loginPage = context.pages().loginPage();

        Allure.step("Open Login page", loginPage::open);
        Allure.step("Enter username", () ->
                loginPage.enterUsername(context.config().username()));
        Allure.step("Enter password", () ->
                loginPage.enterPassword(context.config().password()));
        Allure.step("Click Login button", loginPage::clickLogin);

        DashboardPage dashboard = context.pages().dashboardPage();

        Allure.step("Verify Dashboard page is loaded", () -> {
            Assertions.assertThat(dashboard.isLoaded())
                    .as("Pre-condition: dashboard must be visible")
                    .isTrue();
        });

        LoginPage loginPageAfterLogout = Allure.step(
                "Logout from application",
                dashboard::logout
        );

        Allure.step("Verify Login page is displayed after logout", () -> {
            Assertions.assertThat(loginPageAfterLogout.isLoginPageDisplayed())
                    .as("Login page should be displayed after logout")
                    .isTrue();
        });

        Allure.step("Verify URL redirects to Login page", () -> {
            Assertions.assertThat(context.page().url())
                    .as("URL should redirect back to the login page")
                    .contains("/auth/login");
        });
    }
}