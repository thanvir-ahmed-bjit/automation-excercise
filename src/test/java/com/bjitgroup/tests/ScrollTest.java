package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import io.qameta.allure.*;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Feature("Scroll")
public class ScrollTest extends BaseTest {

    // Test Case 25: Verify Scroll Up using 'Arrow' button
    @Test(description = "Verify Scroll Up using Arrow button")
    @Severity(SeverityLevel.NORMAL)
    @Story("Scroll Up Arrow")
    @Description("Navigate to home, scroll to bottom, verify SUBSCRIPTION, click scroll-up arrow, verify hero text visible.")
    public void verifyScrollUpUsingArrowButton() {
        pages().homePage().open();
        Assertions.assertThat(pages().homePage().isLoaded()).isTrue();

        pages().homePage().scrollToBottom();
        Assertions.assertThat(pages().homePage().isSubscriptionVisible())
                .as("SUBSCRIPTION heading should be visible at bottom").isTrue();

        pages().homePage().clickScrollUpArrow().waitForAnimation();

        Assertions.assertThat(pages().homePage().isHeroTextVisible())
                .as("Hero banner text should be visible after scrolling up").isTrue();
    }

    // Test Case 26: Verify Scroll Up without 'Arrow' button
    @Test(description = "Verify Scroll Up without Arrow button")
    @Severity(SeverityLevel.NORMAL)
    @Story("Scroll Up JavaScript")
    @Description("Navigate to home, scroll to bottom, verify SUBSCRIPTION, scroll up via JS, verify hero text visible.")
    public void verifyScrollUpWithoutArrowButton() {
        pages().homePage().open();
        Assertions.assertThat(pages().homePage().isLoaded()).isTrue();

        pages().homePage().scrollToBottom();
        Assertions.assertThat(pages().homePage().isSubscriptionVisible())
                .as("SUBSCRIPTION heading should be visible at bottom").isTrue();

        pages().homePage().scrollToTop().waitForAnimation();

        Assertions.assertThat(pages().homePage().isHeroTextVisible())
                .as("Hero banner text should be visible after JS scroll up").isTrue();
    }
}
