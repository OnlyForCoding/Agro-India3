package agrostar;

import org.apache.log4j.BasicConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.util.Properties;

public class BaseClass {
    public WebDriver driver;
    static PropertyFileReader propertyFileReader = new PropertyFileReader();
    Utilites utilites = new Utilites();

    static Properties property;
    static {
        property = propertyFileReader.getProperties("setUp");
    }

    @BeforeSuite
    public void setUp(){
        driver = utilites.getDriver();
    }

    @BeforeTest
    public void init(){
        driver.get(property.getProperty("url"));
        driver.manage().window().fullscreen();
    }

    @AfterSuite
    public void tearDown(){
        driver.close();
        driver.quit();
    }

    @AfterMethod
    public void afterMethod(ITestResult iResult){
        String testName = iResult.getTestName();
        if (iResult.getStatus() == iResult.FAILURE){
            utilites.takeScreenShot(driver,testName);
        }
    }
}
