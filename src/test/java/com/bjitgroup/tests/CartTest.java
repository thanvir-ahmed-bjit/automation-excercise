package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import io.qameta.allure.*;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Feature("Cart")
public class CartTest extends BaseTest {

    // Test Case 12: Add Products in Cart
    @Test(description = "Add Products in Cart - hover and add two products")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Add to Cart")
    @Description("Hover first product, add to cart, continue shopping, hover second product, add to cart, view cart, verify both products present.")
    public void addProductsToCartAndVerify() {
        pages().homePage().open();
        pages().homePage().clickProducts();

        pages().productsPage().hoverAndAddToCart(0).clickContinueShopping();
        pages().productsPage().hoverAndAddToCart(1).clickViewCartInModal();

        Assertions.assertThat(pages().cartPage().getCartItemCount())
                .as("Cart should have at least 2 products").isGreaterThanOrEqualTo(2);
        Assertions.assertThat(pages().cartPage().isFirstItemPriceVisible())
                .as("Price should be visible for cart item").isTrue();
        Assertions.assertThat(pages().cartPage().isFirstItemQuantityVisible())
                .as("Quantity should be visible for cart item").isTrue();
        Assertions.assertThat(pages().cartPage().isFirstItemTotalVisible())
                .as("Total should be visible for cart item").isTrue();
    }

    // Test Case 13: Verify Product quantity in Cart
    @Test(description = "Verify Product quantity in Cart is 4")
    @Severity(SeverityLevel.NORMAL)
    @Story("Cart Quantity")
    @Description("Navigate to product detail page, set quantity to 4, add to cart, verify cart shows quantity 4.")
    public void verifyProductQuantityInCart() {
        pages().productDetailPage().openProductById(1);
        pages().productDetailPage().setQuantity("4").clickAddToCart();
        pages().productDetailPage().clickViewCart();

        Assertions.assertThat(pages().cartPage().getFirstItemQuantity())
                .as("Cart quantity should be 4").isEqualTo("4");
    }

    // Test Case 17: Remove Products From Cart
    @Test(description = "Remove Products From Cart")
    @Severity(SeverityLevel.NORMAL)
    @Story("Remove from Cart")
    @Description("Add a product to cart, navigate to cart, delete the item, verify cart is empty.")
    public void removeProductFromCart() {
        pages().homePage().open();
        pages().homePage().clickProducts();

        pages().productsPage().hoverAndAddToCart(0).clickViewCartInModal();

        int initialCount = pages().cartPage().getCartItemCount();
        Assertions.assertThat(initialCount).as("Cart should have at least 1 item").isGreaterThan(0);

        pages().cartPage().deleteFirstItem();

        Assertions.assertThat(pages().cartPage().getCartItemCount())
                .as("Item count should decrease after deletion").isLessThan(initialCount);
    }

    // Test Case 22: Add to cart from Recommended Items
    @Test(description = "Add to cart from Recommended Items")
    @Severity(SeverityLevel.NORMAL)
    @Story("Recommended Items")
    @Description("Scroll to recommended items on home page, add first item to cart, verify product in cart.")
    public void addToCartFromRecommendedItems() {
        pages().homePage().open().scrollToBottom();
        Assertions.assertThat(pages().homePage().isRecommendedItemsVisible())
                .as("Recommended Items section should be visible").isTrue();

        pages().homePage().clickFirstRecommendedAddToCart();
        pages().homePage().clickViewCartInModal();

        Assertions.assertThat(pages().cartPage().getCartItemCount())
                .as("Cart should have at least 1 product from recommended").isGreaterThan(0);
    }
}
