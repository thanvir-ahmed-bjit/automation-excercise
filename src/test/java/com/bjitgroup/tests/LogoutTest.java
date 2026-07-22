package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import com.bjitgroup.utils.AccountHelper;
import io.qameta.allure.*;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Feature("Authentication")
public class LogoutTest extends BaseTest {

    // Test Case 4: Logout User
    @Test(description = "Logout User should navigate to login page")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Logout")
    @Description("Create account, login, verify logged in, logout, verify navigated to login page.")
    public void logoutUserShouldNavigateToLoginPage() {
        String[] account = AccountHelper.createAccount(page());
        String email = account[1];
        String password = account[2];

        pages().homePage().open();
        Assertions.assertThat(pages().homePage().isLoaded())
                .as("Home page should be visible").isTrue();

        pages().homePage().clickSignupLogin();
        Assertions.assertThat(pages().loginPage().isLoginPageDisplayed())
                .as("Login to your account should be visible").isTrue();

        pages().loginPage().attemptLogin(email, password);
        pages().homePage().waitUntilLoaded();
        Assertions.assertThat(pages().homePage().isLoggedIn())
                .as("User should be logged in").isTrue();

        pages().homePage().clickLogout();
        pages().loginPage().waitUntilLoaded();
        Assertions.assertThat(pages().loginPage().isLoginPageDisplayed())
                .as("Should be on login page after logout").isTrue();
    }
}
