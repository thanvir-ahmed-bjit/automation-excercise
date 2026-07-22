package com.bjitgroup.pages;

import com.bjitgroup.actions.BrowserActions;
import com.bjitgroup.actions.InputActions;
import com.bjitgroup.context.PageManager;

import java.util.Properties;

import static com.bjitgroup.utils.PropertyReader.read;

public final class PaymentPage extends BasePage {

    private final Properties loc = read("locators/payment-page.properties");

    public PaymentPage(BrowserActions browser, InputActions input, PageManager pages) {
        super(browser, input, pages);
    }

    public PaymentPage waitUntilLoaded() {
        browser.waitForVisible(loc.getProperty("nameOnCardInput"));
        return this;
    }

    public PaymentPage fillCardName(String name) {
        input.fill(loc.getProperty("nameOnCardInput"), name);
        return this;
    }

    public PaymentPage fillCardNumber(String number) {
        input.fill(loc.getProperty("cardNumberInput"), number);
        return this;
    }

    public PaymentPage fillCvc(String cvc) {
        input.fill(loc.getProperty("cvcInput"), cvc);
        return this;
    }

    public PaymentPage fillExpiryMonth(String month) {
        input.fill(loc.getProperty("expiryMonthInput"), month);
        return this;
    }

    public PaymentPage fillExpiryYear(String year) {
        input.fill(loc.getProperty("expiryYearInput"), year);
        return this;
    }

    public OrderConfirmationPage clickPay() {
        input.click(loc.getProperty("payButton"));
        return pages.orderConfirmationPage().waitUntilLoaded();
    }
}
