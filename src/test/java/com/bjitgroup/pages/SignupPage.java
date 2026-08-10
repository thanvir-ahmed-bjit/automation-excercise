package com.bjitgroup.pages;

import com.bjitgroup.actions.BrowserActions;
import com.bjitgroup.actions.InputActions;
import com.bjitgroup.context.PageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.bjitgroup.utils.PropertyReader.read;

/**
 * Signup and Account Information page for Automation Exercise.
 *
 * <h3>Locators</h3>
 * All CSS/XPath selectors are externalised in
 * {@code src/test/resources/locators/signup-page.properties}.
 *
 * <h3>Fluent interface</h3>
 * Methods that stay on the same page return {@code this};
 * navigation methods return the target page object or other pages.
 *
 * <h3>Validation helpers</h3>
 * Methods with {@code is*} prefix query page state without waiting.
 * Methods with {@code has*} prefix check HTML5 constraint validation flags.
 * Methods with {@code get*} prefix extract text or attribute values.
 *
 * <p>Signup validation relies heavily on browser HTML5 constraint validation API.
 * See {@link #hasValidityFlag(String, String)},
 * {@link #isFieldValid(String)}, and
 * {@link #getValidationMessage(String)} for constraint testing utilities.</p>
 *
 * @see BasePage
 */
public final class SignupPage extends BasePage {

    private static final String HOME_PATH = "/";
    private static final String LOGIN_PATH = "/login";
    private static final String DEFAULT_DOB_DAY = "10";
    private static final String DEFAULT_DOB_MONTH = "5";
    private static final String DEFAULT_DOB_YEAR = "1995";
    private final Properties loc = read("locators/signup-page.properties");

    public SignupPage(
            BrowserActions browser,
            InputActions input,
            PageManager pages
    ) {
        super(browser, input, pages);
    }

    /**
     * Navigates to the home page and waits for the home logo to become visible.
     *
     * @return this signup page
     */
    public SignupPage openHomePage() {
        browser.navigate(HOME_PATH);
        browser.waitForVisible(s("homeLogo"));
        return this;
    }

    /**
     * Navigates directly to the Automation Exercise login/signup page.
     *
     * @return this signup page, with the signup name input visible
     */
    public SignupPage openAutomationExerciseLogin() {
        browser.navigate(LOGIN_PATH);
        browser.waitForVisible(s("signupNameInput"));
        return this;
    }

    /**
     * Checks whether the home page logo is currently visible.
     *
     * @return {@code true} if the logo is visible; otherwise {@code false}
     */
    public boolean isHomePageVisible() {
        return browser.isVisible(s("homeLogo"));
    }

    /**
     * Clicks the Signup/Login navigation link from the home page.
     *
     * @return this signup page
     */
    public SignupPage clickSignupLoginFromHome() {
        input.click(s("signupLoginLink"));
        browser.waitForVisible(s("signupNameInput"));
        return this;
    }

    public boolean isSignupFormVisible() {
        return browser.isVisible(s("signupHeading"))
                && browser.isVisible(s("signupNameInput"))
                && browser.isVisible(s("signupEmailInput"))
                && browser.isVisible(s("signupButton"))
                && browser.isEnabled(s("signupNameInput"))
                && browser.isEnabled(s("signupEmailInput"))
                && browser.isEnabled(s("signupButton"));
    }

    public SignupPage enterSignupName(String name) {
        input.fill(s("signupNameInput"), name);
        return this;
    }

    public SignupPage enterSignupEmail(String email) {
        input.fill(s("signupEmailInput"), email);
        return this;
    }

    public SignupPage clickSignup() {
        input.click(s("signupButton"));
        return this;
    }

    public SignupPage clickSignupExpectingError() {
        clickSignup();
        browser.waitForVisible(s("signupErrorMessage"));
        return this;
    }

    public SignupPage startSignup(String name, String email) {
        return enterSignupName(name)
                .enterSignupEmail(email)
                .clickSignup()
                .waitUntilLoaded();
    }

