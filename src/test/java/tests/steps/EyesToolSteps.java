package tests.steps;

import helpers.EyesTool;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;

import java.util.List;

public class EyesToolSteps {

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
}
