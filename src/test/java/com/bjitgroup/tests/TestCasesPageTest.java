package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import io.qameta.allure.*;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Feature("Test Cases Page")
public class TestCasesPageTest extends BaseTest {

    // Test Case 7: Verify Test Cases Page
    @Test(description = "Verify Test Cases Page is accessible")
    @Severity(SeverityLevel.NORMAL)
    @Story("Test Cases Navigation")
    @Description("Navigate to home, click Test Cases button, verify user is navigated to test cases page.")
    public void verifyTestCasesPageIsAccessible() {
        pages().homePage().open();
        Assertions.assertThat(pages().homePage().isLoaded())
                .as("Home page should be visible").isTrue();

        pages().homePage().clickTestCases();
        Assertions.assertThat(pages().testCasesPage().isLoaded())
                .as("Test Cases page heading should be visible").isTrue();
    }
}
