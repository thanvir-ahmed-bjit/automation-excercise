package com.bjitgroup.utils;

import com.microsoft.playwright.Page;

/**
 * Mouse interaction helpers wrapping Playwright's {@link com.microsoft.playwright.Mouse}.
 */
public final class MouseUtils {

    private MouseUtils() { /* utility */ }

    public static void click(Page page, double x, double y) {
        page.mouse().click(x, y);
    }

    public static void doubleClick(Page page, double x, double y) {
        page.mouse().dblclick(x, y);
    }

    public static void move(Page page, double x, double y) {
        page.mouse().move(x, y);
    }

    public static void hover(Page page, String selector) {
        page.locator(selector).hover();
    }

    public static void dragAndDrop(Page page, String source, String target) {
        page.dragAndDrop(source, target);
    }
}


