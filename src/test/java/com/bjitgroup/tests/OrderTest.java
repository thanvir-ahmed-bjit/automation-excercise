package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import com.bjitgroup.utils.AccountHelper;
import com.microsoft.playwright.Download;
import io.qameta.allure.*;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Feature("Order")
public class OrderTest extends BaseTest {

    private static final String BASE_URL = "https://automationexercise.com";

    private void addFirstProductToCart() {
        page().navigate(BASE_URL + "/products");
        page().waitForSelector(".product-image-wrapper");
        page().locator(".product-image-wrapper").first().hover();
        page().locator(".product-image-wrapper").first().locator("a.add-to-cart").click();
        page().waitForSelector("div#cartModal");
        page().locator("button:has-text('Continue Shopping')").click();
    }

    private void completePayment() {
        page().fill("input[data-qa='name-on-card']", "Test User");
        page().fill("input[data-qa='card-number']", "4111111111111111");
        page().fill("input[data-qa='cvc']", "123");
        page().fill("input[data-qa='expiry-month']", "12");
        page().fill("input[data-qa='expiry-year']", "2030");
        page().locator("button[data-qa='pay-button']").click();
    }

    @Test(description = "Place Order - Register while Checkout")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Order Placement")
    @Description("Add product as guest, proceed to checkout, register via modal, checkout, pay, verify success, delete account.")
    public void placeOrderRegisterWhileCheckout() {
        // Add product as guest
        addFirstProductToCart();

        // Go to cart
        page().navigate(BASE_URL + "/view_cart");
        page().waitForSelector("a:has-text('Proceed To Checkout')");
        page().locator("a:has-text('Proceed To Checkout')").click();

        // Modal for not-logged-in user
        page().waitForSelector(".modal-body a[href='/login']");
        page().locator(".modal-body a[href='/login']").click();

        // Create account
        AccountHelper.createAccount(page());

        // Back to cart and checkout
        page().navigate(BASE_URL + "/view_cart");
        page().waitForSelector("a:has-text('Proceed To Checkout')");
        page().locator("a:has-text('Proceed To Checkout')").click();

        page().waitForSelector("#address_delivery");
        Assertions.assertThat(page().locator("#address_delivery").isVisible())
            .as("Delivery address should be visible").isTrue();

        page().fill("textarea.form-control", "Test order comment");
        page().locator("a:has-text('Place Order')").click();

        page().waitForSelector("input[data-qa='name-on-card']");
        completePayment();

        page().waitForSelector("p:has-text('Congratulations')");
        Assertions.assertThat(page().locator("p:has-text('Congratulations')").isVisible())
            .as("Order success message should be visible").isTrue();

        AccountHelper.deleteAccount(page());
    }

    @Test(description = "Place Order - Register before Checkout")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Order Placement")
    @Description("Register first, add product to cart, checkout, pay, verify success, delete account.")
    public void placeOrderRegisterBeforeCheckout() {
        AccountHelper.createAccount(page());

        addFirstProductToCart();

        page().navigate(BASE_URL + "/view_cart");
        page().waitForSelector("a:has-text('Proceed To Checkout')");
        page().locator("a:has-text('Proceed To Checkout')").click();

        page().waitForSelector("#address_delivery");
        Assertions.assertThat(page().locator("#address_delivery").isVisible())
            .as("Delivery address should be visible").isTrue();
        Assertions.assertThat(page().locator("#address_invoice").isVisible())
            .as("Billing address should be visible").isTrue();

        page().fill("textarea.form-control", "Test order comment");
        page().locator("a:has-text('Place Order')").click();

        page().waitForSelector("input[data-qa='name-on-card']");
        completePayment();

        page().waitForSelector("p:has-text('Congratulations')");
        Assertions.assertThat(page().locator("p:has-text('Congratulations')").isVisible())
            .as("Order success message should be visible").isTrue();

        AccountHelper.deleteAccount(page());
    }

