package com.bjitgroup.pages;

import com.bjitgroup.actions.BrowserActions;
import com.bjitgroup.actions.InputActions;
import com.bjitgroup.context.PageManager;

import java.util.Properties;

import static com.bjitgroup.utils.PropertyReader.read;

/**
 * Signup / Login page for Automation Exercise.
 */
public final class LoginPage extends BasePage {

    private static final String PATH = "https://automationexercise.com/login";

    private final Properties loc = read("locators/login-page.properties");

    public LoginPage(
            BrowserActions browser,
            InputActions input,
            PageManager pages
    ) {
        super(browser, input, pages);
    }

    public LoginPage open() {
        browser.navigate(PATH);
        return waitUntilLoaded();
    }

    public LoginPage waitUntilLoaded() {
        browser.waitForVisible(loc.getProperty("emailInput"));
        browser.waitForVisible(loc.getProperty("signupNameInput"));
        return this;
    }

    public LoginPage enterEmail(String email) {
        input.fill(loc.getProperty("emailInput"), email);
        return this;
    }

    public LoginPage enterUsername(String username) {
        return enterEmail(username);
    }

    public LoginPage enterPassword(String password) {
        input.fill(loc.getProperty("passwordInput"), password);
        return this;
    }

    public LoginPage clickLogin() {
        input.click(loc.getProperty("loginButton"));
        return this;
    }

    public DashboardPage loginAs(String email, String password) {
        attemptLogin(email, password);
        return pages.dashboardPage();
    }

    public LoginPage attemptLogin(String email, String password) {
        return enterEmail(email)
                .enterPassword(password)
                .clickLogin();
    }

    public LoginPage enterSignupName(String name) {
        input.fill(loc.getProperty("signupNameInput"), name);
        return this;
    }

    public LoginPage enterSignupEmail(String email) {
        input.fill(loc.getProperty("signupEmailInput"), email);
        return this;
    }

    public SignupPage clickSignup() {
        input.click(loc.getProperty("signupButton"));
        return pages.signupPage().waitUntilLoaded();
    }

    public LoginPage clickSignupExpectingError() {
        input.click(loc.getProperty("signupButton"));
        return this;
    }

    public SignupPage startSignup(String name, String email) {
        return enterSignupName(name)
                .enterSignupEmail(email)
                .clickSignup();
    }

    public String getSignupErrorMessage() {
        String selector = loc.getProperty("signupErrorMessage");
        browser.waitForVisible(selector);
        return browser.textOf(selector);
    }

    public boolean isSignupFormVisible() {
        return browser.isVisible(loc.getProperty("signupFormHeading"));
    }

    public boolean isLoaded() {
        return browser.isVisible(loc.getProperty("loginFormHeading"));
    }

    public boolean isLoginPageDisplayed() {
        return isLoaded();
    }

    public String getErrorMessage() {
        String selector = loc.getProperty("errorMessage");
        browser.waitForVisible(selector);
        return browser.textOf(selector);
    }
}