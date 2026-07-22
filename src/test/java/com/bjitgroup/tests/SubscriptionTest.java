package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import io.qameta.allure.*;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Feature("Subscription")
public class SubscriptionTest extends BaseTest {

    // Test Case 10: Verify Subscription in home page
    @Test(description = "Verify Subscription in home page")
    @Severity(SeverityLevel.NORMAL)
    @Story("Home Page Subscription")
    @Description("Navigate to home, scroll to footer, enter email, verify subscription success message.")
    public void verifySubscriptionOnHomePage() {
        pages().homePage().open();
        Assertions.assertThat(pages().homePage().isLoaded()).isTrue();

        pages().homePage().scrollToBottom();
        Assertions.assertThat(pages().homePage().isSubscriptionVisible())
                .as("SUBSCRIPTION heading should be visible in footer").isTrue();

        String email = "subscribe." + System.currentTimeMillis() + "@test.example.com";
        pages().homePage().enterSubscriptionEmail(email).clickSubscribe();

        Assertions.assertThat(pages().homePage().getSubscriptionSuccessMessage())
                .as("Success message content").contains("You have been successfully subscribed!");
    }

    // Test Case 11: Verify Subscription in Cart page
    @Test(description = "Verify Subscription in Cart page")
    @Severity(SeverityLevel.NORMAL)
    @Story("Cart Page Subscription")
    @Description("Navigate to cart, scroll to footer, enter email, verify subscription success message.")
    public void verifySubscriptionOnCartPage() {
        pages().cartPage().open();

        pages().cartPage().scrollToBottom();
        Assertions.assertThat(pages().cartPage().isSubscriptionVisible())
                .as("SUBSCRIPTION heading should be visible on cart page footer").isTrue();

        String email = "subscribe." + System.currentTimeMillis() + "@test.example.com";
        pages().cartPage().enterSubscriptionEmail(email).clickSubscribe();

        Assertions.assertThat(pages().cartPage().getSubscriptionSuccessMessage())
                .as("Success message content").contains("You have been successfully subscribed!");
    }
}
