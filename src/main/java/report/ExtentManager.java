package report;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ExtentManager {

    private static Map<String, ExtentTest> extentTestMap;
    private static AgroExtentTestManager extentReports;
    private static Logger logger = Logger.getLogger(ExtentManager.class);

    static {
        extentTestMap = new HashMap<String, ExtentTest>();
        if (extentReports == null) {

            extentReports = new AgroExtentTestManager(System.getProperty("user.dir")+"\\target\\AgroStar-Automation/Agro1-Star-ExtentReport.html", true);

            extentReports.addSystemInfo("Host Name", "Jenkins-Agro-Star-Slave")
                    .addSystemInfo("Environment", "QA");
        }
    }

    public synchronized static AgroExtentTestManager getReporter() {
        return extentReports;
    }

    public synchronized static AgroExtentTestManager sharedInstance() {
        return extentReports;
    }

    public static synchronized void endTest(ExtentTest test) {
        extentReports.endTest(test);

    }

    public static synchronized ExtentTest startTest(String testName) {
        boolean retry = false;
        ExtentTest test = extentTestMap.get(testName);
        if (test != null) {
            extentReports.removeTest(test);
            extentTestMap.remove(test);
            logger.info(testName+ " is being removed from the report as it is being retried.");
            retry = true;
        }

        test = extentReports.startTest(testName);
        if(retry)
            test.log(LogStatus.INFO, "Test being retried.");
        extentTestMap.put(testName, test);
        return test;
    }

    public static synchronized void flush() {
        extentReports.flush();
    }
}
