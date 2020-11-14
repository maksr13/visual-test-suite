package tests.steps;

import helpers.EyesTool;
import helpers.customVisualTestingTool.CustomVisualTestingTool;
import io.cucumber.java.en.When;
import helpers.PercyTool;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import io.cucumber.datatable.DataTable;

import javax.naming.NoInitialContextException;

public class CommonSteps {
    public static HashMap<String, byte[]> screenshots = new HashMap<String, byte[]>();

    @When("^I open web-site$")
    public void iOpenWebSite() {
        open("https://xola01.atlassian.net/jira/software/projects/XMA/boards/46");
    }

    // PercyTool steps
    @When("^I launch Percy tool$")
    public void iLaunchPercyTool() {
        new PercyTool();
    }

    @When("^I take screenshot via Percy tool (.*)$")
    public void iTakeScreenshotViaPercyTool(String screenshotTag) {

        PercyTool.percy.snapshot(screenshotTag);
    }

    // EyesTool steps
    @When("^I launch Eyes tool$")
    public void iLaunchEyesTool(DataTable table) {
        List<List<String>> data = table.asLists(String.class);
        String testName = data.get(1).get(0);
        String appName = data.get(1).get(1);

        new EyesTool("batchName", appName, testName, 800, 600);
    }


    @When("^I take screenshot via Eyes tool (.*)$")
    public void iTakeScreenshotViaEyesTool(String screenshotTag) {

        EyesTool.eyes.checkWindow(screenshotTag);
    }

    @When("^I close Eyes$")
    public void iCloseEyes() {
        EyesTool.eyes.closeAsync();
    }

    // CustomVisualTestingTool steps
    @When("^I launch Custom Visual Testing tool$")
    public void iLaunchCustomVisualTestingTool(DataTable table) throws IOException {
        List<List<String>> data = table.asLists(String.class);
        String feature = data.get(1).get(0);
        String scenario = data.get(1).get(1);

        new CustomVisualTestingTool(feature, scenario);
    }

    @When("^I take screenshot via Custom Visual Testing tool (.*)$")
    public void iTakeScreenshotViaCustomVisualTestingTool(String screenshotTag) throws IOException, NoInitialContextException {
        CustomVisualTestingTool customVisualTestingTool = new CustomVisualTestingTool();

        boolean isScreenshotsEqual = customVisualTestingTool.takeScreenshotAndCompareImages(screenshotTag);
        if (!isScreenshotsEqual) {
            CommonSteps.screenshots.put("expected image", customVisualTestingTool.getExpectedImage());
            CommonSteps.screenshots.put("actual image", customVisualTestingTool.getActualImage());
            CommonSteps.screenshots.put("compared image", customVisualTestingTool.getComparedImage());
        }
        assertThat("Images are different. Please, see attaches", isScreenshotsEqual, equalTo(true));
    }
}
