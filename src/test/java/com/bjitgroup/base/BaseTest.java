package com.bjitgroup.base;

import com.bjitgroup.context.PageManager;
import com.bjitgroup.context.UiContextAware;
import com.bjitgroup.context.UiTestContext;
import com.bjitgroup.utils.CustomLogger;
import com.microsoft.playwright.Page;
import io.qameta.allure.testng.AllureTestNg;
import org.slf4j.Logger;
import org.testng.annotations.Listeners;

/**
 * Abstract base class for all UI tests.
 * <p>
 * Implements {@link UiContextAware} to receive listener-managed browser session injection.
 * Provides convenient access to the page and page objects.
 * </p>
 * <p>
 * Lifecycle is managed by {@link com.bjitgroup.listeners.TestListener}:
 * <ul>
 *   <li>One Playwright instance per test</li>
 *   <li>One Browser instance per test</li>
 *   <li>One BrowserContext per test</li>
 *   <li>One Page per test (initially)</li>
 *   <li>Context injected before test execution</li>
 *   <li>Resources closed after test completion</li>
 * </ul>
 * </p>
 */
@Listeners({AllureTestNg.class, com.bjitgroup.listeners.TestListener.class})
public abstract class BaseTest implements UiContextAware {

    protected final Logger log = CustomLogger.getLogger(getClass());
    protected UiTestContext context;

    @Override
    public void setUiTestContext(UiTestContext context) {
        this.context = context;
    }

    @Override
    public UiTestContext getUiTestContext() {
        if (context == null) {
            throw new IllegalStateException(
                    "UiTestContext has not been initialized"
            );
        }
        return context;
    }

    /**
     * Get the current page.
     * @return the Playwright Page from the test context
     */
    protected Page page() {
        return getUiTestContext().page();
    }

    /**
     * Get the page object manager.
     * @return the PageManager for creating typed page objects
     */
    protected PageManager pages() {
        return getUiTestContext().pages();
    }
}
