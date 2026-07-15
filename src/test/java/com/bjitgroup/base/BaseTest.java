package com.bjitgroup.base;

import com.bjitgroup.context.PageManager;
import com.bjitgroup.context.UiContextAware;
import com.bjitgroup.context.UiTestContext;
import com.bjitgroup.utils.CustomLogger;
import com.microsoft.playwright.Page;
import io.qameta.allure.testng.AllureTestNg;
import org.slf4j.Logger;
import org.testng.annotations.Listeners;

import java.util.Objects;

/**
 * Abstract base class for all UI tests.
 * <p>
 * Implements {@link UiContextAware} to receive listener-managed browser session injection.
 * Uses a {@link ThreadLocal} to store the per-thread {@link UiTestContext}, ensuring
 * complete isolation between parallel test executions running in the same JVM.
 * </p>
 * <p>
 * Lifecycle is managed entirely by {@link com.bjitgroup.listeners.TestListener}:
 * <ul>
 *   <li>One Playwright instance per test method invocation</li>
 *   <li>One Browser instance per test method invocation</li>
 *   <li>One BrowserContext per test method invocation</li>
 *   <li>One Page per test method invocation</li>
 *   <li>Context injected via {@link #setUiTestContext} before the test body runs</li>
 *   <li>Resources closed and ThreadLocal cleared via {@link #clearUiTestContext} after
 *       test completion, regardless of outcome</li>
 * </ul>
 * </p>
 * <p>
 * This class must not start a browser, close a browser, capture screenshots,
 * manage tracing, manage video, or contain {@code @BeforeMethod}/{@code @AfterMethod}
 * lifecycle methods.
 * </p>
 */
@Listeners({AllureTestNg.class, com.bjitgroup.listeners.TestListener.class})
public abstract class BaseTest implements UiContextAware {

    /** Logger available to every concrete test class. */
    protected final Logger log = CustomLogger.getLogger(getClass());

    private final ThreadLocal<UiTestContext> contextHolder = new ThreadLocal<>();

    // -----------------------------------------------------------------------
    // UiContextAware implementation
    // -----------------------------------------------------------------------

    @Override
    public void setUiTestContext(UiTestContext context) {
        contextHolder.set(
                Objects.requireNonNull(context, "UiTestContext must not be null")
        );
    }

    @Override
    public UiTestContext getUiTestContext() {
        UiTestContext context = contextHolder.get();
        if (context == null) {
            throw new IllegalStateException(
                    "UiTestContext has not been initialized for the current test thread"
            );
        }
        return context;
    }

    @Override
    public void clearUiTestContext() {
        contextHolder.remove();
    }

    // -----------------------------------------------------------------------
    // Convenience helpers for concrete test classes
    // -----------------------------------------------------------------------

    /**
     * Returns the per-thread {@link UiTestContext}.
     *
     * @throws IllegalStateException if the context has not been initialized
     */
    protected UiTestContext context() {
        return getUiTestContext();
    }

    /**
     * Returns the Playwright {@link Page} for the current test.
     *
     * @throws IllegalStateException if the context has not been initialized
     */
    protected Page page() {
        return getUiTestContext().page();
    }

    /**
     * Returns the {@link PageManager} for creating typed page objects.
     *
     * @throws IllegalStateException if the context has not been initialized
     */
    protected PageManager pages() {
        return getUiTestContext().pages();
    }
}
