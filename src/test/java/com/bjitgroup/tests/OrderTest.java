package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import com.bjitgroup.utils.AccountHelper;
import com.microsoft.playwright.Download;
import io.qameta.allure.*;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Feature("Order")
public class OrderTest extends BaseTest {

    private void addFirstProductToCart() {
        pages().productsPage().open();
        pages().productsPage().hoverAndAddToCart(0).clickContinueShopping();
    }

    private void completePayment() {
        pages().paymentPage().waitUntilLoaded()
                .fillCardName("Test User")
                .fillCardNumber("4111111111111111")
                .fillCvc("123")
                .fillExpiryMonth("12")
                .fillExpiryYear("2030")
                .clickPay();
    }

    // Test Case 14: Place Order: Register while Checkout
    @Test(description = "Place Order - Register while Checkout")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Order Placement")
    @Description("Add product as guest, proceed to checkout, register via modal, checkout, pay, verify success, delete account.")
    public void placeOrderRegisterWhileCheckout() {
        addFirstProductToCart();

        pages().cartPage().open().clickProceedToCheckout();
        pages().cartPage().clickRegisterLoginInModal();

        AccountHelper.createAccount(page());

        pages().cartPage().open().clickProceedToCheckout();
        Assertions.assertThat(pages().checkoutPage().isDeliveryAddressVisible())
                .as("Delivery address should be visible").isTrue();

        pages().checkoutPage().fillComment("Test order comment").clickPlaceOrder();
        completePayment();

        Assertions.assertThat(pages().orderConfirmationPage().isSuccessMessageVisible())
                .as("Order success message should be visible").isTrue();

        AccountHelper.deleteAccount(page());
    }

    // Test Case 15: Place Order: Register before Checkout
    @Test(description = "Place Order - Register before Checkout")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Order Placement")
    @Description("Register first, add product to cart, checkout, pay, verify success, delete account.")
    public void placeOrderRegisterBeforeCheckout() {
        AccountHelper.createAccount(page());

        addFirstProductToCart();

        pages().cartPage().open().clickProceedToCheckout();
        Assertions.assertThat(pages().checkoutPage().isDeliveryAddressVisible())
                .as("Delivery address should be visible").isTrue();
        Assertions.assertThat(pages().checkoutPage().isBillingAddressVisible())
                .as("Billing address should be visible").isTrue();

        pages().checkoutPage().fillComment("Test order comment").clickPlaceOrder();
        completePayment();

        Assertions.assertThat(pages().orderConfirmationPage().isSuccessMessageVisible())
                .as("Order success message should be visible").isTrue();

        AccountHelper.deleteAccount(page());
    }

    // Test Case 16: Place Order: Login before Checkout
    @Test(description = "Place Order - Login before Checkout")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Order Placement")
    @Description("Create account, logout, login again, add product, checkout, pay, verify success, delete account.")
    public void placeOrderLoginBeforeCheckout() {
        String[] account = AccountHelper.createAccount(page());
        String email = account[1];
        String password = account[2];

        pages().homePage().clickLogout();
        pages().loginPage().waitUntilLoaded();

        pages().homePage().open();
        pages().homePage().clickSignupLogin();
        pages().loginPage().waitUntilLoaded().attemptLogin(email, password);
        pages().homePage().waitUntilLoaded();

        addFirstProductToCart();

        pages().cartPage().open().clickProceedToCheckout();
        Assertions.assertThat(pages().checkoutPage().isDeliveryAddressVisible())
                .as("Delivery address should be visible").isTrue();

        pages().checkoutPage().fillComment("Test order comment").clickPlaceOrder();
        completePayment();

        Assertions.assertThat(pages().orderConfirmationPage().isSuccessMessageVisible())
                .as("Order success message should be visible").isTrue();

        AccountHelper.deleteAccount(page());
    }

    // Test Case 23: Verify address details in checkout page
    @Test(description = "Verify address details in checkout page match signup data")
    @Severity(SeverityLevel.NORMAL)
    @Story("Checkout Address")
    @Description("Create account, add product, checkout, verify delivery and billing address contain signup name.")
    public void verifyAddressDetailsInCheckout() {
        AccountHelper.createAccount(page());

        addFirstProductToCart();

        pages().cartPage().open().clickProceedToCheckout();
        String deliveryAddress = pages().checkoutPage().getDeliveryAddressText();
        String billingAddress = pages().checkoutPage().getBillingAddressText();

        Assertions.assertThat(deliveryAddress)
                .as("Delivery address should contain first name").contains("Test");
        Assertions.assertThat(deliveryAddress)
                .as("Delivery address should contain last name").contains("User");
        Assertions.assertThat(billingAddress)
                .as("Billing address should contain first name").contains("Test");
        Assertions.assertThat(billingAddress)
                .as("Billing address should contain last name").contains("User");

        AccountHelper.deleteAccount(page());
    }

    // Test Case 24: Download Invoice after purchase order
    @Test(description = "Download Invoice after purchase order")
    @Severity(SeverityLevel.NORMAL)
    @Story("Download Invoice")
    @Description("Add product as guest, register via checkout modal, complete order, download invoice, delete account.")
    public void downloadInvoiceAfterPurchase() {
        addFirstProductToCart();

        pages().cartPage().open().clickProceedToCheckout();
        pages().cartPage().clickRegisterLoginInModal();

        AccountHelper.createAccount(page());

        pages().cartPage().open().clickProceedToCheckout();
        pages().checkoutPage().fillComment("Test invoice order").clickPlaceOrder();
        completePayment();

        Assertions.assertThat(pages().orderConfirmationPage().isSuccessMessageVisible())
                .as("Order success message should be visible").isTrue();

        Download download = pages().orderConfirmationPage().downloadInvoice();
        Assertions.assertThat(download.suggestedFilename())
                .as("Downloaded file name should not be empty").isNotEmpty();

        pages().orderConfirmationPage().clickContinue();
        AccountHelper.deleteAccount(page());
    }
}
