package utils;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ScreenshotUtil {

    public synchronized void attachScreenshotToAllureReport(WebDriver driver, String screenshotName) {
        String destination = System.getProperty("user.dir") + "/screenshots/" + screenshotName + ".png";
        //Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot = ((TakesScreenshot) driver);
        //Call getScreenshotAs method to create image file
        File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
        //Move image file to new destination
        File destFile = new File(destination);
        //Copy file at destination
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Allure.addAttachment("page view of " + screenshotName + " method",
                    FileUtils.openInputStream(srcFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
