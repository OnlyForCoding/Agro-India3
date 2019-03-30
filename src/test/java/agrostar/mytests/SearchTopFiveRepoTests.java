package agrostar.mytests;
import agrostar.BaseClass;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ForkRepoPage;

public class SearchTopFiveRepoTests extends BaseClass {
    private ForkRepoPage forkRepoPage;

    @Test
    public void searchForTopFiveRepo() {
        boolean flag = false;
        try {
            extentTest.log(LogStatus.INFO, "Loging in");
            forkRepoPage.signIn(property.getProperty("username"), property.getProperty("password"));
            Assert.assertFalse(forkRepoPage.isUserNotLoggedIn(), "User Has not logged in");
            extentTest.log(LogStatus.INFO, "User has logged in successfully");
            extentTest.log(LogStatus.INFO, "Searching for Programming language Java in search Box");
            forkRepoPage.searchMe("Java");
            extentTest.log(LogStatus.INFO, "Searched for Programming language Java in search Box");
            extentTest.log(LogStatus.INFO, "Sorting the repositories by Most Star");
            forkRepoPage.sortByMostStar();
            extentTest.log(LogStatus.INFO, "Forking the Repositories one after another");
            forkRepoPage.forkItAndVerify();
            extentTest.log(LogStatus.PASS, "Verified that all the repositories are forked successfully");
            flag = true;
        } catch (Throwable throwable){
            extentTest.log(LogStatus.FAIL, throwable.getMessage());
            System.out.println(throwable.getMessage());
        }

        if (!flag){
            Assert.assertTrue(flag, "Search For Top Five Repo is failing");
        }
    }
}
