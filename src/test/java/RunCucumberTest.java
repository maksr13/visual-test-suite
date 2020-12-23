import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import static com.codeborne.selenide.WebDriverRunner.setWebDriver;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features/CustomVisualTestingToolTest.feature"},
        plugin = {"pretty", "html:target/cucumber-html-report.html"})
public class RunCucumberTest {

    @BeforeClass
    public static void webDriverSetup() {
        WebDriver webDriver = null;
        String browserName = Common.getConfigValue("browser");

        switch (browserName) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "src/test/resources/web_drivers/chromedriver.exe");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-web-security", "--window-size=1366,768");
                webDriver = new ChromeDriver(options);
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", "src/test/resources/web_drivers/geckodriver.exe");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                webDriver = new FirefoxDriver(firefoxOptions);
                break;
            case "internet explorer":
                System.setProperty("webdriver.ie.driver", "src/test/resources/web_drivers/IEDriverServer.exe");
                InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
                internetExplorerOptions.setCapability("ignoreZoomSetting", true);
                webDriver = new InternetExplorerDriver(internetExplorerOptions);
                break;
            default:
                webDriver = new ChromeDriver();
        }
        setWebDriver(webDriver);
    }
}
