package com.bjitgroup.utils;

import com.microsoft.playwright.Page;

/**
 * Playwright JavaScript execution helpers.
 */
public final class JavaScriptUtils {

    private JavaScriptUtils() { /* utility */ }

    /** Evaluate arbitrary JavaScript in the page context. */
    public static Object evaluate(Page page, String script) {
        return page.evaluate(script);
    }

    /** Scroll the element matching {@code selector} into view. */
    public static void scrollIntoView(Page page, String selector) {
        page.evaluate("document.querySelector('" + selector + "').scrollIntoView({behavior:'smooth'})");
    }

    /** Remove a DOM attribute (e.g. 'readonly', 'disabled'). */
    public static void removeAttribute(Page page, String selector, String attribute) {
        page.evaluate(
                "el => el.removeAttribute('" + attribute + "')",
                page.locator(selector).elementHandle());
    }

    /** Set the value of an input element via JS (bypasses React synthetic events). */
    public static void setInputValue(Page page, String selector, String value) {
        page.evaluate(
                "([sel, val]) => { const el = document.querySelector(sel); el.value = val; el.dispatchEvent(new Event('input', {bubbles:true})); }",
                new Object[]{selector, value});
    }
}


