package Pages;

import Utilites.LogsUtils;
import Utilites.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class P03_CartPage {

    static float totalPrice = 0;
    private final WebDriver driver;
    private final By pricesOfSelectedProductsLocator = By.xpath("//Button[.=\"Remove\"]//preceding-sibling::div[@class=\"inventory_item_price\"]");
    private final By checkoutButton = By.id("checkout");


    public P03_CartPage(WebDriver driver) {
        this.driver = driver;
    }


    public String getTotalPrice() {
        try {
            List<WebElement> pricesOfSelectedProducts = driver.findElements(pricesOfSelectedProductsLocator);
            for (int i = 1; i <= pricesOfSelectedProducts.size(); i++) {
                By elements = By.xpath("(//Button[.=\"Remove\"]//preceding-sibling::div[@class=\"inventory_item_price\"])[" + i + "]");
                String fullPriceText = Utility.getText(driver, elements);
                totalPrice += Float.parseFloat(fullPriceText.replace("$", ""));
            }
            LogsUtils.info("Total Price = " + totalPrice);
            return String.valueOf(totalPrice);

        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }

    }

    public boolean comparingPrices(String price) {

        return getTotalPrice().equals(price);
    }

    public P04_CheckoutPage clickOnCheckoutButton() {
        Utility.clickingOnElement(driver, checkoutButton);
        return new P04_CheckoutPage(driver);
    }
}
