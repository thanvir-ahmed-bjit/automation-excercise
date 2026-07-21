package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import io.qameta.allure.*;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Feature("Products")
public class ProductTest extends BaseTest {

    private static final String BASE_URL = "https://automationexercise.com";

    @Test(description = "Verify All Products and product detail page")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Product Listing and Detail")
    @Description("Navigate to Products page, verify all products listed, click first product, verify detail fields.")
    public void verifyAllProductsAndProductDetailPage() {
        page().navigate(BASE_URL + "/products");
        page().waitForSelector("h2:has-text('All Products')");
        Assertions.assertThat(page().locator("h2:has-text('All Products')").isVisible())
            .as("All Products heading should be visible").isTrue();

        Assertions.assertThat(page().locator(".product-image-wrapper").count())
            .as("Product list should have items").isGreaterThan(0);

        // Click View Product for first product
        page().locator(".choose a").first().click();
        page().waitForSelector("div.product-information");

        Assertions.assertThat(page().locator("div.product-information h2").isVisible())
            .as("Product name should be visible").isTrue();
        Assertions.assertThat(page().locator("div.product-information p:has-text('Category')").first().isVisible())
            .as("Category should be visible").isTrue();
        Assertions.assertThat(page().locator("div.product-information span:has-text('Rs.')").first().isVisible())
            .as("Price should be visible").isTrue();
        Assertions.assertThat(page().locator("div.product-information p:has-text('Availability')").first().isVisible())
            .as("Availability should be visible").isTrue();
        Assertions.assertThat(page().locator("div.product-information p:has-text('Condition')").first().isVisible())
            .as("Condition should be visible").isTrue();
        Assertions.assertThat(page().locator("div.product-information p:has-text('Brand')").first().isVisible())
            .as("Brand should be visible").isTrue();
    }

    @Test(description = "Search Product and verify results")
    @Severity(SeverityLevel.NORMAL)
    @Story("Product Search")
    @Description("Navigate to Products page, search 'dress', verify SEARCHED PRODUCTS heading and results visible.")
    public void searchProductShouldShowResults() {
        page().navigate(BASE_URL + "/products");
        page().waitForSelector("input#search_product");

        page().fill("input#search_product", "dress");
        page().locator("button#submit_search").click();
        page().waitForSelector("h2:has-text('Searched Products')");

        Assertions.assertThat(page().locator("h2:has-text('Searched Products')").isVisible())
            .as("Searched Products heading should be visible").isTrue();
        Assertions.assertThat(page().locator(".product-image-wrapper").count())
            .as("Search results should have product items").isGreaterThan(0);
    }
}
