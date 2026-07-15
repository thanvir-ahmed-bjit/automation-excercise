package com.bjitgroup.context;

/**
 * Contract for tests that want listener-driven UI context injection.
 * <p>
 * Implementing this interface allows {@link com.bjitgroup.listeners.TestListener}
 * to inject a {@link UiTestContext} before each test method executes and
 * deterministically release the per-thread context after each test.
 * </p>
 */
public interface UiContextAware {

    /**
     * Set the UI test context. Called by the listener before test execution.
     * Implementations must reject a {@code null} argument via
     * {@link java.util.Objects#requireNonNull}.
     *
     * @param context the UiTestContext to set; must not be null
     */
    void setUiTestContext(UiTestContext context);

    /**
     * Get the UI test context for the current thread.
     *
     * @return the UiTestContext
     * @throws IllegalStateException if the context has not been initialized
     */
    UiTestContext getUiTestContext();

    /**
     * Release the UI test context for the current thread.
     * Called by the listener after test completion to prevent stale
     * ThreadLocal references and avoid context leaks between retries.
     */
    void clearUiTestContext();
}
