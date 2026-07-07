package com.bjitgroup.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retries a failed test up to {@value #MAX_RETRIES} times.
 * <p>
 * Annotate each {@code @Test} method with {@code retryAnalyzer = RetryAnalyzer.class}
 * or register globally via the TestNG listener mechanism.
 * </p>
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private static final int MAX_RETRIES = 1;
    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRIES) {
            retryCount++;
            return true;
        }
        return false;
    }
}


