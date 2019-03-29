package agrostar;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import factory.PageFactory;
import org.apache.log4j.BasicConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import report.AgroExtentTestManager;
import report.ExtentManager;
import utilities.PropertyFileReader;
import utilities.Utilities;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

public class BaseClass {
    private WebDriver driver;
    private static PropertyFileReader propertyFileReader = new PropertyFileReader();
    private Utilities utilites = new Utilities();
    private PageFactory pageFactory;
    protected ExtentTest extentTest;
    protected Properties property;


    @BeforeSuite(alwaysRun = true)
    public void setUp() {
        property = propertyFileReader.getProperties("setUp");
    }

    @BeforeTest
    public void beforeTest() {
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        driver = utilites.getDriver();
        init();
    }

    @BeforeMethod(alwaysRun = true)
    public void handleMethod(Method method, Object[] testData) {
        if(testData!=null && testData.length>=1){
            extentTest = ExtentManager.startTest(method.getName()+" -- "+testData[2].toString());
            extentTest.log(LogStatus.INFO, "Test : "+method.getName()+" -- "+testData[2].toString()+" Started");
        }else {
            extentTest = ExtentManager.startTest(method.getName());
            extentTest.log(LogStatus.INFO, "Test: "+method.getName()+" Started");
        }

        try {
            Field[] fs = this.getClass().getDeclaredFields();
            for (Field property : fs) {
                property.setAccessible(true);
                property.set(this,
                        pageFactory.getObject(property.getType()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult iResult) {
        String testName = iResult.getMethod().getMethodName();
        if (iResult.getStatus() == iResult.FAILURE) {
            String screenShotName = utilites.takeScreenShot(driver, testName);
            extentTest.log(LogStatus.FAIL,
                    "Snapshot is " + extentTest.addScreenCapture(screenShotName));
        }

        ExtentManager.endTest(extentTest);
    }

    @AfterTest(alwaysRun = true)
    public void afterTest(){
        ExtentManager.flush();
    }

    private void init() {
        pageFactory = new PageFactory(driver);
    }

    @DataProvider(name = "login")
    public Object[][] getLoginData(){
        Object object [][] = new Object[3][3];
        object[0][0] = "onlyforcoding24@gmail.com";
        object[1][0] = "onlyforwewecoding24@gmail.com";
        object[2][0] = "onlyforwewecoding24gmail.com";
        object[0][1] = "rightpassword";
        object[1][1] = "rightpassword";
        object[2][1] = "solvechallenge@55";
        object[0][2] = "Correct Email Id and incorrect passoword";
        object[1][2] = "Incorrect Email Id and incorrect passoword";
        object[2][2] = "Incorrect Email Id and correct passoword";

        return object;
    }
}
