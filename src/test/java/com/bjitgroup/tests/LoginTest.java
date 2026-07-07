package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import com.bjitgroup.listeners.RetryAnalyzer;
import com.bjitgroup.pages.DashboardPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/**
 * Tests covering the Login feature.
 * Target: OrangeHRM demo (https://opensource-demo.orangehrmlive.com)
 */
@Feature("Authentication")
public class LoginTest extends BaseTest {

    @Test(
            description = "Valid credentials should navigate to the dashboard",
            retryAnalyzer = RetryAnalyzer.class
    )
    @Severity(SeverityLevel.BLOCKER)
    @Story("Valid Login")
    @Description("Enter valid admin credentials and verify the dashboard is displayed.")
    public void validCredentialsShouldLoginSuccessfully() {
        DashboardPage dashboard = loginPage.loginAs(config.username(), config.password());

        Assertions.assertThat(dashboard.isLoaded())
                .as("Dashboard should be visible after successful login")
                .isTrue();
    }

    @Test(
            description = "Invalid credentials should show an error message",
            retryAnalyzer = RetryAnalyzer.class
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Invalid Login")
    @Description("Enter incorrect credentials and verify the error banner appears.")
    public void invalidCredentialsShouldShowError() {
        loginPage.enterUsername("wrong.user@x.com")
                 .enterPassword("WrongPassword999!")
                 .clickLogin();

        Assertions.assertThat(loginPage.getErrorMessage())
                .as("Error message should be displayed for invalid credentials")
                .containsIgnoringCase("Invalid credentials");
    }

    @Test(
            description = "Empty credentials should not allow login",
            retryAnalyzer = RetryAnalyzer.class
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Empty Credentials")
    @Description("Submit empty credentials and verify required field messages.")
    public void emptyCredentialsShouldBlockLogin() {
        loginPage.enterUsername("")
                 .enterPassword("")
                 .clickLogin();

        Assertions.assertThat(loginPage.isLoginPageDisplayed())
                .as("Login page should still be displayed after empty submission")
                .isTrue();
    }
}


