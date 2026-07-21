package com.bjitgroup.pages;

import com.bjitgroup.actions.BrowserActions;
import com.bjitgroup.actions.InputActions;
import com.bjitgroup.context.PageManager;

import java.util.Properties;

import static com.bjitgroup.utils.PropertyReader.read;

/**
 * Account information page for completing Automation Exercise signup.
 */
public final class SignupPage extends BasePage {

    private final Properties loc = read("locators/signup-page.properties");

    public SignupPage(
            BrowserActions browser,
            InputActions input,
            PageManager pages
    ) {
        super(browser, input, pages);
    }

    public SignupPage waitUntilLoaded() {
        browser.waitForVisible(loc.getProperty("accountInfoHeading"));
        return this;
    }

    public SignupPage selectMrTitle() {
        input.click(loc.getProperty("titleMrRadio"));
        return this;
    }

    public SignupPage enterPassword(String password) {
        input.fill(loc.getProperty("passwordInput"), password);
        return this;
    }

    public SignupPage setBirthDate(String day, String month, String year) {
        input.selectByValue(loc.getProperty("daysSelect"), day);
        input.selectByValue(loc.getProperty("monthsSelect"), month);
        input.selectByValue(loc.getProperty("yearsSelect"), year);
        return this;
    }

    public SignupPage enterFirstName(String firstName) {
        input.fill(loc.getProperty("firstNameInput"), firstName);
        return this;
    }

    public SignupPage enterLastName(String lastName) {
        input.fill(loc.getProperty("lastNameInput"), lastName);
        return this;
    }

    public SignupPage enterAddress(String address) {
        input.fill(loc.getProperty("address1Input"), address);
        return this;
    }

    public SignupPage selectCountry(String country) {
        input.selectByLabel(loc.getProperty("countrySelect"), country);
        return this;
    }

    public SignupPage enterState(String state) {
        input.fill(loc.getProperty("stateInput"), state);
        return this;
    }

    public SignupPage enterCity(String city) {
        input.fill(loc.getProperty("cityInput"), city);
        return this;
    }

    public SignupPage enterZipcode(String zipcode) {
        input.fill(loc.getProperty("zipcodeInput"), zipcode);
        return this;
    }

    public SignupPage enterMobileNumber(String mobileNumber) {
        input.fill(loc.getProperty("mobileNumberInput"), mobileNumber);
        return this;
    }

    public SignupPage clickCreateAccount() {
        input.click(loc.getProperty("createAccountButton"));
        return this;
    }

    public SignupPage completeSignup(
            String password,
            String firstName,
            String lastName,
            String address,
            String country,
            String state,
            String city,
            String zipcode,
            String mobileNumber
    ) {
        return selectMrTitle()
                .enterPassword(password)
                .setBirthDate("10", "5", "1995")
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterAddress(address)
                .selectCountry(country)
                .enterState(state)
                .enterCity(city)
                .enterZipcode(zipcode)
                .enterMobileNumber(mobileNumber)
                .clickCreateAccount();
    }

    public String getAccountCreatedMessage() {
        String selector = loc.getProperty("accountCreatedHeading");
        browser.waitForVisible(selector);
        return browser.textOf(selector);
    }

    public SignupPage clickContinue() {
        input.click(loc.getProperty("continueButton"));
        return this;
    }

    public boolean isLoggedInLabelVisible() {
        return browser.isVisible(loc.getProperty("loggedInAsLabel"));
    }

    public LoginPage logoutToLoginPage() {
        input.click(loc.getProperty("logoutLink"));
        return pages.loginPage().waitUntilLoaded();
    }
}
