package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import io.qameta.allure.*;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Feature("Categories and Brands")
public class CategoryBrandTest extends BaseTest {

    // Test Case 18: View Category Products
    @Test(description = "View Category Products - Women Tops then Men")
    @Severity(SeverityLevel.NORMAL)
    @Story("Category Navigation")
    @Description("Verify categories sidebar, click Women > Tops, verify title, click Men subcategory, verify Men page.")
    public void viewCategoryProducts() {
        pages().homePage().open();
        Assertions.assertThat(pages().homePage().isCategoriesSidebarVisible())
                .as("Categories sidebar should be visible").isTrue();

        pages().homePage().clickWomenCategory().clickWomenTops();
        String womenTitle = pages().homePage().getCategoryPageTitle().toUpperCase();
        Assertions.assertThat(womenTitle)
                .as("Category page title should contain WOMEN").contains("WOMEN");
        Assertions.assertThat(womenTitle)
                .as("Category page title should contain TOPS").contains("TOPS");

        pages().homePage().open();
        pages().homePage().clickMenCategory().clickFirstMenSubcategory();
        String menTitle = pages().homePage().getCategoryPageTitle().toUpperCase();
        Assertions.assertThat(menTitle)
                .as("Men category page title should contain MEN").contains("MEN");
    }

    // Test Case 19: View and navigate Brand Products
    @Test(description = "View and navigate Brand Products")
    @Severity(SeverityLevel.NORMAL)
    @Story("Brand Navigation")
    @Description("Navigate to Products page, verify brands sidebar, click first brand, verify page, click second brand, verify page.")
    public void viewBrandProducts() {
        pages().productsPage().open();
        Assertions.assertThat(pages().productsPage().isBrandsSidebarVisible())
                .as("Brands section should be visible").isTrue();

        pages().productsPage().clickFirstBrand();
        String firstBrandTitle = pages().productsPage().getBrandPageTitle();
        Assertions.assertThat(firstBrandTitle)
                .as("Brand page title should contain 'Brand'").containsIgnoringCase("Brand");

        pages().productsPage().open();
        pages().productsPage().clickSecondBrand();
        String secondBrandTitle = pages().productsPage().getBrandPageTitle();
        Assertions.assertThat(secondBrandTitle)
                .as("Second brand page title should contain 'Brand'").containsIgnoringCase("Brand");
    }
}
