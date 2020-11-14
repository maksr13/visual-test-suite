package helpers.customVisualTestingTool;

import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.*;
import javax.naming.NoInitialContextException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class CustomVisualTestingTool {

    private static String actualImagesBaseFolderPath = System.getProperty("user.dir") + "/src/test/java/helpers/customVisualTestingTool/actualImages/";
    private static String expectedImagesBaseFolderPath = System.getProperty("user.dir") + "/src/test/java/helpers/customVisualTestingTool/expectedImages/";
    private static String comparedImagesBaseFolderPath = System.getProperty("user.dir") + "/src/test/java/helpers/customVisualTestingTool/comparedImages/";
    private static String comparedGiffsBaseFolderPath = System.getProperty("user.dir") + "/src/test/java/helpers/customVisualTestingTool/comparedGiffs/";

    private static String actualImagesFolderForScenarioPath;
    private static String expectedImagesFolderForScenarioPath;
    private static String comparedImagesFolderForScenarioPath;
    private static String comparedGiffsFolderForScenarioPath;

    String actualImagePath;
    String expectedImagePath;
    String comparedImagePath;
    String comparedGiffPath;

    public CustomVisualTestingTool(String featureName, String scenarioNumber) throws IOException {
        if (Files.notExists(Paths.get(actualImagesBaseFolderPath))) {
            Files.createDirectory(Paths.get(actualImagesBaseFolderPath));
        }
        if (Files.notExists(Paths.get(expectedImagesBaseFolderPath))) {
            Files.createDirectory(Paths.get(expectedImagesBaseFolderPath));
        }
        if (Files.notExists(Paths.get(comparedImagesBaseFolderPath))) {
            Files.createDirectory(Paths.get(comparedImagesBaseFolderPath));
        }
        if (Files.notExists(Paths.get(comparedGiffsBaseFolderPath))) {
            Files.createDirectory(Paths.get(comparedGiffsBaseFolderPath));
        }

        actualImagesFolderForScenarioPath = createDirectoryForScenarioIfDoesntExist(featureName, scenarioNumber, actualImagesBaseFolderPath);
        expectedImagesFolderForScenarioPath = createDirectoryForScenarioIfDoesntExist(featureName, scenarioNumber, expectedImagesBaseFolderPath);
        comparedImagesFolderForScenarioPath = createDirectoryForScenarioIfDoesntExist(featureName, scenarioNumber, comparedImagesBaseFolderPath);
        comparedGiffsFolderForScenarioPath = createDirectoryForScenarioIfDoesntExist(featureName, scenarioNumber, comparedGiffsBaseFolderPath);
    }

    public CustomVisualTestingTool() throws NoInitialContextException {
        if (StringUtils.isEmpty(actualImagesFolderForScenarioPath) || StringUtils.isEmpty(expectedImagesFolderForScenarioPath) || StringUtils.isEmpty(comparedImagesFolderForScenarioPath) || StringUtils.isEmpty(comparedGiffsFolderForScenarioPath)) {
            throw new NoInitialContextException("Visual Testing tool is not initialized. Please, launch CustomVisualTestingTool(String featureName, String scenarioNumber) constructor firstly");
        }
    }

    private static String buildActualImagePath(String screenshotNumber) throws IOException {
        return actualImagesFolderForScenarioPath + "/" + screenshotNumber + ".png";
    }

    private static String buildExpectedImagePath(String screenshotNumber) throws IOException {
        return expectedImagesFolderForScenarioPath + "/" + screenshotNumber + ".png";
    }

    private static String buildComparedImagePath(String screenshotNumber) throws IOException {
        return comparedImagesFolderForScenarioPath + "/" + screenshotNumber + ".png";
    }

    private static String buildComparedGiffPath(String screenshotNumber) throws IOException {
        return comparedGiffsFolderForScenarioPath + "/" + screenshotNumber + ".gif";
    }

    private static String createDirectoryForScenarioIfDoesntExist(String featureName, String scenarioNumber, String baseFolder) throws IOException {
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

    public Boolean takeScreenshotAndCompareImages(String screenshotTag) throws IOException {
        actualImagePath = buildActualImagePath(screenshotTag);
        expectedImagePath = buildExpectedImagePath(screenshotTag);
        comparedImagePath = buildComparedImagePath(screenshotTag);
        comparedGiffPath = buildComparedGiffPath(screenshotTag);

        if (!Files.isRegularFile(Paths.get(expectedImagePath))) {
            takeScreenshot(expectedImagePath);

            return true;
        } else {
            takeScreenshot(actualImagePath);

            return compareImages(actualImagePath, expectedImagePath, comparedImagePath, comparedGiffPath);
        }
    }

    public byte[] getActualImage() throws IOException {
        return FileUtils.readFileToByteArray(new File(actualImagePath));
    }

    public byte[] getExpectedImage() throws IOException {
        return FileUtils.readFileToByteArray(new File(expectedImagePath));
    }

    public byte[] getComparedImage() throws IOException {
        return FileUtils.readFileToByteArray(new File(comparedImagePath));
    }

    public byte[] getComparedGiff() throws IOException {
        return FileUtils.readFileToByteArray(new File(comparedImagePath));
    }

    public void removeExpectedImages() {
        File directory = new File(expectedImagesBaseFolderPath);
        try {
            FileUtils.cleanDirectory(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeActualImages() {
        File directory = new File(actualImagesBaseFolderPath);
        try {
            FileUtils.cleanDirectory(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeComparedImages() {
        File directory = new File(comparedImagesBaseFolderPath);
        try {
            FileUtils.cleanDirectory(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
