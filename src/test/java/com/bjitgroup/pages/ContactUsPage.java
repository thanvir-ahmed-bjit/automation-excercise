package com.bjitgroup.pages;

import com.bjitgroup.actions.BrowserActions;
import com.bjitgroup.actions.InputActions;
import com.bjitgroup.context.PageManager;

import java.nio.file.Path;
import java.util.Properties;

import static com.bjitgroup.utils.PropertyReader.read;

/**
 * Contact Us page for Automation Exercise.
 */
public final class ContactUsPage extends BasePage {

    private final Properties loc = read("locators/contact-us-page.properties");

    public ContactUsPage(
            BrowserActions browser,
            InputActions input,
            PageManager pages
    ) {
        super(browser, input, pages);
    }

    public ContactUsPage waitUntilLoaded() {
        browser.waitForVisible(loc.getProperty("getInTouchHeading"));
        return this;
    }

    public boolean isGetInTouchVisible() {
        return browser.isVisible(loc.getProperty("getInTouchHeading"));
    }

    public ContactUsPage enterName(String name) {
        input.fill(loc.getProperty("nameInput"), name);
        return this;
    }

    public ContactUsPage enterEmail(String email) {
        input.fill(loc.getProperty("emailInput"), email);
        return this;
    }

    public ContactUsPage enterSubject(String subject) {
        input.fill(loc.getProperty("subjectInput"), subject);
        return this;
    }

    public ContactUsPage enterMessage(String message) {
        input.fill(loc.getProperty("messageTextarea"), message);
        return this;
    }

    public ContactUsPage uploadFile(Path filePath) {
        input.uploadFile(loc.getProperty("uploadFileInput"), filePath);
        return this;
    }

    public ContactUsPage clickSubmit() {
        browser.acceptNextDialog();
        input.click(loc.getProperty("submitButton"));
        return this;
    }

    public String getSuccessMessage() {
        String selector = loc.getProperty("successMessage");
        browser.waitForVisible(selector);
        return browser.textOf(selector);
    }

    public ContactUsPage clickHome() {
        input.click(loc.getProperty("homeButton"));
        return this;
    }

    public boolean isHomeLogoVisible() {
        return browser.isVisible(loc.getProperty("homeLogo"));
    }
}
