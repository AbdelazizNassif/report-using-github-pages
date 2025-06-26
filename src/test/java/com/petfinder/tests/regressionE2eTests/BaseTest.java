package com.petfinder.tests.regressionE2eTests;

import driverSettigns.DriverFactory;
import io.github.cdimascio.dotenv.Dotenv;
import io.qameta.allure.Description;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import utils.ScreenshotUtil;

public class BaseTest {

    public WebDriver driver = null;
    public Dotenv dotenv = null;

    @BeforeClass
    @Description("Initialize chrome browser")
    public void initializeDriver() {
        driver = new DriverFactory().getDriver();
        driver.manage().window().maximize();
        dotenv = Dotenv.load();
    }

    @AfterClass
    @Description("Initialize chrome browser")
    public void quitDriver() {
        driver.quit();
    }

    @AfterMethod
    @Description("Take screenshot")
    public void takeScreenShot(ITestResult result) {
        new ScreenshotUtil().
                attachScreenshotToAllureReport(driver, result.getName());
    }


}


