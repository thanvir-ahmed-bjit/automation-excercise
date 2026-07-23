package com.bjitgroup.pages;

import com.bjitgroup.actions.BrowserActions;
import com.bjitgroup.actions.InputActions;
import com.bjitgroup.context.PageManager;

import java.util.Properties;

import static com.bjitgroup.utils.PropertyReader.read;

public final class ProductDetailPage extends BasePage {

    private static final String BASE_URL = "https://automationexercise.com/product_details/";
    private final Properties loc = read("locators/product-detail-page.properties");

    public ProductDetailPage(BrowserActions browser, InputActions input, PageManager pages) {
        super(browser, input, pages);
    }

    public ProductDetailPage openProductById(int id) {
        browser.navigate(BASE_URL + id);
        return waitUntilLoaded();
    }

    public ProductDetailPage waitUntilLoaded() {
        browser.waitForVisible(loc.getProperty("productInfoContainer"));
        browser.waitForPageLoad();
        return this;
    }

    public boolean isProductNameVisible() {
        return browser.isVisible(loc.getProperty("productName"));
    }

    public boolean isCategoryVisible() {
        return browser.locator(loc.getProperty("categoryField")).first().isVisible();
    }

    public boolean isPriceVisible() {
        return browser.locator(loc.getProperty("priceField")).first().isVisible();
    }

    public boolean isAvailabilityVisible() {
        return browser.locator(loc.getProperty("availabilityField")).first().isVisible();
    }

    public boolean isConditionVisible() {
        return browser.locator(loc.getProperty("conditionField")).first().isVisible();
    }

    public boolean isBrandVisible() {
        return browser.locator(loc.getProperty("brandField")).first().isVisible();
    }

    public ProductDetailPage setQuantity(String quantity) {
        input.clear(loc.getProperty("quantityInput"));
        input.fill(loc.getProperty("quantityInput"), quantity);
        return this;
    }

    public ProductDetailPage clickAddToCart() {
        input.click(loc.getProperty("addToCartButton"));
        browser.waitForVisible(loc.getProperty("cartModal"));
        return this;
    }

    public CartPage clickViewCart() {
        browser.waitForVisible(loc.getProperty("viewCartLink"));
        input.click(loc.getProperty("viewCartLink"));
        return pages.cartPage();
    }

    public boolean isWriteReviewVisible() {
        return browser.isVisible(loc.getProperty("writeReviewLink"));
    }

    public ProductDetailPage fillReview(String name, String email, String text) {
        input.fill(loc.getProperty("reviewNameInput"), name);
        input.fill(loc.getProperty("reviewEmailInput"), email);
        input.fill(loc.getProperty("reviewTextarea"), text);
        return this;
    }

    public ProductDetailPage submitReview() {
        browser.waitForPageLoad();          // ← added
        input.click(loc.getProperty("submitReviewButton"));
        return this;
    }

    public String getReviewSuccessMessage() {
        browser.waitForVisible(loc.getProperty("reviewSuccess"));
        return browser.locator(loc.getProperty("reviewSuccess")).first().innerText().trim();
    }
}
