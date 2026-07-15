package com.bjitgroup.tests;

import com.deque.html.axecore.playwright.AxeBuilder;
import com.deque.html.axecore.results.AxeResults;
import com.deque.html.axecore.results.Rule;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Listeners are declared once on {@link com.bjitgroup.base.BaseTest}.</p>
 */
@Feature("Accessibility")
public class AccessibilityTest extends ContextAwareTest {

    private static final List<String> WCAG_TAGS = Arrays.asList(
            "wcag2a", "wcag2aa", "wcag21a", "wcag21aa"
    );

    @Test(description = "Login page should not have WCAG A/AA accessibility violations")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Login Page Accessibility")
    public void loginPageShouldBeAccessible() throws Exception {
        pages().loginPage().open();

        AxeResults results = runWcagAudit();

        Assertions.assertThat(results.getViolations())
                .as(formatViolations(results.getViolations()))
                .isEmpty();
    }

    @Test(description = "Dashboard page should not have WCAG A/AA accessibility violations")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Dashboard Accessibility")
    public void dashboardPageShouldBeAccessible() throws Exception {
        pages().loginPage()
                .open()
                .loginAs(context().config().username(), context().config().password());

        AxeResults results = runWcagAudit();

        Assertions.assertThat(results.getViolations())
                .as(formatViolations(results.getViolations()))
                .isEmpty();
    }

    @Test(description = "Admin User Management page should not have WCAG A/AA accessibility violations")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Admin Page Accessibility")
    public void adminUserManagementPageShouldBeAccessible() throws Exception {
        pages().loginPage()
                .open()
                .loginAs(context().config().username(), context().config().password())
                .goToAdmin()
                .openUserManagement();

        AxeResults results = runWcagAudit();

        Assertions.assertThat(results.getViolations())
                .as(formatViolations(results.getViolations()))
                .isEmpty();
    }

    private AxeResults runWcagAudit() throws Exception {
        return new AxeBuilder(page()).withTags(WCAG_TAGS).analyze();
    }

    private String formatViolations(List<Rule> violations) {
        if (violations == null || violations.isEmpty()) {
            return "No accessibility violations found";
        }
        return violations.stream()
                .map(v -> String.format(
                        "Rule: %s | Impact: %s | Help: %s | Nodes: %d",
                        v.getId(), v.getImpact(), v.getHelp(), v.getNodes().size()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}