package com.bjitgroup.dataproviders;

import org.testng.annotations.DataProvider;

/**
 * Test data for the mandatory fields on the 'Enter Account Information' form.
 */
public final class SignupDataProvider {

    private SignupDataProvider() {
    }

    /**
     * Every field on the account information form that carries the HTML5
     * {@code required} attribute.
     *
     * <p>Columns: display name of the field and its locator key in
     * {@code locators/signup-page.properties}.</p>
     *
     * <p>Country is intentionally excluded: it is a required {@code <select>}
     * that already defaults to India, so it cannot be emptied through the UI and
     * has no unselected state to assert on.</p>
     */
    @DataProvider(name = "mandatorySignupFields")
    public static Object[][] mandatorySignupFields() {
        return new Object[][]{
                {"Password", "passwordInput"},
                {"First name", "firstNameInput"},
                {"Last name", "lastNameInput"},
                {"Address", "address1Input"},
                {"State", "stateInput"},
                {"City", "cityInput"},
                {"Zipcode", "zipcodeInput"},
                {"Mobile Number", "mobileNumberInput"}
        };
    }
}
