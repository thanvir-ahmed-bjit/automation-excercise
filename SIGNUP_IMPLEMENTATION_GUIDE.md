# Implementation Guide: Aligning SignupTest with LoginTest Style

## 🎯 Overview
This guide provides exact code changes needed to make SignupTest/SignupPage consistent with LoginTest/LoginPage.

---

## STEP 1: Add SignupPage to PageManager

### File: `src/test/java/com/bjitgroup/context/PageManager.java`

**Current:**
```java
public final class PageManager {

    private final BrowserActions browserActions;
    private final InputActions inputActions;

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private AdminPage adminPage;

    // ... constructor and methods ...

    public AdminPage adminPage() {
        if (adminPage == null) {
            adminPage = new AdminPage(browserActions, inputActions, this);
        }
        return adminPage;
    }
}
```

**After (ADD):**
```java
public final class PageManager {

    private final BrowserActions browserActions;
    private final InputActions inputActions;

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private AdminPage adminPage;
    private SignupPage signupPage;  // ✅ ADD THIS

    // ... existing constructor and methods ...

    public AdminPage adminPage() {
        if (adminPage == null) {
            adminPage = new AdminPage(browserActions, inputActions, this);
        }
        return adminPage;
    }

    // ✅ ADD THIS NEW METHOD
    public SignupPage signupPage() {
        if (signupPage == null) {
            signupPage = new SignupPage(browserActions, inputActions, this);
        }
        return signupPage;
    }
}
```

**Also add import:**
```java
import com.bjitgroup.pages.SignupPage;  // ADD THIS
```

---

## STEP 2: Update SignupTest Method Naming

### File: `src/test/java/com/bjitgroup/tests/SignupTest.java`

**Current:**
```java
public void testCase01_registerUser() { }
public void testCase02_verifyNewUserSignupSectionDisplayed() { }
public void testCase03_signupWithEmptyNameAndEmail() { }
public void testCase04_signupWithInvalidEmailFormat() { }
public void testCase05_verifyNameAndEmailCarryOverAndEmailDisabled() { }
public void testCase06_createAccountWithMandatoryFieldsOnly() { }
public void testCase07_createAccountWithAllOptionalFields() { }
public void testCase08_createAccountBlockedWhenMandatoryFieldEmpty(String fieldName, String fieldKey) { }
public void testCase09_verifyTitleRadiosOptionalAndMutuallyExclusive() { }
public void testCase10_verifyDobDropdownsOptionalAndContainValidRanges() { }
public void testCase11_verifyCountryDropdownDefaultsAndOptions() { }
public void testCase12_verifyPasswordFieldMasked() { }
public void testCase15_registerUserWithExistingEmail() { }
public void testCase16_verifyNewlyRegisteredUserCanLoginWithSameCredentials() { }
```

