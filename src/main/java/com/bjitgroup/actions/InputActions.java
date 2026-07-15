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

import static com.bjitgroup.utils.ValidationUtils.validateSelector;
import static com.bjitgroup.utils.ValidationUtils.validateText;

/**
 * Provides reusable user-interaction operations.
 *
 * <p>String-selector methods are preserved for backward compatibility and
 * delegate to Locator-first overloads.</p>
 */
public final class InputActions {

    private final Page page;

    public InputActions(Page page) {
        this.page = Objects.requireNonNull(page, "Page must not be null");
    }

    // -------------------------------------------------------------------------
    // Locator helpers
    // -------------------------------------------------------------------------

    private Locator locator(String selector) {
        validateSelector(selector);
        return page.locator(selector);
    }

    private static Locator requireLocator(Locator locator) {
        return Objects.requireNonNull(locator, "Locator must not be null");
    }

    // -------------------------------------------------------------------------
    // Text input
    // -------------------------------------------------------------------------

    /** Replaces existing input value. A null value is treated as empty. */
    public void fill(String selector, String value) {
        fill(locator(selector), value);
    }

    public void fill(Locator locator, String value) {
        requireLocator(locator).fill(value == null ? "" : value);
    }

    /** Clears the current input value. */
    public void clear(String selector) {
        clear(locator(selector));
    }

    public void clear(Locator locator) {
        requireLocator(locator).clear();
    }

    /**
     * Enters text one character at a time.
     *
     * <p>Backward-compatible alias for {@link #pressSequentially(String, String, double)}.</p>
     */
    public void typeSequentially(String selector, String value, double delayMs) {
        pressSequentially(selector, value, delayMs);
    }

    public void pressSequentially(String selector, String value, double delayMs) {
        pressSequentially(locator(selector), value, delayMs);
    }

    public void pressSequentially(Locator locator, String value, double delayMs) {
        Objects.requireNonNull(value, "Value must not be null");
        if (delayMs < 0) {
            throw new IllegalArgumentException("Delay must not be negative");
        }

        requireLocator(locator).pressSequentially(
                value,
                new Locator.PressSequentiallyOptions().setDelay(delayMs)
        );
    }

    // -------------------------------------------------------------------------
    // Mouse actions
    // -------------------------------------------------------------------------

    public void click(String selector) {
        click(locator(selector));
    }

    public void click(Locator locator) {
        requireLocator(locator).click();
    }

    public void doubleClick(String selector) {
        doubleClick(locator(selector));
    }

    public void doubleClick(Locator locator) {
        requireLocator(locator).dblclick();
    }

    public void rightClick(String selector) {
        rightClick(locator(selector));
    }

    public void rightClick(Locator locator) {
        requireLocator(locator).click(
                new Locator.ClickOptions().setButton(MouseButton.RIGHT)
        );
    }

    public void clickWithModifier(String selector, KeyboardModifier modifier) {
        clickWithModifier(locator(selector), modifier);
    }

    public void clickWithModifier(Locator locator, KeyboardModifier modifier) {
        Objects.requireNonNull(modifier, "Keyboard modifier must not be null");
        requireLocator(locator).click(
                new Locator.ClickOptions().setModifiers(List.of(modifier))
        );
    }

    public void hover(String selector) {
        hover(locator(selector));
    }

    public void hover(Locator locator) {
        requireLocator(locator).hover();
    }

    /** Runs click actionability checks without performing the click. */
    public void trialClick(String selector) {
        trialClick(locator(selector));
    }

    public void trialClick(Locator locator) {
        requireLocator(locator).click(new Locator.ClickOptions().setTrial(true));
    }

    /** Performs a forced click. Use only when bypassing checks is intentional. */
    public void forceClick(String selector) {
        forceClick(locator(selector));
    }

    public void forceClick(Locator locator) {
        requireLocator(locator).click(new Locator.ClickOptions().setForce(true));
    }

    // -------------------------------------------------------------------------
    // Checkbox and radio-button actions
    // -------------------------------------------------------------------------

    public void check(String selector) {
        check(locator(selector));
    }

    public void check(Locator locator) {
        requireLocator(locator).check();
    }

    public void uncheck(String selector) {
        uncheck(locator(selector));
    }

    public void uncheck(Locator locator) {
        requireLocator(locator).uncheck();
    }

    public void setChecked(String selector, boolean checked) {
        setChecked(locator(selector), checked);
    }

    public void setChecked(Locator locator, boolean checked) {
        requireLocator(locator).setChecked(checked);
    }

    // -------------------------------------------------------------------------
    // Select controls
    // -------------------------------------------------------------------------

    /** Selects an option by its value attribute. */
    public List<String> selectByValue(String selector, String value) {
        return selectByValue(locator(selector), value);
    }

    public List<String> selectByValue(Locator locator, String value) {
        validateText(value, "Option value");
        return requireLocator(locator).selectOption(value);
    }

