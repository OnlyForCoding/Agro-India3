package factory;

import org.openqa.selenium.WebDriver;
import pages.ForkRepoPage;

import java.lang.reflect.Field;

public class PageFactory {

    private WebDriver driver;
    private ForkRepoPage forkRepoPage;

    public PageFactory(WebDriver driver){
        this.driver = driver;
    }

    public ForkRepoPage getForkRepoPage() {
        if (forkRepoPage == null){
            forkRepoPage = new ForkRepoPage(driver);
        }
        return forkRepoPage;
    }

    public <T> T getObject(Class<T> validator) throws Exception {

        Field[] fs = this.getClass().getDeclaredFields();
        fs[0].setAccessible(true);
        for (Field property : fs) {
            if (property.getType().isAssignableFrom(validator)) {
                if (property.get(this) == null)
                    property.set(this, validator
                            .getConstructor(WebDriver.class)
                            .newInstance(driver));
                return (T) property.get(this);
            }

        }
        return null;
    }
}
