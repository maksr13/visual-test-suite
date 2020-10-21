import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        format = {"html:target/cucumber-html-report"},
        monochrome = true)
public class CukesFeaturesRunnerTest {

    @BeforeClass
    public static void executeBeforeTests() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver/chromedriver.exe");
    }
}
