package agrostar;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.Properties;

public class ForkRepoPage {

    final static Logger logger = Logger.getLogger(ForkRepoPage.class);
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
        webDriverActivities.clickOnElement(locators.getProperty("loginBtnOnHdr"), "xpath");
        webDriverActivities.enterIntoField(locators.getProperty("username"), "id", username);
        webDriverActivities.enterIntoField(locators.getProperty("password"), "id", password);
        webDriverActivities.clickOnElement(locators.getProperty("loginBtn"), "name");
        logger.info("Signed In Successfully");
    }

    public void searchMe(String language) {
        webDriverActivities.enterIntoField(locators.getProperty("search_language"), "xpath", language);
        webDriverActivities.submit(locators.getProperty("search_language"), "xpath");
        webDriverActivities.clickOnElement(locators.getProperty("searched_language_link"), "xpath");
    }

    public void sortByMostStar() {
        webDriverActivities.clickOnElement(locators.getProperty("drpDwnBestMatch"), "xpath");
        webDriverActivities.clickOnElement(locators.getProperty("optionsMostStar"), "xpath");
    }

    public void forkItAndVerify() {
        try {
            for (int i = 1; i <= 5; i++) {
                WebElement el = driver.findElement(By.xpath("//ul[@class='repo-list']//li[" + i + "]//a"));
                el.click();
                driver.findElement(By.xpath("//form[@class='btn-with-count']//button")).click();
                webDriverActivities.waitForElementToBePresent(locators.getProperty("isProjectForked"), "xpath", 10);
                Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'forked from')]")).isDisplayed());
                driver.navigate().back();
                driver.navigate().back();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
