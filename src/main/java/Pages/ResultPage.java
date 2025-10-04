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

public class ResultPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ResultPage(WebDriver driver) {
        this.driver=driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    }

    private final By headerOfResult = By.xpath("//h1[normalize-space()='headphones']");
    private final By emptySectionResult = By.xpath("//div[contains(@class,'EmptyState_container')]");

    private final By allProduct = By.xpath("//div[@data-qa='plp-product-box']");
    private final By productName = By.xpath("//h2[@data-qa='plp-product-box-name']");
    private final By productPrice = By.xpath("//strong[contains(@class,'Price_amount')]");
    private final By item= By.xpath("//button[contains(@class,'QuickAtc')]");
    private final By headerTitle = By.xpath("//h1[normalize-space()='headphones']");
    private final By numberOfProductOnCart =
            By.xpath("//a[@data-qa='btn_cartLink-Header-Desktop']//span[@class='CartLinkBadge_counter__LiGCD']");
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


        List<String> items = new ArrayList<>();

        // get all products
        List<WebElement> products = driver.findElements(allProduct);

        // loop (here only first 3 products, you can change to products.size())
        for (int productNum = 0; productNum < 3; productNum++) {

            String getProductName = driver.findElements(productName)
                    .get(productNum)
                    .getText();
            // store as {name}
            items.add(new String(getProductName));
        }

        return items;
    }
    public List<String> getItemPriceToVerify() {

        List<String> items = new ArrayList<>();

        // get all products
        List<WebElement> products = driver.findElements(allProduct);

        // loop (here only first 3 products, you can change to products.size())
        for (int productNum = 0; productNum < 3; productNum++) {


            String getProductPrice = driver.findElements(productPrice)
                    .get(productNum)
                    .getText().trim();

            // store as price
            items.add(getProductPrice);
        }

        return items;
    }

    public boolean errorMessageAppears(){

       return driver.findElement(emptySectionResult).isDisplayed();
    }

    public CartPage openCart(){
        wait.until(ExpectedConditions.elementToBeClickable(cart)).click();

        return new CartPage(driver);
    }


}
