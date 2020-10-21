package helpers;
import com.applitools.eyes.*;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import org.springframework.util.StringUtils;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class EyesTool {
    public Eyes eyes;
    private EyesRunner runner;
    private static BatchInfo batch;

    public EyesTool(String batchName, String appName, String testName, int width, int heigth) {
        batch = new BatchInfo(batchName);
        // Initialize the Runner for your test.
        runner = new ClassicRunner();

        // Initialize the eyes SDK
        eyes = new Eyes(runner);

        // Raise an error if no API Key has been found.
        if(StringUtils.isEmpty(System.getenv("APPLITOOLS_API_KEY"))) {
            throw new RuntimeException("No API Key found; Please set environment variable 'APPLITOOLS_API_KEY'.");
        }

        // Set your personal Applitols API Key from your environment variables.
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));

        // set batch name
        eyes.setBatch(batch);
        eyes.open(getWebDriver(), appName, testName, new RectangleSize(width, heigth));
    }
}
