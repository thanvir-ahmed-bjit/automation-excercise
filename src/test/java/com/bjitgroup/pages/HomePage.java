package com.bjitgroup.pages;

import com.bjitgroup.actions.BrowserActions;
import com.bjitgroup.actions.InputActions;
import com.bjitgroup.context.PageManager;

import java.util.Properties;

import static com.bjitgroup.utils.PropertyReader.read;

public final class HomePage extends BasePage {

    private static final String URL = "https://automationexercise.com";
    private final Properties loc = read("locators/home-page.properties");

    public HomePage(BrowserActions browser, InputActions input, PageManager pages) {
        super(browser, input, pages);
    }

    public HomePage open() {
        browser.navigate(URL);
        return waitUntilLoaded();
    }

    public HomePage waitUntilLoaded() {
        browser.waitForVisible(loc.getProperty("homeLogo"));
        return this;
    }

    public boolean isLoaded() {
        return browser.isVisible(loc.getProperty("homeLogo"));
    }

    public LoginPage clickSignupLogin() {
        input.click(loc.getProperty("signupLoginLink"));
        return pages.loginPage().waitUntilLoaded();
    }

    public ContactUsPage clickContactUs() {
        input.click(loc.getProperty("contactUsLink"));
        return pages.contactUsPage().waitUntilLoaded();
    }

    public TestCasesPage clickTestCases() {
        input.click(loc.getProperty("testCasesLink"));
        return pages.testCasesPage().waitUntilLoaded();
    }

    public ProductsPage clickProducts() {
        input.click(loc.getProperty("productsLink"));
        return pages.productsPage().waitUntilLoaded();
    }

    public CartPage clickCart() {
        input.click(loc.getProperty("cartLink"));
        return pages.cartPage();
    }

    public HomePage clickLogout() {
        input.click(loc.getProperty("logoutLink"));
        return this;
    }

    public HomePage clickDeleteAccount() {
        input.click(loc.getProperty("deleteAccountLink"));
        browser.waitForVisible(loc.getProperty("accountDeletedHeading"));
        return this;
    }

    public boolean isLoggedIn() {
        return browser.isVisible(loc.getProperty("loggedInLabel"));
    }

    public String getLoggedInText() {
        return browser.textOf(loc.getProperty("loggedInLabel"));
    }

    public String getAccountDeletedMessage() {
        browser.waitForVisible(loc.getProperty("accountDeletedHeading"));
        return browser.textOf(loc.getProperty("accountDeletedHeading"));
    }

    public HomePage clickContinueAfterAccountAction() {
        input.click(loc.getProperty("continueButton"));
        return this;
    }

    public HomePage scrollToBottom() {
        browser.evaluate("window.scrollTo(0, document.body.scrollHeight)");
        return this;
    }

    public HomePage scrollToTop() {
        browser.evaluate("window.scrollTo(0, 0)");
        return this;
    }

    public boolean isSubscriptionVisible() {
        return browser.isVisible(loc.getProperty("subscriptionHeading"));
    }

    public HomePage enterSubscriptionEmail(String email) {
        input.fill(loc.getProperty("subscriptionEmailInput"), email);
        return this;
    }

    public HomePage clickSubscribe() {
        input.click(loc.getProperty("subscribeButton"));
        return this;
    }

    public String getSubscriptionSuccessMessage() {
        browser.waitForVisible(loc.getProperty("subscriptionSuccess"));
        return browser.textOf(loc.getProperty("subscriptionSuccess"));
    }

    public boolean isRecommendedItemsVisible() {
        return browser.isVisible(loc.getProperty("recommendedItemsSection"));
    }

    public void clickFirstRecommendedAddToCart() {
        input.click(browser.locator(loc.getProperty("recommendedAddToCart")).first());
    }

    public CartPage clickViewCartInModal() {
        browser.waitForVisible(loc.getProperty("cartModal"));
        input.click(loc.getProperty("viewCartInModal"));
        return pages.cartPage();
    }

    public HomePage clickScrollUpArrow() {
        browser.waitForVisible(loc.getProperty("scrollUpArrow"));
        input.click(loc.getProperty("scrollUpArrow"));
        return this;
    }

    public boolean isHeroTextVisible() {
        return browser.locator(loc.getProperty("heroText")).first().isVisible();
    }

    public boolean isCategoriesSidebarVisible() {
        return browser.isVisible(loc.getProperty("categoriesSidebar"));
    }

    public HomePage clickWomenCategory() {
        input.click(loc.getProperty("womenCategoryLink"));
        browser.waitForVisible(loc.getProperty("womenCategoryPanel"));
        return this;
    }

    public HomePage clickWomenTops() {
        input.click(loc.getProperty("womenTopsLink"));
        return this;
    }

    public HomePage clickMenCategory() {
        input.click(loc.getProperty("menCategoryLink"));
        browser.waitForVisible(loc.getProperty("menCategoryPanel"));
        return this;
    }

    public HomePage clickFirstMenSubcategory() {
        input.click(loc.getProperty("menFirstSubcategoryLink"));
        return this;
    }

    public String getCategoryPageTitle() {
        browser.waitForVisible(loc.getProperty("categoryPageTitle"));
        return browser.textOf(loc.getProperty("categoryPageTitle"));
    }

    public HomePage waitForAnimation() {
        browser.waitMs(1000);
        return this;
    }
}
