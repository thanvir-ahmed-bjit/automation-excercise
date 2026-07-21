package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import io.qameta.allure.*;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Feature("Scroll")
public class ScrollTest extends BaseTest {

    private static final String BASE_URL = "https://automationexercise.com";

    @Test(description = "Verify Scroll Up using Arrow button")
    @Severity(SeverityLevel.NORMAL)
    @Story("Scroll Up Arrow")
    @Description("Navigate to home, scroll to bottom, verify SUBSCRIPTION, click scroll-up arrow, verify hero text visible.")
    public void verifyScrollUpUsingArrowButton() {
        page().navigate(BASE_URL);
        page().waitForSelector("img[alt='Website for automation practice']");

        // Scroll to bottom
        page().evaluate("window.scrollTo(0, document.body.scrollHeight)");
        page().waitForSelector("h2:has-text('SUBSCRIPTION')");
        Assertions.assertThat(page().locator("h2:has-text('SUBSCRIPTION')").isVisible())
            .as("SUBSCRIPTION heading should be visible at bottom").isTrue();

        // Click scroll up arrow
        page().waitForSelector("a#scrollUp");
        page().locator("a#scrollUp").click();
        page().waitForTimeout(1000);

        page().waitForSelector("h2:has-text('Full-Fledged practice website for Automation Engineers')");
        Assertions.assertThat(
            page().locator("h2:has-text('Full-Fledged practice website for Automation Engineers')").first().isVisible()
        ).as("Hero banner text should be visible after scrolling up").isTrue();
    }

    @Test(description = "Verify Scroll Up without Arrow button")
    @Severity(SeverityLevel.NORMAL)
    @Story("Scroll Up JavaScript")
    @Description("Navigate to home, scroll to bottom, verify SUBSCRIPTION, scroll up via JS, verify hero text visible.")
    public void verifyScrollUpWithoutArrowButton() {
        page().navigate(BASE_URL);
        page().waitForSelector("img[alt='Website for automation practice']");

        // Scroll to bottom
        page().evaluate("window.scrollTo(0, document.body.scrollHeight)");
        page().waitForSelector("h2:has-text('SUBSCRIPTION')");
        Assertions.assertThat(page().locator("h2:has-text('SUBSCRIPTION')").isVisible())
            .as("SUBSCRIPTION heading should be visible at bottom").isTrue();

        // Scroll up via JavaScript
        page().evaluate("window.scrollTo(0, 0)");
        page().waitForTimeout(1000);

        page().waitForSelector("h2:has-text('Full-Fledged practice website for Automation Engineers')");
        Assertions.assertThat(
            page().locator("h2:has-text('Full-Fledged practice website for Automation Engineers')").first().isVisible()
        ).as("Hero banner text should be visible after JS scroll up").isTrue();
    }
}
