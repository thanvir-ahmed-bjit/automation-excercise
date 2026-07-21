package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import io.qameta.allure.*;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Feature("Cart")
public class CartTest extends BaseTest {

    private static final String BASE_URL = "https://automationexercise.com";

    @Test(description = "Add Products in Cart - hover and add two products")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Add to Cart")
    @Description("Hover first product, add to cart, continue shopping, hover second product, add to cart, view cart, verify both products present.")
    public void addProductsToCartAndVerify() {
        page().navigate(BASE_URL + "/products");
        page().waitForSelector(".product-image-wrapper");

        // Hover first product and add to cart
        page().locator(".product-image-wrapper").first().hover();
        page().locator(".product-image-wrapper").first().locator("a.add-to-cart").first().click();
        page().waitForSelector("div#cartModal");
        page().locator("button:has-text('Continue Shopping')").click();

        // Hover second product and add to cart
        page().locator(".product-image-wrapper").nth(1).hover();
        page().locator(".product-image-wrapper").nth(1).locator("a.add-to-cart").first().click();
        page().waitForSelector("div#cartModal");
        page().locator("div#cartModal a[href='/view_cart']").click();

        page().waitForSelector("#cart_info tbody tr");
        int rowCount = page().locator("#cart_info tbody tr").count();
        Assertions.assertThat(rowCount).as("Cart should have at least 2 products").isGreaterThanOrEqualTo(2);

        // Verify price is visible for first item
        Assertions.assertThat(page().locator("td.cart_price p").first().isVisible())
            .as("Price should be visible for cart item").isTrue();
        Assertions.assertThat(page().locator("td.cart_quantity button").first().isVisible())
            .as("Quantity should be visible for cart item").isTrue();
        Assertions.assertThat(page().locator("td.cart_total p").first().isVisible())
            .as("Total should be visible for cart item").isTrue();
    }

    @Test(description = "Verify Product quantity in Cart is 4")
    @Severity(SeverityLevel.NORMAL)
    @Story("Cart Quantity")
    @Description("Navigate to product detail page, set quantity to 4, add to cart, verify cart shows quantity 4.")
    public void verifyProductQuantityInCart() {
        page().navigate(BASE_URL + "/product_details/1");
        page().waitForSelector("#quantity");

        page().locator("#quantity").fill("4");
        page().locator("button:has-text('Add to cart')").click();
        page().waitForSelector("div#cartModal");
        page().locator("div#cartModal a[href='/view_cart']").click();

        page().waitForSelector("#cart_info tbody tr");
        String quantity = page().locator("td.cart_quantity button").first().innerText().trim();
        Assertions.assertThat(quantity).as("Cart quantity should be 4").isEqualTo("4");
    }

    @Test(description = "Remove Products From Cart")
    @Severity(SeverityLevel.NORMAL)
    @Story("Remove from Cart")
    @Description("Add a product to cart, navigate to cart, delete the item, verify cart is empty.")
    public void removeProductFromCart() {
        page().navigate(BASE_URL + "/products");
        page().waitForSelector(".product-image-wrapper");

        page().locator(".product-image-wrapper").first().hover();
        page().locator(".product-image-wrapper").first().locator("a.add-to-cart").first().click();
        page().waitForSelector("div#cartModal");
        page().locator("div#cartModal a[href='/view_cart']").click();

        page().waitForSelector("#cart_info tbody tr");
        int initialCount = page().locator("#cart_info tbody tr").count();
        Assertions.assertThat(initialCount).as("Cart should have at least 1 item").isGreaterThan(0);

        page().locator("a.cart_quantity_delete").first().click();
        page().waitForTimeout(1000);

        int afterCount = page().locator("#cart_info tbody tr").count();
        Assertions.assertThat(afterCount).as("Item count should decrease after deletion")
            .isLessThan(initialCount);
    }

    @Test(description = "Add to cart from Recommended Items")
    @Severity(SeverityLevel.NORMAL)
    @Story("Recommended Items")
    @Description("Scroll to recommended items on home page, add first item to cart, verify product in cart.")
    public void addToCartFromRecommendedItems() {
        page().navigate(BASE_URL);
        page().waitForSelector("img[alt='Website for automation practice']");

        page().evaluate("window.scrollTo(0, document.body.scrollHeight)");
        page().waitForSelector(".recommended_items");
        Assertions.assertThat(page().locator(".recommended_items").isVisible())
            .as("Recommended Items section should be visible").isTrue();

        page().locator(".recommended_items a[data-product-id]").first().click();
        page().waitForSelector("div#cartModal");
        page().locator("div#cartModal a[href='/view_cart']").click();

        page().waitForSelector("#cart_info tbody tr");
        Assertions.assertThat(page().locator("#cart_info tbody tr").count())
            .as("Cart should have at least 1 product from recommended").isGreaterThan(0);
    }
}
