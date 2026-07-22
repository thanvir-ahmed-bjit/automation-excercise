package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import com.bjitgroup.context.TestAccountStore;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Feature("Purchase")
public class PurchaseProduct extends BaseTest {

    // Test Case (E2E): Created user should purchase a product successfully
    @Test(
            description = "Created user should purchase a product successfully",
            dependsOnGroups = {"account-created", "products-visited"},
            groups = "purchase-complete"
    )
    @Severity(SeverityLevel.BLOCKER)
    @Story("Complete Purchase")
    @Description("Login, add product to cart, checkout, place order, and complete payment.")
    public void userShouldPurchaseProduct() {
        pages().loginPage()
                .open()
                .loginAs(TestAccountStore.email(), TestAccountStore.password());

        pages().productsPage().open();
        pages().productsPage().addProductByIdToCart("1");
        pages().productsPage().clickViewCartInModal();

        pages().cartPage().clickProceedToCheckout();
        pages().checkoutPage().clickPlaceOrder();

        pages().paymentPage().waitUntilLoaded()
                .fillCardName(TestAccountStore.name())
                .fillCardNumber("4111111111111111")
                .fillCvc("123")
                .fillExpiryMonth("12")
                .fillExpiryYear("2030")
                .clickPay();

        Assertions.assertThat(pages().orderConfirmationPage().getSuccessMessage())
                .as("Order confirmation message should be shown after payment")
                .contains("Congratulations! Your order has been confirmed!");
    }

    // Test Case (E2E): Created user should add a product and logout
    @Test(
            description = "Created user should add a product and logout",
            dependsOnGroups = {"account-created", "products-visited"},
            groups = "product-added-logout"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Add Product And Logout")
    @Description("Login, add a product to cart, then logout before account deletion flow.")
    public void userShouldAddProductAndLogout() {
        pages().loginPage()
                .open()
                .loginAs(TestAccountStore.email(), TestAccountStore.password());

        pages().productsPage().open();
        pages().productsPage().addProductByIdToCart("1");
        pages().productsPage().clickViewCartInModal();

        Assertions.assertThat(pages().cartPage().isOnCartPage())
                .as("User should be on cart page after adding a product").isTrue();

        pages().homePage().clickLogout();
        Assertions.assertThat(pages().loginPage().isLoginPageDisplayed())
                .as("User should be redirected to login page after logout").isTrue();
    }
}
