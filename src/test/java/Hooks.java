import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import tests.steps.CommonSteps;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Hooks {
    @AfterStep
    public static void afterStep(Scenario scenario) {
        if (scenario.isFailed() && CommonSteps.screenshots.size() > 0) {
            scenario.attach(CommonSteps.screenshots.get("expected image"), "image/png", "expected image");
            scenario.attach(CommonSteps.screenshots.get("actual image"), "image/png", "actual image");
            scenario.attach(CommonSteps.screenshots.get("compared image"), "image/png", "compared image");
            CommonSteps.screenshots.clear();
        }
    }

    @After
    public void afterFeature() {
        getWebDriver().quit();
    }
}
