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
import java.util.stream.Collectors;

public class CartPage {
    WebDriver driver;
    WebDriverWait wait;
    public CartPage(WebDriver driver) {
        this.driver=driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final By allProduct = By.xpath("//div[contains(@class,'CartItemDesktop_cardWrapper')]");
    private final By productName = By.xpath("//h1[@data-qa='cart-item-name']");
    private final By productPrice = By.xpath("//div[contains(@class,'unitPrice')]");
    private final By cartPageHeader = By.xpath("//strong[contains(@class,'CartHeaderDesktop')]");
    private final By checkoutBtn = By.xpath("//button[@type='button']/span[contains(@class,'Button-module')]");

    public void verifyCartPageOpenedCorrectly(){
        String cartHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(cartPageHeader)).getText();
        Assert.assertTrue(cartHeader.contains("Cart"),"You are not on cart page");
    }

    public List<String> getItemNameToVerify() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutBtn));

        // get all products
        List<WebElement> products = driver.findElements(allProduct).stream().limit(3).collect(Collectors.toList());
       List<String>productNames = products.stream().map
               (p->p.findElement(productName).getText()).collect(Collectors.toList());
      List<String>itemsOnCart= productNames.stream().sorted().collect(Collectors.toList());

        return itemsOnCart;
    }


    public List<String> getItemPriceToVerify() {

        List<String> itemsOnCart = new ArrayList<>();

        // get all products
        List<WebElement> products = driver.findElements(allProduct).stream().limit(3).collect(Collectors.toList());
       List<String>price= products.stream().map
                (p->p.findElement(productPrice).getText().trim()).collect(Collectors.toList());
       List<String>sortedPrice = price.stream().sorted().collect(Collectors.toList());

        return itemsOnCart;
    }

}

