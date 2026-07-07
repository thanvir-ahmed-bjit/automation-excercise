package com.bjitgroup.hooks;

import com.bjitgroup.utils.CustomLogger;
import org.slf4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * Optional class-level hooks for grouping per-class setup/teardown logic.
 * Extend this in test classes that need class-scoped state (e.g. pre-created
 * test data that should survive across multiple test methods in the class).
 */
public abstract class TestHooks {

    protected final Logger log = CustomLogger.getLogger(getClass());

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        log.info("=== Class setup: {} ===", getClass().getSimpleName());
        onBeforeClass();
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        log.info("=== Class teardown: {} ===", getClass().getSimpleName());
        onAfterClass();
    }

    /** Override to add class-level setup. */
    protected void onBeforeClass() { /* no-op by default */ }

    /** Override to add class-level teardown. */
    protected void onAfterClass() { /* no-op by default */ }
}


