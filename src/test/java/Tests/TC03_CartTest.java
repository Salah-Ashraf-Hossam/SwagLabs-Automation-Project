package Tests;

import DriverFactory.DriverFactory;
import Listeners.IInvokeMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Pages.P03_CartPage;
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
public class TC03_CartTest {

    @BeforeMethod
    public void setup() throws IOException {
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : DataUtils.getPropertyValue("environment", "Browser");
        setupDriver(browser);
        LogsUtils.info("Chrome driver is opened");
        getDriver().get(DataUtils.getPropertyValue("environment", "LoginURL"));
        LogsUtils.info("LoginPage is opened");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void comparingPricesTC() {
        String totalPrice = new P01_LoginPage(getDriver())
                .enterUsername(DataUtils.getJsonData("validLoginData", "username"))
                .enterPassword(DataUtils.getJsonData("validLoginData", "password"))
                .clickOnLoginButton()
                .addRandomProducts(2, 6)
                .getTotalPriceOfSelectedProducts();
        new P02_LandingPage(getDriver()).clickONCartIcon();
        Assert.assertTrue(new P03_CartPage(getDriver())
                .comparingPrices(totalPrice));
    }


    @AfterMethod
    public void quit() {
        DriverFactory.quitDriver();
    }
}

