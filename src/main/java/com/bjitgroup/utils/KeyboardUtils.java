package com.bjitgroup.utils;

import com.microsoft.playwright.Page;

/**
 * Keyboard interaction helpers wrapping Playwright's {@link com.microsoft.playwright.Keyboard}.
 */
public final class KeyboardUtils {

    private KeyboardUtils() { /* utility */ }

    public static void press(Page page, String key) {
        page.keyboard().press(key);
    }

    public static void type(Page page, String text) {
        page.keyboard().type(text);
    }

    public static void pressEnter(Page page) {
        page.keyboard().press("Enter");
    }

    public static void pressEscape(Page page) {
        page.keyboard().press("Escape");
    }

    public static void pressTab(Page page) {
        page.keyboard().press("Tab");
    }

    public static void selectAll(Page page) {
        page.keyboard().press("Control+a");
    }
}


