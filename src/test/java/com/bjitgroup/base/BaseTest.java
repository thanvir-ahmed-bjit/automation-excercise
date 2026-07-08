package com.bjitgroup.base;

import com.bjitgroup.config.ConfigManager;
import com.bjitgroup.context.PageManager;
import com.bjitgroup.driver.DriverFactory;
import com.bjitgroup.listeners.TestListener;
import com.bjitgroup.pages.LoginPage;
import com.bjitgroup.reports.AllureManager;
import com.bjitgroup.utils.CustomLogger;
import com.bjitgroup.utils.DateUtils;
import com.bjitgroup.utils.ScreenshotUtils;
import com.microsoft.playwright.Page;
import io.qameta.allure.testng.AllureTestNg;
import org.assertj.core.api.SoftAssertions;
import org.slf4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

/**
 * Abstract base class for all tests.
 *
 * <p>Responsibilities:
 * <ul>
 *   <li>Spin up a Playwright browser per test method (thread-safe)</li>
 *   <li>Open the base URL and expose a typed {@link LoginPage}</li>
 *   <li>On failure - capture screenshot and attach to Allure</li>
 *   <li>Flush {@link SoftAssertions} after every method</li>
 *   <li>Tear down cleanly on success or failure</li>
 * </ul>
 */
@Listeners({AllureTestNg.class, TestListener.class})
public abstract class BaseTest {

    protected final Logger log = CustomLogger.getLogger(getClass());
    protected ConfigManager config;
    protected Page page;
    protected LoginPage loginPage;
    protected PageManager pageManager;
    protected SoftAssertions softly;

    // Suite-level lifecycle

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        config = ConfigManager.getInstance();
        log.info("Suite starting  env={}  browser={}  baseUrl={}",
                config.environment(), config.browser(), config.baseUrl());
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        log.info("Suite finished");
    }

    // Method-level lifecycle

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        DriverFactory.init();
        page = DriverFactory.getPage();
        pageManager = new PageManager(page, config.timeoutMs());
        loginPage = pageManager.loginPage().open();
        softly = new SoftAssertions();
        log.info("Test initialised");
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        // Screenshot on failure
        if (!result.isSuccess() && page != null) {
            String name = result.getMethod().getMethodName()
                    + "_" + DateUtils.timestamp();
            var shot = ScreenshotUtils.take(page, name);
            AllureManager.attachScreenshot("Failure Screenshot", shot);
        }

        // Flush soft assertions (throws if any failed)
        if (softly != null) {
            softly.assertAll();
        }

        // Tear down the browser
        DriverFactory.quit(result.getMethod().getMethodName());
    }
}
