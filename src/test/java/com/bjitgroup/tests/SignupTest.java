package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import com.bjitgroup.context.TestAccountStore;
import com.bjitgroup.dataproviders.SignupDataProvider;
import com.bjitgroup.pages.LoginPage;
import com.bjitgroup.pages.SignupPage;
import com.bjitgroup.utils.AccountHelper;
import com.bjitgroup.utils.RandomDataUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Feature("Authentication")
public class SignupTest extends BaseTest {

    // Test Case 1: Register User
    @Test(
            priority = 1,
            description = "New user should be able to complete signup successfully",
            groups = "account-created"
    )
    @Severity(SeverityLevel.BLOCKER)
    @Story("Signup")
    @Description("Open login/signup page, register a new user, and verify account is created and logged in.")
    public void newUserShouldCompleteSignup() {
        String firstName = RandomDataUtils.firstName();
        String lastName = RandomDataUtils.lastName();
        String fullName = firstName + " " + lastName;
        String email = RandomDataUtils.email();
        String password = RandomDataUtils.password();

        LoginPage loginPage = pages().loginPage().open();
        SignupPage signupPage = loginPage.startSignup(fullName, email);

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
        );

        Assertions.assertThat(signupPage.getAccountCreatedMessage())
                .as("Account should be created after submitting valid signup details")
                .isEqualToIgnoringCase("Account Created!");

        signupPage.clickContinue();

        Assertions.assertThat(signupPage.isLoggedInLabelVisible())
                .as("User should be logged in after finishing signup flow")
                .isTrue();

        TestAccountStore.save(fullName, email, password);
    }

    // Test Case 5: Register User with existing email
    @Test(
            priority = 2,
            description = "Register User with existing email"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Signup")
    @Description("Open signup/login, submit an already-registered email, and verify duplicate-email error.")
    public void registerUserWithExistingEmailShouldShowError() {
        // Arrange: provision a dedicated account so a known-existing email is
        // guaranteed at assert time. This test must not reuse the shared
        // TestAccountStore account, which DeleteUser can remove first depending
        // on suite scheduling, invalidating the precondition.
        String[] account = AccountHelper.createAccount(page());
        String existingEmail = account[1];
        String password = account[2];

        // Return to guest state so the "New User Signup!" form is available
        // (the Signup/Login header link is hidden while logged in).
        pages().homePage().clickLogout();

        // Act: attempt to register the same email again.
        pages().homePage().open();
        Assertions.assertThat(pages().homePage().isLoaded())
                .as("Home page should be visible").isTrue();

        pages().homePage().clickSignupLogin();
        Assertions.assertThat(pages().loginPage().isSignupFormVisible())
                .as("'New User Signup!' should be visible").isTrue();

        pages().loginPage()
                .enterSignupName(RandomDataUtils.fullName())
                .enterSignupEmail(existingEmail)
                .clickSignupExpectingError();

        // Assert: duplicate-email error is shown.
        Assertions.assertThat(pages().loginPage().getSignupErrorMessage())
                .as("Duplicate-email error message should be visible")
                .contains("Email Address already exist!");

        // Cleanup: remove the account this test created so the site stays tidy
        // and re-runs remain idempotent.
        pages().loginPage().open().loginAs(existingEmail, password);
        AccountHelper.deleteAccount(page());
    }

    // Test Case: Create Account with a mandatory field left empty
    @Test(
            priority = 3,
            description = "Create Account should be blocked when a mandatory field is empty",
            dataProvider = "mandatorySignupFields",
            dataProviderClass = SignupDataProvider.class
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Signup - client-side validation")
    @Description("Fill every mandatory field, clear one, submit, and verify the browser blocks account "
            + "creation and marks that field invalid.")
    public void createAccountShouldBeBlockedWhenMandatoryFieldIsEmpty(String fieldName, String fieldKey) {
        String firstName = RandomDataUtils.firstName();
        String lastName = RandomDataUtils.lastName();

        LoginPage loginPage = pages().loginPage().open();
        SignupPage signupPage = loginPage.startSignup(firstName + " " + lastName, RandomDataUtils.email());
        signupPage.waitUntilLoaded();

        // Fill every mandatory field with valid data first, so the field under test
        // is the only reason submission can fail.
        signupPage.enterPassword(RandomDataUtils.password())
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterAddress(RandomDataUtils.address())
                .selectCountry("India")
                .enterState("Dhaka")
                .enterCity("Dhaka")
                .enterZipcode("1207")
                .enterMobileNumber(RandomDataUtils.phone());

        signupPage.clearField(fieldKey);

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
}