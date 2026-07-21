package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import com.bjitgroup.utils.AccountHelper;
import io.qameta.allure.*;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Feature("Product Review")
public class ProductReviewTest extends BaseTest {

    private static final String BASE_URL = "https://automationexercise.com";

    @Test(description = "Search Products and Verify Cart After Login")
    @Severity(SeverityLevel.NORMAL)
    @Story("Search and Cart")
    @Description("Create account, logout, search product as guest, add to cart, login, verify cart still has product.")
    public void searchProductsAndVerifyCartAfterLogin() {
        String[] account = AccountHelper.createAccount(page());
        String email = account[1];
        String password = account[2];

        // Logout
        page().locator("a[href='/logout']").first().click();
        page().waitForSelector("input[data-qa='login-email']");

        // Navigate to Products, search
        page().navigate(BASE_URL + "/products");
        page().waitForSelector("input#search_product");
        page().fill("input#search_product", "Top");
        page().locator("button#submit_search").click();
        page().waitForSelector("h2:has-text('Searched Products')");
        Assertions.assertThat(page().locator("h2:has-text('Searched Products')").isVisible())
            .as("Searched Products heading should be visible").isTrue();

        // Add first search result to cart
        page().locator(".product-image-wrapper").first().hover();
        page().locator(".product-image-wrapper").first().locator("a.add-to-cart").first().click();
        page().waitForSelector("div#cartModal");
        page().locator("button:has-text('Continue Shopping')").click();

        // Go to cart, verify product
        page().navigate(BASE_URL + "/view_cart");
        page().waitForSelector("#cart_info tbody tr");
        Assertions.assertThat(page().locator("#cart_info tbody tr").count())
            .as("Cart should have at least 1 product").isGreaterThan(0);

        // Login
        page().locator("a[href='/login']").first().click();
        pages().loginPage().waitUntilLoaded().attemptLogin(email, password);
        page().waitForSelector("//a[contains(normalize-space(),'Logged in as')]");

        // Verify cart still has product
        page().navigate(BASE_URL + "/view_cart");
        page().waitForLoadState();
        Assertions.assertThat(page().locator("#cart_info tbody tr").count())
            .as("Cart should still have product after login").isGreaterThan(0);

        AccountHelper.deleteAccount(page());
    }

    @Test(description = "Add review on product")
    @Severity(SeverityLevel.NORMAL)
    @Story("Product Review")
    @Description("Navigate to first product detail page, write a review, submit, verify success message.")
    public void addReviewOnProduct() {
        page().navigate(BASE_URL + "/products");
        page().waitForSelector(".choose a");
        page().locator(".choose a").first().click();
        page().waitForSelector("div.product-information");

        // Verify Write Your Review section
        page().waitForSelector("a:has-text('Write Your Review')");
        Assertions.assertThat(page().locator("a:has-text('Write Your Review')").isVisible())
            .as("'Write Your Review' section should be visible").isTrue();

        // Fill review form
        page().fill("input#name", "Review Tester");
        page().fill("input#email", "reviewer." + System.currentTimeMillis() + "@test.example.com");
        page().fill("textarea#review", "This is an automated test review. Great product!");

        page().locator("button#button-review").click();

        page().waitForSelector("div.alert-success");
        Assertions.assertThat(page().locator("div.alert-success").first().innerText())
            .as("Review success message should be displayed")
            .contains("Thank you for your review.");
    }
}
