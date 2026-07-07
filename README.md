# Playwright UI Automation Framework

Production-ready UI Automation Framework built with **Java 21**, **Playwright**, **TestNG**, and **Allure** - following SOLID principles and the Page Object Model pattern.

---

## Framework Architecture

```
automation-training/
|- pom.xml                         <- Maven build + dependencies
|- testng.xml                      <- TestNG suite (parallel execution)
|- README.md
|- .github/
|  \- workflows/
|     \- maven.yml               <- GitHub Actions CI pipeline
\- src/
   |- main/java/com/bjitgroup/
   |  |- config/
   |  |  |- ConfigManager.java  <- Singleton config (system props -> config.properties -> env)
   |  |  \- PropertyLoader.java <- Classpath .properties reader
   |  |- constants/
   |  |  |- BrowserType.java    <- Enum: CHROME | CHROMIUM | FIREFOX | EDGE
   |  |  \- FrameworkConstants.java <- Paths, timeouts
   |  |- driver/
   |  |  \- DriverFactory.java  <- Thread-local Playwright stack (PW -> Browser -> Context -> Page)
   |  |- factory/
   |  |  \- BrowserFactory.java <- Launches the correct browser from config
   |  |- listeners/
   |  |  |- RetryAnalyzer.java  <- Retries failed tests (configurable count)
   |  |  \- TestListener.java   <- Screenshot on failure + Allure env info
   |  |- pages/
   |  |  |- BasePage.java       <- Explicit-wait wrappers (no Thread.sleep)
   |  |  |- LoginPage.java      <- Login POM
   |  |  |- DashboardPage.java  <- Dashboard POM
   |  |  \- AdminPage.java      <- Admin User Management POM
   |  |- utils/
   |  |  |- CustomLogger.java   <- SLF4J logger factory
   |  |  |- WaitUtils.java      <- Explicit waits (visible, hidden, URL, load-state)
   |  |  |- ScreenshotUtils.java <- Full-page PNG capture
   |  |  |- PlaywrightUtils.java <- Network interception, cookies, multi-tab, downloads
   |  |  |- JavaScriptUtils.java <- JS eval, scroll, DOM manipulation
   |  |  |- KeyboardUtils.java  <- press, type, tab, enter, escape
   |  |  |- MouseUtils.java     <- click, dblclick, hover, drag-and-drop
   |  |  |- FileUtils.java      <- Upload and download helpers
   |  |  |- DateUtils.java      <- Date/time formatting
   |  |  |- RandomDataUtils.java <- Faker-backed random data
   |  |  |- TestDataManager.java <- Unified data factory (random/JSON/CSV/Excel)
   |  |  |- JsonUtils.java      <- Jackson JSON reader
   |  |  |- CsvUtils.java       <- Apache Commons CSV reader
   |  |  |- ExcelUtils.java     <- Apache POI Excel reader
   |  |  \- PropertyReader.java <- .properties file reader
   |  |- reports/
   |  |  \- AllureManager.java  <- Allure attachment helpers
   |  |- models/
   |  |  \- UserData.java       <- Test data model (Java record)
   |  \- exceptions/
   |     \- FrameworkException.java <- Unchecked framework exception
   \- test/
      |- java/com/bjitgroup/
      |  |- base/
      |  |  \- BaseTest.java   <- @BeforeSuite/@BeforeMethod/@AfterMethod/@AfterSuite
      |  |- hooks/
      |  |  \- TestHooks.java  <- Optional class-level @BeforeClass/@AfterClass
      |  |- dataproviders/
      |  |  \- UserDataProvider.java <- TestNG DataProviders
      |  \- tests/
      |     |- LoginTest.java          <- 3 login scenarios
      |     |- LogoutTest.java         <- Logout verification
      |     \- UserManagementTest.java <- Create / Search / Edit / Delete user
      \- resources/
         |- config/
         |  \- config.properties       <- browser, headless, environment, timeout, slowMo
         |- environments/
         |  |- dev.properties
         |  |- qa.properties           <- Default: OrangeHRM demo
         |  |- uat.properties
         |  \- prod.properties
         |- locators/
         |  |- login-page.properties
         |  |- dashboard-page.properties
         |  \- admin-page.properties
         |- testdata/
         |  |- user.json
         |  \- users.csv
         \- logback.xml                 <- Console + rolling-file logging
```

---

## Prerequisites

| Tool          | Version   |
|---------------|-----------|
| Java (JDK)    | 21        |
| Maven         | 3.9+      |
| Git           | any       |

---

## Running Tests

### Full suite (uses `testng.xml`)

```bash
mvn clean test
```

### Run a specific TestNG XML

```bash
mvn clean test -Dsurefire.suiteXmlFiles=testng.xml
```

### Run a single test class

```bash
mvn clean test -Dtest=com.bjitgroup.tests.LoginTest
```

### Run a single test method

```bash
mvn clean test "-Dtest=com.bjitgroup.tests.LoginTest#validCredentialsShouldLoginSuccessfully"
```

---

## Browser Selection

Override `browser` via a system property:

```bash
mvn clean test -Dbrowser=chrome
mvn clean test -Dbrowser=firefox
mvn clean test -Dbrowser=edge
mvn clean test -Dbrowser=chromium
```

---

## Headless Execution

```bash
mvn clean test -Dheadless=true
```

---

## Environment Switching

```bash
mvn clean test -Denvironment=dev
mvn clean test -Denvironment=qa
mvn clean test -Denvironment=uat
mvn clean test -Denvironment=prod
```

Each environment has a matching file under `src/test/resources/environments/`.

---

## Parallel Execution

Controlled in `testng.xml`:

```xml
<suite parallel="classes" thread-count="3">
```

Options: `methods` | `classes` | `tests` | `instances`

Adjust `thread-count` to match your CI runner capacity.

---

## Allure Report Generation

```bash
# Run tests first
mvn clean test

# Generate static HTML report
mvn allure:report

# Generate + open in browser instantly
mvn allure:serve
```

Report output: `target/site/allure-maven-plugin/index.html`

---

## Allure Attachments Generated Automatically

| Artefact            | Location                       |
|---------------------|--------------------------------|
| Failure screenshots | `target/artifacts/screenshots` |
| Playwright traces   | `target/artifacts/traces`      |
| Video recordings    | `target/artifacts/videos`      |
| Execution logs      | `target/artifacts/logs`        |
| Allure results      | `target/allure-results`        |

---

## CI/CD (GitHub Actions)

Workflow file: `.github/workflows/maven.yml`

Triggers: push/PR to `main` / `master`, or manual dispatch with browser + environment selection.

Pipeline steps:
1. Checkout code
2. Set up JDK 21 (Temurin)
3. Cache Maven dependencies
4. Install Playwright browsers (`--with-deps`)
5. Execute tests
6. Upload Allure results artifact
7. Upload execution artifacts (screenshots, traces, videos, logs)
8. Generate + upload Allure HTML report

---

## Configuration Reference

`src/test/resources/config/config.properties`

| Key           | Default   | Description                            |
|---------------|-----------|----------------------------------------|
| `browser`     | `chrome`  | chrome / chromium / firefox / edge     |
| `headless`    | `false`   | true / false                           |
| `environment` | `qa`      | dev / qa / uat / prod                  |
| `timeout`     | `20000`   | Element wait timeout in ms             |
| `slowMo`      | `0`       | Artificial slow-down in ms             |

All values can be overridden at runtime with `-Dkey=value`.


