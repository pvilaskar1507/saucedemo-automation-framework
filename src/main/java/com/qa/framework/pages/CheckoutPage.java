package com.qa.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private static final By FIRST_NAME = By.id("first-name");
    private static final By LAST_NAME = By.id("last-name");
    private static final By POSTAL_CODE = By.id("postal-code");
    private static final By CONTINUE_BUTTON = By.id("continue");
    private static final By FINISH_BUTTON = By.id("finish");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");
    private static final By ITEM_TOTAL = By.cssSelector(".summary_subtotal_label");
    private static final By CONFIRMATION_HEADER = By.cssSelector(".complete-header");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void enterCustomerInfo(String firstName, String lastName, String postalCode) {
        type(FIRST_NAME, firstName);
        type(LAST_NAME, lastName);
        type(POSTAL_CODE, postalCode);
    }

    public void clickContinue() {
        click(CONTINUE_BUTTON);
    }

    public void clickFinish() {
        click(FINISH_BUTTON);
    }

    public String getErrorMessage() {
        return getText(ERROR_MESSAGE);
    }

    /** Reads "Item total: $39.98" and returns 39.98 */
    public double getItemTotal() {
        String text = getText(ITEM_TOTAL);
        return Double.parseDouble(text.substring(text.indexOf('$') + 1).trim());
    }

    public String getConfirmationMessage() {
        return getText(CONFIRMATION_HEADER);
    }
}
