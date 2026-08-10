# Side-by-Side Coding Style Comparison

## 1. PAGE OBJECT RETRIEVAL

### ❌ SignupTest (WRONG - Creates new instances)
```java
private SignupPage signupPage() {
    BrowserActions browser = new BrowserActions(page(), context().config().timeoutMs());
    InputActions input = new InputActions(page());
    PageManager manager = new PageManager(page(), context().config().timeoutMs());
    return new SignupPage(browser, input, manager);
}

// Usage:
SignupPage signupPage = signupPage()
        .openHomePage();
```

**Problems:**
- ❌ Creates new BrowserActions instance (should be reused)
- ❌ Creates new InputActions instance (should be reused)  
- ❌ Creates new PageManager instance (should be cached)
- ❌ Bypasses lazy-loading + caching mechanism
- ❌ Inconsistent with framework architecture

---

### ✅ LoginTest (CORRECT - Uses PageManager)
```java
LoginPage loginPage = pages().loginPage();

// What happens internally:
// pages() → returns the cached PageManager instance from UiTestContext
// .loginPage() → uses lazy instantiation + caching (returns same instance per test)
```

**Benefits:**
- ✅ Reuses cached page objects across test
- ✅ Shares BrowserActions and InputActions
- ✅ Follows architectural pattern
- ✅ Centralized page object management

---

## 2. LOCATOR ACCESS PATTERN

### SignupPage (Defensive approach)
```java
private String s(String key) {
    String selector = loc.getProperty(key);
    if (selector == null || selector.isBlank()) {
        throw new IllegalStateException("Missing locator key in signup-page.properties: " + key);
    }
    return selector;
}

// Usage:
input.click(s("signupButton"));  // Fails fast if missing
```

### LoginPage (Direct approach)
```java
// No helper method - direct property access
loc.getProperty("loginButton")  // No null check

// Usage:
browser.waitForVisible(
        loc.getProperty("loginButton")
);
```

**Analysis:**
- SignupPage's `s()` method is **safer** (fail-fast on missing locators)
- LoginPage's approach is **simpler** but less defensive
- **Recommendation:** Adopt SignupPage's defensive pattern in LoginPage for consistency

---

## 3. TEST INSTRUMENTATION WITH ALLURE

### ❌ SignupTest (NO Allure.step instrumentation)
```java
@Test(
    priority = 1,
    description = "New user should be able to complete signup successfully"
)
@Severity(SeverityLevel.BLOCKER)
@Story("Signup")
@Description("Open login/signup page, register a new user...")
public void testCase01_registerUser() {
    String firstName = RandomDataUtils.firstName();
    String lastName = RandomDataUtils.lastName();
    String fullName = firstName + " " + lastName;
    String email = RandomDataUtils.email();
    String password = RandomDataUtils.password();

    SignupPage signupPage = signupPage()  // Direct call, no step
            .openHomePage();  // Direct call, no step

    Assertions.assertThat(signupPage.isHomePageVisible())  // Direct assertion
            .as("Home page should be visible successfully")
            .isTrue();

    signupPage.clickSignupLoginFromHome();  // Direct call, no step
    Assertions.assertThat(signupPage.isSignupFormVisible())  // Direct assertion
            .as("'New User Signup!' section should be visible")
            .isTrue();
}
```

**Allure Report Impact:**
- Only sees @Description text
- No granular step breakdown
- Hard to identify exact failure point
- Missing step-level attachments/logs

---

### ✅ LoginTest (WITH Allure.step instrumentation)
```java
@Test(
    priority = 1,
    description = "Valid credentials should navigate to the dashboard",
    retryAnalyzer = RetryAnalyzer.class
)
@Severity(SeverityLevel.BLOCKER)
@Story("Valid Login")
@Description("Enter valid admin credentials and verify dashboard...")
public void validCredentialsShouldLoginSuccessfully() {

    LoginPage loginPage = pages().loginPage();  // Get page object first

    // Each step is instrumented
    Allure.step("Open Login page", loginPage::open);
    
    DashboardPage dashboard = Allure.step(
            "Login with valid credentials",
            () -> loginPage.loginAs(
                    context().config().username(),
                    context().config().password()
            )
    );
    
    Allure.step("Verify Dashboard page is loaded", () ->
            Assertions.assertThat(dashboard.isLoaded())
                    .as("Dashboard should be visible after successful login")
                    .isTrue());
}
```

**Allure Report Shows:**
1. "Open Login page" ✓
2. "Login with valid credentials" ✓
3. "Verify Dashboard page is loaded" ✓

**Each step can have:**
- Screenshots
- Logs
- Timing information
- Pass/Fail status

---

## 4. TEST METHOD NAMING CONVENTION

### ❌ SignupTest (Test case numbering)
```java
@Test(priority = 1, description = "...")
public void testCase01_registerUser() { }

@Test(priority = 2, description = "...")
public void testCase02_verifyNewUserSignupSectionDisplayed() { }

@Test(priority = 3, description = "...")
public void testCase03_signupWithEmptyNameAndEmail() { }

// ... continues ...

@Test(priority = 13, description = "...")
public void testCase15_registerUserWithExistingEmail() { }  // ⚠️ Number jump!

@Test(priority = 14, description = "...")
public void testCase16_verifyNewlyRegisteredUserCanLoginWithSameCredentials() { }
```

**Problems:**
- ❌ Numbers suggest false ordering/dependencies
- ❌ Jumps from testCase12 → testCase15 (confusing)
- ❌ Method names don't describe business intent
- ❌ Hard to find tests by functionality
- ❌ Not following BDD conventions

---

