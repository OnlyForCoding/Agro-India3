package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utilities.PropertyFileReader;
import utilities.WebDriverActivities;

import java.util.Properties;

public class LoginPage {

    private final static Logger logger = Logger.getLogger(ForkRepoPage.class);
    private WebDriver driver;
    private WebDriverActivities webDriverActivities;
    private PropertyFileReader propertyFileReader = new PropertyFileReader();
    private Properties locators;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        webDriverActivities = new WebDriverActivities(this.driver);
        locators = propertyFileReader.getProperties("forkPage");
    }
}
