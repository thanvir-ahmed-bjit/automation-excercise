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
        // Create account — user is now logged in on the home page
        String[] account = AccountHelper.createAccount(page());

        // Verify home page is visible (user is already logged in)
        Assertions.assertThat(pages().homePage().isLoaded())
                .as("Home page should be visible").isTrue();

        // Verify user is logged in
        Assertions.assertThat(pages().homePage().isLoggedIn())
                .as("User should be logged in after account creation").isTrue();

        // Click Logout
        pages().homePage().clickLogout();

        // Verify navigated to login page
        pages().loginPage().waitUntilLoaded();
        Assertions.assertThat(pages().loginPage().isLoginPageDisplayed())
                .as("Should be on login page after logout").isTrue();
    }
}
