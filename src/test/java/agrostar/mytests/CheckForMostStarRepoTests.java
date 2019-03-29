package agrostar.mytests;

import agrostar.BaseClass;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ForkRepoPage;

public class CheckForMostStarRepoTests extends BaseClass {
    private ForkRepoPage forkRepoPage;

    @Test
    public void checkForMostStarRepo() {
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
            Assert.assertTrue(forkRepoPage.isSortedRepoByMostStar(), "Failing to sort it properly :-)");
            flag = true;
        } catch (Throwable throwable){
            extentTest.log(LogStatus.FAIL, throwable.getMessage());
            throwable.printStackTrace();
        }

        if (!flag){
            Assert.assertTrue(flag, "Search For Top Five Repo is failing");
        }
    }
}
