package Tests;

import DriverFactory.DriverFactory;
import Listeners.IInvokeMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Utilites.DataUtils;
import Utilites.LogsUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.getDriver;
import static DriverFactory.DriverFactory.setupDriver;

@Listeners({IInvokeMethodListenerClass.class, ITestResultListenerClass.class})
public class TC01_LoginTest {

    @BeforeMethod
    public void setup() throws IOException {
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : DataUtils.getPropertyValue("environment", "Browser");
        setupDriver(browser);
        LogsUtils.info("edge driver is opened");
        getDriver().get(DataUtils.getPropertyValue("environment", "LoginURL"));
        LogsUtils.info("LoginPage is opened");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    @Test
    public void validLoginTC() throws IOException {
        new P01_LoginPage(getDriver())
                .enterUsername(DataUtils.getJsonData("validLoginData", "username"))
                .enterPassword(DataUtils.getJsonData("validLoginData", "password"))
                .clickOnLoginButton();
        Assert.assertTrue(new P01_LoginPage(getDriver())
                .assertLoginTC(DataUtils.getPropertyValue("environment", "ProductsURL")));
    }

    @Test
    public void blockedLoginTC() throws IOException {
        new P01_LoginPage(getDriver())
                .enterUsername(DataUtils.getJsonData("blockedLoginData", "blockedUsername"))
                .enterPassword(DataUtils.getJsonData("blockedLoginData", "blockedPassword"))
                .clickOnLoginButton();
        Assert.assertTrue(new P01_LoginPage(getDriver())
                .assertLoginErrorMsg(DataUtils.getJsonData("blockedLoginData", "blockedLoginMsg")));
    }

    @Test
    public void invalidLoginTC() throws IOException {
        new P01_LoginPage(getDriver())
                .enterUsername(DataUtils.getJsonData("invalidLoginData", "invalidUsername"))
                .enterPassword(DataUtils.getJsonData("invalidLoginData", "invalidPassword"))
                .clickOnLoginButton();
        Assert.assertTrue(new P01_LoginPage(getDriver())
                .assertLoginErrorMsg(DataUtils.getJsonData("invalidLoginData", "invalidLoginMsg")));
    }

    @Test
    public void emptyLoginTC() throws IOException {
        new P01_LoginPage(getDriver())
                .clickOnLoginButton();
        Assert.assertTrue(new P01_LoginPage(getDriver())
                .assertLoginErrorMsg(DataUtils.getJsonData("invalidLoginData", "emptyLoginMsg")));
    }


    @AfterMethod
    public void quit() {
        DriverFactory.quitDriver();
    }
}
