package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import com.bjitgroup.context.TestAccountStore;
import com.bjitgroup.pages.LoginPage;
import com.bjitgroup.pages.SignupPage;
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

    @Test(
            priority = 2,
            description = "Register User with existing email",
            dependsOnMethods = "newUserShouldCompleteSignup"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Signup")
    @Description("Open signup/login, submit an already-registered email, and verify duplicate-email error.")
    public void registerUserWithExistingEmailShouldShowError() {
        LoginPage loginPage = pages().loginPage();

        page().navigate("http://automationexercise.com");
        Assertions.assertThat(page().locator("img[alt='Website for automation practice']").isVisible())
                .as("Home page should be visible")
                .isTrue();

        page().locator("a[href='/login']").first().click();
        Assertions.assertThat(loginPage.waitUntilLoaded().isSignupFormVisible())
                .as("'New User Signup!' should be visible")
                .isTrue();

        loginPage.enterSignupName(RandomDataUtils.fullName())
                .enterSignupEmail(TestAccountStore.email())
                .clickSignupExpectingError();

        Assertions.assertThat(loginPage.getSignupErrorMessage())
                .as("Duplicate-email error message should be visible")
                .contains("Email Address already exist!");
    }
}
