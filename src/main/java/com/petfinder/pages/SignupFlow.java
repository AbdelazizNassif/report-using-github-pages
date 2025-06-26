package com.petfinder.pages;

import filesReaders.PropertyFileReader;
import io.github.cdimascio.dotenv.Dotenv;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.EmailService;

import java.time.Duration;
import java.util.ArrayList;

public class SignupFlow {

    WebDriver driver = null;
    BasePage page = null;
    protected Dotenv dotenv = Dotenv.load();
    private WebDriverWait wait;
    final PropertyFileReader executionProps = new PropertyFileReader("execution.properties");

    public SignupFlow(WebDriver driver) {
        this.driver = driver;
        page = new BasePage(driver);
        wait = new WebDriverWait(driver,
                Duration.ofSeconds(executionProps.getIntegerProperty("WAIT_TIME")));
    }

    @Step("create new account")
    public SignupFlow createNewAccount_enterEmail(String accuntEmail) {
        By emailInput = By.id("emailAddress") ;
        By continueBtn = By.cssSelector("[type='submit']") ;
        page.slowTypeOnInputField(driver, emailInput, accuntEmail);
        page.clickElement(driver, continueBtn);
        return new SignupFlow(driver);
    }

    @Step("fill registration form")
    public SignupFlow fillRegistrationForm(String firstName, String lastName, String postalCode,
                                     int numberOfDogs, int numberOfCate) {
        By fnameInput = By.id("firstName");
        By lnameInput = By.id("lastName") ;
        By postalCodeInput = By.id("postalCode");
        By dogsCount = By.cssSelector("[name='dogCount']");
        By catsCount = By.cssSelector("[name='catCount']");
        By submitFormBtn = By.cssSelector("[type='submit']") ;
        page.typeOnInputField(driver, fnameInput, firstName);
        page.typeOnInputField(driver, lnameInput, lastName);
        page.typeOnInputField(driver, postalCodeInput, postalCode);
        page.selectFromDropdown_byValue(driver, dogsCount, String.valueOf(numberOfDogs));
        page.selectFromDropdown_byValue(driver, catsCount, String.valueOf(numberOfCate));
        page.scrollToElement(driver, submitFormBtn);
        page.keyboardAction(driver, submitFormBtn, Keys.ENTER);
        return new SignupFlow(driver);

    }

    @Step("confirm password")
    public SignupFlow fillPassword(String password, String confirmPassword) {
        By passwordInput = By.id("password") ;
        By confirmPasswordInput = By.id("confirmPassword") ;
        By termsCheckbox = By.id("terms") ;
        By submitFormBtn = By.cssSelector("[type='submit']") ;
        page.typeOnInputField(driver, passwordInput, password);
        page.typeOnInputField(driver, confirmPasswordInput, confirmPassword);
        page.clickElement(driver, termsCheckbox);
        page.scrollToElement(driver, submitFormBtn);
        page.keyboardAction(driver, submitFormBtn, Keys.ENTER);
        return new SignupFlow(driver);

    }

    @Step("wait till verification mail is sent")
    public void waitTillVerificationEmailIsSent(String accountEmail) {
        new EmailService().waitUntilInboxToHaveEmails(accountEmail, 1);
    }

    @Step("get verification email subject")
    public ArrayList getEmailSubject(String accountEmail) {
        return (ArrayList) new EmailService().getAllEmailsSubjects(accountEmail);
    }


}
