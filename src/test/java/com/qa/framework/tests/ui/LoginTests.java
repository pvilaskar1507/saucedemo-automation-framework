package com.qa.framework.tests.ui;

import com.qa.framework.base.BaseTest;
import com.qa.framework.pages.LoginPage;
import com.qa.framework.pages.ProductsPage;
import com.qa.framework.utils.ExcelUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    /** Test data comes from the "LoginData" sheet in src/test/resources/testdata/TestData.xlsx */
    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return ExcelUtils.getSheetData("/testdata/TestData.xlsx", "LoginData");
    }

    @Test(dataProvider = "loginData", description = "Data-driven login test using Excel data")
    public void verifyLogin(String username, String password, String expectedResult, String expectedText) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        if (expectedResult.equalsIgnoreCase("success")) {
            ProductsPage productsPage = new ProductsPage(driver);
            Assert.assertEquals(productsPage.getPageTitle(), expectedText, "Products page title mismatch");
        } else {
            Assert.assertTrue(loginPage.getErrorMessage().contains(expectedText),
                    "Unexpected error message: " + loginPage.getErrorMessage());
        }
    }
}
