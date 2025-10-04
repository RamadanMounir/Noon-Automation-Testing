package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;
import java.util.List;

public class SamsungPage {
    WebDriver driver;
    WebDriverWait wait;

    public SamsungPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final By samsungBreadcrumb = By.xpath("//span[@class='Breadcrumb_active__nQtO3']");
    private final By priceFilterButton = By.xpath("//h3[normalize-space()='Price']");
    private final By minPriceField = By.xpath("//input[@name='min']");
    private final By maxPriceField = By.xpath("//input[@name='max']");
    private final By submitButton = By.xpath("//button[@type='submit']");
    private final By searchHeader=By.xpath("//h1[normalize-space()='Samsung Electronics & Mobiles']");
    private final By allProduct = By.xpath("//div[@data-qa='plp-product-box']");
    private final By productName = By.xpath("//h2[@data-qa='plp-product-box-name']");
    private final By productPrice = By.xpath("//strong[contains(@class,'Price_amount')]");
    private final By nextPageBtn = By.xpath("//a[@aria-label='Next page']");
    private final By pages = By.xpath("//ul[@role='navigation']/child::li");
   private final By priceFilterHeader = By.xpath("//span[normalize-space()='Price (EGP)']");

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

    public int getPageNumber() {
        List<WebElement> pagesNumber = driver.findElements(pages);
        return pagesNumber.size();
    }


    public void verifyThatReturnedProductsCorrectAndInPriceRange(String brand_Name,
                                                                 double minPrice, double maxPrice) throws InterruptedException {

        wait.until(ExpectedConditions.visibilityOfElementLocated(searchHeader));
        boolean hasNextPage = true;
        WebElement nextBtn = driver.findElement(nextPageBtn);
        String disabled = nextBtn.getAttribute("aria-disabled");

        while ("false".equals(disabled)) {


            // Get products for the current page
            List<WebElement> products = driver.findElements(allProduct);
            int numberOfProducts = products.size();
            System.out.println("Products found: " + numberOfProducts);

            for (int product = 1; product < numberOfProducts; product++) {
                String getProductName = driver.findElements(productName).get(product).getText();
                String getProductPrice = driver.findElements(productPrice).get(product).getText()
                        .replace(",", "").trim();
                double finalPrice = Double.parseDouble(getProductPrice);
                System.out.println(getProductName+":  "+finalPrice);

                Assert.assertTrue(getProductName.contains(brand_Name), "Wrong product brand");
                Assert.assertTrue((finalPrice >= minPrice && finalPrice <= maxPrice)
                        , "this price out of the range" + getProductName + ": " + getProductPrice);

            }
            if ("true".equals(disabled)) {
                hasNextPage = false;// Stop the loop if no next page
                System.out.println("last page");
            }
            else {
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(searchHeader)).getText();
                    String currentUrl = driver.getCurrentUrl();
                    driver.findElement(nextPageBtn).click();

                    wait.until(ExpectedConditions.not(
                            ExpectedConditions.urlToBe(currentUrl)
                    ));
                   // Thread.sleep(1000);


                    wait.until(ExpectedConditions.visibilityOfElementLocated(allProduct)).isDisplayed();
                }
                catch (ElementClickInterceptedException e){
                    System.out.println("##############################################################");
                    System.out.println("you are on last page");
                    break;
                }
                // Wait for the page to load and for products to appear again

            }



        }

    }
}







