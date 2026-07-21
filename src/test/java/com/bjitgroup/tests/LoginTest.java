package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import com.bjitgroup.listeners.RetryAnalyzer;
import com.bjitgroup.pages.DashboardPage;
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
            description = "Login User with correct email and password",
            retryAnalyzer = RetryAnalyzer.class
    )
    @Severity(SeverityLevel.BLOCKER)
    @Story("Valid Login - AutomationExercise")
    @Description("Create account, navigate to home, login with correct credentials, verify logged in, delete account.")
    public void loginWithCorrectCredentialsShouldSucceed() {
        String[] account = AccountHelper.createAccount(page());
        String email = account[1];
        String password = account[2];

        page().navigate("http://automationexercise.com");
        Assertions.assertThat(page().locator("img[alt='Website for automation practice']").isVisible())
            .as("Home page should be visible").isTrue();

        page().locator("a[href='/login']").first().click();
        Assertions.assertThat(pages().loginPage().waitUntilLoaded().isLoginPageDisplayed())
            .as("Login to your account should be visible").isTrue();

        pages().loginPage().attemptLogin(email, password);
        page().waitForSelector("//a[contains(normalize-space(),'Logged in as')]");
        Assertions.assertThat(page().locator("//a[contains(normalize-space(),'Logged in as')]").isVisible())
            .as("User should be logged in").isTrue();

        page().locator("a[href='/delete_account']").first().click();
        page().waitForSelector("//b[normalize-space()='Account Deleted!']");
        Assertions.assertThat(page().locator("//b[normalize-space()='Account Deleted!']").innerText())
            .isEqualTo("ACCOUNT DELETED!");
    }

    @Test(
            priority = 2,
            description = "Login User with incorrect email and password",
            retryAnalyzer = RetryAnalyzer.class
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Invalid Login")
    @Description("Launch app, open signup/login, submit invalid credentials, and verify incorrect-login error.")
    public void invalidCredentialsShouldShowError() {
        LoginPage loginPage = pages().loginPage();

        Allure.step("Navigate to home page", () ->
                page().navigate("http://automationexercise.com"));
        Allure.step("Verify home page is visible", () ->
                Assertions.assertThat(page().locator("img[alt='Website for automation practice']").isVisible())
                        .as("Home page logo should be visible")
                        .isTrue());
        Allure.step("Click Signup / Login", () ->
                page().locator("a[href='/login']").first().click());
        Allure.step("Verify Login to your account is visible", () ->
                Assertions.assertThat(loginPage.waitUntilLoaded().isLoginPageDisplayed())
                        .as("Login form should be visible")
                        .isTrue());
        Allure.step("Enter incorrect email and password", () ->
                loginPage.attemptLogin("wrong.user@x.com", "WrongPassword999!"));
        Allure.step("Verify error message is displayed", () ->
                Assertions.assertThat(loginPage.getErrorMessage())
                        .as("Incorrect email/password message should be shown")
                        .contains("Your email or password is incorrect!"));
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