**After:**
```java
@Test(priority = 1, description = "New user should be able to complete signup successfully")
@Severity(SeverityLevel.BLOCKER)
@Story("Signup")
@Description("Open login/signup page, register a new user, and verify account is created and logged in.")
public void newUserShouldCompleteSignupSuccessfully() { }

@Test(priority = 2, description = "Verify New User Signup section is displayed on Signup/Login page")
@Severity(SeverityLevel.NORMAL)
@Story("Signup")
@Description("Validate 'New User Signup!' entry section and controls.")
public void signupFormShouldBeVisibleOnSignupPage() { }

@Test(priority = 3, description = "Signup with empty name and email should be blocked")
@Severity(SeverityLevel.NORMAL)
@Story("Signup")
@Description("Leave signup name/email empty and verify HTML5 required validation blocks submission.")
public void signupWithEmptyNameShouldBlockSubmission() { }

@Test(priority = 4, description = "Signup with invalid email format should be blocked")
@Severity(SeverityLevel.NORMAL)
@Story("Signup")
@Description("Enter invalid signup email and verify type mismatch validation blocks account-info navigation.")
public void signupWithInvalidEmailShouldShowBrowserValidation() { }

@Test(priority = 5, description = "Verify signup name/email carry over and account email is not editable")
@Severity(SeverityLevel.NORMAL)
@Story("Signup")
@Description("Verify name/email prefill behaviour on account-info page.")
public void nameAndEmailShouldCarryOverAndEmailShouldBeDisabled() { }

@Test(priority = 6, description = "Create account with mandatory fields only")
@Severity(SeverityLevel.CRITICAL)
@Story("Signup")
@Description("Verify account creation works without optional fields.")
public void accountCreationWithMandatoryFieldsOnlyShouldSucceed() { }

@Test(priority = 7, description = "Create account with optional fields populated")
@Severity(SeverityLevel.CRITICAL)
@Story("Signup")
@Description("Full-field positive path with optional fields and opt-ins.")
public void accountCreationWithAllFieldsShouldSucceed() { }

@Test(priority = 8, description = "Create Account should be blocked when a mandatory field is empty",
      dataProvider = "mandatorySignupFields", dataProviderClass = SignupDataProvider.class,
      retryAnalyzer = com.bjitgroup.listeners.RetryAnalyzer.class)
@Severity(SeverityLevel.NORMAL)
@Story("Signup - client-side validation")
@Description("Fill every mandatory field, clear one, submit, and verify the browser blocks account creation.")
public void emptyMandatoryFieldShouldBlockAccountCreation(String fieldName, String fieldKey) { }

@Test(priority = 9, description = "Title radio buttons are optional and mutually exclusive")
@Severity(SeverityLevel.NORMAL)
@Story("Signup")
@Description("Validate title radio behavior and that no title still allows successful registration.")
public void titleRadiosShouldBeOptionalAndMutuallyExclusive() { }

@Test(priority = 10, description = "DOB dropdowns optional with valid ranges")
@Severity(SeverityLevel.NORMAL)
@Story("Signup")
@Description("Verify day/month/year ranges and that signup can succeed without DOB.")
public void dobDropdownsShouldBeOptionalWithValidRanges() { }

@Test(priority = 11, description = "Country dropdown defaults and supported options")
@Severity(SeverityLevel.NORMAL)
@Story("Signup")
@Description("Verify country default and list values.")
public void countryDropdownShouldDefaultToIndiaWithValidOptions() { }

@Test(priority = 12, description = "Password input should be masked")
@Severity(SeverityLevel.NORMAL)
@Story("Signup")
@Description("Verify account password field type is password.")
public void passwordFieldShouldBeMasked() { }

@Test(priority = 13, description = "Register User with existing email")
@Severity(SeverityLevel.CRITICAL)
@Story("Signup")
@Description("Submit an already-registered email and verify duplicate-email error.")
public void duplicateEmailShouldShowErrorMessage() { }

@Test(priority = 14, description = "Newly registered user can login with same credentials")
@Severity(SeverityLevel.CRITICAL)
@Story("Signup/Login")
@Description("Create account, logout, login using same credentials, then cleanup.")
public void newlyRegisteredUserCanLoginWithSameCredentials() { }
```

---

## STEP 3: Remove Local signupPage() Helper & Add Allure.step()

### File: `src/test/java/com/bjitgroup/tests/SignupTest.java`

**REMOVE the entire helper method:**
```java
// ❌ DELETE THIS
private SignupPage signupPage() {
    BrowserActions browser = new BrowserActions(page(), context().config().timeoutMs());
    InputActions input = new InputActions(page());
    PageManager manager = new PageManager(page(), context().config().timeoutMs());
    return new SignupPage(browser, input, manager);
}
```

**ADD this import:**
```java
import io.qameta.allure.Allure;
```

**Transform testCase01_registerUser() to use Allure.step():**

**BEFORE:**
```java
public void testCase01_registerUser() {
    String firstName = RandomDataUtils.firstName();
    String lastName = RandomDataUtils.lastName();
    String fullName = firstName + " " + lastName;
    String email = RandomDataUtils.email();
    String password = RandomDataUtils.password();

    SignupPage signupPage = signupPage()
            .openHomePage();

    Assertions.assertThat(signupPage.isHomePageVisible())
            .as("Home page should be visible successfully")
            .isTrue();

    signupPage.clickSignupLoginFromHome();
    Assertions.assertThat(signupPage.isSignupFormVisible())
            .as("'New User Signup!' section should be visible")
            .isTrue();

    signupPage.startSignup(fullName, email)
            .completeSignup(
            password,
            firstName,
            lastName,
            RandomDataUtils.address(),
            "India",
            "Dhaka",
            "Dhaka",
            "1207",
            RandomDataUtils.phone()
    );

    Assertions.assertThat(signupPage.getAccountCreatedMessage())
            .as("Account should be created after submitting valid signup details")
            .isEqualToIgnoringCase("Account Created!");

    signupPage.clickContinue();

    Assertions.assertThat(signupPage.isLoggedInLabelVisible())
            .as("User should be logged in after finishing signup flow")
            .isTrue();

    signupPage.deleteCurrentAccount();
}
```

