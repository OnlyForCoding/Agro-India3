package utilities;

import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

public class Utilities {

    private WebDriver driver;
    private PropertyFileReader propertyFileReader = new PropertyFileReader();
    private Properties properties = propertyFileReader.getProperties("setUp");
    private String browser;
    private String system;
    private String ipAddress;
    private DesiredCapabilities desiredCapabilities;

    public WebDriver getDriver() {

        browser = System.getProperty("browser");
        if (browser == null || browser.equals("")) {
            browser = properties.getProperty("browser");
        }

        if (browser.equalsIgnoreCase("chrome")) {
            driver = getChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = getFirefoxDriver();
        } else if (browser.equalsIgnoreCase("ie")) {
            driver = getIeDriver();
        }

        return driver;
    }

    public void takeScreenShot(WebDriver driver, String screenShotName) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File("target/screenshot/" + screenShotName + ".png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
    }

    public WebDriver getChromeDriver() {
        WebDriverManager.chromedriver().forceCache().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("download.default_directory", System.getProperty("user.dir") + "/target");
        new DesiredCapabilities();
        DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        chromeOptions.addArguments(new String[]{"--window-size=1920,1080"});
        if (System.getProperty("hub") != null && !"local".equalsIgnoreCase(System.getProperty("hub"))) {
            try {
                chromeCapabilities.setCapability("goog:chromeOptions", chromeOptions);
                this.driver = new RemoteWebDriver(new URL(System.getProperty("hub") + "/wd/hub"), (new Capabilities()).getBrowserCapabilities(chromeCapabilities));
                ((RemoteWebDriver) this.driver).setFileDetector(new LocalFileDetector());
            } catch (MalformedURLException var5) {
                var5.printStackTrace();
            }
        } else {
            this.driver = new ChromeDriver(chromeOptions);
        }

        return this.driver;
    }

    private WebDriver getFirefoxDriver() {
        FirefoxDriverManager.getInstance().forceCache().setup();
        FirefoxProfile profile = new FirefoxProfile();
        profile.setAssumeUntrustedCertificateIssuer(true);
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.helperApps.alwaysAsk.force", false);
        new DesiredCapabilities();
        DesiredCapabilities firefoxCapabilities = DesiredCapabilities.firefox();
        firefoxCapabilities.setCapability("firefox_profile", profile);
        if (System.getProperty("hub") != null && !"local".equalsIgnoreCase(System.getProperty("hub"))) {
            try {
                this.driver = new RemoteWebDriver(new URL(System.getProperty("hub") + "/wd/hub"), (new Capabilities()).getBrowserCapabilities(firefoxCapabilities));
                ((RemoteWebDriver) this.driver).setFileDetector(new LocalFileDetector());
            } catch (MalformedURLException var4) {
                var4.printStackTrace();
            }
        } else {
            this.driver = new FirefoxDriver();
            this.driver.manage().window().maximize();
        }

        return this.driver;
    }

    private WebDriver getIeDriver() {
        InternetExplorerDriverManager.getInstance().forceCache().setup();
        InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
        internetExplorerOptions.ignoreZoomSettings();
        new DesiredCapabilities();
        DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
        ieCapabilities.setCapability("ignoreZoomSetting", true);
        if (System.getProperty("hub") != null && !"local".equalsIgnoreCase(System.getProperty("hub"))) {
            try {
                this.driver = new RemoteWebDriver(new URL(System.getProperty("hub") + "/wd/hub"), (new Capabilities()).getBrowserCapabilities(ieCapabilities));
                ((RemoteWebDriver) this.driver).setFileDetector(new LocalFileDetector());
            } catch (MalformedURLException var3) {
                var3.printStackTrace();
            }
        } else {
            this.driver = new InternetExplorerDriver(internetExplorerOptions);
            this.driver.manage().window().maximize();
        }

        return this.driver;
    }

}
