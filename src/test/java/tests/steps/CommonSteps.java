package tests.steps;

import helpers.EyesTool;
import io.cucumber.java.en.When;
import helpers.PercyTool;

import static com.codeborne.selenide.Selenide.open;

public class CommonSteps {
    @When("^I open web-site$")
    public void iOpenWebSite() {
        open("https://khai.edu/ua/");
    }

    @When("^I take screenshot via Percy tool$")
    public void iTakeScreenshotViaPercyTool() {
        PercyTool percyTool = new PercyTool();
        percyTool.snapshot("name");
    }

    @When("^I take screenshot via Eyes tool$")
    public void iTakeScreenshotViaEyesTool() {
        EyesTool eyesTool = new EyesTool("batchName", "khaiSite", "test", 1366, 768);
        eyesTool.eyes.checkWindow("Khai site");
    }
}
