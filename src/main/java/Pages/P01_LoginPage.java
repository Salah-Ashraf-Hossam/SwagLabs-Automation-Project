package Pages;

import Utilites.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P01_LoginPage {

    public final WebDriver driver;
    private final By userName = By.id("user-name");
    private final By password = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By loginErrorMsgContainer = By.className("error-message-container");

    public P01_LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public P01_LoginPage enterUsername(String usernameText) {
        Utility.sendData(driver, userName, usernameText);
        return this;
    }

    public P01_LoginPage enterPassword(String passwordText) {
        Utility.sendData(driver, password, passwordText);
        return this;
    }

    public P02_LandingPage clickOnLoginButton() {
        Utility.clickingOnElement(driver, loginButton);
        return new P02_LandingPage(driver);
    }

    public String getLoginErrorMsg() {
        return Utility.getText(driver, loginErrorMsgContainer);
    }

    public boolean assertLoginTC(String expectedValue) {
        return driver.getCurrentUrl().equals(expectedValue);
    }

    public boolean assertLoginErrorMsg(String expectedErrorMsg) {
        return getLoginErrorMsg().equals(expectedErrorMsg);
    }
}
