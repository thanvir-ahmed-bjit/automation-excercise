# Test Cases - Automation Exercise

**Application URL:** http://automationexercise.com  
**Total Test Cases:** 57

Each case below lists its pre-conditions, steps and expected results, plus whether it is
currently covered by the automated suite.

## Automation Coverage

| Status | Count |
|---|---|
| Automated | 30 |
| Automated (partial) | 2 |
| Not automated | 20 |
| Manual only | 5 |
| **Total** | **57** |

## Contents


**Signup / Registration**

- [Test Case 1: Register User](#test-case-1-register-user)
- [Test Case 2: Verify 'New User Signup!' section is displayed on the Signup / Login page](#test-case-2-verify-new-user-signup-section-is-displayed-on-the-signup-login-page)
- [Test Case 3: Signup with empty name and email fields](#test-case-3-signup-with-empty-name-and-email-fields)
- [Test Case 4: Signup with invalid email format](#test-case-4-signup-with-invalid-email-format)
- [Test Case 5: Verify name and email are carried to the Enter Account Information page and email is not editable](#test-case-5-verify-name-and-email-are-carried-to-the-enter-account-information-page-and-email-is-not-editable)
- [Test Case 6: Create Account with mandatory fields only](#test-case-6-create-account-with-mandatory-fields-only)
- [Test Case 7: Create Account with all optional fields populated](#test-case-7-create-account-with-all-optional-fields-populated)
- [Test Case 8: Create Account with a mandatory field left empty](#test-case-8-create-account-with-a-mandatory-field-left-empty)
- [Test Case 9: Verify Title radio buttons are optional and mutually exclusive](#test-case-9-verify-title-radio-buttons-are-optional-and-mutually-exclusive)
- [Test Case 10: Verify Date of Birth dropdowns are optional and contain valid ranges](#test-case-10-verify-date-of-birth-dropdowns-are-optional-and-contain-valid-ranges)
- [Test Case 11: Verify Country dropdown lists the supported countries and defaults correctly](#test-case-11-verify-country-dropdown-lists-the-supported-countries-and-defaults-correctly)
- [Test Case 12: Verify password field masks the entered characters on the signup page](#test-case-12-verify-password-field-masks-the-entered-characters-on-the-signup-page)
- [Test Case 13: Create Account with invalid mobile number format](#test-case-13-create-account-with-invalid-mobile-number-format)
- [Test Case 14: Create Account with XSS and SQL injection payloads in text fields](#test-case-14-create-account-with-xss-and-sql-injection-payloads-in-text-fields)
- [Test Case 15: Register User with existing email](#test-case-15-register-user-with-existing-email)
- [Test Case 16: Verify newly registered user can log in with the same credentials](#test-case-16-verify-newly-registered-user-can-log-in-with-the-same-credentials)

**Login / Logout / Session**

- [Test Case 17: Login User with correct email and password](#test-case-17-login-user-with-correct-email-and-password)
- [Test Case 18: Login User with incorrect email and password](#test-case-18-login-user-with-incorrect-email-and-password)
- [Test Case 19: Login User with unregistered email address](#test-case-19-login-user-with-unregistered-email-address)
- [Test Case 20: Login User with registered email and incorrect password](#test-case-20-login-user-with-registered-email-and-incorrect-password)
- [Test Case 21: Login User with both email and password fields empty](#test-case-21-login-user-with-both-email-and-password-fields-empty)
- [Test Case 22: Login User with empty email and valid password](#test-case-22-login-user-with-empty-email-and-valid-password)
- [Test Case 23: Login User with valid email and empty password](#test-case-23-login-user-with-valid-email-and-empty-password)
- [Test Case 24: Login User with invalid email format](#test-case-24-login-user-with-invalid-email-format)
- [Test Case 25: Login User with leading and trailing spaces in email](#test-case-25-login-user-with-leading-and-trailing-spaces-in-email)
- [Test Case 26: Login User with email address in different letter case](#test-case-26-login-user-with-email-address-in-different-letter-case)
- [Test Case 27: Login User with password in different letter case](#test-case-27-login-user-with-password-in-different-letter-case)
- [Test Case 28: Verify password field masks the entered characters](#test-case-28-verify-password-field-masks-the-entered-characters)
- [Test Case 29: Verify login credentials are not exposed in the URL](#test-case-29-verify-login-credentials-are-not-exposed-in-the-url)
- [Test Case 30: Login User with SQL injection payload](#test-case-30-login-user-with-sql-injection-payload)
- [Test Case 31: Login User with XSS payload in input fields](#test-case-31-login-user-with-xss-payload-in-input-fields)
- [Test Case 32: Verify application behaviour on repeated failed login attempts](#test-case-32-verify-application-behaviour-on-repeated-failed-login-attempts)
- [Test Case 33: Login User by pressing the Enter key](#test-case-33-login-user-by-pressing-the-enter-key)
- [Test Case 34: Verify user session persists after page refresh](#test-case-34-verify-user-session-persists-after-page-refresh)
- [Test Case 35: Logout User](#test-case-35-logout-user)
- [Test Case 36: Verify user session is invalidated after logout](#test-case-36-verify-user-session-is-invalidated-after-logout)

**Products, Cart, Checkout & Navigation**

- [Test Case 37: Contact Us Form](#test-case-37-contact-us-form)
- [Test Case 38: Verify Test Cases Page](#test-case-38-verify-test-cases-page)
- [Test Case 39: Verify All Products and Product Detail Page](#test-case-39-verify-all-products-and-product-detail-page)
- [Test Case 40: Search Product](#test-case-40-search-product)
- [Test Case 41: Verify Subscription in Home Page](#test-case-41-verify-subscription-in-home-page)
- [Test Case 42: Verify Subscription in Cart Page](#test-case-42-verify-subscription-in-cart-page)
- [Test Case 43: Add Products in Cart](#test-case-43-add-products-in-cart)
- [Test Case 44: Verify Product Quantity in Cart](#test-case-44-verify-product-quantity-in-cart)
- [Test Case 45: Place Order: Register while Checkout](#test-case-45-place-order-register-while-checkout)
- [Test Case 46: Place Order: Register before Checkout](#test-case-46-place-order-register-before-checkout)
- [Test Case 47: Place Order: Login before Checkout](#test-case-47-place-order-login-before-checkout)
- [Test Case 48: Remove Products From Cart](#test-case-48-remove-products-from-cart)
- [Test Case 49: View Category Products](#test-case-49-view-category-products)
- [Test Case 50: View & Cart Brand Products](#test-case-50-view-cart-brand-products)
- [Test Case 51: Search Products and Verify Cart After Login](#test-case-51-search-products-and-verify-cart-after-login)
- [Test Case 52: Add Review on Product](#test-case-52-add-review-on-product)
- [Test Case 53: Add to Cart from Recommended Items](#test-case-53-add-to-cart-from-recommended-items)
- [Test Case 54: Verify Address Details in Checkout Page](#test-case-54-verify-address-details-in-checkout-page)
- [Test Case 55: Download Invoice After Purchase Order](#test-case-55-download-invoice-after-purchase-order)
- [Test Case 56: Verify Scroll Up using 'Arrow' Button and Scroll Down Functionality](#test-case-56-verify-scroll-up-using-arrow-button-and-scroll-down-functionality)
- [Test Case 57: Verify Scroll Up without 'Arrow' Button and Scroll Down Functionality](#test-case-57-verify-scroll-up-without-arrow-button-and-scroll-down-functionality)

---

## Test Case 1: Register User

**Main Screen:** Signup / Login Page &nbsp;|&nbsp; **Module:** User Account / Registration &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated (partial) — `SignupTest#newUserShouldCompleteSignup`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Enter name and email address.
5. Click 'Signup' button.
6. Fill details: Title, Name, Email, Password, Date of Birth.
7. Select checkbox 'Sign up for our newsletter!'.
8. Select checkbox 'Receive special offers from our partners!'.
9. Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number.
10. Click 'Create Account' button.
11. Click 'Continue' button.
12. Click 'Delete Account' button.

**Expected Results**

1. Home page is visible successfully.
2. 'New User Signup!' is visible.
3. 'ENTER ACCOUNT INFORMATION' is visible.
4. 'ACCOUNT CREATED!' is visible.
5. 'Logged in as username' is visible.
6. 'ACCOUNT DELETED!' is visible and user can click 'Continue' button.

**Notes:** Automated coverage does not select the 'Sign up for our newsletter!' or 'Receive special offers from our partners!' checkboxes, so those remain a manual check.

---

## Test Case 2: Verify 'New User Signup!' section is displayed on the Signup / Login page

**Main Screen:** Signup / Login Page &nbsp;|&nbsp; **Module:** User Registration &nbsp;|&nbsp; **Test Type:** UI/UX  
**Automation:** Not automated

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Observe the right-hand side of the page.

**Expected Results**

1. Home page is visible successfully.
2. 'New User Signup!' is visible.
3. 'Name' and 'Email Address' input fields are visible and enabled.
4. 'Signup' button is visible and enabled.

**Notes:** Entry-point check for the registration flow.

---

## Test Case 3: Signup with empty name and email fields

**Main Screen:** Signup / Login Page &nbsp;|&nbsp; **Module:** User Registration &nbsp;|&nbsp; **Test Type:** Negative  
**Automation:** Not automated

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Leave both the 'Name' and 'Email Address' fields empty under 'New User Signup!'.
5. Click 'Signup' button.

**Expected Results**

1. Home page is visible successfully.
2. 'New User Signup!' is visible.
3. The form is not submitted and no request is sent.
4. Browser validation message 'Please fill out this field' is shown on the 'Name' field.
5. User remains on the Signup / Login page.

**Notes:** Validation is enforced by the browser, not the application, because the field carries the HTML5 'required' attribute. Assert on the field's validation state rather than a page-rendered error. Automatable via the Constraint Validation API (BrowserActions.hasValidityFlag), the same technique used for the login empty-field cases.

---

## Test Case 4: Signup with invalid email format

**Main Screen:** Signup / Login Page &nbsp;|&nbsp; **Module:** User Registration &nbsp;|&nbsp; **Test Type:** Negative  
**Automation:** Not automated

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Enter a valid name and an invalid email format such as 'testuser' or 'testuser.com'.
5. Click 'Signup' button.

**Expected Results**

1. Home page is visible successfully.
2. 'New User Signup!' is visible.
3. The form is not submitted and no request is sent.
4. Browser validation message "Please include an '@' in the email address" is shown.
5. 'ENTER ACCOUNT INFORMATION' page is not displayed.

**Notes:** The signup email input is type='email', so the format check is performed by the browser. Automatable via the Constraint Validation API (BrowserActions.hasValidityFlag).

---

## Test Case 5: Verify name and email are carried to the Enter Account Information page and email is not editable

**Main Screen:** Signup Page &nbsp;|&nbsp; **Module:** User Registration &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Not automated

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. An email address that has not been registered before is available.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Enter a name and an unused valid email address under 'New User Signup!'.
5. Click 'Signup' button.
6. Inspect the 'Name' and 'Email' fields on the account information page.
7. Attempt to type into the 'Email' field.

**Expected Results**

1. Home page is visible successfully.
2. 'New User Signup!' is visible.
3. 'ENTER ACCOUNT INFORMATION' is visible.
4. 'Name' field is pre-filled with the entered name and remains editable.
5. 'Email' field is pre-filled with the entered email and is disabled.
6. The email value cannot be modified by the user.

**Notes:** The email input is rendered with the 'disabled' attribute, so it is also excluded from the form submission payload. The name input is not disabled and can still be changed at this step.

---

## Test Case 6: Create Account with mandatory fields only

**Main Screen:** Signup Page &nbsp;|&nbsp; **Module:** User Registration &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Not automated

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. An email address that has not been registered before is available.
5. Name and email have been entered under 'New User Signup!' and 'Signup' has been clicked, so the 'ENTER ACCOUNT INFORMATION' page is displayed.

**Steps**

1. Enter a valid password.
2. Leave Title, Date of Birth, Company, Address 2, and both checkboxes untouched.
3. Fill First name, Last name, Address, State, City, Zipcode and Mobile Number.
4. Leave Country at its default value.
5. Click 'Create Account' button.

**Expected Results**

1. 'ACCOUNT CREATED!' is visible.
2. 'Continue' button is visible.
3. On clicking 'Continue', 'Logged in as username' is visible.

**Notes:** Confirms optional fields are genuinely optional. Password, First name, Last name, Address, Country, State, City, Zipcode and Mobile Number all carry the HTML5 'required' attribute. Not covered by the automated signup, which always selects a Title and Date of Birth. Verifying that the optional fields are genuinely optional therefore still requires a manual run.

---

## Test Case 7: Create Account with all optional fields populated

**Main Screen:** Signup Page &nbsp;|&nbsp; **Module:** User Registration &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Not automated

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. An email address that has not been registered before is available.
5. Name and email have been entered under 'New User Signup!' and 'Signup' has been clicked, so the 'ENTER ACCOUNT INFORMATION' page is displayed.

**Steps**

1. Select the 'Mr.' or 'Mrs.' title radio button.
2. Enter a valid password.
3. Select a Day, Month and Year for Date of Birth.
4. Select the 'Sign up for our newsletter!' checkbox.
5. Select the 'Receive special offers from our partners!' checkbox.
6. Fill First name, Last name, Company, Address, Address 2, State, City, Zipcode and Mobile Number.
7. Select a Country from the dropdown.
8. Click 'Create Account' button.

**Expected Results**

1. All entered values are accepted without validation errors.
2. 'ACCOUNT CREATED!' is visible.
3. On clicking 'Continue', 'Logged in as username' is visible.
4. The saved values are reflected on the account or address details page.

**Notes:** Full-field positive path, complementing the mandatory-only case.

---

## Test Case 8: Create Account with a mandatory field left empty

**Main Screen:** Signup Page &nbsp;|&nbsp; **Module:** User Registration &nbsp;|&nbsp; **Test Type:** Negative  
**Automation:** Automated — `SignupTest#createAccountShouldBeBlockedWhenMandatoryFieldIsEmpty (8 data rows)`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. An email address that has not been registered before is available.
5. Name and email have been entered under 'New User Signup!' and 'Signup' has been clicked, so the 'ENTER ACCOUNT INFORMATION' page is displayed.

**Steps**

1. Fill every mandatory field with valid data except one.
2. Leave that single mandatory field empty.
3. Click 'Create Account' button.
4. Repeat steps 1 to 3 for each mandatory field in turn: Password, First name, Last name, Address, State, City, Zipcode, Mobile Number.

**Expected Results**

1. The form is not submitted on any iteration.
2. Browser validation message is displayed on the empty field.
3. Focus moves to the first invalid field.
4. 'ACCOUNT CREATED!' is not displayed.

**Notes:** Run once per mandatory field. Validation is enforced by the browser, not the application, because the field carries the HTML5 'required' attribute. Assert on the field's validation state rather than a page-rendered error.

---

## Test Case 9: Verify Title radio buttons are optional and mutually exclusive

**Main Screen:** Signup Page &nbsp;|&nbsp; **Module:** User Registration &nbsp;|&nbsp; **Test Type:** UI/UX  
**Automation:** Not automated

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. An email address that has not been registered before is available.
5. Name and email have been entered under 'New User Signup!' and 'Signup' has been clicked, so the 'ENTER ACCOUNT INFORMATION' page is displayed.

**Steps**

1. Observe the 'Mr.' and 'Mrs.' radio buttons.
2. Select 'Mr.' and observe the selection state.
3. Select 'Mrs.' and observe the selection state.
4. Complete the remaining mandatory fields without selecting any title and click 'Create Account'.

**Expected Results**

1. Both radio buttons are visible and neither is selected by default.
2. Selecting 'Mrs.' clears the 'Mr.' selection, as both share the same group.
3. Only one title can be selected at a time.
4. Account is created successfully even when no title is selected.

**Notes:** Both radios use name='title', so they are mutually exclusive and neither is required.

---

## Test Case 10: Verify Date of Birth dropdowns are optional and contain valid ranges

**Main Screen:** Signup Page &nbsp;|&nbsp; **Module:** User Registration &nbsp;|&nbsp; **Test Type:** UI/UX  
**Automation:** Not automated

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. An email address that has not been registered before is available.
5. Name and email have been entered under 'New User Signup!' and 'Signup' has been clicked, so the 'ENTER ACCOUNT INFORMATION' page is displayed.

**Steps**

1. Open the 'Day' dropdown and inspect the available values.
2. Open the 'Month' dropdown and inspect the available values.
3. Open the 'Year' dropdown and inspect the available values.
4. Complete the mandatory fields without selecting a date of birth and click 'Create Account'.

**Expected Results**

1. 'Day' offers values 1 to 31.
2. 'Month' offers all twelve months.
3. 'Year' offers a descending list of years.
4. All three dropdowns are blank by default.
5. Account is created successfully without a date of birth being selected.

**Notes:** The three dropdowns do not carry the 'required' attribute, so date of birth is optional.

---

## Test Case 11: Verify Country dropdown lists the supported countries and defaults correctly

**Main Screen:** Signup Page &nbsp;|&nbsp; **Module:** User Registration &nbsp;|&nbsp; **Test Type:** UI/UX  
**Automation:** Not automated

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. An email address that has not been registered before is available.
5. Name and email have been entered under 'New User Signup!' and 'Signup' has been clicked, so the 'ENTER ACCOUNT INFORMATION' page is displayed.

**Steps**

1. Locate the 'Country' dropdown under 'ADDRESS INFORMATION'.
2. Observe the value selected by default.
3. Open the dropdown and inspect the full list of options.
4. Select a different country and complete the registration.

**Expected Results**

1. 'India' is selected by default.
2. The dropdown offers exactly seven options: India, United States, Canada, Australia, Israel, New Zealand and Singapore.
3. No blank or duplicate option is present.
4. The selected country is saved and shown on the address details page.

**Notes:** Country is a required select. Verify the saved value on the address details page, not only in the form.

---

## Test Case 12: Verify password field masks the entered characters on the signup page

**Main Screen:** Signup Page &nbsp;|&nbsp; **Module:** User Registration &nbsp;|&nbsp; **Test Type:** Security  
**Automation:** Not automated

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. An email address that has not been registered before is available.
5. Name and email have been entered under 'New User Signup!' and 'Signup' has been clicked, so the 'ENTER ACCOUNT INFORMATION' page is displayed.

**Steps**

1. Enter any value into the 'Password' field.
2. Observe the characters rendered in the field.

**Expected Results**

1. Entered characters are masked as dots or asterisks.
2. The password is not rendered in plain text at any point.

**Notes:** Field must be type='password'.

---

## Test Case 13: Create Account with invalid mobile number format

**Main Screen:** Signup Page &nbsp;|&nbsp; **Module:** User Registration &nbsp;|&nbsp; **Test Type:** Negative  
**Automation:** Manual only

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. An email address that has not been registered before is available.
5. Name and email have been entered under 'New User Signup!' and 'Signup' has been clicked, so the 'ENTER ACCOUNT INFORMATION' page is displayed.

**Steps**

1. Fill all mandatory fields with valid data.
2. Enter an invalid value in 'Mobile Number' such as alphabetic characters, special characters, or an excessively long numeric string.
3. Click 'Create Account' button.
4. Record the actual behaviour.

**Expected Results**

1. The application rejects the invalid mobile number with a clear validation message.
2. The account is not created with invalid contact data.

**Notes:** The field is a plain text input with no pattern attribute, so any server-side validation must be confirmed. If invalid values are accepted, record the actual behaviour and raise it as a data-quality defect rather than assuming a failure. The Mobile Number input has no 'pattern' attribute, so there is no client-side format check. Determine the server-side behaviour manually once, then the case can be automated with a real assertion.

---

## Test Case 14: Create Account with XSS and SQL injection payloads in text fields

**Main Screen:** Signup Page &nbsp;|&nbsp; **Module:** User Registration &nbsp;|&nbsp; **Test Type:** Security  
**Automation:** Manual only

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. An email address that has not been registered before is available.
5. Name and email have been entered under 'New User Signup!' and 'Signup' has been clicked, so the 'ENTER ACCOUNT INFORMATION' page is displayed.

**Steps**

1. Enter the payload <script>alert(1)</script> into the First name field.
2. Enter the payload ' OR '1'='1 into the Address field.
3. Complete the remaining mandatory fields with valid data.
4. Click 'Create Account' button.
5. Navigate to the account and address details pages where these values are displayed.

**Expected Results**

1. No script is executed and no alert dialog appears at any point.
2. Payloads are escaped and rendered as plain text wherever they are displayed.
3. No database error, SQL statement or stack trace is shown.
4. The application remains stable.

**Notes:** Stored XSS check: the values must be re-inspected on every page that later renders them, not only on the response immediately after submission. Requires an intercepting proxy: the email input is type='email', so raw payloads are blocked by the browser before reaching the server. Best covered by DAST tooling rather than UI automation.

---

## Test Case 15: Register User with existing email

**Main Screen:** Signup Page &nbsp;|&nbsp; **Module:** User Registration &nbsp;|&nbsp; **Test Type:** Negative  
**Automation:** Automated — `SignupTest#registerUserWithExistingEmailShouldShowError`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. An account is already registered with the email address to be used.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Enter name and an already registered email address.
5. Click 'Signup' button.

**Expected Results**

1. Home page is visible successfully.
2. 'New User Signup!' is visible.
3. Error 'Email Address already exist!' is visible.

---

## Test Case 16: Verify newly registered user can log in with the same credentials

**Main Screen:** Signup Page &nbsp;|&nbsp; **Module:** User Registration &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated (partial) — `LoginTest#loginWithCorrectCredentialsShouldSucceed`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. A user account has just been created through the signup flow and its email and password are known.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. If logged in, click 'Logout' button.
5. Enter the email address and password used during registration into the 'Login to your account' form.
6. Click 'Login' button.

**Expected Results**

1. 'Login to your account' is visible.
2. Login is successful.
3. 'Logged in as username' is visible with the registered name.

**Notes:** Closes the loop between registration and authentication; confirms the credentials were persisted as entered.

---

## Test Case 17: Login User with correct email and password

**Main Screen:** Login Page &nbsp;|&nbsp; **Module:** User Login &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `LoginTest#loginWithCorrectCredentialsShouldSucceed`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. A registered user account exists with a valid email address and password.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Enter correct email address and password.
5. Click 'Login' button.
6. Click 'Delete Account' button.

**Expected Results**

1. Home page is visible successfully.
2. 'Login to your account' is visible.
3. 'Logged in as username' is visible.
4. 'ACCOUNT DELETED!' is visible.

---

## Test Case 18: Login User with incorrect email and password

**Main Screen:** Login Page &nbsp;|&nbsp; **Module:** User Login &nbsp;|&nbsp; **Test Type:** Negative  
**Automation:** Automated — `LoginTest#invalidCredentialsShouldShowError`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Enter incorrect email address and password.
5. Click 'Login' button.

**Expected Results**

1. Home page is visible successfully.
2. 'Login to your account' is visible.
3. Error 'Your email or password is incorrect!' is visible.

---

## Test Case 19: Login User with unregistered email address

**Main Screen:** Login Page &nbsp;|&nbsp; **Module:** User Login &nbsp;|&nbsp; **Test Type:** Negative  
**Automation:** Not automated

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. The email address used is not registered in the application.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Enter a valid-format email address that is not registered, and any password.
5. Click 'Login' button.

**Expected Results**

1. Home page is visible successfully.
2. 'Login to your account' is visible.
3. Error 'Your email or password is incorrect!' is visible.
4. User remains on the login page and is not authenticated.

**Notes:** Paired with 'Login User with registered email and incorrect password'. Both must return an identical message; any difference allows user enumeration.

---

## Test Case 20: Login User with registered email and incorrect password

**Main Screen:** Login Page &nbsp;|&nbsp; **Module:** User Login &nbsp;|&nbsp; **Test Type:** Negative  
**Automation:** Not automated

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. A registered user account exists with a valid email address and password.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Enter a registered email address and an incorrect password.
5. Click 'Login' button.

**Expected Results**

1. Home page is visible successfully.
2. 'Login to your account' is visible.
3. Error 'Your email or password is incorrect!' is visible.
4. The message is word-for-word identical to the unregistered-email case.
5. User remains on the login page and is not authenticated.

**Notes:** Security check for user enumeration. If this message differs from the unregistered-email message, raise a defect.

---

## Test Case 21: Login User with both email and password fields empty

**Main Screen:** Login Page &nbsp;|&nbsp; **Module:** User Login &nbsp;|&nbsp; **Test Type:** Negative  
**Automation:** Automated — `LoginTest#browserShouldBlockInvalidLoginSubmission [Both fields empty]`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Leave both the email and password fields empty.
5. Click 'Login' button.

**Expected Results**

1. Home page is visible successfully.
2. 'Login to your account' is visible.
3. The form is not submitted and no request is sent.
4. Browser validation message 'Please fill out this field' is shown on the email field.
5. User remains on the login page.

**Notes:** The email and password inputs carry the HTML5 'required' attribute and the email input is type='email', so this validation is enforced by the browser, not by the application. Assert on the field's validation state, not on a page-rendered error message.

---

## Test Case 22: Login User with empty email and valid password

**Main Screen:** Login Page &nbsp;|&nbsp; **Module:** User Login &nbsp;|&nbsp; **Test Type:** Negative  
**Automation:** Automated — `LoginTest#browserShouldBlockInvalidLoginSubmission [Email empty, password provided]`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. A registered user account exists with a valid email address and password.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Leave the email field empty and enter a valid password.
5. Click 'Login' button.

**Expected Results**

1. Home page is visible successfully.
2. 'Login to your account' is visible.
3. The form is not submitted.
4. Browser validation message is shown on the email field.
5. User remains on the login page.

**Notes:** The email and password inputs carry the HTML5 'required' attribute and the email input is type='email', so this validation is enforced by the browser, not by the application. Assert on the field's validation state, not on a page-rendered error message.

---

## Test Case 23: Login User with valid email and empty password

**Main Screen:** Login Page &nbsp;|&nbsp; **Module:** User Login &nbsp;|&nbsp; **Test Type:** Negative  
**Automation:** Automated — `LoginTest#browserShouldBlockInvalidLoginSubmission [Password empty, email provided]`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. A registered user account exists with a valid email address and password.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Enter a registered email address and leave the password field empty.
5. Click 'Login' button.

**Expected Results**

1. Home page is visible successfully.
2. 'Login to your account' is visible.
3. The form is not submitted.
4. Browser validation message is shown on the password field.
5. User remains on the login page.

**Notes:** The email and password inputs carry the HTML5 'required' attribute and the email input is type='email', so this validation is enforced by the browser, not by the application. Assert on the field's validation state, not on a page-rendered error message.

---

## Test Case 24: Login User with invalid email format

**Main Screen:** Login Page &nbsp;|&nbsp; **Module:** User Login &nbsp;|&nbsp; **Test Type:** Negative  
**Automation:** Automated — `LoginTest#browserShouldBlockInvalidLoginSubmission [Malformed email without @ / without domain]`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Enter an invalid email format such as 'testuser' or 'testuser.com', and a valid password.
5. Click 'Login' button.

**Expected Results**

1. Home page is visible successfully.
2. 'Login to your account' is visible.
3. The form is not submitted and no request is sent.
4. Browser validation message "Please include an '@' in the email address" is shown.
5. User remains on the login page.

**Notes:** The email and password inputs carry the HTML5 'required' attribute and the email input is type='email', so this validation is enforced by the browser, not by the application. Assert on the field's validation state, not on a page-rendered error message.

---

## Test Case 25: Login User with leading and trailing spaces in email

**Main Screen:** Login Page &nbsp;|&nbsp; **Module:** User Login &nbsp;|&nbsp; **Test Type:** Negative  
**Automation:** Not automated

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. A registered user account exists with a valid email address and password.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Enter the registered email address with leading and trailing spaces, and the correct password.
5. Click 'Login' button.

**Expected Results**

1. Home page is visible successfully.
2. 'Login to your account' is visible.
3. Surrounding whitespace is trimmed.
4. Login is successful and 'Logged in as username' is visible.

**Notes:** Whitespace is commonly introduced by copy/paste. If login fails, raise a defect for missing input trimming.

---

## Test Case 26: Login User with email address in different letter case

**Main Screen:** Login Page &nbsp;|&nbsp; **Module:** User Login &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Not automated

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. A registered user account exists with a valid email address and password.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Enter the registered email address in uppercase (for example TESTUSER@EXAMPLE.COM) and the correct password.
5. Click 'Login' button.

**Expected Results**

1. Home page is visible successfully.
2. 'Login to your account' is visible.
3. Login is successful and 'Logged in as username' is visible.
4. Email address is treated as case-insensitive.

**Notes:** Email addresses are case-insensitive by RFC. Failure here is a defect.

---

## Test Case 27: Login User with password in different letter case

**Main Screen:** Login Page &nbsp;|&nbsp; **Module:** User Login &nbsp;|&nbsp; **Test Type:** Negative  
**Automation:** Not automated

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. A registered user account exists with a valid email address and password.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Enter the registered email address and the correct password with its letter case changed.
5. Click 'Login' button.

**Expected Results**

1. Home page is visible successfully.
2. 'Login to your account' is visible.
3. Login fails and error 'Your email or password is incorrect!' is visible.
4. Password is treated as case-sensitive.

**Notes:** Deliberate contrast with the email case-insensitivity test. A successful login here means the password is being normalised, which is a serious defect.

---

## Test Case 28: Verify password field masks the entered characters

**Main Screen:** Login Page &nbsp;|&nbsp; **Module:** User Login &nbsp;|&nbsp; **Test Type:** UI/UX  
**Automation:** Not automated

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Enter any value into the password field.
5. Observe the characters displayed in the field.

**Expected Results**

1. Home page is visible successfully.
2. 'Login to your account' is visible.
3. Entered characters are masked as dots or asterisks.
4. The password is not rendered in plain text.

**Notes:** Field must be type='password'.

---

## Test Case 29: Verify login credentials are not exposed in the URL

**Main Screen:** Login Page &nbsp;|&nbsp; **Module:** User Login &nbsp;|&nbsp; **Test Type:** Security  
**Automation:** Not automated

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. A registered user account exists with a valid email address and password.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Enter a registered email address and the correct password.
5. Click 'Login' button.
6. Observe the browser address bar after submission.

**Expected Results**

1. Home page is visible successfully.
2. 'Login to your account' is visible.
3. The form is submitted using the POST method.
4. The URL contains no email or password parameter.
5. Credentials do not appear in browser history.

**Notes:** Login form posts to /login. Credentials in a query string would be logged by proxies and browser history.

---

## Test Case 30: Login User with SQL injection payload

**Main Screen:** Login Page &nbsp;|&nbsp; **Module:** User Login &nbsp;|&nbsp; **Test Type:** Security  
**Automation:** Manual only

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Enter a valid-format email address and the payload ' OR '1'='1 in the password field.
5. Click 'Login' button.
6. Repeat the attempt with the payload in the email field submitted via an intercepting proxy.

**Expected Results**

1. Home page is visible successfully.
2. 'Login to your account' is visible.
3. Login fails and the standard error 'Your email or password is incorrect!' is visible.
4. No database error, SQL statement, or stack trace is displayed.
5. User is not authenticated.

**Notes:** The email input is type='email', so a raw payload there is blocked by the browser. Place the payload in the password field, or bypass client-side validation with a proxy, to reach the server. Requires an intercepting proxy to bypass the type='email' client-side check.

---

## Test Case 31: Login User with XSS payload in input fields

**Main Screen:** Login Page &nbsp;|&nbsp; **Module:** User Login &nbsp;|&nbsp; **Test Type:** Security  
**Automation:** Manual only

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Enter the payload <script>alert(1)</script> into the password field and a valid-format email address.
5. Click 'Login' button.
6. Observe whether the payload is reflected on the page.

**Expected Results**

1. Home page is visible successfully.
2. 'Login to your account' is visible.
3. Login fails with the standard error message.
4. The payload is escaped and rendered as text if reflected.
5. No script is executed and no alert dialog appears.

**Notes:** Check both the reflected response and any stored location where the value may later be displayed. Requires an intercepting proxy to bypass the type='email' client-side check.

---

## Test Case 32: Verify application behaviour on repeated failed login attempts

**Main Screen:** Login Page &nbsp;|&nbsp; **Module:** User Login &nbsp;|&nbsp; **Test Type:** Security  
**Automation:** Manual only

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. A registered user account exists with a valid email address and password.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Enter a registered email address with an incorrect password.
5. Click 'Login' button.
6. Repeat steps 4 and 5 ten times in quick succession.
7. Observe the application response and record it.

**Expected Results**

1. Home page is visible successfully.
2. 'Login to your account' is visible.
3. A protective control is applied after repeated failures, such as account lockout, rate limiting, or a CAPTCHA challenge.
4. No account credentials are disclosed in any response.

**Notes:** Exploratory in nature. If no protective control exists, record the actual behaviour and raise it as a security observation rather than a functional defect. Kept manual on purpose: repeated deliberate authentication failures against a shared public demo site trigger edge rate limiting that destabilises unrelated automated tests.

---

## Test Case 33: Login User by pressing the Enter key

**Main Screen:** Login Page &nbsp;|&nbsp; **Module:** User Login &nbsp;|&nbsp; **Test Type:** UI/UX  
**Automation:** Not automated

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. A registered user account exists with a valid email address and password.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Enter a registered email address and the correct password.
5. Press the 'Enter' key while focus is in the password field.

**Expected Results**

1. Home page is visible successfully.
2. 'Login to your account' is visible.
3. The form is submitted without clicking the 'Login' button.
4. Login is successful and 'Logged in as username' is visible.

**Notes:** Keyboard accessibility. The Login control is a submit button inside the form, so Enter should submit.

---

## Test Case 34: Verify user session persists after page refresh

**Main Screen:** Login Page &nbsp;|&nbsp; **Module:** User Login &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Not automated

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. A registered user account exists with a valid email address and password.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Enter a registered email address and the correct password.
5. Click 'Login' button.
6. Refresh the page using F5.

**Expected Results**

1. Home page is visible successfully.
2. 'Login to your account' is visible.
3. Login is successful and 'Logged in as username' is visible.
4. After refresh the user remains logged in and 'Logged in as username' is still visible.

---

## Test Case 35: Logout User

**Main Screen:** Login Page &nbsp;|&nbsp; **Module:** User Login &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `LogoutTest#logoutUserShouldNavigateToLoginPage`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. A registered user account exists with a valid email address and password.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Enter correct email address and password.
5. Click 'Login' button.
6. Click 'Logout' button.

**Expected Results**

1. Home page is visible successfully.
2. 'Login to your account' is visible.
3. 'Logged in as username' is visible.
4. User is navigated to the login page.

---

## Test Case 36: Verify user session is invalidated after logout

**Main Screen:** Login Page &nbsp;|&nbsp; **Module:** User Login &nbsp;|&nbsp; **Test Type:** Security  
**Automation:** Not automated

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. A registered user account exists with a valid email address and password.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Signup / Login' button.
4. Enter a registered email address and the correct password.
5. Click 'Login' button.
6. Click 'Logout' button.
7. Click the browser 'Back' button.

**Expected Results**

1. Home page is visible successfully.
2. 'Login to your account' is visible.
3. Login is successful and 'Logged in as username' is visible.
4. After logout the user is navigated to the login page and 'Signup / Login' is visible.
5. Pressing 'Back' does not restore the authenticated session or protected content.

**Notes:** Verifies the session is invalidated server-side, not merely hidden from the UI.

---

## Test Case 37: Contact Us Form

**Main Screen:** Contact Us Page &nbsp;|&nbsp; **Module:** Contact Us &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `ContactUs#userShouldSubmitContactUsFormSuccessfully`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. A file is available on the local machine to upload.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Contact Us' button.
4. Enter name, email, subject and message.
5. Upload file.
6. Click 'Submit' button.
7. Click 'OK' button on the confirmation dialog.
8. Click 'Home' button.

**Expected Results**

1. Home page is visible successfully.
2. 'GET IN TOUCH' is visible.
3. Success message 'Success! Your details have been submitted successfully.' is visible.
4. User is navigated back to the home page successfully.

**Notes:** Verified against the live site: the submit handler returns false in both branches, so the form is never actually posted to /contact_us. The 'Success!' banner and the file upload are purely client-side, and no data reaches the server. Confirm this is intended demo behaviour before treating the success message as proof of submission.

---

## Test Case 38: Verify Test Cases Page

**Main Screen:** Test Cases Page &nbsp;|&nbsp; **Module:** Navigation &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `TestCasesPageTest#verifyTestCasesPageIsAccessible`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Test Cases' button.

**Expected Results**

1. Home page is visible successfully.
2. User is navigated to the Test Cases page successfully.

---

## Test Case 39: Verify All Products and Product Detail Page

**Main Screen:** Products Page &nbsp;|&nbsp; **Module:** Products &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `ProductTest#verifyAllProductsAndProductDetailPage`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Products' button.
4. Click on 'View Product' of the first product.

**Expected Results**

1. Home page is visible successfully.
2. User is navigated to ALL PRODUCTS page successfully.
3. The products list is visible.
4. User is landed on the product detail page.
5. Product detail is visible: product name, category, price, availability, condition, brand.

---

## Test Case 40: Search Product

**Main Screen:** Products Page &nbsp;|&nbsp; **Module:** Products / Search &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `ProductTest#searchProductShouldShowResults`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Products' button.
4. Enter product name in the search input and click the search button.

**Expected Results**

1. Home page is visible successfully.
2. User is navigated to ALL PRODUCTS page successfully.
3. 'SEARCHED PRODUCTS' is visible.
4. All the products related to the search are visible.

---

## Test Case 41: Verify Subscription in Home Page

**Main Screen:** Home Page (Footer) &nbsp;|&nbsp; **Module:** Subscription &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `SubscriptionTest#verifySubscriptionOnHomePage`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Scroll down to the footer.
4. Enter email address in the input and click the arrow button.

**Expected Results**

1. Home page is visible successfully.
2. Text 'SUBSCRIPTION' is visible.
3. Success message 'You have been successfully subscribed!' is visible.

---

## Test Case 42: Verify Subscription in Cart Page

**Main Screen:** Cart Page (Footer) &nbsp;|&nbsp; **Module:** Subscription &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `SubscriptionTest#verifySubscriptionOnCartPage`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click 'Cart' button.
4. Scroll down to the footer.
5. Enter email address in the input and click the arrow button.

**Expected Results**

1. Home page is visible successfully.
2. Text 'SUBSCRIPTION' is visible.
3. Success message 'You have been successfully subscribed!' is visible.

---

## Test Case 43: Add Products in Cart

**Main Screen:** Products / Cart Page &nbsp;|&nbsp; **Module:** Cart &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `CartTest#addProductsToCartAndVerify`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click 'Products' button.
4. Hover over the first product and click 'Add to cart'.
5. Click 'Continue Shopping' button.
6. Hover over the second product and click 'Add to cart'.
7. Click 'View Cart' button.

**Expected Results**

1. Home page is visible successfully.
2. Both products are added to the Cart.
3. Their prices, quantity and total price are displayed correctly.

---

## Test Case 44: Verify Product Quantity in Cart

**Main Screen:** Product Detail / Cart Page &nbsp;|&nbsp; **Module:** Cart &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `CartTest#verifyProductQuantityInCart`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click 'View Product' for any product on the home page.
4. Increase quantity to 4.
5. Click 'Add to cart' button.
6. Click 'View Cart' button.

**Expected Results**

1. Home page is visible successfully.
2. Product detail is opened.
3. Product is displayed on the cart page with the exact quantity (4).

---

## Test Case 45: Place Order: Register while Checkout

**Main Screen:** Cart / Checkout Page &nbsp;|&nbsp; **Module:** Checkout &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `OrderTest#placeOrderRegisterWhileCheckout`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Add products to cart.
4. Click 'Cart' button.
5. Click 'Proceed To Checkout'.
6. Click 'Register / Login' button.
7. Fill all details in Signup and create account.
8. Click 'Continue' button.
9. Click 'Cart' button.
10. Click 'Proceed To Checkout' button.
11. Enter description in the comment text area and click 'Place Order'.
12. Enter payment details: Name on Card, Card Number, CVC, Expiration date.
13. Click 'Pay and Confirm Order' button.
14. Click 'Delete Account' button.

**Expected Results**

1. Home page is visible successfully.
2. Cart page is displayed.
3. 'ACCOUNT CREATED!' is visible.
4. 'Logged in as username' is visible at the top.
5. Address Details and Review Your Order are displayed.
6. Success message 'Your order has been placed successfully!' is visible.
7. 'ACCOUNT DELETED!' is visible and user can click 'Continue' button.

---

## Test Case 46: Place Order: Register before Checkout

**Main Screen:** Signup / Checkout Page &nbsp;|&nbsp; **Module:** Checkout &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `OrderTest#placeOrderRegisterBeforeCheckout`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click 'Signup / Login' button.
4. Fill all details in Signup and create account.
5. Click 'Continue' button.
6. Add products to cart.
7. Click 'Cart' button.
8. Click 'Proceed To Checkout'.
9. Enter description in the comment text area and click 'Place Order'.
10. Enter payment details: Name on Card, Card Number, CVC, Expiration date.
11. Click 'Pay and Confirm Order' button.
12. Click 'Delete Account' button.

**Expected Results**

1. Home page is visible successfully.
2. 'ACCOUNT CREATED!' is visible.
3. 'Logged in as username' is visible at the top.
4. Cart page is displayed.
5. Address Details and Review Your Order are displayed.
6. Success message 'Your order has been placed successfully!' is visible.
7. 'ACCOUNT DELETED!' is visible and user can click 'Continue' button.

---

## Test Case 47: Place Order: Login before Checkout

**Main Screen:** Login / Checkout Page &nbsp;|&nbsp; **Module:** Checkout &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `OrderTest#placeOrderLoginBeforeCheckout`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. A registered user account exists with a valid email address and password.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click 'Signup / Login' button.
4. Fill email, password and click 'Login' button.
5. Add products to cart.
6. Click 'Cart' button.
7. Click 'Proceed To Checkout'.
8. Enter description in the comment text area and click 'Place Order'.
9. Enter payment details: Name on Card, Card Number, CVC, Expiration date.
10. Click 'Pay and Confirm Order' button.
11. Click 'Delete Account' button.

**Expected Results**

1. Home page is visible successfully.
2. 'Logged in as username' is visible at the top.
3. Cart page is displayed.
4. Address Details and Review Your Order are displayed.
5. Success message 'Your order has been placed successfully!' is visible.
6. 'ACCOUNT DELETED!' is visible and user can click 'Continue' button.

---

## Test Case 48: Remove Products From Cart

**Main Screen:** Cart Page &nbsp;|&nbsp; **Module:** Cart &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `CartTest#removeProductFromCart`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Add products to cart.
4. Click 'Cart' button.
5. Click 'X' button corresponding to a particular product.

**Expected Results**

1. Home page is visible successfully.
2. Cart page is displayed.
3. Product is removed from the cart.

---

## Test Case 49: View Category Products

**Main Screen:** Home / Category Page &nbsp;|&nbsp; **Module:** Products / Categories &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `CategoryBrandTest#viewCategoryProducts`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Women' category.
4. Click on any category link under 'Women' category (e.g., Dress).
5. On the left side bar, click on any sub-category link of 'Men' category.

**Expected Results**

1. Categories are visible on the left side bar.
2. Category page is displayed and text 'WOMEN - TOPS PRODUCTS' is confirmed.
3. User is navigated to the selected 'Men' category page.

---

## Test Case 50: View & Cart Brand Products

**Main Screen:** Products / Brand Page &nbsp;|&nbsp; **Module:** Products / Brands &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `CategoryBrandTest#viewBrandProducts`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Products' button.
4. Click on any brand name.
5. On the left side bar, click on any other brand link.

**Expected Results**

1. Brands are visible on the left side bar.
2. User is navigated to the brand page and brand products are displayed.
3. User is navigated to the other brand page and can see its products.

---

## Test Case 51: Search Products and Verify Cart After Login

**Main Screen:** Products / Cart Page &nbsp;|&nbsp; **Module:** Cart / Search &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `ProductReviewTest#searchProductsAndVerifyCartAfterLogin`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.
4. A registered user account exists for login.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Products' button.
4. Enter product name in the search input and click the search button.
5. Add those products to cart.
6. Click 'Cart' button.
7. Click 'Signup / Login' button and submit login details.
8. Go to the Cart page again.

**Expected Results**

1. User is navigated to ALL PRODUCTS page successfully.
2. 'SEARCHED PRODUCTS' is visible.
3. All the products related to the search are visible.
4. Products are visible in the cart.
5. Those products are still visible in the cart after login.

---

## Test Case 52: Add Review on Product

**Main Screen:** Product Detail Page &nbsp;|&nbsp; **Module:** Products / Review &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `ProductReviewTest#addReviewOnProduct`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click on 'Products' button.
4. Click on 'View Product' button.
5. Enter name, email and review.
6. Click 'Submit' button.

**Expected Results**

1. User is navigated to ALL PRODUCTS page successfully.
2. 'Write Your Review' is visible.
3. Success message 'Thank you for your review.' is visible.

**Notes:** Verified against the live site: the success banner is displayed for only about 2 seconds before the page re-hides it, and its container renders with zero height. Any verification must target the inner success alert within that window.

---

## Test Case 53: Add to Cart from Recommended Items

**Main Screen:** Home / Cart Page &nbsp;|&nbsp; **Module:** Cart &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `CartTest#addToCartFromRecommendedItems`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Scroll to the bottom of the page.
4. Click on 'Add To Cart' on a recommended product.
5. Click on 'View Cart' button.

**Expected Results**

1. 'RECOMMENDED ITEMS' are visible.
2. Product is displayed on the cart page.

---

## Test Case 54: Verify Address Details in Checkout Page

**Main Screen:** Checkout / Address Page &nbsp;|&nbsp; **Module:** Checkout &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `OrderTest#verifyAddressDetailsInCheckout`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Click 'Signup / Login' button.
4. Fill all details in Signup and create account.
5. Click 'Continue' button.
6. Add products to cart.
7. Click 'Cart' button.
8. Click 'Proceed To Checkout'.
9. Click 'Delete Account' button.

**Expected Results**

1. Home page is visible successfully.
2. 'ACCOUNT CREATED!' is visible.
3. 'Logged in as username' is visible at the top.
4. Cart page is displayed.
5. The delivery address matches the address entered during account registration.
6. The billing address matches the address entered during account registration.
7. 'ACCOUNT DELETED!' is visible and user can click 'Continue' button.

---

## Test Case 55: Download Invoice After Purchase Order

**Main Screen:** Checkout / Invoice &nbsp;|&nbsp; **Module:** Checkout / Invoice &nbsp;|&nbsp; **Test Type:** Functional  
**Automation:** Automated — `OrderTest#downloadInvoiceAfterPurchase`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Add products to cart.
4. Click 'Cart' button.
5. Click 'Proceed To Checkout'.
6. Click 'Register / Login' button.
7. Fill all details in Signup and create account.
8. Click 'Continue' button.
9. Click 'Cart' button.
10. Click 'Proceed To Checkout' button.
11. Enter description in the comment text area and click 'Place Order'.
12. Enter payment details: Name on Card, Card Number, CVC, Expiration date.
13. Click 'Pay and Confirm Order' button.
14. Click 'Download Invoice' button.
15. Click 'Continue' button.
16. Click 'Delete Account' button.

**Expected Results**

1. Home page is visible successfully.
2. Cart page is displayed.
3. 'ACCOUNT CREATED!' is visible.
4. 'Logged in as username' is visible at the top.
5. Address Details and Review Your Order are displayed.
6. Success message 'Your order has been placed successfully!' is visible.
7. Invoice is downloaded successfully.
8. 'ACCOUNT DELETED!' is visible and user can click 'Continue' button.

---

## Test Case 56: Verify Scroll Up using 'Arrow' Button and Scroll Down Functionality

**Main Screen:** Home Page &nbsp;|&nbsp; **Module:** Navigation / UI &nbsp;|&nbsp; **Test Type:** UI/UX  
**Automation:** Automated — `ScrollTest#verifyScrollUpUsingArrowButton`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Scroll down the page to the bottom.
4. Click on the arrow at the bottom right side to move upward.

**Expected Results**

1. Home page is visible successfully.
2. 'SUBSCRIPTION' is visible.
3. Page is scrolled up and the text 'Full-Fledged practice website for Automation Engineers' is visible on screen.

---

## Test Case 57: Verify Scroll Up without 'Arrow' Button and Scroll Down Functionality

**Main Screen:** Home Page &nbsp;|&nbsp; **Module:** Navigation / UI &nbsp;|&nbsp; **Test Type:** UI/UX  
**Automation:** Automated — `ScrollTest#verifyScrollUpWithoutArrowButton`

**Pre-Conditions**

1. Internet connection is available.
2. Web browser is installed and available.
3. Application URL http://automationexercise.com is accessible.

**Steps**

1. Launch browser.
2. Navigate to http://automationexercise.com.
3. Scroll down the page to the bottom.
4. Scroll up the page to the top.

**Expected Results**

1. Home page is visible successfully.
2. 'SUBSCRIPTION' is visible.
3. Page is scrolled up and the text 'Full-Fledged practice website for Automation Engineers' is visible on screen.

---
