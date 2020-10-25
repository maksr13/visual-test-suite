import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import utils.Common;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.setWebDriver;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        plugin = {"pretty", "html:target/cucumber-html-report.html"})
public class RunCucumberTest {

    @Before
    public void webDriverSetup() {
        String browserName = Common.getConfigValue("browser");

        if (browserName.equalsIgnoreCase("Chrome")) {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver/chromedriver.exe");

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-web-security", "--window-size=1366,768");
            WebDriver webDriver = new ChromeDriver(options);
            setWebDriver(webDriver);
        } else {
            throw new IllegalArgumentException(String.format("Wrong browser specified - '%s'", Common.getConfigValue("browser")));
        }
    }

    @After
    public void tearDown() {
        getWebDriver().quit();
    }
}
