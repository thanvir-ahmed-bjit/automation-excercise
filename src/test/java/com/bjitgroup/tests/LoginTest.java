package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import com.bjitgroup.listeners.RetryAnalyzer;
import com.bjitgroup.pages.DashboardPage;
import com.bjitgroup.pages.LoginPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/**
 * Tests covering the Login feature.
 * Target: OrangeHRM demo
 * <a href="https://opensource-demo.orangehrmlive.com">OrangeHRM Demo</a>.
 *
 * <p>Listeners are declared once on {@link com.bjitgroup.base.BaseTest}.</p>
 */
@Feature("Authentication")
public class LoginTest extends BaseTest {

    @Test(
            priority = 1,
            description = "Valid credentials should navigate to the dashboard",
            retryAnalyzer = RetryAnalyzer.class
    )
    @Severity(SeverityLevel.BLOCKER)
    @Story("Valid Login")
    @Description("Enter valid admin credentials and verify the dashboard is displayed.")
    public void validCredentialsShouldLoginSuccessfully() {

        LoginPage loginPage = pages().loginPage();

        Allure.step("Open Login page", loginPage::open);
        DashboardPage dashboard = Allure.step(
                "Login with valid credentials",
                () -> loginPage.loginAs(
                        context().config().username(),
                        context().config().password()
                )
        );
        Allure.step("Verify Dashboard page is loaded", () ->
                Assertions.assertThat(dashboard.isLoaded())
                        .as("Dashboard should be visible after successful login")
                        .isTrue());
    }

    @Test(
            priority = 2,
            description = "Invalid credentials should show an error message",
            retryAnalyzer = RetryAnalyzer.class
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Invalid Login")
    @Description("Enter incorrect credentials and verify the error banner appears.")
    public void invalidCredentialsShouldShowError() {

        LoginPage loginPage = pages().loginPage();

        Allure.step("Open Login page", loginPage::open);
        Allure.step("Enter username", () ->
                loginPage.enterUsername("wrong.user@x.com"));
        Allure.step("Enter password", () ->
                loginPage.enterPassword("WrongPassword999!"));
        Allure.step("Click Login button", loginPage::clickLogin);
        Allure.step("Verify error message is displayed", () ->
                Assertions.assertThat(loginPage.getErrorMessage())
                        .as("Error message should be displayed for invalid credentials")
                        .containsIgnoringCase("Invalid credentials"));
    }

    @Test(
            priority = 3,
            description = "Empty credentials should not allow login",
            retryAnalyzer = RetryAnalyzer.class
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Empty Credentials")
    @Description("Submit empty credentials and verify required field messages.")
    public void emptyCredentialsShouldBlockLogin() {

        LoginPage loginPage = pages().loginPage();

        Allure.step("Open Login page", loginPage::open);
        Allure.step("Leave username empty", () -> loginPage.enterUsername(""));
        Allure.step("Leave password empty", () -> loginPage.enterPassword(""));
        Allure.step("Click Login button", loginPage::clickLogin);
        Allure.step("Verify Login page is still displayed", () ->
                Assertions.assertThat(loginPage.isLoginPageDisplayed())
                        .as("Login page should still be displayed after empty submission")
                        .isTrue());
    }
}
