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

    private static String actualImagesFolderPath = System.getProperty("user.dir") + "/src/test/java/helpers/customVisualTestingTool/actualImages/";
    private static String expectedImagesFolderPath = System.getProperty("user.dir") + "/src/test/java/helpers/customVisualTestingTool/expectedImages/";
    private static String comparedImagesFolderPath = System.getProperty("user.dir") + "/src/test/java/helpers/customVisualTestingTool/comparedImages/";
    private static String comparedGiffsFolderPath = System.getProperty("user.dir") + "/src/test/java/helpers/customVisualTestingTool/comparedGiffs/";

    private static String createDirectoryForScenarioIfDoesntExist(String featureName, String scenarioNumber, String baseFolder) throws IOException {
        String featureFolderPath = baseFolder + "/" + featureName;
        if (Files.notExists(Paths.get(featureFolderPath))) {
            Files.createDirectory(Paths.get(featureFolderPath));
        }
        String scenarioFolderPath = featureFolderPath + "/" + scenarioNumber;
        if (Files.notExists(Paths.get(scenarioFolderPath))) {
            Files.createDirectory(Paths.get(scenarioFolderPath));
        }
        return scenarioFolderPath;
    }

    public static String buildActualImagePath(String featureName, String scenarioNumber, String screenshotNumber) throws IOException {
        String directoryForScenario = createDirectoryForScenarioIfDoesntExist(featureName, scenarioNumber, actualImagesFolderPath);

        return directoryForScenario + "/" + screenshotNumber + ".png";
    }

    public static String buildExpectedImagePath(String featureName, String scenarioNumber, String screenshotNumber) throws IOException {
        String directoryForScenario = createDirectoryForScenarioIfDoesntExist(featureName, scenarioNumber, expectedImagesFolderPath);

        return directoryForScenario + "/" + screenshotNumber + ".png";
    }

    public static String buildComparedImagePath(String featureName, String scenarioNumber, String screenshotNumber) throws IOException {
        String directoryForScenario = createDirectoryForScenarioIfDoesntExist(featureName, scenarioNumber, comparedImagesFolderPath);

        return directoryForScenario + "/" + screenshotNumber + ".png";
    }

    public static String buildComparedGiffsPath(String featureName, String scenarioNumber, String screenshotNumber) throws IOException {
        String directoryForScenario = createDirectoryForScenarioIfDoesntExist(featureName, scenarioNumber, comparedGiffsFolderPath);

        return directoryForScenario + "/" + screenshotNumber + ".png";
    }

    private static boolean takeScreenshot(String imagePath) {
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

    private static boolean compareImages(String actualImagePath, String expectedImagePath, String comparedImagePath, String comparedGiffPath) throws IOException {
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

    public static Boolean takeScreenshotAndCompareImages(String actualImagePath, String expectedImagePath, String comparedImagePath, String comparedGiffPath) throws IOException {
        if (!Files.isRegularFile(Paths.get(expectedImagePath))) {
            takeScreenshot(expectedImagePath);

            return true;
        } else {
            takeScreenshot(actualImagePath);

            return compareImages(actualImagePath, expectedImagePath, comparedImagePath, comparedGiffPath);
        }
    }

    public static void removeExpectedImages() {
        File directory = new File(expectedImagesFolderPath);
        try {
            FileUtils.cleanDirectory(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeActualImages() {
        File directory = new File(actualImagesFolderPath);
        try {
            FileUtils.cleanDirectory(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeComparedImages() {
        File directory = new File(comparedImagesFolderPath);
        try {
            FileUtils.cleanDirectory(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
