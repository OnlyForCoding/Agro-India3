package agrostar;

import factory.PageFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.PropertyFileReader;
import utilities.Utilites;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

public class BaseClass {
    private WebDriver driver;
    private static PropertyFileReader propertyFileReader = new PropertyFileReader();
    private Utilites utilites = new Utilites();
    private PageFactory pageFactory;

    static Properties property;

    static {
        property = propertyFileReader.getProperties("setUp");
    }

    @BeforeSuite
    public void setUp() {
        driver = utilites.getDriver();
    }

    @BeforeTest
    public void beforeTest() {
        driver.get(property.getProperty("url"));
        driver.manage().window().fullscreen();
    }

    @BeforeClass
    public void beforeClass() {
        init();
    }


    @BeforeMethod(alwaysRun = true)
    public void handleMethod(Method method) {

        try {
            Field[] fs = this.getClass().getDeclaredFields();
            fs[0].setAccessible(true);
            for (Field property : fs) {
                property.setAccessible(true);
                property.set(this,
                        pageFactory.getObject(property.getType()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @AfterSuite
    public void tearDown() {
        driver.close();
        driver.quit();
    }

    @AfterMethod
    public void afterMethod(ITestResult iResult) {
        String testName = iResult.getMethod().getMethodName();
        if (iResult.getStatus() == iResult.FAILURE) {
            utilites.takeScreenShot(driver, testName);
        }
    }

    private void init() {
        pageFactory = new PageFactory(driver);
    }
}
