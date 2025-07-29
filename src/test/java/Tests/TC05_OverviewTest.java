package Tests;

import DriverFactory.DriverFactory;
import Listeners.IInvokeMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Pages.P05_OverviewPage;
import Utilites.DataUtils;
import Utilites.LogsUtils;
import Utilites.Utility;
import com.github.javafaker.Faker;
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
public class TC05_OverviewTest {

    private final String firstName = DataUtils.getJsonData("checkoutData", "firstname") + "-" + Utility.getTimestamp();
    private final String lastName = DataUtils.getJsonData("checkoutData", "lastname") + "-" + Utility.getTimestamp();
    private final String postalCode = new Faker().number().digits(5);

    @BeforeMethod
    public void setup() throws IOException {
        setupDriver(DataUtils.getPropertyValue("environment", "Browser"));
        LogsUtils.info("Chrome driver is opened");
        getDriver().get(DataUtils.getPropertyValue("environment", "LoginURL"));
        LogsUtils.info("LoginPage is opened");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void checkoutStepTwoTC() throws IOException {
        new P01_LoginPage(getDriver())
                .enterUsername(DataUtils.getJsonData("validLoginData", "username"))
                .enterPassword(DataUtils.getJsonData("validLoginData", "password"))
                .clickOnLoginButton()
                .addAllProductsToCart()
                .clickONCartIcon()
                .clickOnCheckoutButton()
                .fillData(firstName, lastName, postalCode)
                .clickOnContinueButton();
        LogsUtils.info(firstName + " " + lastName + " " + postalCode);
        Assert.assertTrue(new P05_OverviewPage(getDriver()).compareTotalPrice());
    }


    @AfterMethod
    public void quit() {
        DriverFactory.quitDriver();
    }
}
