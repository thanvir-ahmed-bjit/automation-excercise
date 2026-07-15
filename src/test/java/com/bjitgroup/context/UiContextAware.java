package com.bjitgroup.context;

/**
 * Contract for tests that want listener-driven UI context injection.
 * <p>
 * Implementing this interface allows {@link com.bjitgroup.listeners.TestListener}
 * to inject a {@link UiTestContext} before each test method executes.
 * </p>
 */
public interface UiContextAware {

    /**
     * Set the UI test context. Called by the listener before test execution.
     * @param context the UiTestContext to be set
     */
    void setUiTestContext(UiTestContext context);

    /**
     * Get the UI test context.
     * @return the UiTestContext
     */
    UiTestContext getUiTestContext();
}

