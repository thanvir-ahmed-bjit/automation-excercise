package com.bjitgroup.pages;

import com.bjitgroup.actions.BrowserActions;
import com.bjitgroup.actions.InputActions;
import com.bjitgroup.context.PageManager;
import com.microsoft.playwright.Locator;

import java.util.Properties;

import static com.bjitgroup.utils.PropertyReader.read;

public final class ProductsPage extends BasePage {

    private static final String URL = "https://automationexercise.com/products";
    private final Properties loc = read("locators/products-page.properties");

    public ProductsPage(BrowserActions browser, InputActions input, PageManager pages) {
        super(browser, input, pages);
    }

    public ProductsPage open() {
        browser.navigate(URL);
        return waitUntilLoaded();
    }

    public ProductsPage waitUntilLoaded() {
        browser.waitForVisible(loc.getProperty("allProductsHeading"));
        return this;
    }

    public boolean isLoaded() {
        return browser.isVisible(loc.getProperty("allProductsHeading"));
    }

    public int getProductCount() {
        return browser.count(loc.getProperty("productCards"));
    }

    public ProductDetailPage clickViewProduct(int index) {
        input.click(browser.locator(loc.getProperty("viewProductLink")).nth(index));
        return pages.productDetailPage().waitUntilLoaded();
    }

    public ProductsPage hoverAndAddToCart(int productIndex) {
        Locator productCard = browser.locator(loc.getProperty("productCards")).nth(productIndex);
        input.hover(productCard);
        input.click(productCard.locator(loc.getProperty("addToCartButton")).first());
        browser.waitForVisible(loc.getProperty("cartModal"));
        return this;
    }

    public ProductsPage addProductByIdToCart(String productId) {
        input.click(browser.locator("a.add-to-cart[data-product-id='" + productId + "']").first());
        browser.waitForVisible(loc.getProperty("cartModal"));
        return this;
    }

    public ProductsPage clickContinueShopping() {
        browser.waitForVisible(loc.getProperty("continueShoppingButton"));
        input.click(loc.getProperty("continueShoppingButton"));
        return this;
    }

    public CartPage clickViewCartInModal() {
        browser.waitForVisible(loc.getProperty("viewCartLink"));
        input.click(loc.getProperty("viewCartLink"));
        return pages.cartPage();
    }

    public ProductsPage searchProduct(String term) {
        input.fill(loc.getProperty("searchInput"), term);
        input.click(loc.getProperty("searchButton"));
        browser.waitForVisible(loc.getProperty("searchedProductsHeading"));
        return this;
    }

    public boolean isSearchedProductsVisible() {
        return browser.isVisible(loc.getProperty("searchedProductsHeading"));
    }

    public boolean isBrandsSidebarVisible() {
        return browser.isVisible(loc.getProperty("brandsSidebar"));
    }

    public ProductsPage clickFirstBrand() {
        browser.waitForVisible(loc.getProperty("firstBrandLink"));
        input.click(loc.getProperty("firstBrandLink"));
        browser.waitForUrlContains("/brand_products/");
        browser.waitForVisible(loc.getProperty("brandPageTitle"));
        return this;
    }

    public ProductsPage clickSecondBrand() {
        browser.waitForVisible(loc.getProperty("secondBrandLink"));
        input.click(loc.getProperty("secondBrandLink"));
        browser.waitForUrlContains("/brand_products/");
        browser.waitForVisible(loc.getProperty("brandPageTitle"));
        return this;
    }

    public String getBrandPageTitle() {
        browser.waitForVisible(loc.getProperty("brandPageTitle"));
        return browser.textOf(loc.getProperty("brandPageTitle"));
    }
}
