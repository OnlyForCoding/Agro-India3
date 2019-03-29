package agrostar.mytests;

import agrostar.BaseClass;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ForkRepoPage;

public class LoginTests extends BaseClass {
    private ForkRepoPage forkRepoPage;

    @Test(dataProvider = "login")
    public void loginWithInvalidCredential(String username, String password, String description) {
        boolean flag = false;
        try {
            extentTest.log(LogStatus.INFO, "Signing In");
            forkRepoPage.signIn(username, password);
            Assert.assertTrue(forkRepoPage.isUserNotLoggedIn(), description);
            extentTest.log(LogStatus.INFO, "Unable to login with username : " + username + " and password " + password);
            Assert.assertEquals(forkRepoPage.getErrorMessageWhileLogin(), "Incorrect username or password.", "Error message is not matching");
            extentTest.log(LogStatus.INFO, "Getting error message as Incorrect username or password.");
            flag = true;
        } catch (Throwable t) {
            extentTest.log(LogStatus.FAIL, t.getMessage());
        }

        if (!flag){
            Assert.assertTrue(flag , "Login Test failed");
        }
    }
}
