package helpers;

import io.percy.selenium.Percy;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class PercyTool {
    public static Percy percy;


    public PercyTool() {
        percy = new Percy(getWebDriver());
    }
}