    public String getSignupErrorMessage() {
        return browser.textOf(s("signupErrorMessage"));
    }

    public boolean isSignupUrl() {
        return browser.currentUrl().contains("/login");
    }

    public boolean isSignupFieldValid(String fieldKey) {
        Object result = browser.locator(signupSelectorForKey(fieldKey)).evaluate("el => el.checkValidity()");
        return Boolean.TRUE.equals(result);
    }

    public boolean hasSignupValidityFlag(String fieldKey, String flag) {
        Object result = browser.locator(signupSelectorForKey(fieldKey))
                .evaluate("(el, f) => Boolean(el.validity && el.validity[f])", flag);
        return Boolean.TRUE.equals(result);
    }

    public String getSignupValidationMessage(String fieldKey) {
        Object message = browser.locator(signupSelectorForKey(fieldKey))
                .evaluate("el => el.validationMessage || ''");
        return message == null ? "" : String.valueOf(message);
    }

    public SignupPage waitUntilLoaded() {
        browser.waitForVisible(s("accountInfoHeading"));
        return this;
    }

    public boolean isAccountInfoVisible() {
        return browser.isVisible(s("accountInfoHeading"));
    }

    public SignupPage selectMrTitle() {
        input.click(s("titleMrRadio"));
        return this;
    }

    public SignupPage selectMrsTitle() {
        input.click(s("titleMrsRadio"));
        return this;
    }

    public boolean isMrTitleSelected() {
        return browser.isChecked(s("titleMrRadio"));
    }

    public boolean isMrsTitleSelected() {
        return browser.isChecked(s("titleMrsRadio"));
    }

    public boolean isNoTitleSelectedByDefault() {
        return !isMrTitleSelected() && !isMrsTitleSelected();
    }

    public SignupPage enterPassword(String password) {
        input.fill(s("passwordInput"), password);
        return this;
    }

    public boolean isPasswordMaskedField() {
        return "password".equalsIgnoreCase(browser.attributeOf(s("passwordInput"), "type"));
    }

    public SignupPage setBirthDate(String day, String month, String year) {
        input.selectByValue(s("daysSelect"), day);
        input.selectByValue(s("monthsSelect"), month);
        input.selectByValue(s("yearsSelect"), year);
        return this;
    }

    public boolean isBirthDateBlankByDefault() {
        return browser.inputValueOf(s("daysSelect")).isEmpty()
                && browser.inputValueOf(s("monthsSelect")).isEmpty()
                && browser.inputValueOf(s("yearsSelect")).isEmpty();
    }

    public List<String> getDayOptions() {
        return getSelectOptionValues(s("daysSelect"));
    }

    public List<String> getMonthOptions() {
        return getSelectOptionValues(s("monthsSelect"));
    }

    public List<String> getYearOptions() {
        return getSelectOptionValues(s("yearsSelect"));
    }

    public SignupPage checkNewsletter() {
        input.check(s("newsletterCheckbox"));
        return this;
    }

    public SignupPage checkSpecialOffers() {
        input.check(s("offersCheckbox"));
        return this;
    }

    public SignupPage enterFirstName(String firstName) {
        input.fill(s("firstNameInput"), firstName);
        return this;
    }

    public SignupPage enterLastName(String lastName) {
        input.fill(s("lastNameInput"), lastName);
        return this;
    }

    public SignupPage enterCompany(String company) {
        input.fill(s("companyInput"), company);
        return this;
    }

    public SignupPage enterAddress(String address) {
        input.fill(s("address1Input"), address);
        return this;
    }

    public SignupPage enterAddress2(String address2) {
        input.fill(s("address2Input"), address2);
        return this;
    }

    public SignupPage selectCountry(String country) {
        input.selectByLabel(s("countrySelect"), country);
        return this;
    }

    /**
     * Returns the currently selected country value from the country dropdown.
     *
     * @return the selected country name (e.g., "India", "United States")
     */
    public String selectedCountry() {
        return browser.inputValueOf(s("countrySelect"));
    }

