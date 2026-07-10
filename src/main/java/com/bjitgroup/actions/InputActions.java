package com.bjitgroup.actions;

import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.KeyboardModifier;
import com.microsoft.playwright.options.MouseButton;
import com.microsoft.playwright.options.SelectOption;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

/**
 * Provides reusable user-interaction operations.
 *
 * <p>This class is responsible for:</p>
 * <ul>
 *     <li>Text input</li>
 *     <li>Mouse actions</li>
 *     <li>Keyboard actions</li>
 *     <li>Checkbox and radio-button actions</li>
 *     <li>Select controls</li>
 *     <li>File upload</li>
 *     <li>Drag-and-drop</li>
 *     <li>Focus and scrolling</li>
 * </ul>
 */
public final class InputActions {

    private final Page page;

    public InputActions(Page page) {
        this.page = Objects.requireNonNull(
                page,
                "Page must not be null"
        );
    }

    // -------------------------------------------------------------------------
    // Locator
    // -------------------------------------------------------------------------

    private Locator locator(String selector) {
        validateSelector(selector);
        return page.locator(selector);
    }

    // -------------------------------------------------------------------------
    // Text input
    // -------------------------------------------------------------------------

    /**
     * Replaces the existing input value with the supplied value.
     *
     * <p>A null value is treated as an empty string.</p>
     */
    public void fill(String selector, String value) {
        locator(selector).fill(value == null ? "" : value);
    }

    /**
     * Clears the current input value.
     */
    public void clear(String selector) {
        locator(selector).clear();
    }

    /**
     * Enters text one character at a time.
     *
     * <p>Use only when the application depends on individual keyboard events.
     * For ordinary text entry, prefer {@link #fill(String, String)}.</p>
     */
    public void typeSequentially(
            String selector,
            String value,
            double delayMs
    ) {
        Objects.requireNonNull(value, "Value must not be null");

        if (delayMs < 0) {
            throw new IllegalArgumentException(
                    "Delay must not be negative"
            );
        }

        locator(selector).pressSequentially(
                value,
                new Locator.PressSequentiallyOptions()
                        .setDelay(delayMs)
        );
    }

    // -------------------------------------------------------------------------
    // Mouse actions
    // -------------------------------------------------------------------------

    public void click(String selector) {
        locator(selector).click();
    }

    public void doubleClick(String selector) {
        locator(selector).dblclick();
    }

    public void rightClick(String selector) {
        locator(selector).click(
                new Locator.ClickOptions()
                        .setButton(MouseButton.RIGHT)
        );
    }

    public void clickWithModifier(
            String selector,
            KeyboardModifier modifier
    ) {
        Objects.requireNonNull(
                modifier,
                "Keyboard modifier must not be null"
        );

        locator(selector).click(
                new Locator.ClickOptions()
                        .setModifiers(List.of(modifier))
        );
    }

    public void hover(String selector) {
        locator(selector).hover();
    }

    /**
     * Runs click actionability checks without performing the click.
     */
    public void trialClick(String selector) {
        locator(selector).click(
                new Locator.ClickOptions()
                        .setTrial(true)
        );
    }

    /**
     * Performs a forced click.
     *
     * <p>Use only when bypassing normal actionability checks is intentional.</p>
     */
    public void forceClick(String selector) {
        locator(selector).click(
                new Locator.ClickOptions()
                        .setForce(true)
        );
    }

    // -------------------------------------------------------------------------
    // Checkbox and radio-button actions
    // -------------------------------------------------------------------------

    public void check(String selector) {
        locator(selector).check();
    }

    public void uncheck(String selector) {
        locator(selector).uncheck();
    }

    public void setChecked(
            String selector,
            boolean checked
    ) {
        locator(selector).setChecked(checked);
    }

    // -------------------------------------------------------------------------
    // Select controls
    // -------------------------------------------------------------------------

    /**
     * Selects an option by its value attribute.
     */
    public List<String> selectByValue(
            String selector,
            String value
    ) {
        validateText(value, "Option value");
        return locator(selector).selectOption(value);
    }

    /**
     * Selects an option by its visible label.
     */
    public List<String> selectByLabel(
            String selector,
            String label
    ) {
        validateText(label, "Option label");

        return locator(selector).selectOption(
                new SelectOption().setLabel(label)
        );
    }

