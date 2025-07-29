package Pages;

import Utilites.LogsUtils;
import Utilites.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P06_FinishingOrderPage {

    private final By completeMessage = By.className("complete-header");
    private final WebDriver driver;

    public P06_FinishingOrderPage(WebDriver driver) {

        this.driver = driver;
    }

    public boolean checkCompleteMessageVisibility() {
        LogsUtils.info("finish message : " + Utility.getText(driver, completeMessage));
        return Utility.findWebElement(driver, completeMessage).isDisplayed();
    }
}