    /** Selects an option by its visible label. */
    public List<String> selectByLabel(String selector, String label) {
        return selectByLabel(locator(selector), label);
    }

    public List<String> selectByLabel(Locator locator, String label) {
        validateText(label, "Option label");
        return requireLocator(locator).selectOption(new SelectOption().setLabel(label));
    }

    /** Selects an option by its zero-based index. */
    public List<String> selectByIndex(String selector, int index) {
        return selectByIndex(locator(selector), index);
    }

    public List<String> selectByIndex(Locator locator, int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Option index must not be negative");
        }
        return requireLocator(locator).selectOption(new SelectOption().setIndex(index));
    }

    /** Selects multiple options by value. */
    public List<String> selectMultiple(String selector, String... values) {
        return selectMultiple(locator(selector), values);
    }

    public List<String> selectMultiple(Locator locator, String... values) {
        validateOptionValues(values);
        return requireLocator(locator).selectOption(values);
    }

    // -------------------------------------------------------------------------
    // Keyboard actions
    // -------------------------------------------------------------------------

    public void press(String selector, String key) {
        press(locator(selector), key);
    }

    public void press(Locator locator, String key) {
        validateText(key, "Key");
        requireLocator(locator).press(key);
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

    public void pressShortcut(String selector, String shortcut) {
        press(selector, shortcut);
    }

    // -------------------------------------------------------------------------
    // Focus
    // -------------------------------------------------------------------------

    public void focus(String selector) {
        focus(locator(selector));
    }

    public void focus(Locator locator) {
        requireLocator(locator).focus();
    }

    public void blur(String selector) {
        blur(locator(selector));
    }

    public void blur(Locator locator) {
        requireLocator(locator).blur();
    }

    // -------------------------------------------------------------------------
    // File upload
    // -------------------------------------------------------------------------

    public void uploadFile(String selector, Path file) {
        uploadFile(locator(selector), file);
    }

    public void uploadFile(Locator locator, Path file) {
        validateFile(file);
        requireLocator(locator).setInputFiles(file);
    }

    public void uploadFiles(String selector, Path... files) {
        uploadFiles(locator(selector), files);
    }

    public void uploadFiles(Locator locator, Path... files) {
        validateFiles(files);
        requireLocator(locator).setInputFiles(files);
    }

    /** Clears all files from a file input. */
    public void clearUploadedFiles(String selector) {
        clearUploadedFiles(locator(selector));
    }

    public void clearUploadedFiles(Locator locator) {
        requireLocator(locator).setInputFiles(new Path[0]);
    }

    /**
     * Uploads a file when the file input is created dynamically after clicking
     * an upload trigger.
     */
    public void uploadUsingFileChooser(String triggerSelector, Path file) {
        validateFile(file);
        FileChooser fileChooser = page.waitForFileChooser(() -> click(triggerSelector));
        fileChooser.setFiles(file);
    }

    // -------------------------------------------------------------------------
    // Drag-and-drop
    // -------------------------------------------------------------------------

    public void dragAndDrop(String sourceSelector, String targetSelector) {
        dragAndDrop(locator(sourceSelector), locator(targetSelector));
    }

    public void dragAndDrop(Locator source, Locator target) {
        requireLocator(source).dragTo(requireLocator(target));
    }

    // -------------------------------------------------------------------------
    // Scrolling
    // -------------------------------------------------------------------------

    /** Scrolls the element into view when necessary. */
    public void scrollIntoView(String selector) {
        scrollIntoView(locator(selector));
    }

    public void scrollIntoView(Locator locator) {
        requireLocator(locator).scrollIntoViewIfNeeded();
    }

    /** Scrolls using the mouse wheel. */
    public void scrollMouseWheel(double deltaX, double deltaY) {
        page.mouse().wheel(deltaX, deltaY);
    }

    // -------------------------------------------------------------------------
    // Validation helpers
    // -------------------------------------------------------------------------

    private void validateOptionValues(String[] values) {
        Objects.requireNonNull(values, "Option values must not be null");
        if (values.length == 0) {
            throw new IllegalArgumentException("At least one option value is required");
        }
        for (String value : values) {
            validateText(value, "Option value");
        }
    }

    private void validateFiles(Path[] files) {
        Objects.requireNonNull(files, "Files must not be null");

        if (files.length == 0) {
            throw new IllegalArgumentException("At least one file is required");
        }

        for (Path file : files) {
            validateFile(file);
        }
    }

    private void validateFile(Path file) {
        Objects.requireNonNull(file, "File path must not be null");

        if (!Files.exists(file)) {
            throw new IllegalArgumentException(
                    "Upload file does not exist: " + file.toAbsolutePath());
        }

        if (!Files.isRegularFile(file)) {
            throw new IllegalArgumentException(
                    "Upload path is not a regular file: " + file.toAbsolutePath());
        }
    }
}