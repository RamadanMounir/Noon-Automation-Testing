package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CartPage {
    WebDriver driver;
    WebDriverWait wait;
    public CartPage(WebDriver driver) {
        this.driver=driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final By allProduct = By.xpath("//div[contains(@class,'CartItemDesktop_cardWrapper')]");
    private final By productName = By.xpath("//h1[@data-qa='cart-item-name']");
    private final By productPrice = By.xpath("//b[contains(@class,'CartItemDesktop_unitPrice')]");
    private final By cartPageHeader = By.xpath("//strong[@class='CartHeaderDesktop_title__uPT6U']");
    private final By checkoutBtn = By.xpath("//button[contains(@class,'Button_button')]");

    public void verifyCartPageOpenedCorrectly(){
        String cartHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(cartPageHeader)).getText();
        Assert.assertTrue(cartHeader.contains("Cart"),"You are not on cart page");
    }

    public List<String> getItemNameToVerify() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutBtn));

        List<String> itemsOnCart = new ArrayList<>();

        // get all products
        List<WebElement> products = driver.findElements(allProduct);

        // loop (here only for first 3 products, you can change to products.size())
        for (int productNum = 0; productNum < 3; productNum++) {

            String getProductName = driver.findElements(productName)
                    .get(productNum)
                    .getText();

            // store as {name}
            itemsOnCart.add(new String(getProductName));
        }

        return itemsOnCart;
    }
    public List<String> getItemPriceToVerify() {

        List<String> itemsOnCart = new ArrayList<>();

        // get all products
        List<WebElement> products = driver.findElements(allProduct);

        // loop (here only for first 3 products, you can change to products.size())
        for (int productNum = 0; productNum < 3; productNum++) {

            String getProductPrice = driver.findElements(productPrice)
                    .get(productNum)
                    .getText().trim();



            // store as {name, price}
            itemsOnCart.add(getProductPrice);
        }

        return itemsOnCart;
    }

}

