package com.petfinder.pages;

import filesReaders.PropertyFileReader;
import io.github.cdimascio.dotenv.Dotenv;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    WebDriver driver = null;
    BasePage page = null;
    protected Dotenv dotenv = Dotenv.load();
    private WebDriverWait wait;
    final PropertyFileReader executionProps = new PropertyFileReader("execution.properties");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        page = new BasePage(driver);
        wait = new WebDriverWait(driver,
                Duration.ofSeconds(executionProps.getIntegerProperty("WAIT_TIME")));
    }

    // login

    @Step("navigate to home page")
    public HomePage navigateToHomePage() {
        page.navigateToURL(driver, dotenv.get("APP_URL")) ;
        return new HomePage(driver);
    }

    @Step("Close application ad")
    public HomePage closeApplicationAd() {
        By closeAdIcon = By.id("optlyCloseModal");
        page.clickElement(driver, closeAdIcon);
        return new HomePage(driver);
    }

    @Step("Get sign in success message")
    public String getSigninSuccessMessage() {
        By signinSuccessMsg =  By.xpath("//main[@id='Site_Main']//p[contains(text(), 'Please wait')]") ;
        String successMessage = page.getTextOfElement(driver, signinSuccessMsg);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(signinSuccessMsg));
        return successMessage;
    }


}
