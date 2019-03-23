package agrostar;
import org.testng.annotations.Test;
import pages.ForkRepoPage;

public class TestGitHub extends BaseClass{
    private ForkRepoPage forkRepoPage;

    @Test
    public void searchForTopFiveRepo() throws InterruptedException {
        forkRepoPage.signIn(property.getProperty("username"), property.getProperty("password"));
        forkRepoPage.searchMe("Java");
        forkRepoPage.sortByMostStar();
        forkRepoPage.forkItAndVerify();
    }
}
