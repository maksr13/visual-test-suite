package helpers;

import io.percy.selenium.Percy;

import java.util.List;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class PercyTool {
    Percy percy;


    public PercyTool() {
        percy = new Percy(getWebDriver());
    }
}
