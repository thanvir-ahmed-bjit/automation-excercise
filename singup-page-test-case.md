# Test Cases - Automation Exercise

**Application URL:** http://automationexercise.com  
**Total Test Cases:** 57
##Signup Page Test Cases

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
