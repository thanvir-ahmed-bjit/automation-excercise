package com.bjitgroup.dataproviders;

import org.testng.annotations.DataProvider;

/**
 * Test data for login submissions that the browser rejects before any request
 * is sent, because the email and password inputs are declared {@code required}
 * and the email input is {@code type="email"}.
 */
public final class LoginDataProvider {

    private LoginDataProvider() {
    }

    /**
     * Login inputs that fail HTML5 constraint validation.
     *
     * <p>Columns: description, email, password, locator key of the field expected
     * to be invalid, and the {@code ValidityState} flag expected on it.</p>
     *
     * <p>The flag is asserted rather than the browser's message text, because the
     * message is browser- and locale-dependent and would break the suite on a
     * non-English runner.</p>
     *
     * <p>No registered account is required: none of these rows reaches the
     * server, so the email values only need to be well-formed or deliberately
     * malformed.</p>
     */
    @DataProvider(name = "browserBlockedLoginData")
    public static Object[][] browserBlockedLoginData() {
        return new Object[][]{
                {"Both fields empty", "", "", "emailInput", "valueMissing"},
                {"Email empty, password provided", "", "Auto@123456", "emailInput", "valueMissing"},
                {"Password empty, email provided", "qa.user@example.com", "", "passwordInput", "valueMissing"},
                {"Malformed email without @", "not-an-email", "Auto@123456", "emailInput", "typeMismatch"},
                {"Malformed email without domain", "user@", "Auto@123456", "emailInput", "typeMismatch"}
        };
    }
}
