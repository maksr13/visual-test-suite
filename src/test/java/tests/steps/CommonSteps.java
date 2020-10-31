package tests.steps;

import helpers.EyesTool;
import helpers.customVisualTestingTool.CustomVisualTestingTool;
import io.cucumber.java.en.When;
import helpers.PercyTool;

import java.io.IOException;
import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import io.cucumber.datatable.DataTable;

public class CommonSteps {

    @When("^I open web-site$")
    public void iOpenWebSite() {
        open("https://khai.edu/ua/");
    }

    @When("^I launch Percy tool$")
    public void iLaunchPercyTool() {
        new PercyTool();
    }

    @When("^I take screenshot via Percy tool$")
    public void iTakeScreenshotViaPercyTool() {
        PercyTool.percy.snapshot("name");
    }

    @When("^I open Eyes$")
    public void iOpenEyes() {
        new EyesTool("batchName", "khaiSite", "test", 800, 600);
    }

    @When("^I take screenshot via Eyes tool$")
    public void iTakeScreenshotViaEyesTool() {
        EyesTool.eyes.checkWindow("test");
    }

    @When("^I close Eyes$")
    public void iCloseEyes() {
        EyesTool.eyes.closeAsync();
    }

    @When("^I take screenshot via Custom Visual Testing tool$")
    public void iTakeScreenshotViaCustomVisualTestingTool(DataTable table) throws IOException {
        List<List<String>> data = table.asLists(String.class);
        String feature = data.get(1).get(0);
        String scenario = data.get(1).get(1);
        String screenshotNumber = data.get(1).get(2);

        CustomVisualTestingTool customVisualTestingTool = new CustomVisualTestingTool();

        String expectedImagePath = customVisualTestingTool.buildExpectedImagePath(feature, scenario, screenshotNumber);
        String actualImagePath = customVisualTestingTool.buildActualImagePath(feature, scenario, screenshotNumber);
        String comparedImagePath = customVisualTestingTool.buildComparedImagePath(feature, scenario, screenshotNumber);
        String comparedGiffsPath = customVisualTestingTool.buildComparedGiffsPath(feature, scenario, screenshotNumber);

        customVisualTestingTool.takeScreenshotAndCompareImages(actualImagePath, expectedImagePath, comparedImagePath, comparedGiffsPath);
    }
}