    public List<String> getCountryOptions() {
        return getSelectOptionValues(s("countrySelect"));
    }

    public String getAccountNameValue() {
        return browser.inputValueOf(s("accountNameInput"));
    }

    public String getAccountEmailValue() {
        return browser.inputValueOf(s("accountEmailInput"));
    }

    public boolean isAccountEmailDisabled() {
        return browser.isDisabled(s("accountEmailInput"));
    }

    public boolean isAccountNameEditable() {
        return browser.isEditable(s("accountNameInput"));
    }

    public String tryChangingAccountEmailAndRead() {
        if (!isAccountEmailDisabled()) {
            input.fill(s("accountEmailInput"), "edited@example.com");
        }
        return getAccountEmailValue();
    }

    public SignupPage enterState(String state) {
        input.fill(s("stateInput"), state);
        return this;
    }

    public SignupPage enterCity(String city) {
        input.fill(s("cityInput"), city);
        return this;
    }

    public SignupPage enterZipcode(String zipcode) {
        input.fill(s("zipcodeInput"), zipcode);
        return this;
    }

    public SignupPage enterMobileNumber(String mobileNumber) {
        input.fill(s("mobileNumberInput"), mobileNumber);
        return this;
    }

    public SignupPage clickCreateAccount() {
        input.click(s("createAccountButton"));
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
                .setBirthDate(DEFAULT_DOB_DAY, DEFAULT_DOB_MONTH, DEFAULT_DOB_YEAR)
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

    public SignupPage completeMandatoryOnlySignup(
            String password,
            String firstName,
            String lastName,
            String address,
            String state,
            String city,
            String zipcode,
            String mobileNumber
    ) {
        return enterPassword(password)
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterAddress(address)
                .enterState(state)
                .enterCity(city)
                .enterZipcode(zipcode)
                .enterMobileNumber(mobileNumber)
                .clickCreateAccount();
    }

    public SignupPage completeAllOptionalAndMandatorySignup(
            String password,
            String firstName,
            String lastName,
            String company,
            String address1,
            String address2,
            String country,
            String state,
            String city,
            String zipcode,
            String mobileNumber
    ) {
        return selectMrTitle()
                .enterPassword(password)
                .setBirthDate(DEFAULT_DOB_DAY, DEFAULT_DOB_MONTH, DEFAULT_DOB_YEAR)
                .checkNewsletter()
                .checkSpecialOffers()
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterCompany(company)
                .enterAddress(address1)
                .enterAddress2(address2)
                .selectCountry(country)
                .enterState(state)
                .enterCity(city)
                .enterZipcode(zipcode)
                .enterMobileNumber(mobileNumber)
                .clickCreateAccount();
    }

    public String getAccountCreatedMessage() {
        browser.waitForVisible(s("accountCreatedHeading"));
        return browser.textOf(s("accountCreatedHeading"));
    }

    public SignupPage clickContinue() {
        input.click(s("continueButton"));
        return this;
    }

    public boolean isLoggedInLabelVisible() {
        return browser.isVisible(s("loggedInAsLabel"));
    }

    public String getLoggedInLabelText() {
        return browser.textOf(s("loggedInAsLabel"));
    }

    public SignupPage logoutToSignupForm() {
        input.click(s("logoutLink"));
        browser.waitForVisible(s("signupNameInput"));
        return this;
    }

    public SignupPage loginWithExistingAccount(String email, String password) {
        input.fill(s("loginEmailInput"), email);
        input.fill(s("loginPasswordInput"), password);
        input.click(s("loginButton"));
        browser.waitForVisible(s("loggedInAsLabel"));
        return this;
    }

    public SignupPage deleteCurrentAccount() {
        input.click(s("deleteAccountLink"));
        browser.waitForVisible(s("accountDeletedHeading"));
        input.click(s("continueButton"));
        return this;
    }

    // ---------------------------------------------------------------
    // HTML5 constraint validation
    //
    // Password, First name, Last name, Address, Country, State, City, Zipcode
    // and Mobile Number are all declared "required", so leaving any one empty
    // makes the browser block submission without rendering a page error.
    // ---------------------------------------------------------------

    /** Clears the field named by the locator key. */
    public SignupPage clearField(String locatorKey) {
        input.fill(selectorForKey(locatorKey), "");
        return this;
    }

    /**
     * Checks whether the specified form field passes HTML5 constraint validation.
     *
     * <p>This method calls {@code HTMLInputElement.checkValidity()} on the field.
     * It returns immediately without waiting for the field state to change.</p>
     *
     * @param locatorKey the field identifier in signup-page.properties
     * @return {@code true} if the field passes validation; {@code false} otherwise
     * @throws IllegalArgumentException if the locator key is not supported
     */
    public boolean isFieldValid(String locatorKey) {
        Object result = browser.locator(selectorForKey(locatorKey)).evaluate("el => el.checkValidity()");
        return Boolean.TRUE.equals(result);
    }

    /**
     * Checks a specific HTML5 {@code ValidityState} flag for the field.
     *
     * <p>Common flags: {@code valueMissing}, {@code typeMismatch}, {@code tooShort},
     * {@code tooLong}, {@code patternMismatch}, {@code badInput}.</p>
     *
     * @param locatorKey the field identifier in signup-page.properties
     * @param flag the validity flag name (e.g., "valueMissing")
     * @return {@code true} if the flag is set; otherwise {@code false}
     */
    public boolean hasValidityFlag(String locatorKey, String flag) {
        Object result = browser.locator(selectorForKey(locatorKey))
                .evaluate("(el, f) => Boolean(el.validity && el.validity[f])", flag);
        return Boolean.TRUE.equals(result);
    }

    /**
     * Returns the browser's native HTML5 validation message for a field.
     *
     * <p>Useful for test diagnostics. Returns empty string if no message is available.</p>
     *
     * @param locatorKey the field identifier in signup-page.properties
     * @return the validation message, or empty string if not applicable
     */
    public String getValidationMessage(String locatorKey) {
        Object message = browser.locator(selectorForKey(locatorKey))
                .evaluate("el => el.validationMessage || ''");
        return message == null ? "" : String.valueOf(message);
    }

    /** Returns {@code true} when the 'Account Created!' confirmation is shown. */
    public boolean isAccountCreatedVisible() {
        return browser.isVisible(s("accountCreatedHeading"));
    }

    private String selectorForKey(String locatorKey) {
        return switch (locatorKey) {
            case "passwordInput" -> s("passwordInput");
            case "firstNameInput" -> s("firstNameInput");
            case "lastNameInput" -> s("lastNameInput");
            case "address1Input" -> s("address1Input");
            case "stateInput" -> s("stateInput");
            case "cityInput" -> s("cityInput");
            case "zipcodeInput" -> s("zipcodeInput");
            case "mobileNumberInput" -> s("mobileNumberInput");
            default -> throw new IllegalArgumentException("Unsupported locator key: " + locatorKey);
        };
    }

    private String signupSelectorForKey(String fieldKey) {
        return switch (fieldKey) {
            case "signupNameInput" -> s("signupNameInput");
            case "signupEmailInput" -> s("signupEmailInput");
            default -> throw new IllegalArgumentException("Unsupported signup field key: " + fieldKey);
        };
    }

    private String s(String key) {
        String selector = loc.getProperty(key);
        if (selector == null || selector.isBlank()) {
            throw new IllegalStateException("Missing locator key in signup-page.properties: " + key);
        }
        return selector;
    }

    @SuppressWarnings("unchecked")
    private List<String> getSelectOptionValues(String selector) {
        Object raw = browser.locator(selector)
                .evaluate("el => Array.from(el.options).map(o => (o.textContent || '').trim())");
        List<String> values = new ArrayList<>();
        if (raw instanceof List) {
            for (Object item : (List<Object>) raw) {
                values.add(String.valueOf(item));
            }
        }
        return values;
    }
}