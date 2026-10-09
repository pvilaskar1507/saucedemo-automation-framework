package com.qa.framework.tests.ui;

import com.qa.framework.base.BaseTest;
import com.qa.framework.config.ConfigReader;
import com.qa.framework.pages.CartPage;
import com.qa.framework.pages.CheckoutPage;
import com.qa.framework.pages.LoginPage;
import com.qa.framework.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

public class CheckoutTests extends BaseTest {

    private static final String BACKPACK = "Sauce Labs Backpack";
    private static final String BIKE_LIGHT = "Sauce Labs Bike Light";

    private ProductsPage productsPage;

    @BeforeMethod(alwaysRun = true)
    public void loginBeforeEachTest() {
        new LoginPage(driver).login(ConfigReader.get("valid.username"), ConfigReader.get("valid.password"));
        productsPage = new ProductsPage(driver);
    }

    @Test(description = "End-to-end: add two products, checkout and place the order")
    public void verifyEndToEndPurchase() {
        productsPage.addToCart(BACKPACK);
        productsPage.addToCart(BIKE_LIGHT);

        CartPage cartPage = productsPage.goToCart();
        Assert.assertEquals(cartPage.getItemNames(), Arrays.asList(BACKPACK, BIKE_LIGHT));

        CheckoutPage checkoutPage = cartPage.clickCheckout();
        checkoutPage.enterCustomerInfo("Test", "User", "413709");
        checkoutPage.clickContinue();

        Assert.assertEquals(checkoutPage.getItemTotal(), 39.98, 0.001, "Item total is wrong");

        checkoutPage.clickFinish();
        Assert.assertEquals(checkoutPage.getConfirmationMessage(), "Thank you for your order!");
    }

    @Test(description = "Removing the only item makes the cart empty")
    public void verifyRemoveItemFromCart() {
        productsPage.addToCart(BACKPACK);
        CartPage cartPage = productsPage.goToCart();
        cartPage.removeItem(BACKPACK);
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty");
    }

    @Test(description = "Checkout without first name shows an error")
    public void verifyCheckoutWithoutFirstName() {
        productsPage.addToCart(BACKPACK);
        CheckoutPage checkoutPage = productsPage.goToCart().clickCheckout();
        checkoutPage.enterCustomerInfo("", "User", "413709");
        checkoutPage.clickContinue();
        Assert.assertEquals(checkoutPage.getErrorMessage(), "Error: First Name is required");
    }
}
