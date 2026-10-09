package com.qa.framework.tests.ui;

import com.qa.framework.base.BaseTest;
import com.qa.framework.config.ConfigReader;
import com.qa.framework.pages.LoginPage;
import com.qa.framework.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductTests extends BaseTest {

    private LoginPage loginPage;
    private ProductsPage productsPage;

    // Runs after BaseTest.setUp(), so the browser is already open here
    @BeforeMethod(alwaysRun = true)
    public void loginBeforeEachTest() {
        loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("valid.username"), ConfigReader.get("valid.password"));
        productsPage = new ProductsPage(driver);
    }

    @Test(description = "Inventory page shows 6 products")
    public void verifyProductCount() {
        Assert.assertEquals(productsPage.getProductNames().size(), 6);
    }

    @Test(description = "Adding one product updates the cart badge to 1")
    public void verifyAddSingleProductToCart() {
        productsPage.addToCart("Sauce Labs Backpack");
        Assert.assertEquals(productsPage.getCartCount(), 1);
    }

    @Test(description = "Adding three products updates the cart badge to 3")
    public void verifyAddMultipleProductsToCart() {
        productsPage.addToCart("Sauce Labs Backpack");
        productsPage.addToCart("Sauce Labs Bike Light");
        productsPage.addToCart("Sauce Labs Bolt T-Shirt");
        Assert.assertEquals(productsPage.getCartCount(), 3);
    }

    @Test(description = "Sort by price low to high")
    public void verifySortPriceLowToHigh() {
        productsPage.sortBy("Price (low to high)");
        List<Double> actual = productsPage.getProductPrices();
        List<Double> expected = new ArrayList<>(actual);
        Collections.sort(expected);
        Assert.assertEquals(actual, expected, "Prices are not sorted low to high");
    }

    @Test(description = "Sort by name Z to A")
    public void verifySortNameZToA() {
        productsPage.sortBy("Name (Z to A)");
        List<String> actual = productsPage.getProductNames();
        List<String> expected = new ArrayList<>(actual);
        expected.sort(Collections.reverseOrder());
        Assert.assertEquals(actual, expected, "Names are not sorted Z to A");
    }

    @Test(description = "Logout returns the user to the login page")
    public void verifyLogout() {
        productsPage.logout();
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login page not displayed after logout");
    }
}
