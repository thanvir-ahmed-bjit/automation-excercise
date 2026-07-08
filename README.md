# Playwright UI Automation Framework

Production-ready UI Automation Framework built with **Java 21**, **Playwright**, **TestNG**, and **Allure**, designed with composition-first enterprise architecture principles.

---

## Framework Architecture

### Core Design Decisions

- Composition over inheritance: tests no longer extend a shared base test class.
- Listener-driven test lifecycle: `TestListener` creates and disposes a per-test `UiTestContext`.
- Context injection contract: tests implement `UiContextAware` to receive execution context.
- Session abstraction: browser lifecycle is managed by `BrowserSessionFactory` and `BrowserSession`.
- Page composition: page objects delegate interactions to a reusable `PageActions` component.
- Static usage minimized: static helpers remain only for lightweight utility concerns.

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
   |  |- runtime/
   |  |  |- ArtifactManager.java <- Artifact paths + directory management
   |  |  |- BrowserSession.java  <- One browser stack per test
   |  |  \- BrowserSessionFactory.java <- Session orchestration
   |  |- factory/
   |  |  \- BrowserFactory.java <- Launches the correct browser from config
   |  |- listeners/
   |  |  |- RetryAnalyzer.java  <- Retries failed tests (configurable count)
   |  |  \- TestListener.java   <- Context orchestration + failure attachments
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
      |  |- context/
      |  |  |- UiContextAware.java  <- Context injection contract for test classes
      |  |  |- UiTestContext.java   <- Per-test runtime context
      |  |  \- PageObjectFactory.java <- Typed page-object factory
      |  |- hooks/
      |  |  \- TestHooks.java  <- Optional class-level @BeforeClass/@AfterClass
      |  |- pages/
      |  |  |- PageActions.java     <- Shared page interaction component
      |  |  |- LoginPage.java       <- Login POM
      |  |  |- DashboardPage.java   <- Dashboard POM
      |  |  \- AdminPage.java       <- Admin User Management POM
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


