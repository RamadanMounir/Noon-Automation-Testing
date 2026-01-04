package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class SamsungPage {
    WebDriver driver;
    WebDriverWait wait;

    public SamsungPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final By samsungBreadcrumb = By.xpath("//span[contains(@class,'Breadcrumb') and contains(@class,'active')]");
    private final By priceFilterButton = By.xpath("//h3[normalize-space()='Price']");
    private final By minPriceField = By.xpath("//input[@name='min']");
    private final By maxPriceField = By.xpath("//input[@name='max']");
    private final By submitButton = By.xpath("//button[@type='submit']");
    private final By allProduct = By.xpath("//div[@data-qa='plp-product-box']");
    private final By productName = By.xpath("//h2[@data-qa='plp-product-box-name']");
    private final By productPrice = By.xpath("//strong[contains(@class,'amount')]");
    private final By nextPageBtn = By.xpath("//a[@aria-label='Next page']");


    public String getSamsungPageTitle() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(samsungBreadcrumb));
        return driver.getTitle();

    }

    public void filterPrice(int minPrice, int maxPrice) {
        driver.findElement(priceFilterButton).click();
        driver.findElement(minPriceField).clear();
        driver.findElement(minPriceField).sendKeys((String.valueOf(minPrice)));
        driver.findElement(maxPriceField).clear();
        driver.findElement(maxPriceField).sendKeys((String.valueOf(maxPrice)));
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        String currentUrl = driver.getCurrentUrl();

        wait.until(ExpectedConditions.not(
                ExpectedConditions.urlToBe(currentUrl)
        ));


    }

    public void verifyThatReturnedProductsCorrectAndInPriceRange(String brand_Name,
                                                                 double minPrice, double maxPrice) {

        String nextPage;
        List<String>itemsName;
        List<String>itemsPrice;

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        do {
               nextPage = driver.findElement(nextPageBtn).getAttribute("aria-disabled");
            List<WebElement> allItems = driver.findElements(allProduct);
            itemsName =  allItems.stream().map(i->i.findElement(productName)
                      .getText()).collect(Collectors.toList());

            Assert.assertTrue(itemsName.stream().allMatch(i->i.toLowerCase().contains(brand_Name.toLowerCase()))
                    ,"Not all items contain Samsung");

            itemsPrice =  allItems.stream().map(i->i.findElement(productPrice)
                    .getText().replace(",","").trim()).collect(Collectors.toList());
            List<Integer> finalPrice = itemsPrice.stream().map(p -> Integer.parseInt(
                    p.replaceAll("[^\\d.]", "")))
                    .collect(Collectors.toList());

            Assert.assertTrue(finalPrice.stream().allMatch(p->p>=minPrice&&p<=maxPrice)
                    ,"price of products out of the range");

              if(nextPage.equals("false")) {
                  driver.findElement(nextPageBtn).click();
                  wait.until(ExpectedConditions.stalenessOf(allItems.get(0)));
              }
        } while (nextPage.equals("false"));


    }

}









