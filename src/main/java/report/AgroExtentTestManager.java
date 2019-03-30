package report;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.util.List;

public class AgroExtentTestManager extends ExtentReports{

    private static final long serialVersionUID = 2543311573206081367L;

    public AgroExtentTestManager(String filePath, Boolean replaceExisting) {
        super(filePath,replaceExisting);
    }

    public List<ExtentTest> getTestList() {
        return testList;
    }

    public synchronized boolean removeTest(ExtentTest extentTest){
        return testList.remove(extentTest);
    }
}
