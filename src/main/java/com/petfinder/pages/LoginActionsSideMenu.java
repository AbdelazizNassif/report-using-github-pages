package com.petfinder.pages;

import filesReaders.PropertyFileReader;
import io.github.cdimascio.dotenv.Dotenv;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginActionsSideMenu {

    WebDriver driver = null;
    BasePage page = null;
    protected Dotenv dotenv = Dotenv.load();
    private WebDriverWait wait;
    final PropertyFileReader executionProps = new PropertyFileReader("execution.properties");

    public LoginActionsSideMenu(WebDriver driver) {
        this.driver = driver;
        page = new BasePage(driver);
        wait = new WebDriverWait(driver,
                Duration.ofSeconds(executionProps.getIntegerProperty("WAIT_TIME")));
    }

    @Step("From side mene, click login")
    public LoginModal clickLogin() {
        By loginBtn = By.xpath("//*[text()='Log in']");
        page.clickElement(driver, loginBtn);
        return new LoginModal(driver);
    }

    @Step("From side menu, click create account")
    public void clickCreateAccount() {
        By createAccountBtn = By.xpath("//*[text()='Create Account']") ;
        page.clickElement(driver, createAccountBtn);
    }


}
