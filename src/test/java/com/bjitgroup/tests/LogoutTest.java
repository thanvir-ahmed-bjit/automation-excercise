package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import com.bjitgroup.listeners.RetryAnalyzer;
import com.bjitgroup.pages.DashboardPage;
import com.bjitgroup.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/**
 * Tests covering the Logout feature.
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
        DashboardPage dashboard = loginPage.loginAs(config.username(), config.password());
        Assertions.assertThat(dashboard.isLoaded())
                .as("Pre-condition: dashboard must be visible")
                .isTrue();

        LoginPage loginPageAfterLogout = dashboard.logout();

        Assertions.assertThat(loginPageAfterLogout.isLoginPageDisplayed())
                .as("Login page should be displayed after logout")
                .isTrue();

        Assertions.assertThat(page.url())
                .as("URL should redirect back to the login page")
                .contains("/auth/login");
    }
}


