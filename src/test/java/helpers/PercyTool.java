package helpers;

import io.percy.selenium.Percy;

import java.util.List;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class PercyTool {
    Percy percy;


    public PercyTool() {
        percy = new Percy(getWebDriver());
    }

    public void snapshot(String name) {
        percy.snapshot(name);
    }

    public void snapshot(String name, List<Integer> widths) {
        percy.snapshot(name, widths);
    }

    public void snapshot(String name, List<Integer> widths, Integer minHeight) {
        percy.snapshot(name, widths, minHeight);
    }

    public void snapshot(String name, List<Integer> widths, Integer minHeight, boolean enableJavaScript) {
        percy.snapshot(name, widths, minHeight, enableJavaScript);
    }
}
