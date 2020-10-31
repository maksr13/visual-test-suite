package helpers.customVisualTestingTool;

import org.apache.commons.io.FileUtils;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class CustomVisualTestingTool {

    private String actualImagesFolderPath = System.getProperty("user.dir") + "/src/test/java/helpers/customVisualTestingTool/actualImages/";
    private String expectedImagesFolderPath = System.getProperty("user.dir") + "/src/test/java/helpers/customVisualTestingTool/expectedImages/";
    private String comparedImagesFolderPath = System.getProperty("user.dir") + "/src/test/java/helpers/customVisualTestingTool/comparedImages/";
    private String comparedGiffsFolderPath = System.getProperty("user.dir") + "/src/test/java/helpers/customVisualTestingTool/comparedGiffs/";

    private String actualImagePath;
    private String expectedImagePath;
    private String comparedImagePath;
    private String comparedGiffPath;

    public CustomVisualTestingTool(String featureName, String scenarioNumber, String screenshotNumber) throws IOException {
        if (Files.notExists(Paths.get(actualImagesFolderPath))) {
            Files.createDirectory(Paths.get(actualImagesFolderPath));
        }
        if (Files.notExists(Paths.get(expectedImagesFolderPath))) {
            Files.createDirectory(Paths.get(expectedImagesFolderPath));
        }
        if (Files.notExists(Paths.get(comparedImagesFolderPath))) {
            Files.createDirectory(Paths.get(comparedImagesFolderPath));
        }
        if (Files.notExists(Paths.get(comparedGiffsFolderPath))) {
            Files.createDirectory(Paths.get(comparedGiffsFolderPath));
        }
        actualImagePath = buildActualImagePath(featureName, scenarioNumber, screenshotNumber);
        expectedImagePath = buildExpectedImagePath(featureName, scenarioNumber, screenshotNumber);
        comparedImagePath = buildComparedImagePath(featureName, scenarioNumber, screenshotNumber);
        comparedGiffPath = buildComparedGiffPath(featureName, scenarioNumber, screenshotNumber);
    }

    private String buildActualImagePath(String featureName, String scenarioNumber, String screenshotNumber) throws IOException {
        String directoryForScenarioPath = createDirectoryForScenarioIfDoesntExist(featureName, scenarioNumber, actualImagesFolderPath);

        return directoryForScenarioPath + "/" + screenshotNumber + ".png";
    }

    private String buildExpectedImagePath(String featureName, String scenarioNumber, String screenshotNumber) throws IOException {
        String directoryForScenarioPath = createDirectoryForScenarioIfDoesntExist(featureName, scenarioNumber, expectedImagesFolderPath);

        return directoryForScenarioPath + "/" + screenshotNumber + ".png";
    }

    private String buildComparedImagePath(String featureName, String scenarioNumber, String screenshotNumber) throws IOException {
        String directoryForScenarioPath = createDirectoryForScenarioIfDoesntExist(featureName, scenarioNumber, comparedImagesFolderPath);

        return directoryForScenarioPath + "/" + screenshotNumber + ".png";
    }

    private String buildComparedGiffPath(String featureName, String scenarioNumber, String screenshotNumber) throws IOException {
        String directoryForScenarioPath = createDirectoryForScenarioIfDoesntExist(featureName, scenarioNumber, comparedGiffsFolderPath);

        return directoryForScenarioPath + "/" + screenshotNumber + ".gif";
    }

    private String createDirectoryForScenarioIfDoesntExist(String featureName, String scenarioNumber, String baseFolder) throws IOException {
        String directoryForFeaturePath = baseFolder + "/" + featureName;
        if (Files.notExists(Paths.get(directoryForFeaturePath))) {
            Files.createDirectory(Paths.get(directoryForFeaturePath));
        }
        String directoryForScenarioPath = directoryForFeaturePath + "/" + scenarioNumber;
        if (Files.notExists(Paths.get(directoryForScenarioPath))) {
            Files.createDirectory(Paths.get(directoryForScenarioPath));
        }
        return directoryForScenarioPath;
    }

    private boolean takeScreenshot(String imagePath) {
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(getWebDriver());
        File screenshotFile = new File(imagePath);
        try {
            ImageIO.write(screenshot.getImage(), "png", screenshotFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean compareImages(String actualImagePath, String expectedImagePath, String comparedImagePath, String comparedGiffPath) throws IOException {
        Screenshot expected = new Screenshot(ImageIO.read(new File(expectedImagePath)));
        Screenshot actual = new Screenshot(ImageIO.read(new File(actualImagePath)));

        ImageDiff diffObject = new ImageDiffer().makeDiff(expected, actual);

        if (diffObject.getDiffSize() > 10) {
            File diffFile = new File(comparedImagePath);

            try {
                ImageIO.write(diffObject.getMarkedImage(), "png", diffFile);
                GiffCreater.createComparedGif(actualImagePath, expectedImagePath, comparedImagePath, comparedGiffPath);
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return true;
        }
    }

    public Boolean takeScreenshotAndCompareImages() throws IOException {
        if (!Files.isRegularFile(Paths.get(expectedImagePath))) {
            takeScreenshot(expectedImagePath);

            return true;
        } else {
            takeScreenshot(actualImagePath);

            return compareImages(actualImagePath, expectedImagePath, comparedImagePath, comparedGiffPath);
        }
    }

    public void removeExpectedImages() {
        File directory = new File(expectedImagesFolderPath);
        try {
            FileUtils.cleanDirectory(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeActualImages() {
        File directory = new File(actualImagesFolderPath);
        try {
            FileUtils.cleanDirectory(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeComparedImages() {
        File directory = new File(comparedImagesFolderPath);
        try {
            FileUtils.cleanDirectory(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
