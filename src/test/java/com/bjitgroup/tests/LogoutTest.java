package com.bjitgroup.tests;

import com.bjitgroup.context.UiContextAware;
import com.bjitgroup.context.UiTestContext;
import com.bjitgroup.listeners.RetryAnalyzer;
import com.bjitgroup.listeners.TestListener;
import com.bjitgroup.pages.DashboardPage;
import com.bjitgroup.pages.LoginPage;
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
 * Tests covering the Logout feature.
 */
@Feature("Authentication")
@Listeners({AllureTestNg.class, TestListener.class})
public class LogoutTest implements UiContextAware {

    private UiTestContext context;

    @Override
    public void setUiTestContext(UiTestContext context) {
        this.context = context;
    }

    @Override
    public UiTestContext getUiTestContext() {
        return context;
    }

    @Test(
            description = "Authenticated user should be able to log out",
            retryAnalyzer = RetryAnalyzer.class
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Logout")
    @Description("Login with valid credentials, click Logout, and verify redirect to Login page.")
    public void loggedInUserShouldLogoutSuccessfully() {
        DashboardPage dashboard = context.pages()
                .loginPage()
                .open()
                .loginAs(context.config().username(), context.config().password());
        Assertions.assertThat(dashboard.isLoaded())
                .as("Pre-condition: dashboard must be visible")
                .isTrue();

        LoginPage loginPageAfterLogout = dashboard.logout();

        Assertions.assertThat(loginPageAfterLogout.isLoginPageDisplayed())
                .as("Login page should be displayed after logout")
                .isTrue();

        Assertions.assertThat(context.page().url())
                .as("URL should redirect back to the login page")
                .contains("/auth/login");
    }
}


