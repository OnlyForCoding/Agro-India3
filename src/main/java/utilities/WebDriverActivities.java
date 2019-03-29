package utilities;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
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

    public WebElement getElement(String locatorFromPropFile) {

        String locatorType = "";
        WebElement element = null;
        String locatorValue = "";

        if (locatorFromPropFile.contains(":")){
            locatorType  = locatorFromPropFile.split(":::")[0].toUpperCase();
            locatorValue = locatorFromPropFile.split(":::")[1];
        }else{
            throw new RuntimeException("Locator format does not contains colon");
        }

        switch (locatorType) {
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

    public By getElementBy(String locatorFromPropFile) {

        String locatorType = "";
        String locatorValue = "";
        By by = null;

        if (locatorFromPropFile.contains(":")){
            locatorType  = locatorFromPropFile.split(":::")[0].toUpperCase();
            locatorValue = locatorFromPropFile.split(":::")[1];
        }else{
            throw new RuntimeException("Locator format does not contains colon");
        }

        switch (locatorType) {
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

    public void clickOnElement(String locatorFromPropFile) {
        try {
            getElement(locatorFromPropFile).click();
        } catch (StaleElementReferenceException ex){
            clickOnStaleElement(getElementBy(locatorFromPropFile));
        }
    }

    public void waitForElementToBePresent(String locatorFromPropFile, int timeout) {
        logger.info("waiting for an element to be present " + locatorFromPropFile);
        WebDriverWait wait = getWebDriverWaitInstance(timeout);
        wait.until(ExpectedConditions.presenceOfElementLocated(getElementBy(locatorFromPropFile)));
        logger.info("waited for an element " + locatorFromPropFile + " for " + timeout + " seconds");
    }

    public WebDriverWait getWebDriverWaitInstance(int timeout) {
        return new WebDriverWait(driver, timeout);
    }

    public void enterIntoField(String locatorFromPropFile, String value) {
        logger.info("entering value for " + locatorFromPropFile);
        getElement(locatorFromPropFile).clear();
        getElement(locatorFromPropFile).sendKeys(value);
        logger.info("entered an value " + value + " for an locator " + locatorFromPropFile);
    }

    public void submit(String locatorFromPropFile) {
        logger.info("submitting the " + locatorFromPropFile);
        getElement(locatorFromPropFile).submit();
        logger.info("submitted the " + locatorFromPropFile);
    }

    public void waitForElementTobeClickable(String locatorFromPropFile, int timeout) {
        WebDriverWait wait = getWebDriverWaitInstance(timeout);
        wait.until(ExpectedConditions.elementToBeClickable(getElementBy(locatorFromPropFile)));
    }

    public void waitForElementTobeLocated(String locatorFromPropFile, int timeout) {
        WebDriverWait wait = getWebDriverWaitInstance(timeout);
        wait.until(ExpectedConditions.presenceOfElementLocated(getElementBy(locatorFromPropFile)));
    }

    public void waitForElementTobeLocated(By by, int timeout) {
        WebDriverWait wait = getWebDriverWaitInstance(timeout);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void waitUntilStalenessOfLocator(WebElement element, int timeout) {
        WebDriverWait wait = getWebDriverWaitInstance(timeout);
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element)));
    }

    public boolean isNotStale(String locatorFromPropFile) {
        int counter = 0;
        do {
            try {
                if (getElement(locatorFromPropFile).isEnabled() && getElement(locatorFromPropFile).isDisplayed()) {
                    counter = counter++;
                    clickOnElement(locatorFromPropFile);
                    return true;
                }
            } catch (Exception ex) {
            }
        } while (counter == 0);

        return false;
    }

    public boolean isElementPresent(String locatorFromPropFile){
        boolean isPresent = false;
        try {
            getElement(locatorFromPropFile).isDisplayed();
            isPresent = true;
        } catch (NoSuchElementException ex){

        }

        return isPresent;
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
