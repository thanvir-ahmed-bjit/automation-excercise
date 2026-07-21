package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import io.qameta.allure.*;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Feature("Subscription")
public class SubscriptionTest extends BaseTest {

    private static final String BASE_URL = "https://automationexercise.com";

    @Test(description = "Verify Subscription in home page")
    @Severity(SeverityLevel.NORMAL)
    @Story("Home Page Subscription")
    @Description("Navigate to home, scroll to footer, enter email, verify subscription success message.")
    public void verifySubscriptionOnHomePage() {
        page().navigate(BASE_URL);
        page().waitForSelector("img[alt='Website for automation practice']");

        page().evaluate("window.scrollTo(0, document.body.scrollHeight)");
        page().waitForSelector("h2:has-text('SUBSCRIPTION')");
        Assertions.assertThat(page().locator("h2:has-text('SUBSCRIPTION')").isVisible())
            .as("SUBSCRIPTION heading should be visible in footer").isTrue();

        String email = "subscribe." + System.currentTimeMillis() + "@test.example.com";
        page().fill("#susbscribe_email", email);
        page().locator("#subscribe").click();

        page().waitForSelector("div.alert-success");
        Assertions.assertThat(page().locator("div.alert-success").isVisible())
            .as("Subscription success message should be visible").isTrue();
        Assertions.assertThat(page().locator("div.alert-success").innerText())
            .as("Success message content").contains("You have been successfully subscribed!");
    }

    @Test(description = "Verify Subscription in Cart page")
    @Severity(SeverityLevel.NORMAL)
    @Story("Cart Page Subscription")
    @Description("Navigate to cart, scroll to footer, enter email, verify subscription success message.")
    public void verifySubscriptionOnCartPage() {
        page().navigate(BASE_URL + "/view_cart");
        page().waitForLoadState();

        page().evaluate("window.scrollTo(0, document.body.scrollHeight)");
        page().waitForSelector("h2:has-text('SUBSCRIPTION')");
        Assertions.assertThat(page().locator("h2:has-text('SUBSCRIPTION')").isVisible())
            .as("SUBSCRIPTION heading should be visible on cart page footer").isTrue();

        String email = "subscribe." + System.currentTimeMillis() + "@test.example.com";
        page().fill("#susbscribe_email", email);
        page().locator("#subscribe").click();

        page().waitForSelector("div.alert-success");
        Assertions.assertThat(page().locator("div.alert-success").isVisible())
            .as("Subscription success message should be visible").isTrue();
        Assertions.assertThat(page().locator("div.alert-success").innerText())
            .as("Success message content").contains("You have been successfully subscribed!");
    }
}
