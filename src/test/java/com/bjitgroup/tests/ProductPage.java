package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import com.bjitgroup.context.TestAccountStore;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Feature("Products")
public class ProductPage extends BaseTest {

    private static final String BASE_URL = "https://automationexercise.com";

    @Test(
            description = "Created user should be able to login and visit product list",
            dependsOnGroups = "account-created",
            groups = "products-visited"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Visit Products")
    @Description("Login with created account and open the products list page.")
    public void userShouldVisitProductList() {
        pages().loginPage()
                .open()
                .loginAs(TestAccountStore.email(), TestAccountStore.password());

        page().navigate(BASE_URL + "/products");
        page().waitForSelector("//h2[normalize-space()='All Products']");

        Assertions.assertThat(page().url())
                .as("Products page URL should be opened")
                .contains("/products");
    }
}
