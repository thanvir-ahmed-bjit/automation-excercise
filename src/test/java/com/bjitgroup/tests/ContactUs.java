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

    private static final String BASE_URL = "https://automationexercise.com";

    @Test(
            description = "User should be able to submit contact us form successfully",
            groups = "contact-us"
    )
    @Severity(SeverityLevel.NORMAL)
    @Story("Submit Contact Form")
    @Description("Navigate to Contact Us page, fill the form with file upload, submit and verify success.")
    public void userShouldSubmitContactUsFormSuccessfully() throws URISyntaxException {
        // Step 1-3: Navigate to home and verify
        page().navigate(BASE_URL);
        page().waitForSelector("img[alt='Website for automation practice']");
        Assertions.assertThat(page().url())
                .as("Home page should be loaded")
                .contains("automationexercise.com");

        // Step 4: Click Contact Us button
        page().locator("a[href='/contact_us']").first().click();
        page().waitForLoadState();

        // Step 5: Verify 'GET IN TOUCH' is visible
        page().waitForSelector("h2:has-text('GET IN TOUCH')");
        boolean getInTouchVisible = page()
                .locator("h2:has-text('GET IN TOUCH')")
                .isVisible();
        Assertions.assertThat(getInTouchVisible)
                .as("'GET IN TOUCH' heading should be visible")
                .isTrue();

        // Step 6: Enter name, email, subject and message
        page().fill("input[data-qa='name']", "Test User");
        page().fill("input[data-qa='email']", "testuser@example.com");
        page().fill("input[data-qa='subject']", "Automation Test Subject");
        page().fill("textarea[data-qa='message']", "This is an automated test message sent via Playwright.");

        // Step 7: Upload file
        Path uploadFile = Paths.get(
                getClass().getClassLoader()
                        .getResource("testdata/playwright commands text.txt")
                        .toURI()
        );
        page().locator("input[name='upload_file']").setInputFiles(uploadFile);

        // Step 8-9: Click Submit and accept the confirmation dialog (OK button)
        page().onDialog(dialog -> dialog.accept());
        page().locator("input[data-qa='submit-button']").click();

        // Step 10: Verify success message
        page().waitForSelector(".status.alert-success");
        String successText = page().locator(".status.alert-success").innerText();
        Assertions.assertThat(successText)
                .as("Success message should be displayed after form submission")
                .contains("Success! Your details have been submitted successfully.");

        // Step 11: Click Home and verify landing on home page
        page().locator("a:has-text('Home')").first().click();
        page().waitForSelector("img[alt='Website for automation practice']");
        Assertions.assertThat(page().url())
                .as("User should be navigated back to home page")
                .contains("automationexercise.com");
        Assertions.assertThat(page().locator("img[alt='Website for automation practice']").isVisible())
                .as("Home page logo should be visible")
                .isTrue();
    }
}
