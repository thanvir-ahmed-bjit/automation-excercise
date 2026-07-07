package com.bjitgroup.utils;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Route;
import com.microsoft.playwright.options.Cookie;

import java.nio.file.Path;
import java.util.List;

/**
 * High-level Playwright helper covering:
 * network interception, cookie management, multi-tab, and downloads.
 */
public final class PlaywrightUtils {

    private PlaywrightUtils() { /* utility */ }

    // Network interception

    /** Stub any URL matching {@code urlPattern} with a static response. */
    public static void stubRoute(Page page, String urlPattern, int status, String body) {
        page.route(urlPattern, route ->
                route.fulfill(new Route.FulfillOptions()
                        .setStatus(status)
                        .setBody(body)
                        .setContentType("application/json")));
    }

    /** Remove a previously registered route stub. */
    public static void clearRoute(Page page, String urlPattern) {
        page.unroute(urlPattern);
    }

    // Cookie management

    /** Add a single cookie to the context. */
    public static void addCookie(BrowserContext ctx, String name, String value, String domain, String path) {
        ctx.addCookies(List.of(new Cookie(name, value).setDomain(domain).setPath(path)));
    }

    /** Return all cookies currently in the context. */
    public static List<Cookie> getCookies(BrowserContext ctx) {
        return ctx.cookies();
    }

    /** Clear all cookies from the context. */
    public static void clearCookies(BrowserContext ctx) {
        ctx.clearCookies();
    }

    // Multi-tab

    /** Open a new tab inside an existing context and navigate to {@code url}. */
    public static Page openNewTab(BrowserContext ctx, String url) {
        Page newTab = ctx.newPage();
        newTab.navigate(url);
        return newTab;
    }

    // Downloads

    /** Trigger a download action and save the file to {@code savePath}. */
    public static Path handleDownload(Page page, Runnable trigger, Path savePath) {
        Download dl = page.waitForDownload(trigger::run);
        dl.saveAs(savePath);
        return savePath;
    }
}


