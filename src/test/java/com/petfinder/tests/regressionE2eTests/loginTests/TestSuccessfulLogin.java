package com.petfinder.tests.regressionE2eTests.loginTests;

import com.petfinder.pages.HomePage;
import com.petfinder.pages.NavBar;
import com.petfinder.tests.regressionE2eTests.BaseTest;
import io.github.cdimascio.dotenv.Dotenv;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestSuccessfulLogin extends BaseTest {
    String email;
    String password;
    @BeforeClass
    @Description("Read test data")
    public void readTestData () {
        Dotenv dotenv = Dotenv.load();
        email = dotenv.get("REGISTERED_EMAIL");
        password = dotenv.get("VALID_PASSWORD");
    }
    @Test
    @Description("Login with valid credentials and check success message and profile username is displayed")
    public void testSuccessfulLogin () {
        new HomePage(driver)
                .navigateToHomePage()
                .closeApplicationAd();
        new NavBar(driver)
                .clickSignin()
                .clickLogin()
                .fillLoginFormThenSubmit(email, password);
        String msg = new HomePage(driver).getSigninSuccessMessage();
        Assert.assertEquals(msg, "Please wait while we finish signing you in...",
                "Sign in success message is not as expected found: " + msg);
        var profileSideMenu = new NavBar(driver).clickProfileIcon();
        String name = profileSideMenu.getProfileOwnerName();
        Assert.assertEquals(name, "Abdelaziz Nassif",
                "user name is not as expected found: " + name);
    }
}
