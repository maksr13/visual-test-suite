package tests.pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class GooglePage {
    private By googleLogo = By.id("hplogo");
    private By googleOfferedInTextLocator = By.cssSelector("div#SIvCob");

    public void clickOnGoogleLogo() {
        $(googleLogo).click();
    }

    public void clickOnGoogleOfferInText() {
        $(googleOfferedInTextLocator).click();
    }
}
