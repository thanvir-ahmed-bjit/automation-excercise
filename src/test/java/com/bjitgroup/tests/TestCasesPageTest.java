package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import io.qameta.allure.*;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Feature("Test Cases Page")
public class TestCasesPageTest extends BaseTest {

    private static final String BASE_URL = "https://automationexercise.com";

    @Test(description = "Verify Test Cases Page is accessible")
    @Severity(SeverityLevel.NORMAL)
    @Story("Test Cases Navigation")
    @Description("Navigate to home, click Test Cases button, verify user is navigated to test cases page.")
    public void verifyTestCasesPageIsAccessible() {
        page().navigate(BASE_URL);
        page().waitForSelector("img[alt='Website for automation practice']");
        Assertions.assertThat(page().locator("img[alt='Website for automation practice']").isVisible())
            .as("Home page should be visible").isTrue();

        page().locator("a[href='/test_cases']").first().click();
        page().waitForLoadState();

        Assertions.assertThat(page().url())
            .as("URL should contain /test_cases").contains("/test_cases");
        page().waitForSelector("h2:has-text('Test Cases')");
        Assertions.assertThat(page().locator("h2:has-text('Test Cases')").isVisible())
            .as("Test Cases page heading should be visible").isTrue();
    }
}
