package com.petfinder.pages;

import filesReaders.PropertyFileReader;
import io.github.cdimascio.dotenv.Dotenv;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginModal {

    WebDriver driver = null;
    BasePage page = null;
    protected Dotenv dotenv = Dotenv.load();
    private WebDriverWait wait;
    final PropertyFileReader executionProps = new PropertyFileReader("execution.properties");

    public LoginModal(WebDriver driver) {
        this.driver = driver;
        page = new BasePage(driver);
        wait = new WebDriverWait(driver,
                Duration.ofSeconds(executionProps.getIntegerProperty("WAIT_TIME")));
    }

    @Step("Fill Login form")
    public void fillLoginFormThenSubmit(String email, String password) {
        By emailInput = By.xpath("//div[@class='span-or-verical background-customizable']//following-sibling::div/div/form/div/input[@id='signInFormUsername']") ;
        By passwordInput = By.xpath("//div[@class='span-or-verical background-customizable']//following-sibling::div/div/form/div/input[@id='signInFormPassword']") ;
        By signinBtn = By.xpath("//div[@class='span-or-verical background-customizable']//following-sibling::div/div/form/input[@name='signInSubmitButton']") ;
        page.typeOnInputField(driver, emailInput, email);
        page.typeOnInputField(driver, passwordInput, password);
        page.clickElement(driver, signinBtn);
    }

    @Step("Get invalid login error message")
    public String getInvalidLoginErrorMessage () {
        By invalidLoginErrMsg = By.xpath("//div[@class='span-or-verical background-customizable']//following-sibling::div/div/form/p[@id='loginErrorMessage']") ;
        return page.getTextOfElement(driver, invalidLoginErrMsg);
    }

}
