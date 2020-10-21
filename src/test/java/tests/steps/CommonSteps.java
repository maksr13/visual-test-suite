package tests.steps;

import cucumber.api.java.en.When;
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
}
