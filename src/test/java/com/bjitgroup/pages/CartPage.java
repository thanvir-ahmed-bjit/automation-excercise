package com.bjitgroup.pages;

import com.bjitgroup.actions.BrowserActions;
import com.bjitgroup.actions.InputActions;
import com.bjitgroup.context.PageManager;

import java.util.Properties;

import static com.bjitgroup.utils.PropertyReader.read;

public final class CartPage extends BasePage {

    private static final String URL = "https://automationexercise.com/view_cart";
    private final Properties loc = read("locators/cart-page.properties");

    public CartPage(BrowserActions browser, InputActions input, PageManager pages) {
        super(browser, input, pages);
    }

    public CartPage open() {
        browser.navigate(URL);
        return waitUntilLoaded();
    }

    public CartPage waitUntilLoaded() {
        browser.waitForUrlContains("/view_cart");   // ← added
        browser.waitForPageLoad();
        return this;
    }

    public boolean isOnCartPage() {
        return browser.currentUrl().contains("/view_cart");
    }

    public int getCartItemCount() {
        return browser.count(loc.getProperty("cartRows"));
    }

    public CartPage deleteFirstItem() {
        input.click(browser.locator(loc.getProperty("deleteButton")).first());
        browser.waitMs(500);
        return this;
    }

    public String getFirstItemQuantity() {
        return browser.locator(loc.getProperty("itemQuantity")).first().innerText().trim();
    }

    public boolean isFirstItemPriceVisible() {
        return browser.locator(loc.getProperty("itemPrice")).first().isVisible();
    }

    public boolean isFirstItemQuantityVisible() {
        return browser.locator(loc.getProperty("itemQuantity")).first().isVisible();
    }

    public boolean isFirstItemTotalVisible() {
        return browser.locator(loc.getProperty("itemTotal")).first().isVisible();
    }

    public CartPage clickProceedToCheckout() {
        browser.waitForVisible(loc.getProperty("proceedToCheckoutButton"));
        input.click(loc.getProperty("proceedToCheckoutButton"));
        return this;
    }

    public LoginPage clickRegisterLoginInModal() {
        browser.waitForVisible(loc.getProperty("checkoutModalRegisterLink"));
        input.click(loc.getProperty("checkoutModalRegisterLink"));
        return pages.loginPage().waitUntilLoaded();
    }

    public CartPage scrollToBottom() {
        browser.evaluate("window.scrollTo(0, document.body.scrollHeight)");
        return this;
    }

    public boolean isSubscriptionVisible() {
        return browser.isVisible(loc.getProperty("subscriptionHeading"));
    }

    public CartPage enterSubscriptionEmail(String email) {
        input.fill(loc.getProperty("subscriptionEmailInput"), email);
        return this;
    }

    public CartPage clickSubscribe() {
        input.click(loc.getProperty("subscribeButton"));
        return this;
    }

    public String getSubscriptionSuccessMessage() {
        browser.waitForVisible(loc.getProperty("subscriptionSuccess"));
        return browser.textOf(loc.getProperty("subscriptionSuccess"));
    }
}
