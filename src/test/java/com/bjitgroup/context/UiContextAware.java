package com.bjitgroup.context;

/**
 * Contract for tests that want listener-driven UI context injection.
 */
public interface UiContextAware {

    void setUiTestContext(UiTestContext context);

    UiTestContext getUiTestContext();
}

