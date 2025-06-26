package com.petfinder.pages;

import filesReaders.PropertyFileReader;
import io.github.cdimascio.dotenv.Dotenv;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NavBar {

    WebDriver driver = null;
    BasePage page = null;
    protected Dotenv dotenv = Dotenv.load();
    private WebDriverWait wait;
    final PropertyFileReader executionProps = new PropertyFileReader("execution.properties");

    public NavBar(WebDriver driver) {
        this.driver = driver;
        page = new BasePage(driver);
        wait = new WebDriverWait(driver,
                Duration.ofSeconds(executionProps.getIntegerProperty("WAIT_TIME")));
    }

    @Step("From nav bar, Close sign in ad")
    public LoginActionsSideMenu clickSignin() {
        By signinBtn = By.xpath("//span[text()='Sign In']") ;
        page.clickElement(driver, signinBtn);
        return new LoginActionsSideMenu(driver);
    }

    public ProfileSideMenu clickProfileIcon () {
        By profileBtn = By.xpath("//*[contains(@class,'purina-profile')]//button");
        page.clickElement(driver, profileBtn);
        return new ProfileSideMenu(driver);
    }

}
