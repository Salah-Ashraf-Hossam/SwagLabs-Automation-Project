package Pages;

import Utilites.LogsUtils;
import Utilites.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P05_OverviewPage {


    private final WebDriver driver;
    private final By subTotalPrice = By.className("summary_subtotal_label");
    private final By tax = By.className("summary_tax_label");
    private final By totalPrice = By.className("summary_total_label");
    private final By finishButton = By.id("finish");

    public P05_OverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    public float getSubTotalPrice() {
        return Float.parseFloat(Utility.getText(driver, subTotalPrice).replace("Item total: $", " "));
    }

    public float getTax() {
        return Float.parseFloat(Utility.getText(driver, tax).replace("Tax: $", " "));
    }

    public float getTotalPrice() {
        LogsUtils.info("Actual Total Pricce = " + Utility.getText(driver, totalPrice).replace("Total: $", " "));
        return Float.parseFloat(Utility.getText(driver, totalPrice).replace("Total: $", " "));
    }

    public String calculateTotalPrice() {
        LogsUtils.info("Calculated Total Pricce = " + (getSubTotalPrice() + getTax()));
        return String.valueOf(getSubTotalPrice() + getTax());
    }

    public boolean compareTotalPrice() {
        return calculateTotalPrice().equals(String.valueOf(getTotalPrice()));
    }

    public P06_FinishingOrderPage clickOnFinishButton() {
        Utility.clickingOnElement(driver, finishButton);
        return new P06_FinishingOrderPage(driver);
    }
}
