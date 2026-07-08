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
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG listener that:
 * <ul>
 *   <li>Captures screenshots on failure and attaches them to Allure</li>
 *   <li>Logs test lifecycle events</li>
 *   <li>Adds environment meta-data to the Allure report</li>
 * </ul>
 */
public class TestListener implements ITestListener {

    private static final Logger LOG = LoggerFactory.getLogger(TestListener.class);
    private static final String CONTEXT_KEY = "uiTestContext";

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
    public void onTestStart(ITestResult result) {
        UiTestContext context = new UiTestContext(config, sessionFactory.create(), artifactManager);
        result.setAttribute(CONTEXT_KEY, context);

        if (result.getInstance() instanceof UiContextAware awareTest) {
            awareTest.setUiTestContext(context);
        }

        LOG.info(">> TEST STARTED  : {}", qualifiedName(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOG.info("OK TEST PASSED   : {}", qualifiedName(result));
        closeContext(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOG.error("XX TEST FAILED   : {}", qualifiedName(result));
        attachFailureScreenshot(result);
        closeContext(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOG.warn("-- TEST SKIPPED  : {}", qualifiedName(result));
        closeContext(result);
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
            if (context == null) return;
            String name = result.getMethod().getMethodName() + "_FAIL_" + System.currentTimeMillis();
            var shot = context.captureScreenshot(name);
            AllureManager.attachScreenshot("Failure Screenshot", shot);
        } catch (Exception e) {
            LOG.warn("Could not capture failure screenshot: {}", e.getMessage());
        }
    }

    private UiTestContext contextFrom(ITestResult result) {
        Object fromResult = result.getAttribute(CONTEXT_KEY);
        if (fromResult instanceof UiTestContext context) {
            return context;
        }
        if (result.getInstance() instanceof UiContextAware aware) {
            return aware.getUiTestContext();
        }
        return null;
    }

    private void closeContext(ITestResult result) {
        UiTestContext context = contextFrom(result);
        if (context == null) {
            return;
        }
        try {
            context.close(result.getMethod().getMethodName());
        } finally {
            result.removeAttribute(CONTEXT_KEY);
            if (result.getInstance() instanceof UiContextAware aware) {
                aware.setUiTestContext(null);
            }
        }
    }

    private String qualifiedName(ITestResult result) {
        return result.getTestClass().getName() + "#" + result.getMethod().getMethodName();
    }
}


