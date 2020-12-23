package tests.steps;

import helpers.PercyTool;
import io.cucumber.java.en.When;

public class PercyToolSteps {

    @When("^I launch Percy tool$")
    public void iLaunchPercyTool() {
        new PercyTool();
    }

    @When("^I take screenshot via Percy tool (.*)$")
    public void iTakeScreenshotViaPercyTool(String screenshotTag) {

        PercyTool.percy.snapshot(screenshotTag);
    }
}
