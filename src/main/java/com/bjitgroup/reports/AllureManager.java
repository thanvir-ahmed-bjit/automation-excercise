package com.bjitgroup.reports;

import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Convenience wrapper for attaching content to the Allure report.
 */
public final class AllureManager {

    private AllureManager() { /* utility */ }

    /** Attach a plain-text string. */
    public static void attachText(String name, String content) {
        Allure.addAttachment(name, "text/plain",
                new ByteArrayInputStream(content.getBytes()), ".txt");
    }

    /** Attach a PNG screenshot from a file path. */
    public static void attachScreenshot(String name, Path screenshotPath) {
        if (!Files.exists(screenshotPath)) return;
        try (InputStream in = Files.newInputStream(screenshotPath)) {
            Allure.addAttachment(name, "image/png", in, ".png");
        } catch (IOException ignored) { /* best-effort */ }
    }

    /** Attach a byte array as a PNG screenshot (for inline captures). */
    public static void attachScreenshot(String name, byte[] screenshotBytes) {
        Allure.addAttachment(name, "image/png",
                new ByteArrayInputStream(screenshotBytes), ".png");
    }

    /** Attach arbitrary file content. */
    public static void attachFile(String name, Path path, String mimeType) {
        if (!Files.exists(path)) return;
        try (InputStream in = Files.newInputStream(path)) {
            Allure.addAttachment(name, mimeType, in, "");
        } catch (IOException ignored) { /* best-effort */ }
    }

    /** Record the environment label in the report. */
    public static void addEnvironmentInfo(String browser, String environment, String baseUrl) {
        Allure.addAttachment("Environment Info",
                "Browser: " + browser + "\nEnvironment: " + environment + "\nBase URL: " + baseUrl);
    }
}


