package agrostar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class Utilites {

    private WebDriver driver;
    private PropertyFileReader propertyFileReader = new PropertyFileReader();
    private Properties properties = propertyFileReader.getProperties("setUp");

    public WebDriver getDriver() {

        String browser = System.getProperty("browser");
        if (browser == null || browser.equals("")) {
            browser = properties.getProperty("browser");
        }

        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", properties.getProperty("chromedriver"));
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", properties.getProperty("gheckodriver"));
            driver = new FirefoxDriver();
        } else {
            System.setProperty("webdriver.chrome.driver", properties.getProperty("chromedriver"));
            driver = new ChromeDriver();
        }

        return driver;
    }

    public void takeScreenShot(WebDriver driver, String screenShotName) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File("target/screenshot/"+screenShotName+".png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
    }
}
