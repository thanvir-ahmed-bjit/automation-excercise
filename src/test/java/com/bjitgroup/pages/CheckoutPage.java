package com.bjitgroup.pages;

import com.bjitgroup.actions.BrowserActions;
import com.bjitgroup.actions.InputActions;
import com.bjitgroup.context.PageManager;

import java.util.Properties;

import static com.bjitgroup.utils.PropertyReader.read;

public final class CheckoutPage extends BasePage {

    private final Properties loc = read("locators/checkout-page.properties");

    public CheckoutPage(BrowserActions browser, InputActions input, PageManager pages) {
        super(browser, input, pages);
    }

    public CheckoutPage waitUntilLoaded() {
        browser.waitForVisible(loc.getProperty("deliveryAddress"));
        return this;
    }

    public boolean isDeliveryAddressVisible() {
        return browser.isVisible(loc.getProperty("deliveryAddress"));
    }

    public boolean isBillingAddressVisible() {
        return browser.isVisible(loc.getProperty("billingAddress"));
    }

    public String getDeliveryAddressText() {
        browser.waitForVisible(loc.getProperty("deliveryAddress"));
        return browser.textOf(loc.getProperty("deliveryAddress"));
    }

    public String getBillingAddressText() {
        browser.waitForVisible(loc.getProperty("billingAddress"));
        return browser.textOf(loc.getProperty("billingAddress"));
    }

    public CheckoutPage fillComment(String comment) {
        input.fill(loc.getProperty("commentInput"), comment);
        return this;
    }

    public PaymentPage clickPlaceOrder() {
        input.click(loc.getProperty("placeOrderButton"));
        return pages.paymentPage().waitUntilLoaded();
    }
}
