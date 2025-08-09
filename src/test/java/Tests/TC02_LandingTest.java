package Tests;

import DriverFactory.DriverFactory;
import Listeners.IInvokeMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Utilites.DataUtils;
import Utilites.LogsUtils;
import Utilites.Utility;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import static DriverFactory.DriverFactory.*;

@Listeners({IInvokeMethodListenerClass.class, ITestResultListenerClass.class})
public class TC02_LandingTest {

    private Set<Cookie> cookies;

    @BeforeClass
    public void login() throws IOException {
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : DataUtils.getPropertyValue("environment", "Browser");
        setupDriver(browser);
        LogsUtils.info("edge driver is opened");
        getDriver().get(DataUtils.getPropertyValue("environment", "LoginURL"));
        LogsUtils.info("LoginPage is opened");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        new P01_LoginPage(getDriver())
                .enterUsername(DataUtils.getJsonData("validLoginData", "username"))
                .enterPassword(DataUtils.getJsonData("validLoginData", "password"))
                .clickOnLoginButton();
        cookies = Utility.getAllCookies(getDriver());
        quitDriver();

    }

    @BeforeMethod
    public void setup() throws IOException {
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : DataUtils.getPropertyValue("environment", "Browser");
        setupDriver(browser);
        LogsUtils.info("edge driver is opened");
        getDriver().get(DataUtils.getPropertyValue("environment", "LoginURL"));
        LogsUtils.info("LoginPage is opened");
        Utility.restoreSession(getDriver(), cookies);
        getDriver().get(DataUtils.getPropertyValue("environment", "ProductsURL"));
        getDriver().navigate().refresh();
    }

    @Test
    public void checkingNumberOfSelectedProductsTC() {

        new P02_LandingPage(getDriver()).addAllProductsToCart();
        Assert.assertTrue(new P02_LandingPage(getDriver())
                .comparingNumberOfSelectedProductsWithCart());
    }

    @Test
    public void addingRandomProductsToCartTC() {
        new P02_LandingPage(getDriver()).addRandomProducts(3, 6);
        Assert.assertTrue(new P02_LandingPage(getDriver())
                .comparingNumberOfSelectedProductsWithCart());
    }

    @Test
    public void clickOnCartIconURLTC() throws IOException {
        new P02_LandingPage(getDriver()).clickONCartIcon();
        Assert.assertTrue(Utility.verifyURL(getDriver(), (DataUtils.getPropertyValue("environment", "cartURL"))));
    }


    @AfterMethod
    public void quit() {
        DriverFactory.quitDriver();
    }

    @AfterClass
    public void deleteSession() {
        cookies.clear();
    }
}
