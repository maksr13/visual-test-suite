package tests.steps;

import helpers.customVisualTestingTool.CustomVisualTestingTool;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;

import javax.naming.NoInitialContextException;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CustomVisualTestingToolSteps {

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
