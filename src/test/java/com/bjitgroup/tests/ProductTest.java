package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import io.qameta.allure.*;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Feature("Products")
public class ProductTest extends BaseTest {

    // Test Case 8: Verify All Products and product detail page
    @Test(description = "Verify All Products and product detail page")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Product Listing and Detail")
    @Description("Navigate to Products page, verify all products listed, click first product, verify detail fields.")
    public void verifyAllProductsAndProductDetailPage() {
        pages().homePage().open();
        pages().homePage().clickProducts();
        Assertions.assertThat(pages().productsPage().isLoaded())
                .as("All Products heading should be visible").isTrue();

        Assertions.assertThat(pages().productsPage().getProductCount())
                .as("Product list should have items").isGreaterThan(0);

        pages().productsPage().clickViewProduct(0);

        Assertions.assertThat(pages().productDetailPage().isProductNameVisible())
                .as("Product name should be visible").isTrue();
        Assertions.assertThat(pages().productDetailPage().isCategoryVisible())
                .as("Category should be visible").isTrue();
        Assertions.assertThat(pages().productDetailPage().isPriceVisible())
                .as("Price should be visible").isTrue();
        Assertions.assertThat(pages().productDetailPage().isAvailabilityVisible())
                .as("Availability should be visible").isTrue();
        Assertions.assertThat(pages().productDetailPage().isConditionVisible())
                .as("Condition should be visible").isTrue();
        Assertions.assertThat(pages().productDetailPage().isBrandVisible())
                .as("Brand should be visible").isTrue();
    }

    // Test Case 9: Search Product
    @Test(description = "Search Product and verify results")
    @Severity(SeverityLevel.NORMAL)
    @Story("Product Search")
    @Description("Navigate to Products page, search 'dress', verify SEARCHED PRODUCTS heading and results visible.")
    public void searchProductShouldShowResults() {
        pages().homePage().open();
        pages().homePage().clickProducts();
        pages().productsPage().searchProduct("dress");

        Assertions.assertThat(pages().productsPage().isSearchedProductsVisible())
                .as("Searched Products heading should be visible").isTrue();
        Assertions.assertThat(pages().productsPage().getProductCount())
                .as("Search results should have product items").isGreaterThan(0);
    }
}
