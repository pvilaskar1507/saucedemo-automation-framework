package com.qa.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class ProductsPage extends BasePage {

    private static final By PAGE_TITLE = By.cssSelector(".title");
    private static final By PRODUCT_NAMES = By.cssSelector(".inventory_item_name");
    private static final By PRODUCT_PRICES = By.cssSelector(".inventory_item_price");
    private static final By SORT_DROPDOWN = By.cssSelector("[data-test='product-sort-container']");
    private static final By CART_BADGE = By.cssSelector(".shopping_cart_badge");
    private static final By CART_LINK = By.cssSelector(".shopping_cart_link");
    private static final By MENU_BUTTON = By.id("react-burger-menu-btn");
    private static final By LOGOUT_LINK = By.id("logout_sidebar_link");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return getText(PAGE_TITLE);
    }

    public List<String> getProductNames() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_NAMES));
        return driver.findElements(PRODUCT_NAMES).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_PRICES));
        return driver.findElements(PRODUCT_PRICES).stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "").trim()))
                .collect(Collectors.toList());
    }

    public void addToCart(String productName) {
        click(productButton(productName));
    }

    public int getCartCount() {
        List<WebElement> badge = driver.findElements(CART_BADGE);
        return badge.isEmpty() ? 0 : Integer.parseInt(badge.get(0).getText().trim());
    }

    public void sortBy(String visibleText) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(SORT_DROPDOWN));
        new Select(dropdown).selectByVisibleText(visibleText);
    }

    public CartPage goToCart() {
        click(CART_LINK);
        return new CartPage(driver);
    }

    public void logout() {
        click(MENU_BUTTON);
        click(LOGOUT_LINK);
    }

    private By productButton(String productName) {
        return By.xpath("//div[contains(@class,'inventory_item_name') and normalize-space()='" + productName + "']"
                + "/ancestor::div[contains(concat(' ',normalize-space(@class),' '),' inventory_item ')]//button");
    }
}
