package com.bjitgroup.pages;

import com.bjitgroup.actions.BrowserActions;
import com.bjitgroup.actions.InputActions;
import com.bjitgroup.context.PageManager;

import java.util.Properties;

import static com.bjitgroup.utils.PropertyReader.read;

/**
 * Signup / Login page for Automation Exercise.
 */
public final class LoginPage {

    private static final String PATH = "https://automationexercise.com/login";

    private final BrowserActions browser;
    private final InputActions input;
    private final PageManager pages;

    private final Properties loc = read("locators/login-page.properties");

    public LoginPage(
            BrowserActions browser,
            InputActions input,
            PageManager pages
    ) {
        this.browser = browser;
        this.input = input;
        this.pages = pages;
    }

    public LoginPage open() {
        browser.navigate(PATH);
        return waitUntilLoaded();
    }

    public LoginPage waitUntilLoaded() {
        browser.waitForVisible(locator("emailInput"));
        browser.waitForVisible(locator("signupNameInput"));
        return this;
    }

    public LoginPage enterEmail(String email) {
        input.fill(locator("emailInput"), email);
        return this;
    }

    public LoginPage enterUsername(String username) {
        input.fill(locator("usernameInput"), username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        input.fill(locator("passwordInput"), password);
        return this;
    }

    public LoginPage clickLogin() {
        input.click(locator("loginButton"));
        return this;
    }

    public DashboardPage loginAs(String email, String password) {
        attemptLogin(email, password);
        return pages.dashboardPage();
    }

    public LoginPage attemptLogin(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        return clickLogin();
    }

    public LoginPage enterSignupName(String name) {
        input.fill(locator("signupNameInput"), name);
        return this;
    }

    public LoginPage enterSignupEmail(String email) {
        input.fill(locator("signupEmailInput"), email);
        return this;
    }

    public SignupPage clickSignup() {
        input.click(locator("signupButton"));
        return pages.signupPage().waitUntilLoaded();
    }

    public LoginPage clickSignupExpectingError() {
        input.click(locator("signupButton"));
        return this;
    }

    public SignupPage startSignup(String name, String email) {
        enterSignupName(name);
        enterSignupEmail(email);
        return clickSignup();
    }

    public String getSignupErrorMessage() {
        String selector = locator("signupErrorMessage");
        browser.waitForVisible(selector);
        return browser.textOf(selector);
    }

    public boolean isSignupFormVisible() {
        return browser.isVisible(locator("signupFormHeading"));
    }

    public boolean isLoaded() {
        return browser.isVisible(locator("loginFormHeading"));
    }

    public boolean isLoginPageDisplayed() {
        return isLoaded();
    }

    public String getErrorMessage() {
        String selector = locator("errorMessage");
        browser.waitForVisible(selector);
        return browser.textOf(selector);
    }

    // ---------------------------------------------------------------
    // HTML5 constraint validation
    //
    // Both login inputs are declared "required" and the email input is
    // type="email", so an empty or malformed entry is rejected by the browser
    // before any request is sent. There is no page-rendered error to locate in
    // that case, so these accessors expose the Constraint Validation API.
    // ---------------------------------------------------------------

    /** Returns {@code true} when the field named by the locator key passes HTML5 validation. */
    public boolean isFieldValid(String locatorKey) {
        return browser.isFieldValid(locator(locatorKey));
    }

    /** Returns a single {@code ValidityState} flag, e.g. {@code valueMissing} or {@code typeMismatch}. */
    public boolean hasValidityFlag(String locatorKey, String flag) {
        return browser.hasValidityFlag(locator(locatorKey), flag);
    }

    /** Returns the browser's native validation message for diagnostics. */
    public String getValidationMessage(String locatorKey) {
        return browser.getValidationMessage(locator(locatorKey));
    }

    private String locator(String key) {
        String selector = loc.getProperty(key);
        if (selector == null || selector.isBlank()) {
            throw new IllegalArgumentException("Missing locator key in login-page.properties: " + key);
        }
        return selector;
    }
}