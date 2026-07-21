package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import io.qameta.allure.*;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Feature("Categories and Brands")
public class CategoryBrandTest extends BaseTest {

    private static final String BASE_URL = "https://automationexercise.com";

    @Test(description = "View Category Products - Women Tops then Men")
    @Severity(SeverityLevel.NORMAL)
    @Story("Category Navigation")
    @Description("Verify categories sidebar, click Women > Tops, verify title, click Men subcategory, verify Men page.")
    public void viewCategoryProducts() {
        page().navigate(BASE_URL);
        page().waitForSelector("a[href='#Women']");
        Assertions.assertThat(page().locator("#accordian").isVisible())
            .as("Categories sidebar should be visible").isTrue();

        // Click Women
        page().locator("a[href='#Women']").click();
        page().waitForSelector("#Women .panel-body");

        // Click Tops under Women
        page().locator("#Women .panel-body a:has-text('Tops')").click();
        page().waitForLoadState();

        String womenTitle = page().locator("h2.title").innerText().toUpperCase();
        Assertions.assertThat(womenTitle)
            .as("Category page title should contain WOMEN").contains("WOMEN");
        Assertions.assertThat(womenTitle)
            .as("Category page title should contain TOPS").contains("TOPS");

        // Click Men
        page().navigate(BASE_URL);
        page().waitForSelector("a[href='#Men']");
        page().locator("a[href='#Men']").click();
        page().waitForSelector("#Men .panel-body");

        // Click first Men subcategory
        page().locator("#Men .panel-body a").first().click();
        page().waitForLoadState();

        String menTitle = page().locator("h2.title").innerText().toUpperCase();
        Assertions.assertThat(menTitle)
            .as("Men category page title should contain MEN").contains("MEN");
    }

    @Test(description = "View and navigate Brand Products")
    @Severity(SeverityLevel.NORMAL)
    @Story("Brand Navigation")
    @Description("Navigate to Products page, verify brands sidebar, click first brand, verify page, click second brand, verify page.")
    public void viewBrandProducts() {
        page().navigate(BASE_URL + "/products");
        page().waitForSelector(".brands_products .brands-name");
        page().waitForSelector(".brands_products .brands-name li a");
        Assertions.assertThat(page().locator(".brands_products .brands-name").isVisible())
            .as("Brands section should be visible").isTrue();

        // Click first brand
        page().locator(".brands_products .brands-name li:first-child a").click();
        page().waitForSelector("h2.title");

        String firstBrandTitle = page().locator("h2.title").innerText();
        Assertions.assertThat(firstBrandTitle)
            .as("Brand page title should contain 'Brand'").containsIgnoringCase("Brand");

        // Navigate back to products and click second brand
        page().navigate(BASE_URL + "/products");
        page().waitForSelector(".brands_products .brands-name li a");

        page().locator(".brands_products .brands-name li a").nth(1).click();
        page().waitForSelector("h2.title");

        String secondBrandTitle = page().locator("h2.title").innerText();
        Assertions.assertThat(secondBrandTitle)
            .as("Second brand page title should contain 'Brand'").containsIgnoringCase("Brand");
    }
}
