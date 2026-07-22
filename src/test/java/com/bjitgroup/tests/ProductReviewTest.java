package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import com.bjitgroup.utils.AccountHelper;
import io.qameta.allure.*;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Feature("Product Review")
public class ProductReviewTest extends BaseTest {

    // Test Case 20: Search Products and Verify Cart After Login
    @Test(description = "Search Products and Verify Cart After Login")
    @Severity(SeverityLevel.NORMAL)
    @Story("Search and Cart")
    @Description("Create account, logout, search product as guest, add to cart, login, verify cart still has product.")
    public void searchProductsAndVerifyCartAfterLogin() {
        String[] account = AccountHelper.createAccount(page());
        String email = account[1];
        String password = account[2];

        pages().homePage().clickLogout();
        pages().loginPage().waitUntilLoaded();

        pages().productsPage().open();
        pages().productsPage().searchProduct("Top");
        Assertions.assertThat(pages().productsPage().isSearchedProductsVisible())
                .as("Searched Products heading should be visible").isTrue();

        pages().productsPage().hoverAndAddToCart(0).clickContinueShopping();

        pages().cartPage().open();
        Assertions.assertThat(pages().cartPage().getCartItemCount())
                .as("Cart should have at least 1 product").isGreaterThan(0);

        pages().homePage().open();
        pages().homePage().clickSignupLogin();
        pages().loginPage().waitUntilLoaded().attemptLogin(email, password);
        pages().homePage().waitUntilLoaded();

        pages().cartPage().open();
        Assertions.assertThat(pages().cartPage().getCartItemCount())
                .as("Cart should still have product after login").isGreaterThan(0);

        AccountHelper.deleteAccount(page());
    }

    // Test Case 21: Add review on product
    @Test(description = "Add review on product")
    @Severity(SeverityLevel.NORMAL)
    @Story("Product Review")
    @Description("Navigate to first product detail page, write a review, submit, verify success message.")
    public void addReviewOnProduct() {
        pages().homePage().open();
        pages().homePage().clickProducts();
        pages().productsPage().clickViewProduct(0);

        Assertions.assertThat(pages().productDetailPage().isWriteReviewVisible())
                .as("'Write Your Review' section should be visible").isTrue();

        pages().productDetailPage()
                .fillReview(
                        "Review Tester",
                        "reviewer." + System.currentTimeMillis() + "@test.example.com",
                        "This is an automated test review. Great product!"
                )
                .submitReview();

        Assertions.assertThat(pages().productDetailPage().getReviewSuccessMessage())
                .as("Review success message should be displayed")
                .contains("Thank you for your review.");
    }
}
