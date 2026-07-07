package com.bjitgroup.listeners;

import com.bjitgroup.config.ConfigManager;
import com.bjitgroup.driver.DriverFactory;
import com.bjitgroup.reports.AllureManager;
import com.bjitgroup.utils.CustomLogger;
import com.bjitgroup.utils.ScreenshotUtils;
import org.slf4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.nio.file.Path;

/**
 * TestNG listener that:
 * <ul>
 *   <li>Captures screenshots on failure and attaches them to Allure</li>
 *   <li>Logs test lifecycle events</li>
 *   <li>Adds environment meta-data to the Allure report</li>
 * </ul>
 */
public class TestListener implements ITestListener {

    private static final Logger LOG = CustomLogger.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext ctx) {
        ConfigManager cfg = ConfigManager.getInstance();
        AllureManager.addEnvironmentInfo(
                cfg.browser().name(), cfg.environment(), cfg.baseUrl());
        LOG.info("=== Suite '{}' started  env={}  browser={} ===",
                ctx.getName(), cfg.environment(), cfg.browser());
    }

    @Override
    public void onTestStart(ITestResult result) {
        LOG.info(">> TEST STARTED  : {}", qualifiedName(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOG.info("OK TEST PASSED   : {}", qualifiedName(result));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOG.error("XX TEST FAILED   : {}", qualifiedName(result));
        attachFailureScreenshot(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOG.warn("-- TEST SKIPPED  : {}", qualifiedName(result));
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
            if (DriverFactory.getPage() == null) return;
            String name = result.getMethod().getMethodName() + "_FAIL_" + System.currentTimeMillis();
            Path shot = ScreenshotUtils.take(DriverFactory.getPage(), name);
            AllureManager.attachScreenshot("Failure Screenshot", shot);
        } catch (Exception e) {
            LOG.warn("Could not capture failure screenshot: {}", e.getMessage());
        }
    }

    private String qualifiedName(ITestResult result) {
        return result.getTestClass().getName() + "#" + result.getMethod().getMethodName();
    }
}


