package com.bjitgroup.pages;

import com.bjitgroup.actions.BrowserActions;
import com.bjitgroup.actions.InputActions;
import com.bjitgroup.context.PageManager;
import com.microsoft.playwright.Download;

import java.util.Properties;

import static com.bjitgroup.utils.PropertyReader.read;

public final class OrderConfirmationPage extends BasePage {

    private final Properties loc = read("locators/order-confirmation-page.properties");

    public OrderConfirmationPage(BrowserActions browser, InputActions input, PageManager pages) {
        super(browser, input, pages);
    }

    public OrderConfirmationPage waitUntilLoaded() {
        browser.waitForVisible(loc.getProperty("successMessage"));
        return this;
    }

    public boolean isSuccessMessageVisible() {
        return browser.isVisible(loc.getProperty("successMessage"));
    }

    public String getSuccessMessage() {
        browser.waitForVisible(loc.getProperty("successMessage"));
        return browser.textOf(loc.getProperty("successMessage"));
    }

    public Download downloadInvoice() {
        return browser.waitForDownload(() -> input.click(loc.getProperty("downloadInvoiceButton")));
    }

    public HomePage clickContinue() {
        input.click(loc.getProperty("continueButton"));
        return pages.homePage().waitUntilLoaded();
    }
}
