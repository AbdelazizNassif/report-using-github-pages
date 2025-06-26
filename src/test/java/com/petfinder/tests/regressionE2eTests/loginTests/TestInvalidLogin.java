package com.petfinder.tests.regressionE2eTests.loginTests;

import com.petfinder.tests.regressionE2eTests.BaseTest;
import com.petfinder.pages.HomePage;
import com.petfinder.pages.LoginModal;
import com.petfinder.pages.NavBar;
import filesReaders.JsonFileReader;
import io.github.cdimascio.dotenv.Dotenv;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestInvalidLogin extends BaseTest {


    @BeforeClass
    @Description("navigate to login form")
    public void navigateToLoginForm(){
        new HomePage(driver)
                .navigateToHomePage()
                .closeApplicationAd();
        new NavBar(driver)
                .clickSignin()
                .clickLogin();
    }

    @DataProvider(name = "invalid-login-credentials")
    public Object[][] dpMethod(){
        // read Test data
        Dotenv dotenv = Dotenv.load();
        String registeredEmail = dotenv.get("REGISTERED_EMAIL");
        String validPassword = dotenv.get("VALID_PASSWORD");
        JsonFileReader testData = new JsonFileReader("dataByKey/loginTestData.json");
        String unregisterEmail = testData.getJsonStringValueByKey("unregisteredEmail");
        String invalidPassword = testData.getJsonStringValueByKey("invalidPassword");
        return new Object[][] {{registeredEmail, invalidPassword, "Incorrect username or password."},
                                {unregisterEmail, validPassword, "User does not exist."}};
    }

    @Test (dataProvider = "invalid-login-credentials")
    @Description("Invalid login operations then check error messages")
    public void testInvalidLoginThenCheckErrorMessage (String email, String password, String expectedErrorMsg) {
        new LoginModal(driver)
                .fillLoginFormThenSubmit(email, password);
        String actualErrorMessage = new LoginModal(driver).getInvalidLoginErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMsg,
                "error message is not as expected found " + actualErrorMessage);
    }
}
