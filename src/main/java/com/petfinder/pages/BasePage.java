package com.petfinder.pages;


import filesReaders.PropertyFileReader;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitUtility;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BasePage {

    private WebDriverWait wait;
    final private PropertyFileReader executionProps = new PropertyFileReader("execution.properties");

    public BasePage(WebDriver driver) {
        wait = new WebDriverWait(driver,
                Duration.ofSeconds(executionProps.getIntegerProperty("WAIT_TIME")));
    }

    synchronized protected WebElement locateElement(WebDriver driver, By elementLocator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
        wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
        wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        return driver.findElement(elementLocator);
    }

    synchronized protected List<WebElement> locateListOfElements(WebDriver driver, By elementsLocator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(elementsLocator));
        wait.until(ExpectedConditions.elementToBeClickable(elementsLocator));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(elementsLocator, 0));
        return driver.findElements(elementsLocator);
    }

    synchronized protected void clickElement(WebDriver driver, By elementLocator) {
        locateElement(driver, elementLocator).click();
    }

    synchronized protected void clickElement(WebElement element) {
        element.click();
    }

    synchronized protected void clearText(WebDriver driver, By elementLocator) {
        locateElement(driver, elementLocator).clear();
    }

    synchronized protected void typeOnInputField(WebDriver driver, By elementLocator, String text) {
        locateElement(driver, elementLocator).sendKeys(text);
    }

    synchronized protected void keyboardAction(WebDriver driver, By elementLocator, Keys key) {
        locateElement(driver, elementLocator).sendKeys(key);
    }

    synchronized protected void slowTypeOnInputField(WebDriver driver, By elementLocator, String text) {
        for (int letterIndex = 0; letterIndex < text.length(); letterIndex++) {
            locateElement(driver, elementLocator).sendKeys(String.valueOf(text.charAt(letterIndex)));
        }
        new WaitUtility().waitForInterval(25);
    }

    synchronized protected void selectFromDropdown(WebDriver driver, By elementLocator, String selection) {
        WebElement dropdown = locateElement(driver, elementLocator);
        Select select = new Select(dropdown);
        select.selectByVisibleText(selection);
    }

    synchronized protected void selectFromDropdown_byValue(WebDriver driver, By elementLocator, String selectionValue) {
        WebElement dropdown = locateElement(driver, elementLocator);
        Select select = new Select(dropdown);
        select.selectByValue(selectionValue);
    }

    synchronized protected String getTextOfElement(WebDriver driver, By elementLocator) {
        String elementText = locateElement(driver, elementLocator).getText();
        return elementText;
    }

    synchronized protected String getTextOfElement(WebElement element) {
        return element.getText();
    }

    synchronized protected String getAttributeOfElement(WebDriver driver, By elementLocator, String attributeKey) {
        return locateElement(driver, elementLocator).getDomAttribute(attributeKey);
    }

    synchronized protected String getAttributeOfElement(WebElement element, String attributeKey) {
        return element.getDomAttribute(attributeKey);
    }

    synchronized protected void hoverOverElement(WebDriver driver, By elementLocator) {
        Actions actions = new Actions(driver);
        actions.moveToElement(locateElement(driver, elementLocator));
        actions.perform();
    }

    synchronized protected void hoverOverElement(WebDriver driver, WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
    }

    /*scrolling to certain web element on the DOM*/
    synchronized protected void scrollToElement(WebDriver driver, By elementLocator) {
        /*Casting WebDriver to JavascriptExecutor to execute javaScript code*/
        JavascriptExecutor js = (JavascriptExecutor) driver;
        /*getting the location of the web element to scroll to it*/
        WebElement element = locateElement(driver, elementLocator);
        Point loc = element.getLocation();
        int x;
        int y;
        /*generating the x and y of the location*/
        x = loc.getX();
        y = loc.getY();
        int finalY = y - 50;
        /*scroll to the element*/
        js.executeScript("window.scrollBy(" + x + "," + finalY + ")");
    }

    /*scrolling to certain web element on the DOM*/
    synchronized protected void scrollElementIntoView(WebDriver driver, By elementLocator) {
        /*Casting WebDriver to JavascriptExecutor to execute javaScript code*/
        JavascriptExecutor js = (JavascriptExecutor) driver;
        /*getting the location of the web element to scroll to it*/
        WebElement element = locateElement(driver, elementLocator);
        /*generating the x and y of the location*/
        /*scroll to the element*/
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    synchronized protected void scrollVertically(WebDriver driver, int y_pixels) {
        /*Casting WebDriver to JavascriptExecutor to execute javaScript code*/
        JavascriptExecutor js = (JavascriptExecutor) driver;
        /*getting the location of the web element to scroll to it*/
        /*scroll to the element*/
        js.executeScript("window.scrollBy(" + 0 + "," + y_pixels + ")");
    }

    synchronized protected void scrollToElement(WebDriver driver, WebElement element) {
        /*Casting WebDriver to JavascriptExecutor to execute javaScript code*/
        JavascriptExecutor js = (JavascriptExecutor) driver;
        /*getting the location of the web element to scroll to it*/
        Point loc = element.getLocation();
        int x;
        int y;
        /*generating the x and y of the location*/
        x = loc.getX();
        y = loc.getY();
        int finalY = y - 100;
        /*scroll to the element*/
        js.executeScript("window.scrollBy(" + x + "," + finalY + ")");
    }

    synchronized protected void forceClickOnElement(WebDriver driver, By elementLocator) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = locateElement(driver, elementLocator);
        js.executeScript("arguments[0].click()", element);
    }

    synchronized protected void forceClickOnElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", element);
    }

    synchronized protected void navigateToURL(WebDriver driver, String url) {

        if (url.equalsIgnoreCase(driver.getCurrentUrl())) {
            refreshCurrentPage(driver);
        } else {
            driver.get(url);
        }
    }


    synchronized protected void refreshCurrentPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    synchronized protected void waitSpinnerToLoad(WebDriver driver) {
        WebDriverWait spinnerWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By spinLocator = By.cssSelector(".ant-spin-dot-spin");
        try {
            if (spinnerWait.until(ExpectedConditions.visibilityOfElementLocated(spinLocator)).isDisplayed()) {
                spinnerWait.until(ExpectedConditions.
                        invisibilityOfElementLocated(spinLocator));
            }
        } catch (Exception e) {
            System.out.println("Spinner is not displayed");
        }
        finally {
            wait = new WebDriverWait(driver,
                    Duration.ofSeconds(executionProps.getIntegerProperty("WAIT_TIME")));
        }
    }

    synchronized protected boolean waitUntilElementHasAttribute(WebElement element,
                                                                String attributeKey,
                                                                String expectedAttributeValue) {
        try {
            wait.until(ExpectedConditions.attributeToBe(element, attributeKey, expectedAttributeValue));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    synchronized protected List<String> getTextOfListOfElements(WebDriver driver, By elementLocator) {
        List<WebElement> elements = locateListOfElements(driver, elementLocator);
        List<String> elementTextList = new ArrayList<>();

        for (WebElement element : elements) {
            elementTextList.add(element.getText());
        }
        return elementTextList;
    }

    synchronized protected void closeAllTabsExceptCurrentOne(WebDriver driver, String mainWindowHandle) {
        HashSet<String> allWindows = (HashSet<String>) driver.getWindowHandles();
        for (String windowHandle : allWindows) {
            if (windowHandle.contains(mainWindowHandle)) {
                // do nothing
            } else {
                driver.switchTo().window(windowHandle);
                driver.close();
            }
        }
        driver.switchTo().window(mainWindowHandle);
    }

    synchronized protected WebElement locateStaleElement(WebDriver driver, By elementLocator) {
        final String PROPERTIES_FILE_NAME = "execution.properties";
        final PropertyFileReader executionProps = new PropertyFileReader(PROPERTIES_FILE_NAME);
        try {
            new WebDriverWait(driver,
                    Duration.ofSeconds(executionProps.getIntegerProperty("WAIT_TIME")))
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.elementToBeClickable(elementLocator));
        }
        catch (Exception e) {
            System.out.println("Spinner is not displayed");
        }
        return driver.findElement(elementLocator);
    }

}