**AFTER:**
```java
public void newUserShouldCompleteSignupSuccessfully() {
    String firstName = RandomDataUtils.firstName();
    String lastName = RandomDataUtils.lastName();
    String fullName = firstName + " " + lastName;
    String email = RandomDataUtils.email();
    String password = RandomDataUtils.password();

    SignupPage signupPage = pages().signupPage();  // ✅ USE pages()

    Allure.step("Navigate to home page", signupPage::openHomePage);
    
    Allure.step("Verify home page is visible", () ->
            Assertions.assertThat(signupPage.isHomePageVisible())
                    .as("Home page should be visible successfully")
                    .isTrue());

    Allure.step("Click Signup/Login link", signupPage::clickSignupLoginFromHome);
    
    Allure.step("Verify signup form is visible", () ->
            Assertions.assertThat(signupPage.isSignupFormVisible())
                    .as("'New User Signup!' section should be visible")
                    .isTrue());

    Allure.step("Start signup with name and email", () ->
            signupPage.startSignup(fullName, email));
    
    Allure.step("Complete signup with account details", () ->
            signupPage.completeSignup(
                    password,
                    firstName,
                    lastName,
                    RandomDataUtils.address(),
                    "India",
                    "Dhaka",
                    "Dhaka",
                    "1207",
                    RandomDataUtils.phone()
            ));

    Allure.step("Verify account created message", () ->
            Assertions.assertThat(signupPage.getAccountCreatedMessage())
                    .as("Account should be created after submitting valid signup details")
                    .isEqualToIgnoringCase("Account Created!"));

    Allure.step("Click continue button", signupPage::clickContinue);

    Allure.step("Verify user is logged in", () ->
            Assertions.assertThat(signupPage.isLoggedInLabelVisible())
                    .as("User should be logged in after finishing signup flow")
                    .isTrue());

    Allure.step("Delete account for cleanup", signupPage::deleteCurrentAccount);
}
```

---

## STEP 4: Update All Remaining Test Methods

### Example transformations for other methods:

**testCase02 → signupFormShouldBeVisibleOnSignupPage():**
```java
public void signupFormShouldBeVisibleOnSignupPage() {
    SignupPage signupPage = pages().signupPage();  // ✅ Changed

    Allure.step("Navigate to home page", signupPage::openHomePage);
    Allure.step("Click signup/login link", signupPage::clickSignupLoginFromHome);
    
    Allure.step("Verify home page is visible", () ->
            Assertions.assertThat(signupPage.isHomePageVisible())
                    .as("Home page should be visible successfully")
                    .isTrue());
    
    Allure.step("Verify signup form is visible", () ->
            Assertions.assertThat(signupPage.isSignupFormVisible())
                    .as("Name/email inputs and Signup button should be visible")
                    .isTrue());
}
```

**testCase03 → signupWithEmptyNameShouldBlockSubmission():**
```java
public void signupWithEmptyNameShouldBlockSubmission() {
    SignupPage signupPage = pages().signupPage();  // ✅ Changed
    String before = page().url();
    
    Allure.step("Navigate to login page", signupPage::openAutomationExerciseLogin);
    Allure.step("Click signup without entering name/email", signupPage::clickSignup);

    Allure.step("Verify URL did not change", () ->
            Assertions.assertThat(page().url())
                    .as("Form should not be submitted")
                    .isEqualTo(before));
    
    Allure.step("Verify name field has required validation", () ->
            Assertions.assertThat(signupPage.hasSignupValidityFlag("signupNameInput", "valueMissing"))
                    .as("Name field should report required validation")
                    .isTrue());
    
    Allure.step("Verify validation message is shown", () ->
            Assertions.assertThat(signupPage.getSignupValidationMessage("signupNameInput"))
                    .as("Browser should show validation message for missing required field")
                    .isNotBlank());
    
    Allure.step("Verify user is on signup page", () ->
            Assertions.assertThat(signupPage.isSignupUrl())
                    .as("User should remain on Signup/Login page")
                    .isTrue());
}
```

---

## STEP 5: Reorganize Imports

### File: `src/test/java/com/bjitgroup/tests/SignupTest.java`

**BEFORE:**
```java
import com.bjitgroup.actions.BrowserActions;
import com.bjitgroup.actions.InputActions;
import com.bjitgroup.base.BaseTest;
import com.bjitgroup.context.PageManager;
import com.bjitgroup.dataproviders.SignupDataProvider;
import com.bjitgroup.pages.SignupPage;
import com.bjitgroup.utils.RandomDataUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
```

**AFTER:**
```java
import com.bjitgroup.base.BaseTest;
import com.bjitgroup.dataproviders.SignupDataProvider;
import com.bjitgroup.pages.SignupPage;
import com.bjitgroup.utils.RandomDataUtils;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
```

**Changes:**
- ❌ REMOVE: `BrowserActions`, `InputActions`, `PageManager` imports (not needed anymore)
- ✅ ADD: `Allure` import
- ✅ REORDER: Keep framework packages first

---

## STEP 6: Add Javadoc to SignupPage

### File: `src/test/java/com/bjitgroup/pages/SignupPage.java`

