package tests.steps;

import io.cucumber.java.en.When;

import java.util.HashMap;

import static com.codeborne.selenide.Selenide.open;

import tests.pages.GooglePage;

public class CommonSteps {
    public static HashMap<String, byte[]> screenshots = new HashMap<String, byte[]>();

    @When("^I open web-site (.*)$")
    public void iOpenWebSite(String url) {
        open(url);
    }

    @When("^I click on some text$")
    public void iClickOnGoogleLogo() {
        GooglePage googlePage = new GooglePage();
        googlePage.clickOnGoogleOfferInText();
    }
}
