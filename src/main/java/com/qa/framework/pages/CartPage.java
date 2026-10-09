package com.qa.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {

    private static final By CART_ITEM_NAMES = By.cssSelector(".cart_item .inventory_item_name");
    private static final By CART_ITEMS = By.cssSelector(".cart_item");
    private static final By CHECKOUT_BUTTON = By.id("checkout");
    private static final By CART_LIST = By.cssSelector(".cart_list");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getItemNames() {
        wait.until(ExpectedConditions.presenceOfElementLocated(CART_LIST));
        return driver.findElements(CART_ITEM_NAMES).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public void removeItem(String productName) {
        By removeButton = By.xpath("//div[contains(@class,'inventory_item_name') and normalize-space()='" + productName + "']"
                + "/ancestor::div[contains(concat(' ',normalize-space(@class),' '),' cart_item ')]//button");
        click(removeButton);
    }

    public boolean isCartEmpty() {
        wait.until(ExpectedConditions.presenceOfElementLocated(CART_LIST));
        return driver.findElements(CART_ITEMS).isEmpty();
    }

    public CheckoutPage clickCheckout() {
        click(CHECKOUT_BUTTON);
        return new CheckoutPage(driver);
    }
}