### ✅ LoginTest (Descriptive naming)
```java
@Test(
    priority = 1,
    description = "Valid credentials should navigate to the dashboard",
    retryAnalyzer = RetryAnalyzer.class
)
@Severity(SeverityLevel.BLOCKER)
@Story("Valid Login")
@Description("Enter valid admin credentials and verify dashboard...")
public void validCredentialsShouldLoginSuccessfully() { }

@Test(
    priority = 2,
    description = "Invalid credentials should show an error message",
    retryAnalyzer = RetryAnalyzer.class
)
@Severity(SeverityLevel.CRITICAL)
@Story("Invalid Login")
@Description("Enter incorrect credentials and verify error banner...")
public void invalidCredentialsShouldShowError() { }

@Test(
    priority = 3,
    description = "Empty credentials should not allow login",
    retryAnalyzer = RetryAnalyzer.class
)
@Severity(SeverityLevel.NORMAL)
@Story("Empty Credentials")
@Description("Submit empty credentials and verify required field messages.")
public void emptyCredentialsShouldBlockLogin() { }
```

**Benefits:**
- ✅ Method names clearly describe the behavior
- ✅ Follows BDD (Given-When-Then) convention
- ✅ Business intent is immediately clear
- ✅ Easier to search/find tests
- ✅ No false ordering implied

**Mapping for Signup tests:**
| Current | Recommended |
|---------|------------|
| `testCase01_registerUser()` | `newUserShouldCompleteSignupSuccessfully()` |
| `testCase02_verifyNewUserSignupSectionDisplayed()` | `signupFormShouldBeVisibleOnSignupPage()` |
| `testCase03_signupWithEmptyNameAndEmail()` | `signupWithEmptyNameShouldBlockSubmission()` |
| `testCase04_signupWithInvalidEmailFormat()` | `signupWithInvalidEmailShouldShowBrowserValidation()` |
| `testCase15_registerUserWithExistingEmail()` | `duplicateEmailShouldShowErrorMessage()` |
| `testCase16_verifyNewlyRegisteredUserCanLoginWithSameCredentials()` | `newlyRegisteredUserCanLoginWithSameCredentials()` |

---

## 5. JAVADOC DOCUMENTATION

### ❌ SignupPage (Minimal)
```java
/**
 * Account information page for completing Automation Exercise signup.
 */
public final class SignupPage extends BasePage {
    // ... no method javadoc ...
    
    public SignupPage openHomePage() {
        browser.navigate(HOME_URL);
        browser.waitForVisible(s("homeLogo"));
        return this;
    }
    
    public boolean isHomePageVisible() {
        return browser.isVisible(s("homeLogo"));
    }
}
```

---

### ✅ LoginPage (Comprehensive)
```java
/**
 * Login page for OrangeHRM.
 *
 * <h3>Locators</h3>
 * All CSS/XPath selectors are externalised in
 * {@code src/test/resources/locators/login-page.properties}.
 *
 * <h3>Fluent interface</h3>
 * Methods that stay on the same page return {@code this};
 * navigation methods return the target page object.
 */
public final class LoginPage extends BasePage {

    /**
     * Waits until the login page readiness element is visible.
     *
     * <p>Throws a Playwright timeout error when the page does not become ready
     * within the configured timeout.</p>
     *
     * @return this login page
     */
    public LoginPage waitUntilLoaded() {
        browser.waitForVisible(loc.getProperty("loginButton"));
        return this;
    }

    /**
     * Returns whether the login page readiness element is currently visible.
     *
     * <p>This is an immediate state query and does not wait. Call
     * {@link #waitUntilLoaded()} when synchronization is required.</p>
     *
     * @return {@code true} when the login button is visible now; otherwise {@code false}
     */
    public boolean isLoaded() {
        return browser.isVisible(loc.getProperty("loginButton"));
    }
}
```

**Missing in SignupPage:**
- ❌ No explanation of locator externalization
- ❌ No documentation of fluent interface pattern
- ❌ No @param/@return documentation
- ❌ No explanation of method behavior expectations
- ❌ No distinction between state queries vs actions

---

## 6. IMPORT ORGANIZATION

### SignupTest (Mixed order)
```java
import com.bjitgroup.actions.BrowserActions;
import com.bjitgroup.actions.InputActions;
import com.bjitgroup.base.BaseTest;
import com.bjitgroup.context.PageManager;
import com.bjitgroup.dataproviders.SignupDataProvider;
import com.bjitgroup.pages.SignupPage;
import com.bjitgroup.utils.RandomDataUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
```

### LoginTest (Consistent order)
```java
import com.bjitgroup.base.BaseTest;
import com.bjitgroup.listeners.RetryAnalyzer;
import com.bjitgroup.pages.DashboardPage;
import com.bjitgroup.pages.LoginPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
```

**Standard order:**
1. **com.bjitgroup.*** (framework packages)
2. **com.bjitgroup.pages.*** (page objects)
3. **io.qameta.*** (test instrumentation)
4. **org.*** (external libraries)
5. **java.*** (JDK packages)

---

## SUMMARY TABLE

| Style Element | LoginTest | SignupTest | Alignment |
|---|---|---|---|
| Page object retrieval | `pages().loginPage()` | Local helper with `new` | ❌ NO |
| Allure.step() instrumentation | ✅ Full coverage | ❌ None | ❌ NO |
| Test method naming | Descriptive camelCase | Numbered format | ❌ NO |
| Method-level Javadoc | ✅ Comprehensive | ❌ Minimal | ❌ NO |
| Locator validation | Direct (no checks) | Defensive `s()` helper | ⚠️ PARTIAL |
| Import organization | ✅ Consistent | ⚠️ Mixed | ⚠️ PARTIAL |