    /**
     * Selects an option by its zero-based index.
     */
    public List<String> selectByIndex(
            String selector,
            int index
    ) {
        if (index < 0) {
            throw new IllegalArgumentException(
                    "Option index must not be negative"
            );
        }

        return locator(selector).selectOption(
                new SelectOption().setIndex(index)
        );
    }

    /**
     * Selects multiple options by value.
     */
    public List<String> selectMultiple(
            String selector,
            String... values
    ) {
        Objects.requireNonNull(
                values,
                "Option values must not be null"
        );

        if (values.length == 0) {
            throw new IllegalArgumentException(
                    "At least one option value is required"
            );
        }

        return locator(selector).selectOption(values);
    }

    // -------------------------------------------------------------------------
    // Keyboard actions
    // -------------------------------------------------------------------------

    public void press(
            String selector,
            String key
    ) {
        validateText(key, "Key");
        locator(selector).press(key);
    }

    public void pressEnter(String selector) {
        press(selector, "Enter");
    }

    public void pressTab(String selector) {
        press(selector, "Tab");
    }

    public void pressEscape(String selector) {
        press(selector, "Escape");
    }

    public void pressShortcut(
            String selector,
            String shortcut
    ) {
        press(selector, shortcut);
    }

    // -------------------------------------------------------------------------
    // Focus
    // -------------------------------------------------------------------------

    public void focus(String selector) {
        locator(selector).focus();
    }

    public void blur(String selector) {
        locator(selector).blur();
    }

    // -------------------------------------------------------------------------
    // File upload
    // -------------------------------------------------------------------------

    public void uploadFile(
            String selector,
            Path file
    ) {
        validateFile(file);
        locator(selector).setInputFiles(file);
    }

    public void uploadFiles(
            String selector,
            Path... files
    ) {
        validateFiles(files);
        locator(selector).setInputFiles(files);
    }

    /**
     * Clears all files from a file input.
     */
    public void clearUploadedFiles(String selector) {
        locator(selector).setInputFiles(new Path[0]);
    }

    /**
     * Uploads a file when the file input is created dynamically after clicking
     * an upload button.
     */
    public void uploadUsingFileChooser(
            String triggerSelector,
            Path file
    ) {
        validateFile(file);

        FileChooser fileChooser = page.waitForFileChooser(
                () -> locator(triggerSelector).click()
        );

        fileChooser.setFiles(file);
    }

    // -------------------------------------------------------------------------
    // Drag-and-drop
    // -------------------------------------------------------------------------

    public void dragAndDrop(
            String sourceSelector,
            String targetSelector
    ) {
        Locator source = locator(sourceSelector);
        Locator target = locator(targetSelector);

        source.dragTo(target);
    }

    // -------------------------------------------------------------------------
    // Scrolling
    // -------------------------------------------------------------------------

    /**
     * Scrolls the element into view when necessary.
     */
    public void scrollIntoView(String selector) {
        locator(selector).scrollIntoViewIfNeeded();
    }

    /**
     * Scrolls using the mouse wheel.
     */
    public void scrollMouseWheel(
            double deltaX,
            double deltaY
    ) {
        page.mouse().wheel(deltaX, deltaY);
    }

    // -------------------------------------------------------------------------
    // Validation
    // -------------------------------------------------------------------------

    private void validateSelector(String selector) {
        validateText(selector, "Selector");
    }

    private void validateText(
            String value,
            String fieldName
    ) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " must not be null or blank"
            );
        }
    }

    private void validateFiles(Path[] files) {
        Objects.requireNonNull(files, "Files must not be null");

        if (files.length == 0) {
            throw new IllegalArgumentException(
                    "At least one file is required"
            );
        }

        for (Path file : files) {
            validateFile(file);
        }
    }

    private void validateFile(Path file) {
        Objects.requireNonNull(
                file,
                "File path must not be null"
        );

        if (!Files.exists(file)) {
            throw new IllegalArgumentException(
                    "Upload file does not exist: "
                            + file.toAbsolutePath()
            );
        }

        if (!Files.isRegularFile(file)) {
            throw new IllegalArgumentException(
                    "Upload path is not a regular file: "
                            + file.toAbsolutePath()
            );
        }
    }
}