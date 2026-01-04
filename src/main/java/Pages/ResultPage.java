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

public class ResultPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ResultPage(WebDriver driver) {
        this.driver=driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    }

    private final By headerOfResult = By.xpath("//h1[normalize-space()='headphones']");
    private final By emptySectionResult = By.xpath("//div[contains(@class,'EmptyState')]");

    private final By allProduct = By.xpath("//div[@data-qa='plp-product-box']");
    private final By productName = By.xpath("//h2[@data-qa='plp-product-box-name']");
    private final By productPrice = By.xpath("//strong[contains(@class,'Price')]");
    private final By item= By.xpath("//button[contains(@class,'QuickAtc')]");
    private final By headerTitle = By.xpath("//h1[normalize-space()='headphones']");
    private final By numberOfProductOnCart =
            By.xpath("//a[contains(@data-qa,'btn_cartLink-Header-Desktop')]//span[@data-qa='btn_cart_count']");
    private final By cart = By.xpath("//a[@data-qa='btn_cartLink-Header-Desktop']");

    public void verifyCorrectSearchResultAppears(String Target){
       String Title= wait.until(ExpectedConditions.visibilityOfElementLocated(headerTitle)).getText();
        Assert.assertTrue(Title.contains(Target),"your search target incorrect");
    }


    public void addThreeItemsToCartAndGetNameAndPriceOfEachOne()  {
        wait.until(ExpectedConditions.visibilityOfElementLocated(headerOfResult));
        wait.until(ExpectedConditions.visibilityOfElementLocated(allProduct));
        List<WebElement> products = driver.findElements(allProduct);

        for (int productNum=0;productNum<3;productNum++){


            String getProductName = driver.findElements(productName).get(productNum).getText();
            String getProductPrice = driver.findElements(productPrice).get(productNum).getText()
                    .replace(",", "").trim();
          float  price = Float.parseFloat(getProductPrice);

            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(item)));
            WebElement p =driver.findElements(item).get(productNum);
            p.click();
            driver.navigate().refresh();

            System.out.println("This item was added to the cart: \n"+getProductName+": "+price);

        }

    }
    public void verifyNumberOfProductOnCart(int num){
       String numberOfProducts= wait.until(
               ExpectedConditions.visibilityOfElementLocated(numberOfProductOnCart))
               .getText();
       int productCount=Integer.parseInt(numberOfProducts);
       Assert.assertEquals(productCount, num);
    }
    public List<String> getItemNameToVerify() {


        List<WebElement> products = driver.findElements(allProduct);
        List<String>elementPrice = products.stream().limit(3).map
                (p->p.findElement(productName).getText().trim()).collect(Collectors.toList());
        List<String>itemsName = elementPrice.stream().sorted().collect(Collectors.toList());


        return itemsName;
    }
    public List<String> getItemPriceToVerify() {

        // get all products
        List<WebElement> products = driver.findElements(allProduct);
        List<String>elementPrice = products.stream().limit(3).map
                (p->p.findElement(productPrice).getText().trim()).collect(Collectors.toList());
        List<String>itemsPrice = elementPrice.stream().sorted().collect(Collectors.toList());

        return itemsPrice ;
    }

    public boolean errorMessageAppears(){

       return driver.findElement(emptySectionResult).isDisplayed();
    }

    public CartPage openCart(){
        wait.until(ExpectedConditions.elementToBeClickable(cart)).click();

        return new CartPage(driver);
    }


}
