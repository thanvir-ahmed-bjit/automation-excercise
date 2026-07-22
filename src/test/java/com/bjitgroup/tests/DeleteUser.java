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

@Feature("Account")
public class DeleteUser extends BaseTest {

    // Test Case (E2E): Created user should be deleted at the end of flow
    @Test(
            description = "Created user should be deleted at the end of flow",
            dependsOnGroups = {"account-created", "product-added-logout"}
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Delete Account")
    @Description("Login with created user and delete the account.")
    public void userShouldBeDeleted() {
        pages().loginPage()
                .open()
                .loginAs(TestAccountStore.email(), TestAccountStore.password());

        pages().homePage().clickDeleteAccount();
        Assertions.assertThat(pages().homePage().getAccountDeletedMessage())
                .as("Delete account confirmation should be visible")
                .isEqualTo("ACCOUNT DELETED!");
    }
}
