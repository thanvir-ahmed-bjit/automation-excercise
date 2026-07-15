package com.bjitgroup.listeners;

import com.bjitgroup.config.ConfigManager;
import com.bjitgroup.context.UiContextAware;
import com.bjitgroup.context.UiTestContext;
import com.bjitgroup.factory.BrowserFactory;
import com.bjitgroup.reports.AllureManager;
import com.bjitgroup.runtime.ArtifactManager;
import com.bjitgroup.runtime.BrowserSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Objects;

/**
 * TestNG listener that manages browser session lifecycle and artifacts.
 * <p>
 * Responsibilities:
 * <ul>
 *   <li>Creates one BrowserSession per test method</li>
 *   <li>Injects UiTestContext into UiContextAware tests</li>
 *   <li>Captures failure screenshots and attaches to Allure</li>
 *   <li>Closes browser session after test completion</li>
 *   <li>Logs test lifecycle events</li>
 *   <li>Adds environment metadata to Allure report</li>
 * </ul>
 * </p>
 * <p>
 * Attribute keys are stored in result attributes to avoid static mutable state
 * and support parallel execution.
 * </p>
 */
public class TestListener implements ITestListener, IInvokedMethodListener {

    private static final Logger LOG = LoggerFactory.getLogger(TestListener.class);

    // Unique attribute keys to store session and context per test
    private static final String CONTEXT_ATTRIBUTE =
            TestListener.class.getName() + ".uiTestContext";
    private static final String SESSION_ATTRIBUTE =
            TestListener.class.getName() + ".browserSession";
    private static final String CLOSED_ATTRIBUTE =
            TestListener.class.getName() + ".sessionClosed";

    private final ConfigManager config = ConfigManager.loadDefault();
    private final ArtifactManager artifactManager = new ArtifactManager();
    private final BrowserSessionFactory sessionFactory =
            new BrowserSessionFactory(config, new BrowserFactory(config), artifactManager);

    @Override
    public void onStart(ITestContext ctx) {
        AllureManager.addEnvironmentInfo(
                config.browser().name(), config.environment(), config.baseUrl());
        LOG.info("=== Suite '{}' started  env={}  browser={} ===",
                ctx.getName(), config.environment(), config.browser());
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult result) {
        // Only initialize browser session for actual test methods
        if (!method.isTestMethod()) {
            return;
        }

        try {
            // Create browser session
            var session = sessionFactory.create();
            result.setAttribute(SESSION_ATTRIBUTE, session);

            // Create context
            UiTestContext context = new UiTestContext(config, session, artifactManager);
            result.setAttribute(CONTEXT_ATTRIBUTE, context);

            // Inject context into test instance if it implements UiContextAware
            if (result.getInstance() instanceof UiContextAware awareTest) {
                awareTest.setUiTestContext(context);
            }

            LOG.info(">> TEST STARTED  : {}", qualifiedName(result));
        } catch (Exception ex) {
            LOG.error("Failed to initialize browser session for test: {}", qualifiedName(result), ex);
            result.setStatus(ITestResult.FAILURE);
            result.setThrowable(ex);
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result) {
        // Only cleanup for actual test methods
        if (!method.isTestMethod()) {
            return;
        }

        // Determine test status and log appropriately
        if (result.isSuccess()) {
            LOG.info("OK TEST PASSED   : {}", qualifiedName(result));
        } else if (result.getStatus() == ITestResult.FAILURE) {
            LOG.error("XX TEST FAILED   : {}", qualifiedName(result));
            attachFailureScreenshot(result);
        } else if (result.getStatus() == ITestResult.SKIP) {
            LOG.warn("-- TEST SKIPPED  : {}", qualifiedName(result));
        }

        // Close browser session
        closeSession(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOG.warn("-- TEST SKIPPED  : {}", qualifiedName(result));
        closeSession(result);
    }

    @Override
    public void onFinish(ITestContext ctx) {
        LOG.info("=== Suite '{}' finished  passed={} failed={} skipped={} ===",
                ctx.getName(),
                ctx.getPassedTests().size(),
                ctx.getFailedTests().size(),
                ctx.getSkippedTests().size());
    }

    // Internal helpers

    private void attachFailureScreenshot(ITestResult result) {
        try {
            UiTestContext context = contextFrom(result);
            if (context == null) {
                return;
            }
            String name = result.getMethod().getMethodName() + "_FAIL_" + System.currentTimeMillis();
            var shot = context.captureScreenshot(name);
            AllureManager.attachScreenshot("Failure Screenshot", shot);
        } catch (Exception e) {
            LOG.warn("Could not capture failure screenshot: {}", e.getMessage());
        }
    }

    private UiTestContext contextFrom(ITestResult result) {
        Object fromResult = result.getAttribute(CONTEXT_ATTRIBUTE);
        if (fromResult instanceof UiTestContext context) {
            return context;
        }
        if (result.getInstance() instanceof UiContextAware aware) {
            try {
                return aware.getUiTestContext();
            } catch (IllegalStateException ignored) {
                // Context not initialized
                return null;
            }
        }
        return null;
    }

    private void closeSession(ITestResult result) {
        // Idempotent: only close once
        if (result.getAttribute(CLOSED_ATTRIBUTE) != null) {
            return;
        }
        result.setAttribute(CLOSED_ATTRIBUTE, true);

        UiTestContext context = contextFrom(result);
        if (context == null) {
            return;
        }

        try {
            context.close(result.getMethod().getMethodName());
        } finally {
            // Clean up attributes and instance state
            result.removeAttribute(CONTEXT_ATTRIBUTE);
            result.removeAttribute(SESSION_ATTRIBUTE);
            if (result.getInstance() instanceof UiContextAware aware) {
                try {
                    aware.setUiTestContext(null);
                } catch (Exception ignored) {
                    // Ignore errors during cleanup
                }
            }
        }

        LOG.debug("Browser session closed for test: {}", qualifiedName(result));
    }

    private String qualifiedName(ITestResult result) {
        return result.getTestClass().getName() + "#" + result.getMethod().getMethodName();
    }
}


