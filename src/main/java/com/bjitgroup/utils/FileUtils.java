package com.bjitgroup.utils;

import com.bjitgroup.constants.FrameworkConstants;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Page;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * File upload and download helpers.
 */
public final class FileUtils {

    private FileUtils() { /* utility */ }

    /**
     * Set a file input to the given file path (upload).
     *
     * @param page     active page
     * @param selector CSS/XPath selector targeting the {@code <input type="file">}
     * @param filePath absolute path of the file to upload
     */
    public static void upload(Page page, String selector, Path filePath) {
        page.setInputFiles(selector, filePath);
    }

    /**
     * Trigger a download action and save the file locally.
     *
     * @param page          active page
     * @param triggerAction runnable that causes the download (e.g. button click)
     * @param fileName      desired file name under the downloads directory
     * @return path to the saved file
     */
    public static Path download(Page page, Runnable triggerAction, String fileName) {
        ensureDir();
        Download download = page.waitForDownload(triggerAction::run);
        Path target = FrameworkConstants.DOWNLOAD_DIR.resolve(fileName);
        download.saveAs(target);
        return target;
    }

    private static void ensureDir() {
        try {
            Files.createDirectories(FrameworkConstants.DOWNLOAD_DIR);
        } catch (IOException ignored) { /* best-effort */ }
    }
}