    @Test(description = "Place Order - Login before Checkout")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Order Placement")
    @Description("Create account, logout, login again, add product, checkout, pay, verify success, delete account.")
    public void placeOrderLoginBeforeCheckout() {
        String[] account = AccountHelper.createAccount(page());
        String email = account[1];
        String password = account[2];

        // Logout
        page().locator("a[href='/logout']").first().click();
        page().waitForSelector("input[data-qa='login-email']");

        // Login
        page().navigate(BASE_URL);
        page().locator("a[href='/login']").first().click();
        pages().loginPage().waitUntilLoaded().attemptLogin(email, password);
        page().waitForSelector("//a[contains(normalize-space(),'Logged in as')]");

        addFirstProductToCart();

        page().navigate(BASE_URL + "/view_cart");
        page().waitForSelector("a:has-text('Proceed To Checkout')");
        page().locator("a:has-text('Proceed To Checkout')").click();

        page().waitForSelector("#address_delivery");
        Assertions.assertThat(page().locator("#address_delivery").isVisible())
            .as("Delivery address should be visible").isTrue();

        page().fill("textarea.form-control", "Test order comment");
        page().locator("a:has-text('Place Order')").click();

        page().waitForSelector("input[data-qa='name-on-card']");
        completePayment();

        page().waitForSelector("p:has-text('Congratulations')");
        Assertions.assertThat(page().locator("p:has-text('Congratulations')").isVisible())
            .as("Order success message should be visible").isTrue();

        AccountHelper.deleteAccount(page());
    }

    @Test(description = "Verify address details in checkout page match signup data")
    @Severity(SeverityLevel.NORMAL)
    @Story("Checkout Address")
    @Description("Create account, add product, checkout, verify delivery and billing address contain signup name.")
    public void verifyAddressDetailsInCheckout() {
        AccountHelper.createAccount(page());

        addFirstProductToCart();

        page().navigate(BASE_URL + "/view_cart");
        page().waitForSelector("a:has-text('Proceed To Checkout')");
        page().locator("a:has-text('Proceed To Checkout')").click();

        page().waitForSelector("#address_delivery");
        String deliveryAddress = page().locator("#address_delivery").innerText();
        String billingAddress = page().locator("#address_invoice").innerText();

        Assertions.assertThat(deliveryAddress)
            .as("Delivery address should contain first name").contains("Test");
        Assertions.assertThat(deliveryAddress)
            .as("Delivery address should contain last name").contains("User");
        Assertions.assertThat(billingAddress)
            .as("Billing address should contain first name").contains("Test");
        Assertions.assertThat(billingAddress)
            .as("Billing address should contain last name").contains("User");

        page().navigate(BASE_URL);
        AccountHelper.deleteAccount(page());
    }

    @Test(description = "Download Invoice after purchase order")
    @Severity(SeverityLevel.NORMAL)
    @Story("Download Invoice")
    @Description("Add product as guest, register via checkout modal, complete order, download invoice, delete account.")
    public void downloadInvoiceAfterPurchase() {
        // Add product as guest
        addFirstProductToCart();

        page().navigate(BASE_URL + "/view_cart");
        page().waitForSelector("a:has-text('Proceed To Checkout')");
        page().locator("a:has-text('Proceed To Checkout')").click();

        // Modal for guest
        page().waitForSelector(".modal-body a[href='/login']");
        page().locator(".modal-body a[href='/login']").click();

        AccountHelper.createAccount(page());

        page().navigate(BASE_URL + "/view_cart");
        page().waitForSelector("a:has-text('Proceed To Checkout')");
        page().locator("a:has-text('Proceed To Checkout')").click();

        page().waitForSelector("#address_delivery");
        page().fill("textarea.form-control", "Test invoice order");
        page().locator("a:has-text('Place Order')").click();

        page().waitForSelector("input[data-qa='name-on-card']");
        completePayment();

        page().waitForSelector("p:has-text('Congratulations')");
        Assertions.assertThat(page().locator("p:has-text('Congratulations')").isVisible())
            .as("Order success message should be visible").isTrue();

        // Download invoice
        Download download = page().waitForDownload(() ->
            page().locator("a.btn-default:has-text('Download Invoice')").click()
        );
        Assertions.assertThat(download.suggestedFilename())
            .as("Downloaded file name should not be empty").isNotEmpty();

        page().locator("a[data-qa='continue-button']").click();
        AccountHelper.deleteAccount(page());
    }
}
