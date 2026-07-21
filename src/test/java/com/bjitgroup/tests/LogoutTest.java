package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import com.bjitgroup.utils.AccountHelper;
import io.qameta.allure.*;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Feature("Authentication")
public class LogoutTest extends BaseTest {

    @Test(description = "Logout User should navigate to login page")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Logout")
    @Description("Create account, login, verify logged in, logout, verify navigated to login page.")
    public void logoutUserShouldNavigateToLoginPage() {
        String[] account = AccountHelper.createAccount(page());
        String email = account[1];
        String password = account[2];

        page().navigate("https://automationexercise.com");
        Assertions.assertThat(page().locator("img[alt='Website for automation practice']").isVisible())
            .as("Home page should be visible").isTrue();

        page().locator("a[href='/login']").first().click();
        Assertions.assertThat(pages().loginPage().waitUntilLoaded().isLoginPageDisplayed())
            .as("Login to your account should be visible").isTrue();

        pages().loginPage().attemptLogin(email, password);
        page().waitForSelector("//a[contains(normalize-space(),'Logged in as')]");
        Assertions.assertThat(page().locator("//a[contains(normalize-space(),'Logged in as')]").isVisible())
            .as("User should be logged in").isTrue();

        page().locator("a[href='/logout']").first().click();
        page().waitForSelector("input[data-qa='login-email']");
        Assertions.assertThat(page().url()).as("Should be on login page").contains("/login");
    }
}