package utilities;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ForkRepoPage;

import java.util.HashSet;
import java.util.Set;

public class WebDriverActivities {
    private WebDriver driver;
    private final static Logger logger = Logger.getLogger(ForkRepoPage.class);
    private Set<String> windowHandle = new HashSet<>();

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
        logger.info("entering value for " + locatorValue);
        getElement(locatorValue, locatorType).clear();
        getElement(locatorValue, locatorType).sendKeys(value);
        logger.info("entered an value " + value + " for an locator " + locatorValue);
    }

    public void submit(String locatorValue, String locatorType) {
        logger.info("submitting the " + locatorValue);
        getElement(locatorValue, locatorType).submit();
        logger.info("submitted the " + locatorValue);
    }

    public void waitForElementTobeClickable(String locatorValue, String locatorType, int timeout) {
        WebDriverWait wait = getWebDriverWaitInstance(timeout);
        wait.until(ExpectedConditions.elementToBeClickable(getElementBy(locatorValue, locatorType)));
    }

    public void waitForElementTobeLocated(String locatorValue, String locatorType, int timeout) {
        WebDriverWait wait = getWebDriverWaitInstance(timeout);
        wait.until(ExpectedConditions.presenceOfElementLocated(getElementBy(locatorValue, locatorType)));
    }

    public void waitForElementTobeLocated(By by, int timeout) {
        WebDriverWait wait = getWebDriverWaitInstance(timeout);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void waitUntilStalenessOfLocator(WebElement element, int timeout) {
        WebDriverWait wait = getWebDriverWaitInstance(timeout);
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element)));
    }

    public boolean isNotStale(String locator, String locatorType) {
        int counter = 0;
        do {
            try {
                if (getElement(locator, locatorType).isEnabled() && getElement(locator, locatorType).isDisplayed()) {
                    counter = counter++;
                    clickOnElement(locator, locatorType);
                    return true;
                }
            } catch (Exception ex) {
            }
        } while (counter == 0);

        return false;
    }

    public void clickOnStaleElement(By by) {
        int count = 0;
        while (count < 10) {
            try {
                WebElement staledElement = driver.findElement(by);
                Thread.sleep(1000);
                staledElement.click();
                break;
            } catch (StaleElementReferenceException e) {
                count = count + 1;
            } catch (InterruptedException ex1) {
            }
        }
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void navigateToForward() {
        driver.navigate().forward();
    }

    public void navigateToBackward() {
        driver.navigate().back();
    }

    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    public void closeAlert() {
        driver.switchTo().alert().dismiss();
    }

    public String getTextOfAlert() {
        return driver.switchTo().alert().getText();
    }

    public void switchToFrame(String id) {
        driver.switchTo().frame(id);
    }

    public void switchToFrame(int frame) {
        driver.switchTo().frame(frame);
    }

    public void switchToFrame(WebElement element) {
        driver.switchTo().frame(element);
    }

    public void switchToParentFrame() {
        driver.switchTo().parentFrame();
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public Set<String> getWindowHandles() {
        windowHandle = driver.getWindowHandles();
        return windowHandle;
    }

    public void switchToSpecificWindow(String window) {
        driver.switchTo().window(window);
    }

    public void closeCurrentTab() {
        driver.close();
    }

    public String getTitle() {
        return driver.getTitle();
    }

}
