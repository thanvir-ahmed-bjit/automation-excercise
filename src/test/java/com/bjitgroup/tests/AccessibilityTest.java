package com.bjitgroup.tests;

import com.bjitgroup.context.UiContextAware;
import com.bjitgroup.context.UiTestContext;
import com.bjitgroup.listeners.TestListener;
import com.deque.html.axecore.playwright.AxeBuilder;
import com.deque.html.axecore.results.AxeResults;
import com.deque.html.axecore.results.Rule;
import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Feature("Accessibility")
@Listeners({AllureTestNg.class, TestListener.class})
public class AccessibilityTest implements UiContextAware {

    private UiTestContext context;

    @Override
    public void setUiTestContext(UiTestContext context) {
        this.context = context;
    }

    @Override
    public UiTestContext getUiTestContext() {
        return context;
    }

    @Test(description = "Login page should not have WCAG A/AA accessibility violations")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Login Page Accessibility")
    public void loginPageShouldBeAccessible() throws Exception {
        context.pages()
                .loginPage()
                .open();

        AxeResults results = new AxeBuilder(context.page())
                .withTags(Arrays.asList("wcag2a", "wcag2aa", "wcag21a", "wcag21aa"))
                .analyze();

        Assertions.assertThat(results.getViolations())
                .as(formatViolations(results.getViolations()))
                .isEmpty();
    }

    @Test(description = "Dashboard page should not have WCAG A/AA accessibility violations")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Dashboard Accessibility")
    public void dashboardPageShouldBeAccessible() throws Exception {
        context.pages()
                .loginPage()
                .open()
                .loginAs(context.config().username(), context.config().password());

        AxeResults results = new AxeBuilder(context.page())
                .withTags(Arrays.asList("wcag2a", "wcag2aa", "wcag21a", "wcag21aa"))
                .analyze();

        Assertions.assertThat(results.getViolations())
                .as(formatViolations(results.getViolations()))
                .isEmpty();
    }

    @Test(description = "Admin User Management page should not have WCAG A/AA accessibility violations")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Admin Page Accessibility")
    public void adminUserManagementPageShouldBeAccessible() throws Exception {
        context.pages()
                .loginPage()
                .open()
                .loginAs(context.config().username(), context.config().password())
                .goToAdmin()
                .openUserManagement();

        AxeResults results = new AxeBuilder(context.page())
                .withTags(Arrays.asList("wcag2a", "wcag2aa", "wcag21a", "wcag21aa"))
                .analyze();

        Assertions.assertThat(results.getViolations())
                .as(formatViolations(results.getViolations()))
                .isEmpty();
    }

    private String formatViolations(List<Rule> violations) {
        if (violations == null || violations.isEmpty()) {
            return "No accessibility violations found";
        }

        return violations.stream()
                .map(v -> String.format(
                        "Rule: %s | Impact: %s | Help: %s | Nodes: %d",
                        v.getId(),
                        v.getImpact(),
                        v.getHelp(),
                        v.getNodes().size()
                ))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}