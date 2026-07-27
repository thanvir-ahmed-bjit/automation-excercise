package com.bjitgroup.tests;

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

@Feature("Authentication")
public class SignupTest extends BaseTest {

    @Test(
            priority = 1,
            description = "New user should be able to complete signup successfully"
    )
    @Severity(SeverityLevel.BLOCKER)
    @Story("Signup")
    @Description("Open login/signup page, register a new user, and verify account is created and logged in.")
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

    @Test(
            priority = 2,
            description = "Verify New User Signup section is displayed on Signup/Login page"
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Signup")
    @Description("Validate 'New User Signup!' entry section and controls.")
    public void testCase02_verifyNewUserSignupSectionDisplayed() {
        SignupPage signupPage = signupPage().openHomePage().clickSignupLoginFromHome();
        Assertions.assertThat(signupPage.isHomePageVisible())
                .as("Home page should be visible successfully")
                .isTrue();
        Assertions.assertThat(signupPage.isSignupFormVisible())
                .as("Name/email inputs and Signup button should be visible and enabled")
                .isTrue();
    }

    @Test(
            priority = 3,
            description = "Signup with empty name and email should be blocked by browser validation"
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Signup")
    @Description("Leave signup name/email empty and verify HTML5 required validation blocks submission.")
    public void testCase03_signupWithEmptyNameAndEmail() {
        SignupPage signupPage = signupPage().openAutomationExerciseLogin();
        String before = page().url();
        signupPage.clickSignup();

        Assertions.assertThat(page().url())
                .as("Form should not be submitted")
                .isEqualTo(before);
        Assertions.assertThat(signupPage.hasSignupValidityFlag("signupNameInput", "valueMissing"))
                .as("Name field should report required validation")
                .isTrue();
        Assertions.assertThat(signupPage.getSignupValidationMessage("signupNameInput"))
                .as("Browser should show validation message for missing required field")
                .isNotBlank();
        Assertions.assertThat(signupPage.isSignupUrl())
                .as("User should remain on Signup/Login page")
                .isTrue();
    }

    @Test(
            priority = 4,
            description = "Signup with invalid email format should be blocked by browser validation"
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Signup")
    @Description("Enter invalid signup email and verify type mismatch validation blocks account-info navigation.")
    public void testCase04_signupWithInvalidEmailFormat() {
        SignupPage signupPage = signupPage().openAutomationExerciseLogin();
        String before = page().url();

        signupPage.enterSignupName(RandomDataUtils.fullName())
                .enterSignupEmail("testuser.com")
                .clickSignup();

        Assertions.assertThat(page().url())
                .as("Form should not be submitted")
                .isEqualTo(before);
        Assertions.assertThat(signupPage.hasSignupValidityFlag("signupEmailInput", "typeMismatch"))
                .as("Email field should report format/type mismatch")
                .isTrue();
        Assertions.assertThat(signupPage.isAccountInfoVisible())
                .as("Account information page must not be shown")
                .isFalse();
    }

    @Test(
            priority = 5,
            description = "Verify signup name/email carry over and account email is not editable"
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Signup")
    @Description("Verify name/email prefill behaviour on account-info page.")
    public void testCase05_verifyNameAndEmailCarryOverAndEmailDisabled() {
        String fullName = RandomDataUtils.fullName();
        String email = RandomDataUtils.email();
        SignupPage signupPage = signupPage()
                .openAutomationExerciseLogin()
                .startSignup(fullName, email);

        Assertions.assertThat(signupPage.isAccountInfoVisible()).isTrue();
        Assertions.assertThat(signupPage.getAccountNameValue()).isEqualTo(fullName);
        Assertions.assertThat(signupPage.isAccountNameEditable()).isTrue();
        Assertions.assertThat(signupPage.getAccountEmailValue()).isEqualTo(email);
        Assertions.assertThat(signupPage.isAccountEmailDisabled()).isTrue();
        Assertions.assertThat(signupPage.tryChangingAccountEmailAndRead()).isEqualTo(email);
    }

    @Test(
            priority = 6,
            description = "Create account with mandatory fields only"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Signup")
    @Description("Verify account creation works without optional fields (title, DOB, company, address2, opt-ins).")
    public void testCase06_createAccountWithMandatoryFieldsOnly() {
        String firstName = RandomDataUtils.firstName();
        String lastName = RandomDataUtils.lastName();
        SignupPage signupPage = signupPage()
                .openAutomationExerciseLogin()
                .startSignup(firstName + " " + lastName, RandomDataUtils.email())
                .completeMandatoryOnlySignup(
                        RandomDataUtils.password(),
                        firstName,
                        lastName,
                        RandomDataUtils.address(),
                        "Dhaka",
                        "Dhaka",
                        "1207",
                        RandomDataUtils.phone()
                );

        Assertions.assertThat(signupPage.getAccountCreatedMessage())
                .isEqualToIgnoringCase("Account Created!");
        signupPage.clickContinue();
        Assertions.assertThat(signupPage.isLoggedInLabelVisible()).isTrue();
        signupPage.deleteCurrentAccount();
    }

    @Test(
            priority = 7,
            description = "Create account with optional fields populated"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Signup")
    @Description("Full-field positive path with optional fields and opt-ins.")
    public void testCase07_createAccountWithAllOptionalFields() {
        String firstName = RandomDataUtils.firstName();
        String lastName = RandomDataUtils.lastName();
        SignupPage signupPage = signupPage()
                .openAutomationExerciseLogin()
                .startSignup(firstName + " " + lastName, RandomDataUtils.email())
                .completeAllOptionalAndMandatorySignup(
                        RandomDataUtils.password(),
                        firstName,
                        lastName,
                        "BJIT Group",
                        RandomDataUtils.address(),
                        "Flat 4A",
                        "Australia",
                        "Victoria",
                        "Melbourne",
                        "3000",
                        RandomDataUtils.phone()
                );

        Assertions.assertThat(signupPage.getAccountCreatedMessage())
                .isEqualToIgnoringCase("Account Created!");
        signupPage.clickContinue();
        Assertions.assertThat(signupPage.isLoggedInLabelVisible()).isTrue();
        signupPage.deleteCurrentAccount();
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
    @Description("Fill every mandatory field, clear one, submit, and verify the browser blocks account "
            + "creation and marks that field invalid.")
    @SuppressWarnings("resource")
    public void testCase08_createAccountBlockedWhenMandatoryFieldEmpty(String fieldName, String fieldKey) {
        String firstName = RandomDataUtils.firstName();
        String lastName = RandomDataUtils.lastName();

        SignupPage signupPage = signupPage()
                .openAutomationExerciseLogin()
                .startSignup(firstName + " " + lastName, RandomDataUtils.email());
        signupPage.waitUntilLoaded();

        // Fill every mandatory field with valid data first, so the field under test
        // is the only reason submission can fail.
        signupPage
                .enterPassword(RandomDataUtils.password())
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterAddress(RandomDataUtils.address())
                .selectCountry("India")
                .enterState("Dhaka")
                .enterCity("Dhaka")
                .enterZipcode("1207")
                .enterMobileNumber(RandomDataUtils.phone())
                .clearField(fieldKey);

        String urlBeforeSubmit = page().url();
        signupPage.clickCreateAccount();

        // No account is created by this test: the browser refuses to submit while a
        // required field is empty, so the request never reaches the server.
        Assertions.assertThat(page().url())
                .as("[%s empty] No request should be sent, so the URL must not change", fieldName)
                .isEqualTo(urlBeforeSubmit);

        Assertions.assertThat(signupPage.isAccountCreatedVisible())
                .as("[%s empty] 'Account Created!' must not be displayed", fieldName)
                .isFalse();

        Assertions.assertThat(signupPage.isFieldValid(fieldKey))
                .as("[%s empty] Field should fail HTML5 validation. Browser message: '%s'",
                        fieldName, signupPage.getValidationMessage(fieldKey))
                .isFalse();

        Assertions.assertThat(signupPage.hasValidityFlag(fieldKey, "valueMissing"))
                .as("[%s empty] Field should report validity flag 'valueMissing'", fieldName)
                .isTrue();
    }

    @Test(
            priority = 9,
            description = "Title radio buttons are optional and mutually exclusive"
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Signup")
    @Description("Validate title radio behavior and that no title still allows successful registration.")
    public void testCase09_verifyTitleRadiosOptionalAndMutuallyExclusive() {
        String firstName = RandomDataUtils.firstName();
        String lastName = RandomDataUtils.lastName();
        SignupPage signupPage = signupPage()
                .openAutomationExerciseLogin()
                .startSignup(firstName + " " + lastName, RandomDataUtils.email());

        Assertions.assertThat(signupPage.isNoTitleSelectedByDefault()).isTrue();
        signupPage.selectMrTitle();
        Assertions.assertThat(signupPage.isMrTitleSelected()).isTrue();
        signupPage.selectMrsTitle();
        Assertions.assertThat(signupPage.isMrsTitleSelected()).isTrue();
        Assertions.assertThat(signupPage.isMrTitleSelected()).isFalse();

        signupPage.completeMandatoryOnlySignup(
                RandomDataUtils.password(),
                firstName,
                lastName,
                RandomDataUtils.address(),
                "Dhaka",
                "Dhaka",
                "1207",
                RandomDataUtils.phone()
        );
        Assertions.assertThat(signupPage.getAccountCreatedMessage()).isEqualToIgnoringCase("Account Created!");
        signupPage.clickContinue().deleteCurrentAccount();
    }

    @Test(
            priority = 10,
            description = "DOB dropdowns optional with valid ranges"
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Signup")
    @Description("Verify day/month/year ranges and that signup can succeed without DOB.")
    public void testCase10_verifyDobDropdownsOptionalAndContainValidRanges() {
        String firstName = RandomDataUtils.firstName();
        String lastName = RandomDataUtils.lastName();
        SignupPage signupPage = signupPage()
                .openAutomationExerciseLogin()
                .startSignup(firstName + " " + lastName, RandomDataUtils.email());

        List<String> days = signupPage.getDayOptions();
        List<String> months = signupPage.getMonthOptions();
        List<String> years = signupPage.getYearOptions();

        Assertions.assertThat(signupPage.isBirthDateBlankByDefault()).isTrue();
        Assertions.assertThat(days).contains("1", "31");
        Assertions.assertThat(months).contains("January", "December");
        Assertions.assertThat(years.size()).isGreaterThan(20);

        signupPage.completeMandatoryOnlySignup(
                RandomDataUtils.password(),
                firstName,
                lastName,
                RandomDataUtils.address(),
                "Dhaka",
                "Dhaka",
                "1207",
                RandomDataUtils.phone()
        );
        Assertions.assertThat(signupPage.getAccountCreatedMessage()).isEqualToIgnoringCase("Account Created!");
        signupPage.clickContinue().deleteCurrentAccount();
    }

    @Test(
            priority = 11,
            description = "Country dropdown defaults and supported options"
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Signup")
    @Description("Verify country default and list values.")
    public void testCase11_verifyCountryDropdownDefaultsAndOptions() {
        SignupPage signupPage = signupPage()
                .openAutomationExerciseLogin()
                .startSignup(RandomDataUtils.fullName(), RandomDataUtils.email());

        List<String> expectedCountries = Arrays.asList(
                "India",
                "United States",
                "Canada",
                "Australia",
                "Israel",
                "New Zealand",
                "Singapore"
        );

        Assertions.assertThat(signupPage.selectedCountry()).isEqualTo("India");
        Assertions.assertThat(signupPage.getCountryOptions()).containsExactlyElementsOf(expectedCountries);
    }

    @Test(
            priority = 12,
            description = "Password input should be masked"
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Signup")
    @Description("Verify account password field type is password.")
    public void testCase12_verifyPasswordFieldMasked() {
        SignupPage signupPage = signupPage()
                .openAutomationExerciseLogin()
                .startSignup(RandomDataUtils.fullName(), RandomDataUtils.email());
        Assertions.assertThat(signupPage.isPasswordMaskedField()).isTrue();
    }

    @Test(
            priority = 13,
            description = "Register User with existing email"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Signup")
    @Description("Submit an already-registered email and verify duplicate-email error.")
    public void testCase15_registerUserWithExistingEmail() {
        String firstName = RandomDataUtils.firstName();
        String lastName = RandomDataUtils.lastName();
        String existingEmail = RandomDataUtils.email();
        String password = RandomDataUtils.password();

        SignupPage signupPage = signupPage()
                .openAutomationExerciseLogin()
                .startSignup(firstName + " " + lastName, existingEmail)
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
                )
                .clickContinue();

        Assertions.assertThat(signupPage.isLoggedInLabelVisible()).isTrue();
        signupPage.logoutToSignupForm();

        signupPage.enterSignupName(RandomDataUtils.fullName())
                .enterSignupEmail(existingEmail)
                .clickSignupExpectingError();

        Assertions.assertThat(signupPage.getSignupErrorMessage())
                .contains("Email Address already exist!");

        signupPage.loginWithExistingAccount(existingEmail, password).deleteCurrentAccount();
    }

    @Test(
            priority = 14,
            description = "Newly registered user can login with same credentials"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Signup/Login")
    @Description("Create account, logout, login using same credentials, then cleanup.")
    public void testCase16_verifyNewlyRegisteredUserCanLoginWithSameCredentials() {
        String firstName = RandomDataUtils.firstName();
        String lastName = RandomDataUtils.lastName();
        String fullName = firstName + " " + lastName;
        String email = RandomDataUtils.email();
        String password = RandomDataUtils.password();

        SignupPage signupPage = signupPage()
                .openAutomationExerciseLogin()
                .startSignup(fullName, email)
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
                )
                .clickContinue();

        Assertions.assertThat(signupPage.isLoggedInLabelVisible()).isTrue();
        signupPage.logoutToSignupForm()
                .loginWithExistingAccount(email, password);

        Assertions.assertThat(signupPage.isLoggedInLabelVisible()).isTrue();
        Assertions.assertThat(signupPage.getLoggedInLabelText()).contains(firstName);
        signupPage.deleteCurrentAccount();
    }

    private SignupPage signupPage() {
        BrowserActions browser = new BrowserActions(page(), context().config().timeoutMs());
        InputActions input = new InputActions(page());
        PageManager manager = new PageManager(page(), context().config().timeoutMs());
        return new SignupPage(browser, input, manager);
    }
}