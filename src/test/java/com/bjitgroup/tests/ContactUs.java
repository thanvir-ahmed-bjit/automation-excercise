package com.bjitgroup.tests;

import com.bjitgroup.base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Feature("Contact Us")
public class ContactUs extends BaseTest {

    // Test Case 6: Contact Us Form
    @Test(
            description = "User should be able to submit contact us form successfully",
            groups = "contact-us"
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Submit Contact Form")
    @Description("Navigate to Contact Us page, fill the form with file upload, submit and verify success.")
    public void userShouldSubmitContactUsFormSuccessfully() throws URISyntaxException {
        pages().homePage().open();
        Assertions.assertThat(pages().homePage().isLoaded())
                .as("Home page should be loaded").isTrue();

        pages().homePage().clickContactUs();
        Assertions.assertThat(pages().contactUsPage().isGetInTouchVisible())
                .as("'GET IN TOUCH' heading should be visible").isTrue();

        Path uploadFile = Paths.get(
                getClass().getClassLoader()
                        .getResource("testdata/playwright commands text.txt")
                        .toURI()
        );

        pages().contactUsPage()
                .enterName("Test User")
                .enterEmail("testuser@example.com")
                .enterSubject("Automation Test Subject")
                .enterMessage("This is an automated test message sent via Playwright.")
                .uploadFile(uploadFile)
                .clickSubmit();

        Assertions.assertThat(pages().contactUsPage().getSuccessMessage())
                .as("Success message should be displayed after form submission")
                .contains("Success! Your details have been submitted successfully.");

        pages().contactUsPage().clickHome();
        Assertions.assertThat(pages().contactUsPage().isHomeLogoVisible())
                .as("Home page logo should be visible").isTrue();
    }
}
