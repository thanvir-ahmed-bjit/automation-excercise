package com.bjitgroup.utils;

import com.microsoft.playwright.Page;

/**
 * Helper for creating/deleting automationexercise.com test accounts inline.
 * Used by standalone test cases that need fresh credentials.
 */
public final class AccountHelper {
    private static final String BASE_URL = "https://automationexercise.com";
    private AccountHelper() {}

    /**
     * Creates a fresh account. Returns {name, email, password}.
     * Browser lands on home page (logged in) after completion.
     */
    public static String[] createAccount(Page page) {
        long ts = System.currentTimeMillis();
        String name = "TestUser" + ts;
        String email = "qa." + ts + "@test.example.com";
        String password = "Auto@123456";

        page.navigate(BASE_URL + "/login");
        page.fill("input[data-qa='signup-name']", name);
        page.fill("input[data-qa='signup-email']", email);
        page.locator("button[data-qa='signup-button']").click();

        page.waitForSelector("#id_gender1");
        page.locator("#id_gender1").click();
        page.fill("input[data-qa='password']", password);
        page.selectOption("select[data-qa='days']", "10");
        page.selectOption("select[data-qa='months']", "5");
        page.selectOption("select[data-qa='years']", "1995");
        page.locator("input#newsletter").check();
        page.locator("input#optin").check();
        page.fill("input[data-qa='first_name']", "Test");
        page.fill("input[data-qa='last_name']", "User");
        page.fill("input[data-qa='address']", "123 Test Street");
        page.selectOption("select[data-qa='country']", "India");
        page.fill("input[data-qa='state']", "TestState");
        page.fill("input[data-qa='city']", "TestCity");
        page.fill("input[data-qa='zipcode']", "12345");
        page.fill("input[data-qa='mobile_number']", "1234567890");
        page.locator("button[data-qa='create-account']").click();

        page.waitForSelector("//b[normalize-space()='Account Created!']");
        page.locator("a[data-qa='continue-button']").click();
        page.waitForSelector("//a[contains(normalize-space(),'Logged in as')]");
        return new String[]{name, email, password};
    }

    /**
     * Deletes current logged-in user's account. Browser lands on home after.
     */
    public static void deleteAccount(Page page) {
        page.locator("a[href='/delete_account']").first().click();
        page.waitForSelector("//b[normalize-space()='Account Deleted!']");
        page.locator("a[data-qa='continue-button']").click();
    }
}
