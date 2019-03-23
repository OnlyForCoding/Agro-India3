package agrostar;
import org.testng.annotations.Test;

public class TestGitHub extends BaseClass{

    private ForkRepoPage forkRepoPage;

    @Test
    public void searchForTopFiveRepo() throws InterruptedException {
        forkRepoPage = new ForkRepoPage(driver);
        forkRepoPage.signIn(property.getProperty("username"), property.getProperty("password"));
        forkRepoPage.searchMe("Java");
        forkRepoPage.sortByMostStar();
        forkRepoPage.forkItAndVerify();
    }
}
