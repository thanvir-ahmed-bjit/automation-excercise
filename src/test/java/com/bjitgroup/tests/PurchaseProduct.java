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

    private static final String BASE_URL = "https://automationexercise.com";

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

        page().navigate(BASE_URL + "/products");
        page().locator("a.add-to-cart[data-product-id='1']").first().click();
        page().locator("div#cartModal a[href='/view_cart']").click();

        page().locator("a:has-text('Proceed To Checkout')").first().click();
        page().locator("a:has-text('Place Order')").first().click();

        page().fill("input[data-qa='name-on-card']", TestAccountStore.name());
        page().fill("input[data-qa='card-number']", "4111111111111111");
        page().fill("input[data-qa='cvc']", "123");
        page().fill("input[data-qa='expiry-month']", "12");
        page().fill("input[data-qa='expiry-year']", "2030");
        page().locator("button[data-qa='pay-button']").click();

        String successMessage = page()
                .locator("p:has-text('Congratulations! Your order has been confirmed!')")
                .innerText();

        Assertions.assertThat(successMessage)
                .as("Order confirmation message should be shown after payment")
                .contains("Congratulations! Your order has been confirmed!");
    }

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

        page().navigate(BASE_URL + "/products");
        page().locator("a.add-to-cart[data-product-id='1']").first().click();
        page().locator("div#cartModal a[href='/view_cart']").click();

        Assertions.assertThat(page().url())
                .as("User should be on cart page after adding a product")
                .contains("/view_cart");

        page().locator("a[href='/logout']").first().click();

        Assertions.assertThat(page().url())
                .as("User should be redirected to login page after logout")
                .contains("/login");
    }
}
