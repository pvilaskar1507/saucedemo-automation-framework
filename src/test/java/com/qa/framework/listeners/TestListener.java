package com.qa.framework.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.qa.framework.driver.DriverFactory;
import com.qa.framework.utils.ExtentManager;
import com.qa.framework.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/** Writes pass/fail results to the Extent HTML report and captures a screenshot on UI failures. */
public class TestListener implements ITestListener {

    private static final ExtentReports EXTENT = ExtentManager.getInstance();
    private static final ThreadLocal<ExtentTest> CURRENT_TEST = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        String name = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        Object[] params = result.getParameters();
        if (params != null && params.length > 0) {
            name = name + " " + java.util.Arrays.toString(params);
        }
        CURRENT_TEST.set(EXTENT.createTest(name, description));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        CURRENT_TEST.get().pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = CURRENT_TEST.get();
        test.fail(result.getThrowable());

        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            test.addScreenCaptureFromBase64String(ScreenshotUtils.asBase64(driver), "Failure screenshot");
            ScreenshotUtils.saveToFile(driver, result.getMethod().getMethodName());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        CURRENT_TEST.get().skip("Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        EXTENT.flush();
    }
}
