package utilities;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ForkRepoPage;

public class WebDriverActivities {
    private WebDriver driver;
    final static Logger logger = Logger.getLogger(ForkRepoPage.class);

    public WebDriverActivities(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getElement(String locatorValue, String locatorType) {
        String locator = locatorType.toUpperCase();
        WebElement element = null;
        switch (locator) {
            case "ID":
                element = driver.findElement(By.id(locatorValue));
                break;
            case "XPATH":
                element = driver.findElement(By.xpath(locatorValue));
                break;
            case "CLASS":
                element = driver.findElement(By.className(locatorValue));
                break;
            case "NAME":
                element = driver.findElement(By.name(locatorValue));
                break;
            default:
                throw new RuntimeException("Locator type passed is not matching with expected type");

        }
        return element;
    }

    public By getElementBy(String locatorValue, String locatorType) {
        String locator = locatorType.toUpperCase();
        By by = null;
        switch (locator) {
            case "ID":
                by = By.id(locatorValue);
                break;
            case "XPATH":
                by = By.xpath(locatorValue);
                break;
            case "CLASS":
                by = By.className(locatorValue);
                break;
            default:
                throw new RuntimeException("Locator type passed is not matching with expected type");

        }
        return by;
    }

    public void clickOnElement(String locatorValue, String locatorType) {
        logger.info("Clicking on an element " + locatorValue);
        getElement(locatorValue, locatorType).click();
        logger.info("Clicked on an element : " + locatorValue);
    }

    public void waitForElementToBePresent(String locatorValue, String locatorType, int timeout) {
        logger.info("waiting for an element to be present " + locatorValue);
        WebDriverWait wait = getWebDriverWaitInstance(timeout);
        wait.until(ExpectedConditions.presenceOfElementLocated(getElementBy(locatorValue, locatorType)));
        logger.info("waited for an element " + locatorValue + " for " + timeout + " seconds");
    }

    public WebDriverWait getWebDriverWaitInstance(int timeout) {
        return new WebDriverWait(driver, timeout);
    }

    public void enterIntoField(String locatorValue, String locatorType, String value) {
        logger.info("entering value for "+locatorValue);
        getElement(locatorValue, locatorType).clear();
        getElement(locatorValue, locatorType).sendKeys(value);
        logger.info("entered an value "+value+" for an locator "+ locatorValue);
    }

    public void submit(String locatorValue, String locatorType) {
        logger.info("submitting the "+locatorValue);
        getElement(locatorValue, locatorType).submit();
        logger.info("submitted the " + locatorValue);
    }
}
