package com.petfinder.tests.regressionE2eTests.registrationTests;

import com.petfinder.pages.HomePage;
import com.petfinder.pages.LoginActionsSideMenu;
import com.petfinder.pages.NavBar;
import com.petfinder.pages.SignupFlow;
import com.petfinder.tests.regressionE2eTests.BaseTest;
import filesReaders.JsonFileReader;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DataGeneration;
import utils.EmailService;

public class TestRegistration extends BaseTest {
    String username;
    String accountEmail;
    String firstname;
    String lastname;
    String postalCode;
    int dogsCount;
    int catsCount;
    String password;
    String confirmPassword;

    @BeforeClass
    @Description("Create unique email for account registration and read test data")
    public void createUniqueEmail() {
        username = new DataGeneration().generateRandomString(8);
        accountEmail = new EmailService().createUniqueEmail(username);
        JsonFileReader testData = new JsonFileReader("dataByKey/registrationTestData.json");
        firstname = testData.getJsonStringValueByKey("firstname");
        lastname = testData.getJsonStringValueByKey("lastname");
        postalCode = testData.getJsonStringValueByKey("postalCode");
        dogsCount = testData.getJsonIntegerValueByKey("postalCode");
        catsCount = testData.getJsonIntegerValueByKey("catsCount");
        postalCode = testData.getJsonStringValueByKey("password");
        postalCode = testData.getJsonStringValueByKey("confirmPassword");
    }

    @Test
    @Description("test registration with valid data")
    public void testRegistrationWithValidInfo() {
        new HomePage(driver)
                .navigateToHomePage()
                .closeApplicationAd();
        new NavBar(driver)
                .clickSignin();
        new LoginActionsSideMenu(driver)
                .clickCreateAccount();
        new SignupFlow(driver)
                .createNewAccount_enterEmail(accountEmail)
                .fillRegistrationForm(firstname, lastname, postalCode,dogsCount,catsCount)
                .fillPassword(password, confirmPassword)
                .waitTillVerificationEmailIsSent(accountEmail);
        Assert.assertTrue(new SignupFlow(driver).getEmailSubject(accountEmail)
                .contains("Welcome! Please verify your email for Purina"))  ;
    }

}
