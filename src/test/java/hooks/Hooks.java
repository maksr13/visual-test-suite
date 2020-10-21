package hooks;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import utils.Common;

import java.util.List;
import java.util.logging.Level;

import static com.codeborne.selenide.Selenide.getWebDriverLogs;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.setWebDriver;
import static org.openqa.selenium.OutputType.BYTES;

public class Hooks {

    @Before
    public void testDataSetup() {
        if (Common.getConfigValue("browser").equalsIgnoreCase("Chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-web-security", "--window-size=1366,768");
            WebDriver webDriver = new ChromeDriver(options);
            setWebDriver(webDriver);
        } else {
            throw new IllegalArgumentException(String.format("Wrong browser specified - '%s'", Common.getConfigValue("browser")));
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            TakesScreenshot camera = (TakesScreenshot) getWebDriver();
            byte[] screenshot = camera.getScreenshotAs(BYTES);
            scenario.write("\n");
            scenario.embed(screenshot, "image/png");

            List<String> errors = getWebDriverLogs(LogType.BROWSER, Level.SEVERE);
            String logs = "******* JavaScript Errors *********\n";
            for (String error : errors) {
                logs = logs + error + "\n";
            }
            System.out.print(logs);
            scenario.write(logs);
        }
        getWebDriver().quit();
    }
}
