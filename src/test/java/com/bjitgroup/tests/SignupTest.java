package com.bjitgroup.tests;

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

@Feature("Authentication")
public class SignupTest extends BaseTest {

    private static final String DEFAULT_COUNTRY = "India";
    private static final String DEFAULT_STATE = "Dhaka";
    private static final String DEFAULT_CITY = "Dhaka";
    private static final String DEFAULT_ZIPCODE = "1207";
    private static final List<String> SUPPORTED_COUNTRIES = Arrays.asList(
            "India",
            "United States",
            "Canada",
            "Australia",
            "Israel",
            "New Zealand",
            "Singapore"
    );

    @Test(
            priority = 1,
            description = "New user should be able to complete signup successfully"
    )
    @Severity(SeverityLevel.BLOCKER)
    @Story("Signup")
    @Description("Open login/signup page, register a new user, and verify account is created and logged in.")
    public void newUserShouldCompleteSignupSuccessfully() {
        SignupPage signupPage = pages().signupPage();
        String firstName = RandomDataUtils.firstName();
        String lastName = RandomDataUtils.lastName();
        String fullName = firstName + " " + lastName;
        String email = RandomDataUtils.email();
        String password = RandomDataUtils.password();

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
                signupPage.completeSignup(password, firstName, lastName,
                        RandomDataUtils.address(), DEFAULT_COUNTRY, DEFAULT_STATE, DEFAULT_CITY, DEFAULT_ZIPCODE, RandomDataUtils.phone()));
        Allure.step("Verify account created message", () ->
                Assertions.assertThat(signupPage.getAccountCreatedMessage())
                        .as("Account should be created after submitting valid signup details")
                        .isEqualToIgnoringCase("Account Created!"));
        Allure.step("Click Continue", signupPage::clickContinue);
        Allure.step("Verify user is logged in", () ->
                Assertions.assertThat(signupPage.isLoggedInLabelVisible())
                        .as("User should be logged in after finishing signup flow")
                        .isTrue());
        Allure.step("Delete account", signupPage::deleteCurrentAccount);
    }

    @Test(
            priority = 2,
            description = "Verify New User Signup section is displayed on Signup/Login page"
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Signup")
    @Description("Validate 'New User Signup!' entry section and controls.")
    public void signupFormShouldBeVisibleOnSignupPage() {
        SignupPage signupPage = pages().signupPage();
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

    @Test(
            priority = 3,
            description = "Signup with empty name and email should be blocked"
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Signup")
    @Description("Leave signup name/email empty and verify HTML5 required validation blocks submission.")
    public void signupWithEmptyNameShouldBlockSubmission() {
        SignupPage signupPage = pages().signupPage();
        Allure.step("Navigate to login page", signupPage::openAutomationExerciseLogin);
        // Captured after navigation completes so the baseline reflects the actual
        // Signup/Login page URL, not the fresh page's initial "about:blank" state.
        String before = page().url();
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

    @Test(
            priority = 4,
            description = "Signup with invalid email format should be blocked"
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Signup")
    @Description("Enter invalid signup email and verify type mismatch validation blocks account-info navigation.")
    public void signupWithInvalidEmailShouldShowBrowserValidation() {
        SignupPage signupPage = pages().signupPage();
        Allure.step("Navigate to Automation Exercise login page", signupPage::openAutomationExerciseLogin);
        final String[] before = {page().url()};
        Allure.step("Enter name and invalid email, click signup", () ->
                signupPage.enterSignupName(RandomDataUtils.fullName())
                        .enterSignupEmail("testuser.com")
                        .clickSignup());
        Allure.step("Verify URL has not changed", () ->
                Assertions.assertThat(page().url())
                        .as("Form should not be submitted")
                        .isEqualTo(before[0]));
        Allure.step("Verify email field reports typeMismatch", () ->
                Assertions.assertThat(signupPage.hasSignupValidityFlag("signupEmailInput", "typeMismatch"))
                        .as("Email field should report format/type mismatch")
                        .isTrue());
        Allure.step("Verify account info page is not shown", () ->
                Assertions.assertThat(signupPage.isAccountInfoVisible())
                        .as("Account information page must not be shown")
                        .isFalse());
    }

    @Test(
            priority = 5,
            description = "Verify signup name/email carry over and account email is not editable"
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Signup")
    @Description("Verify name/email prefill behaviour on account-info page.")
    public void nameAndEmailShouldCarryOverAndEmailShouldBeDisabled() {
        String fullName = RandomDataUtils.fullName();
        String email = RandomDataUtils.email();
        SignupPage signupPage = pages().signupPage();
        Allure.step("Navigate to login page and start signup with name and email", () ->
                signupPage.openAutomationExerciseLogin().startSignup(fullName, email));
        Allure.step("Verify account info page is visible", () ->
                Assertions.assertThat(signupPage.isAccountInfoVisible()).isTrue());
        Allure.step("Verify name is carried over and editable", () -> {
            Assertions.assertThat(signupPage.getAccountNameValue()).isEqualTo(fullName);
            Assertions.assertThat(signupPage.isAccountNameEditable()).isTrue();
        });
        Allure.step("Verify email is carried over and disabled", () -> {
            Assertions.assertThat(signupPage.getAccountEmailValue()).isEqualTo(email);
            Assertions.assertThat(signupPage.isAccountEmailDisabled()).isTrue();
            Assertions.assertThat(signupPage.tryChangingAccountEmailAndRead()).isEqualTo(email);
        });
    }

    @Test(
            priority = 6,
            description = "Create account with mandatory fields only"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Signup")
    @Description("Verify account creation works without optional fields.")
    public void accountCreationWithMandatoryFieldsOnlyShouldSucceed() {
        String firstName = RandomDataUtils.firstName();
        String lastName = RandomDataUtils.lastName();
        SignupPage signupPage = pages().signupPage();
        Allure.step("Navigate and start signup", () ->
                signupPage.openAutomationExerciseLogin()
                        .startSignup(firstName + " " + lastName, RandomDataUtils.email())
                        .completeMandatoryOnlySignup(RandomDataUtils.password(), firstName, lastName,
                                RandomDataUtils.address(), DEFAULT_STATE, DEFAULT_CITY, DEFAULT_ZIPCODE, RandomDataUtils.phone()));
        Allure.step("Verify account created", () ->
                Assertions.assertThat(signupPage.getAccountCreatedMessage())
                        .isEqualToIgnoringCase("Account Created!"));
        Allure.step("Click Continue and verify logged in", () -> {
            signupPage.clickContinue();
            Assertions.assertThat(signupPage.isLoggedInLabelVisible()).isTrue();
        });
        Allure.step("Delete account", signupPage::deleteCurrentAccount);
    }

    @Test(
            priority = 7,
            description = "Create account with optional fields populated"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Signup")
    @Description("Full-field positive path with optional fields and opt-ins.")
    public void accountCreationWithAllFieldsShouldSucceed() {
        String firstName = RandomDataUtils.firstName();
        String lastName = RandomDataUtils.lastName();
        SignupPage signupPage = pages().signupPage();
        Allure.step("Navigate and complete full signup with all optional fields", () ->
                signupPage.openAutomationExerciseLogin()
                        .startSignup(firstName + " " + lastName, RandomDataUtils.email())
                        .completeAllOptionalAndMandatorySignup(RandomDataUtils.password(), firstName, lastName,
                                "BJIT Group", RandomDataUtils.address(), "Flat 4A",
                                "Australia", "Victoria", "Melbourne", "3000", RandomDataUtils.phone()));
        Allure.step("Verify account created", () ->
                Assertions.assertThat(signupPage.getAccountCreatedMessage())
                        .isEqualToIgnoringCase("Account Created!"));
        Allure.step("Click Continue and verify logged in", () -> {
            signupPage.clickContinue();
            Assertions.assertThat(signupPage.isLoggedInLabelVisible()).isTrue();
        });
        Allure.step("Delete account", signupPage::deleteCurrentAccount);
    }

    @Test(
            priority = 8,
            description = "Create Account should be blocked when a mandatory field is empty",
            dataProvider = "mandatorySignupFields",
            dataProviderClass = SignupDataProvider.class,
            retryAnalyzer = com.bjitgroup.listeners.RetryAnalyzer.class
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Signup - client-side validation")
    @Description("Fill every mandatory field, clear one, submit, and verify the browser blocks account creation.")
    @SuppressWarnings("resource")
    public void emptyMandatoryFieldShouldBlockAccountCreation(String fieldName, String fieldKey) {
        String firstName = RandomDataUtils.firstName();
        String lastName = RandomDataUtils.lastName();
        SignupPage signupPage = pages().signupPage();
        Allure.step("Navigate, start signup and fill all mandatory fields", () -> {
            signupPage.openAutomationExerciseLogin()
                    .startSignup(firstName + " " + lastName, RandomDataUtils.email());
            signupPage.waitUntilLoaded();
            signupPage.enterPassword(RandomDataUtils.password())
                    .enterFirstName(firstName)
                    .enterLastName(lastName)
                    .enterAddress(RandomDataUtils.address())
                    .selectCountry(DEFAULT_COUNTRY)
                    .enterState(DEFAULT_STATE)
                    .enterCity(DEFAULT_CITY)
                    .enterZipcode(DEFAULT_ZIPCODE)
                    .enterMobileNumber(RandomDataUtils.phone())
                    .clearField(fieldKey);
        });
        final String[] urlBeforeSubmit = {page().url()};
        Allure.step("Click Create Account", signupPage::clickCreateAccount);
        Allure.step("Verify URL has not changed (browser blocked submission)", () ->
                Assertions.assertThat(page().url())
                        .as("[%s empty] No request should be sent, so the URL must not change", fieldName)
                        .isEqualTo(urlBeforeSubmit[0]));
        Allure.step("Verify account created page is not shown", () ->
                Assertions.assertThat(signupPage.isAccountCreatedVisible())
                        .as("[%s empty] 'Account Created!' must not be displayed", fieldName)
                        .isFalse());
        Allure.step("Verify field fails HTML5 validation", () ->
                Assertions.assertThat(signupPage.isFieldValid(fieldKey))
                        .as("[%s empty] Field should fail HTML5 validation. Browser message: '%s'",
                                fieldName, signupPage.getValidationMessage(fieldKey))
                        .isFalse());
        Allure.step("Verify valueMissing flag is set on field", () ->
                Assertions.assertThat(signupPage.hasValidityFlag(fieldKey, "valueMissing"))
                        .as("[%s empty] Field should report validity flag 'valueMissing'", fieldName)
                        .isTrue());
    }

    @Test(
            priority = 9,
            description = "Title radio buttons are optional and mutually exclusive"
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Signup")
    @Description("Validate title radio behavior and that no title still allows successful registration.")
    public void titleRadiosShouldBeOptionalAndMutuallyExclusive() {
        String firstName = RandomDataUtils.firstName();
        String lastName = RandomDataUtils.lastName();
        SignupPage signupPage = pages().signupPage();
        Allure.step("Navigate and start signup", () ->
                signupPage.openAutomationExerciseLogin()
                        .startSignup(firstName + " " + lastName, RandomDataUtils.email()));
        Allure.step("Verify no title is selected by default", () ->
                Assertions.assertThat(signupPage.isNoTitleSelectedByDefault()).isTrue());
        Allure.step("Select Mr title and verify", () -> {
            signupPage.selectMrTitle();
            Assertions.assertThat(signupPage.isMrTitleSelected()).isTrue();
        });
        Allure.step("Select Mrs title and verify mutual exclusivity", () -> {
            signupPage.selectMrsTitle();
            Assertions.assertThat(signupPage.isMrsTitleSelected()).isTrue();
            Assertions.assertThat(signupPage.isMrTitleSelected()).isFalse();
        });
        Allure.step("Complete mandatory signup and verify account created", () -> {
            signupPage.completeMandatoryOnlySignup(RandomDataUtils.password(), firstName, lastName,
                    RandomDataUtils.address(), DEFAULT_STATE, DEFAULT_CITY, DEFAULT_ZIPCODE, RandomDataUtils.phone());
            Assertions.assertThat(signupPage.getAccountCreatedMessage()).isEqualToIgnoringCase("Account Created!");
        });
        Allure.step("Continue and delete account", () ->
                signupPage.clickContinue().deleteCurrentAccount());
    }

    @Test(
            priority = 10,
            description = "DOB dropdowns optional with valid ranges"
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Signup")
    @Description("Verify day/month/year ranges and that signup can succeed without DOB.")
    public void dobDropdownsShouldBeOptionalWithValidRanges() {
        String firstName = RandomDataUtils.firstName();
        String lastName = RandomDataUtils.lastName();
        SignupPage signupPage = pages().signupPage();
        Allure.step("Navigate and start signup", () ->
                signupPage.openAutomationExerciseLogin()
                        .startSignup(firstName + " " + lastName, RandomDataUtils.email()));
        Allure.step("Verify DOB dropdowns are blank by default and contain valid ranges", () -> {
            List<String> days = signupPage.getDayOptions();
            List<String> months = signupPage.getMonthOptions();
            List<String> years = signupPage.getYearOptions();
            Assertions.assertThat(signupPage.isBirthDateBlankByDefault()).isTrue();
            Assertions.assertThat(days).contains("1", "31");
            Assertions.assertThat(months).contains("January", "December");
            Assertions.assertThat(years.size()).isGreaterThan(20);
        });
        Allure.step("Complete mandatory signup and verify account created", () -> {
            signupPage.completeMandatoryOnlySignup(RandomDataUtils.password(), firstName, lastName,
                    RandomDataUtils.address(), DEFAULT_STATE, DEFAULT_CITY, DEFAULT_ZIPCODE, RandomDataUtils.phone());
            Assertions.assertThat(signupPage.getAccountCreatedMessage()).isEqualToIgnoringCase("Account Created!");
        });
        Allure.step("Continue and delete account", () ->
                signupPage.clickContinue().deleteCurrentAccount());
    }

    @Test(
            priority = 11,
            description = "Country dropdown defaults and supported options"
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Signup")
    @Description("Verify country default and list values.")
    public void countryDropdownShouldDefaultToIndiaWithValidOptions() {
        SignupPage signupPage = pages().signupPage();
        Allure.step("Navigate and start signup", () ->
                signupPage.openAutomationExerciseLogin()
                        .startSignup(RandomDataUtils.fullName(), RandomDataUtils.email()));
        Allure.step("Verify country defaults to India and contains expected options", () -> {
            Assertions.assertThat(signupPage.selectedCountry()).isEqualTo(DEFAULT_COUNTRY);
            Assertions.assertThat(signupPage.getCountryOptions()).containsExactlyElementsOf(SUPPORTED_COUNTRIES);
        });
    }

    @Test(
            priority = 12,
            description = "Password input should be masked"
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Signup")
    @Description("Verify account password field type is password.")
    public void passwordFieldShouldBeMasked() {
        SignupPage signupPage = pages().signupPage();
        Allure.step("Navigate and start signup", () ->
                signupPage.openAutomationExerciseLogin()
                        .startSignup(RandomDataUtils.fullName(), RandomDataUtils.email()));
        Allure.step("Verify password field is masked", () ->
                Assertions.assertThat(signupPage.isPasswordMaskedField()).isTrue());
    }

    @Test(
            priority = 13,
            description = "Register User with existing email"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Signup")
    @Description("Submit an already-registered email and verify duplicate-email error.")
    public void duplicateEmailShouldShowErrorMessage() {
        String firstName = RandomDataUtils.firstName();
        String lastName = RandomDataUtils.lastName();
        String existingEmail = RandomDataUtils.email();
        String password = RandomDataUtils.password();
        SignupPage signupPage = pages().signupPage();
        Allure.step("Navigate and register a new account", () ->
                signupPage.openAutomationExerciseLogin()
                        .startSignup(firstName + " " + lastName, existingEmail)
                        .completeSignup(password, firstName, lastName,
                                RandomDataUtils.address(), DEFAULT_COUNTRY, DEFAULT_STATE, DEFAULT_CITY, DEFAULT_ZIPCODE, RandomDataUtils.phone())
                        .clickContinue());
        Allure.step("Verify user is logged in", () ->
                Assertions.assertThat(signupPage.isLoggedInLabelVisible()).isTrue());
        Allure.step("Logout and return to signup form", signupPage::logoutToSignupForm);
        Allure.step("Attempt signup with the same email", () ->
                signupPage.enterSignupName(RandomDataUtils.fullName())
                        .enterSignupEmail(existingEmail)
                        .clickSignupExpectingError());
        Allure.step("Verify duplicate email error message", () ->
                Assertions.assertThat(signupPage.getSignupErrorMessage())
                        .contains("Email Address already exist!"));
        Allure.step("Login with existing account and delete", () ->
                signupPage.loginWithExistingAccount(existingEmail, password).deleteCurrentAccount());
    }

    @Test(
            priority = 14,
            description = "Newly registered user can login with same credentials"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Signup/Login")
    @Description("Create account, logout, login using same credentials, then cleanup.")
    public void newlyRegisteredUserCanLoginWithSameCredentials() {
        String firstName = RandomDataUtils.firstName();
        String lastName = RandomDataUtils.lastName();
        String fullName = firstName + " " + lastName;
        String email = RandomDataUtils.email();
        String password = RandomDataUtils.password();
        SignupPage signupPage = pages().signupPage();
        Allure.step("Navigate and complete signup", () ->
                signupPage.openAutomationExerciseLogin()
                        .startSignup(fullName, email)
                        .completeSignup(password, firstName, lastName,
                                RandomDataUtils.address(), DEFAULT_COUNTRY, DEFAULT_STATE, DEFAULT_CITY, DEFAULT_ZIPCODE, RandomDataUtils.phone())
                        .clickContinue());
        Allure.step("Verify user is logged in after signup", () ->
                Assertions.assertThat(signupPage.isLoggedInLabelVisible()).isTrue());
        Allure.step("Logout and login with the same credentials", () ->
                signupPage.logoutToSignupForm().loginWithExistingAccount(email, password));
        Allure.step("Verify user is logged in with correct name", () -> {
            Assertions.assertThat(signupPage.isLoggedInLabelVisible()).isTrue();
            Assertions.assertThat(signupPage.getLoggedInLabelText()).contains(firstName);
        });
        Allure.step("Delete account", signupPage::deleteCurrentAccount);
    }
}