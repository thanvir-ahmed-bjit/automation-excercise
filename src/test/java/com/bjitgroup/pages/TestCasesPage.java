package com.bjitgroup.pages;

import com.bjitgroup.actions.BrowserActions;
import com.bjitgroup.actions.InputActions;
import com.bjitgroup.context.PageManager;

import java.util.Properties;

import static com.bjitgroup.utils.PropertyReader.read;

public final class TestCasesPage extends BasePage {

    private final Properties loc = read("locators/test-cases-page.properties");

    public TestCasesPage(BrowserActions browser, InputActions input, PageManager pages) {
        super(browser, input, pages);
    }

    public TestCasesPage waitUntilLoaded() {
        browser.waitForVisible(loc.getProperty("pageTitle"));
        return this;
    }

    public boolean isLoaded() {
        return browser.isVisible(loc.getProperty("pageTitle"));
    }
}