**BEFORE:**
```java
/**
 * Account information page for completing Automation Exercise signup.
 */
public final class SignupPage extends BasePage {

    private static final String HOME_URL = "https://automationexercise.com/";
    private static final String LOGIN_URL = "https://automationexercise.com/login";
    private final Properties loc = read("locators/signup-page.properties");

    public SignupPage(
            BrowserActions browser,
            InputActions input,
            PageManager pages
    ) {
        super(browser, input, pages);
    }

    public SignupPage openHomePage() {
        browser.navigate(HOME_URL);
        browser.waitForVisible(s("homeLogo"));
        return this;
    }
```

**AFTER:**
```java
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

    private static final String HOME_URL = "https://automationexercise.com/";
    private static final String LOGIN_URL = "https://automationexercise.com/login";
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
        browser.navigate(HOME_URL);
        browser.waitForVisible(s("homeLogo"));
        return this;
    }
```

**Add method-level Javadoc to key methods:**

```java
/**
 * Navigates directly to the Automation Exercise login/signup page.
 *
 * @return this signup page, with the signup name input visible
 */
public SignupPage openAutomationExerciseLogin() { }

/**
 * Checks whether the home page logo is currently visible.
 *
 * @return {@code true} if the logo is visible; otherwise {@code false}
 */
public boolean isHomePageVisible() { }

/**
 * Clicks the Signup/Login navigation link from the home page.
 *
 * @return this signup page
 */
public SignupPage clickSignupLoginFromHome() { }

/**
 * Returns the currently selected country value from the country dropdown.
 *
 * @return the selected country name (e.g., "India", "United States")
 */
public String selectedCountry() { }

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
public boolean isFieldValid(String locatorKey) { }

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
public boolean hasValidityFlag(String locatorKey, String flag) { }

/**
 * Returns the browser's native HTML5 validation message for a field.
 *
 * <p>Useful for test diagnostics. Returns empty string if no message is available.</p>
 *
 * @param locatorKey the field identifier in signup-page.properties
 * @return the validation message, or empty string if not applicable
 */
public String getValidationMessage(String locatorKey) { }
```

---

## STEP 7: Constants for Hardcoded Values

### In `SignupTest.java` or create a test constants file

**Extract magic strings:**

**BEFORE:**
```java
.selectCountry("India")
.enterState("Dhaka")
.enterCity("Dhaka")
.enterZipcode("1207")

.setBirthDate("10", "5", "1995")

List<String> expectedCountries = Arrays.asList(
        "India",
        "United States",
        "Canada",
        "Australia",
        "Israel",
        "New Zealand",
        "Singapore"
);
```

**AFTER:**
```java
private static final String DEFAULT_COUNTRY = "India";
private static final String DEFAULT_STATE = "Dhaka";
private static final String DEFAULT_CITY = "Dhaka";
private static final String DEFAULT_ZIPCODE = "1207";

private static final String DEFAULT_DOB_DAY = "10";
private static final String DEFAULT_DOB_MONTH = "5";
private static final String DEFAULT_DOB_YEAR = "1995";

private static final List<String> SUPPORTED_COUNTRIES = Arrays.asList(
        "India",
        "United States",
        "Canada",
        "Australia",
        "Israel",
        "New Zealand",
        "Singapore"
);

// Usage:
.selectCountry(DEFAULT_COUNTRY)
.enterState(DEFAULT_STATE)
.enterCity(DEFAULT_CITY)
.enterZipcode(DEFAULT_ZIPCODE)

.setBirthDate(DEFAULT_DOB_DAY, DEFAULT_DOB_MONTH, DEFAULT_DOB_YEAR)

Assertions.assertThat(signupPage.getCountryOptions())
        .containsExactlyElementsOf(SUPPORTED_COUNTRIES);
```

---

## ✅ Verification Checklist

After making all changes, verify:

- [ ] PageManager compiles with new `signupPage()` method
- [ ] All SignupTest methods renamed to descriptive camelCase
- [ ] All SignupTest methods use `pages().signupPage()` instead of local helper
- [ ] All test methods wrapped with `Allure.step()`
- [ ] Test priorities are sequential (1-14)
- [ ] Imports are reorganized and clean
- [ ] SignupPage has comprehensive Javadoc
- [ ] All hardcoded constants extracted
- [ ] Tests compile without errors
- [ ] Tests pass with new method names

---

## 📊 Impact Summary

| Change | Files | Lines Changed | Complexity |
|--------|-------|---------------|----|
| Add signupPage() to PageManager | 1 | +8 | Low |
| Remove local helper from SignupTest | 1 | -6 | Low |
| Rename all test methods | 1 | 14 method signatures | Medium |
| Add Allure.step() instrumentation | 1 | +~150 | Medium |
| Add Javadoc to SignupPage | 1 | +~50 | Low |
| Reorganize imports | 1 | -3/+1 | Low |
| Extract constants | 1 | +8 | Low |
| **TOTAL** | **3 files** | **~200 lines** | **Medium** |

**Estimated Time: 2-3 hours**


