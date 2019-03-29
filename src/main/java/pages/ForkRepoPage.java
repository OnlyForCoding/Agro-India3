package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utilities.PropertyFileReader;
import utilities.WebDriverActivities;

import java.util.Properties;

public class ForkRepoPage {

    private final static Logger logger = Logger.getLogger(ForkRepoPage.class);
    private WebDriver driver;
    private WebDriverActivities webDriverActivities;
    private PropertyFileReader propertyFileReader = new PropertyFileReader();
    private Properties locators;

    public ForkRepoPage(WebDriver driver) {
        this.driver = driver;
        webDriverActivities = new WebDriverActivities(this.driver);
        locators = propertyFileReader.getProperties("forkPage");
    }

    public void signIn(String username, String password) {
        logger.info("Signing In");
        webDriverActivities.enterIntoField(locators.getProperty("username"), username);
        webDriverActivities.enterIntoField(locators.getProperty("password"),  password);
        webDriverActivities.clickOnElement(locators.getProperty("loginBtn"));
        logger.info("Signed In Successfully");
    }

    public void searchMe(String language) {
        webDriverActivities.enterIntoField(locators.getProperty("search_language"), language);
        webDriverActivities.submit(locators.getProperty("search_language"));
        webDriverActivities.waitForElementToBePresent(locators.getProperty("searched_language_link"), 10);
        webDriverActivities.waitForElementTobeClickable(locators.getProperty("searched_language_link"), 10);
        webDriverActivities.clickOnElement(locators.getProperty("searched_language_link"));
    }

    public void sortByMostStar() {
        webDriverActivities.clickOnElement(locators.getProperty("drpDwnBestMatch"));
        webDriverActivities.clickOnElement(locators.getProperty("optionsMostStar"));
    }

    public void forkItAndVerify() {
        for (int i = 1; i <= 5; i++) {
            WebElement element = driver.findElement(By.xpath("//ul[@class='repo-list']//li[" + i + "]//a"));
            webDriverActivities.clickOnStaleElement(By.xpath("//ul[@class='repo-list']//li[" + i + "]//a"));
            webDriverActivities.waitForElementTobeClickable(locators.getProperty("forkBtn"), 10);
            webDriverActivities.clickOnElement(locators.getProperty("forkBtn"));
            webDriverActivities.waitForElementToBePresent(locators.getProperty("isProjectForked"),10);
            Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'forked from')]")).isDisplayed());
            driver.navigate().back();
            driver.navigate().back();
        }
    }

    public boolean isUserNotLoggedIn(){
        return webDriverActivities.isElementPresent(locators.getProperty("txtLoginFail"));
    }

    public String getErrorMessageWhileLogin(){
        if (webDriverActivities.isElementPresent(locators.getProperty("txtLoginFail"))){
            return webDriverActivities.getElement(locators.getProperty("txtLoginFail")).getText();
        }
        return null;
    }
}
