package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import com.bjitgroup.listeners.RetryAnalyzer;
import com.bjitgroup.pages.LoginPage;
import com.bjitgroup.utils.AccountHelper;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Feature("Authentication")
public class LoginTest extends BaseTest {

    // Test Case 2: Login User with correct email and password
    @Test(
            description = "Login User with correct email and password",
            retryAnalyzer = RetryAnalyzer.class
    )
    @Severity(SeverityLevel.BLOCKER)
    @Story("Valid Login - AutomationExercise")
    @Description("Create account, navigate to home, login with correct credentials, verify logged in, delete account.")
    public void loginWithCorrectCredentialsShouldSucceed() {
        // Create account — user is now logged in
        String[] account = AccountHelper.createAccount(page());
        String email = account[1];
        String password = account[2];

        // Logout first so we can test the login flow
        pages().homePage().clickLogout();
        pages().loginPage().waitUntilLoaded();

        // Navigate to home, verify visible
        pages().homePage().open();
        Assertions.assertThat(pages().homePage().isLoaded())
                .as("Home page should be visible").isTrue();

        // Click Signup / Login, verify form visible
        pages().homePage().clickSignupLogin();
        Assertions.assertThat(pages().loginPage().isLoginPageDisplayed())
                .as("Login to your account should be visible").isTrue();

        // Login with correct credentials
        pages().loginPage().attemptLogin(email, password);
        pages().homePage().waitUntilLoaded();
        Assertions.assertThat(pages().homePage().isLoggedIn())
                .as("User should be logged in").isTrue();

        // Delete account and verify
        pages().homePage().clickDeleteAccount();
        Assertions.assertThat(pages().homePage().getAccountDeletedMessage())
                .isEqualTo("ACCOUNT DELETED!");
    }

    // Test Case 3: Login User with incorrect email and password
    @Test(
            priority = 2,
            description = "Login User with incorrect email and password",
            retryAnalyzer = RetryAnalyzer.class
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Invalid Login")
    @Description("Launch app, open signup/login, submit invalid credentials, and verify incorrect-login error.")
    public void invalidCredentialsShouldShowError() {
        Allure.step("Navigate to home page", () -> pages().homePage().open());
        Allure.step("Verify home page is visible", () ->
                Assertions.assertThat(pages().homePage().isLoaded())
                        .as("Home page logo should be visible").isTrue());
        Allure.step("Click Signup / Login", () -> pages().homePage().clickSignupLogin());
        Allure.step("Verify Login to your account is visible", () ->
                Assertions.assertThat(pages().loginPage().isLoginPageDisplayed())
                        .as("Login form should be visible").isTrue());
        Allure.step("Enter incorrect email and password", () ->
                pages().loginPage().attemptLogin("wrong.user@x.com", "WrongPassword999!"));
        Allure.step("Verify error message is displayed", () ->
                Assertions.assertThat(pages().loginPage().getErrorMessage())
                        .as("Incorrect email/password message should be shown")
                        .contains("Your email or password is incorrect!"));
    }

    // Test Case (sanity): Empty credentials should not allow login
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