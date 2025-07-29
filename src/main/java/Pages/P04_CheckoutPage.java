package Pages;

import Utilites.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P04_CheckoutPage {


    private final WebDriver driver;
    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By postalCode = By.id("postal-code");
    private final By continueButton = By.id("continue");

    public P04_CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public P04_CheckoutPage fillData(String firstname, String lastname, String postalcode) {
        Utility.sendData(driver, firstName, firstname);
        Utility.sendData(driver, lastName, lastname);
        Utility.sendData(driver, postalCode, postalcode);
        return this;
    }

    public P05_OverviewPage clickOnContinueButton() {
        Utility.clickingOnElement(driver, continueButton);
        return new P05_OverviewPage(driver);
    }


}
