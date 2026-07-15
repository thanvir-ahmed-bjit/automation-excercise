package com.bjitgroup.listeners;

import com.bjitgroup.config.ConfigManager;
import com.bjitgroup.context.UiContextAware;
import com.bjitgroup.context.UiTestContext;
import com.bjitgroup.factory.BrowserFactory;
import com.bjitgroup.reports.AllureManager;
import com.bjitgroup.runtime.ArtifactManager;
import com.bjitgroup.runtime.BrowserSession;
import com.bjitgroup.runtime.BrowserSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG listener that manages browser session lifecycle and artifacts.
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Creates exactly one {@link BrowserSession} per test method invocation</li>
 *   <li>Injects {@link UiTestContext} into {@link UiContextAware} test instances</li>
 *   <li>Captures failure screenshots and attaches them to the Allure report</li>
 *   <li>Closes the browser session after each test, regardless of outcome</li>
 *   <li>Clears per-thread context via {@link UiContextAware#clearUiTestContext()}</li>
 *   <li>Generates unique execution names so artifacts from retries and parallel
 *       DataProvider invocations never overwrite each other</li>
 * </ul>
 *
 * <p>Per-test state is stored in {@link ITestResult} attributes, not in listener
 * instance fields, so this listener is safe for any parallel execution mode.</p>
 */
public class TestListener implements ITestListener, IInvokedMethodListener {

    private static final Logger LOG = LoggerFactory.getLogger(TestListener.class);

    // ITestResult attribute keys — fully-qualified to avoid collisions
    private static final String CONTEXT_ATTRIBUTE =
            TestListener.class.getName() + ".uiTestContext";
    private static final String SESSION_ATTRIBUTE =
            TestListener.class.getName() + ".browserSession";
    private static final String CLOSED_ATTRIBUTE =
            TestListener.class.getName() + ".sessionClosed";
    private static final String EXECUTION_NAME_ATTRIBUTE =
            TestListener.class.getName() + ".executionName";

    // Stateless collaborators — safe to share across parallel invocations
    private final ConfigManager config = ConfigManager.loadDefault();
    private final ArtifactManager artifactManager = new ArtifactManager();
    private final BrowserSessionFactory sessionFactory =
            new BrowserSessionFactory(config, new BrowserFactory(config), artifactManager);

    // -----------------------------------------------------------------------
    // Suite-level callbacks
    // -----------------------------------------------------------------------

    @Override
    public void onStart(ITestContext ctx) {
        AllureManager.addEnvironmentInfo(
                config.browser().name(), config.environment(), config.baseUrl());
        LOG.info("=== Suite '{}' started  env={}  browser={} ===",
                ctx.getName(), config.environment(), config.browser());
    }

    @Override
    public void onFinish(ITestContext ctx) {
        LOG.info("=== Suite '{}' finished  passed={}  failed={}  skipped={} ===",
                ctx.getName(),
                ctx.getPassedTests().size(),
                ctx.getFailedTests().size(),
                ctx.getSkippedTests().size());
    }

    // -----------------------------------------------------------------------
    // Invocation callbacks
    // -----------------------------------------------------------------------

    /**
     * Creates and injects a fresh browser session before every test method.
     *
     * <p>If initialization fails at any step:</p>
     * <ol>
     *   <li>The original exception is logged with its full stack trace.</li>
     *   <li>Any partially created {@link BrowserSession} is closed.</li>
     *   <li>All result attributes are removed.</li>
     *   <li>Any injected context is cleared from the test instance.</li>
     *   <li>The exception is re-thrown so the test body never runs with an
     *       uninitialized context.</li>
     * </ol>
     */
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult result) {
        if (!method.isTestMethod()) {
            return;
        }

        // Build and store the execution name first so it is available for
        // artifact naming even when initialization later fails.
        String executionName = buildExecutionName(result);
        result.setAttribute(EXECUTION_NAME_ATTRIBUTE, executionName);

        BrowserSession session = null;
        try {
            // 1. Create BrowserSession and immediately persist it so that
            //    the cleanup path in the catch block can close it.
            session = sessionFactory.create();
            result.setAttribute(SESSION_ATTRIBUTE, session);

            // 2. Create UiTestContext and persist it.
            UiTestContext context = new UiTestContext(config, session, artifactManager);
            result.setAttribute(CONTEXT_ATTRIBUTE, context);

            // 3. Inject context into the test instance.
            if (result.getInstance() instanceof UiContextAware awareTest) {
                awareTest.setUiTestContext(context);
            }

            // 4. Log test start.
            LOG.info(">> TEST STARTED  : {}", qualifiedName(result));

        } catch (RuntimeException ex) {
            LOG.error("Failed to initialize browser session for test '{}'",
                    qualifiedName(result), ex);

            // Close any partially created session.
            if (session != null) {
                try {
                    session.close(executionName);
                } catch (Exception closeEx) {
                    LOG.warn("Could not close partial session for '{}'",
                            qualifiedName(result), closeEx);
                }
            }

            // Clean up result attributes.
            result.removeAttribute(SESSION_ATTRIBUTE);
            result.removeAttribute(CONTEXT_ATTRIBUTE);

            // Clear any injected context from the test instance.
            if (result.getInstance() instanceof UiContextAware aware) {
                aware.clearUiTestContext();
            }

            // Rethrow — prevents the test body from running with an
            // uninitialized context.
            throw ex;
        }
    }

    /**
     * Logs the test outcome and closes the browser session after every test.
     * Handles pass, fail, and skip outcomes.
     */
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result) {
        if (!method.isTestMethod()) {
            return;
        }

        if (result.isSuccess()) {
            LOG.info("OK TEST PASSED   : {}", qualifiedName(result));
            LOG.info("Screenshot skipped: {}", qualifiedName(result));
        } else if (result.getStatus() == ITestResult.FAILURE) {
            LOG.error("XX TEST FAILED   : {}", qualifiedName(result));
            attachFailureScreenshot(result);
        } else if (result.getStatus() == ITestResult.SKIP) {
            LOG.warn("-- TEST SKIPPED  : {}", qualifiedName(result));
            LOG.info("Screenshot skipped: {}", qualifiedName(result));
        }

        closeSession(result);
    }

    /**
     * Defensive fallback for tests that are skipped before invocation
     * (e.g., because a group dependency was not satisfied).  For tests
     * that were actually invoked, {@link #afterInvocation} already handles
     * cleanup; the idempotency guard in {@link #closeSession} makes the
     * double-call safe.
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        closeSession(result);
    }

    // -----------------------------------------------------------------------
    // Internal helpers
    // -----------------------------------------------------------------------

    private void attachFailureScreenshot(ITestResult result) {
        try {
            UiTestContext context = contextFrom(result);
            if (context == null) {
                LOG.info("Screenshot skipped: {}", qualifiedName(result));
                return;
            }
            String screenshotName = executionNameFrom(result);
            var shot = context.captureScreenshot(screenshotName);
            LOG.info("Screenshot captured: {}", shot.toAbsolutePath());
            AllureManager.attachScreenshot("Failure Screenshot", shot);
        } catch (Exception ex) {
            LOG.warn("Could not capture failure screenshot for '{}'",
                    qualifiedName(result), ex);
        }
    }

    /**
     * Closes the browser session for the given result.  Fully idempotent —
     * additional calls after the first are no-ops.
     *
     * <p>Cleanup order:</p>
     * <ol>
     *   <li>Mark as closed (prevents duplicate cleanup).</li>
     *   <li>Close via {@link UiTestContext} when available; otherwise fall
     *       back to the raw {@link BrowserSession} stored in the attribute.</li>
     *   <li>Always remove result attributes in a {@code finally} block.</li>
     *   <li>Always call {@link UiContextAware#clearUiTestContext()} to release
     *       the ThreadLocal.</li>
     * </ol>
     */
    private void closeSession(ITestResult result) {
        if (Boolean.TRUE.equals(result.getAttribute(CLOSED_ATTRIBUTE))) {
            return;
        }
        result.setAttribute(CLOSED_ATTRIBUTE, Boolean.TRUE);

        String executionName = executionNameFrom(result);

        try {
            UiTestContext context = contextFrom(result);
            if (context != null) {
                context.close(executionName);
            } else {
                // Context was never created — close the raw session directly.
                BrowserSession session = sessionFrom(result);
                if (session != null) {
                    session.close(executionName);
                }
            }
        } catch (Exception ex) {
            LOG.warn("Could not completely close browser session for '{}'",
                    qualifiedName(result), ex);
        } finally {
            result.removeAttribute(CONTEXT_ATTRIBUTE);
            result.removeAttribute(SESSION_ATTRIBUTE);

            if (result.getInstance() instanceof UiContextAware aware) {
                aware.clearUiTestContext();
            }
        }

        LOG.debug("Browser session closed for test: {}", qualifiedName(result));
    }

    /**
     * Retrieves the {@link UiTestContext} stored in result attributes or
     * directly from the test instance.  Returns {@code null} when unavailable
     * rather than throwing.
     */
    private UiTestContext contextFrom(ITestResult result) {
        Object obj = result.getAttribute(CONTEXT_ATTRIBUTE);
        if (obj instanceof UiTestContext context) {
            return context;
        }
        if (result.getInstance() instanceof UiContextAware aware) {
            try {
                return aware.getUiTestContext();
            } catch (IllegalStateException ignored) {
                return null;
            }
        }
        return null;
    }

    /**
     * Retrieves the {@link BrowserSession} stored in result attributes.
     * Returns {@code null} when unavailable.  Never performs an unsafe cast.
     */
    private BrowserSession sessionFrom(ITestResult result) {
        Object obj = result.getAttribute(SESSION_ATTRIBUTE);
        if (obj instanceof BrowserSession session) {
            return session;
        }
        return null;
    }

    /**
     * Retrieves the execution name stored during {@link #beforeInvocation}, or
     * generates a fresh one as a fallback (e.g., when beforeInvocation was
     * never called for a skipped test).
     */
    private String executionNameFrom(ITestResult result) {
        Object stored = result.getAttribute(EXECUTION_NAME_ATTRIBUTE);
        if (stored instanceof String name) {
            return name;
        }
        return buildExecutionName(result);
    }

    /**
     * Builds a filesystem-safe execution name that is unique per test
     * invocation, even across retries and parallel DataProvider threads.
     *
     * <p>Format: {@code ClassName_methodName_threadId_invocationCount_timestamp}</p>
     * <p>Example: {@code LoginTest_validLogin_24_1_1721045123456}</p>
     */
    private static String buildExecutionName(ITestResult result) {
        String className  = result.getTestClass().getRealClass().getSimpleName();
        String methodName = result.getMethod().getMethodName();
        long threadId     = Thread.currentThread().threadId();
        int invocationCount = result.getMethod().getCurrentInvocationCount();
        long timestamp    = System.currentTimeMillis();
        String raw = className + "_" + methodName + "_" + threadId + "_"
                + invocationCount + "_" + timestamp;
        return raw.replaceAll("[^A-Za-z0-9._-]", "_");
    }

    private static String qualifiedName(ITestResult result) {
        return result.getTestClass().getName() + "#" + result.getMethod().getMethodName();
    }
}
