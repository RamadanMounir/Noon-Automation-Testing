package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.time.Duration;

public class HomePage {
    WebDriver driver;
    Actions actions;
    WebDriverWait wait;

    public HomePage(WebDriver driver)
    {
    this.driver=driver;
    actions=new Actions(driver);
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By Electronics = By.xpath("//li[@data-qa='btn_main_menu_Electronics']");
    private By SamsungSection = By.xpath("//span[normalize-space()='Samsung']");
    private By searchField = By.id("search-input");


    public void hoverOnElectronics(){
        actions.moveToElement(driver.findElement(Electronics))
                .build()
                .perform();
    }
    public SamsungPage openSamsungPage(){
        actions.moveToElement(driver.findElement(SamsungSection))
                .click()
                .build()
                .perform();
        return new SamsungPage(driver);
    }

    public ResultPage searchAbout(String targetProduct){
        String currentUrl = driver.getCurrentUrl();
        driver.findElement(searchField).clear();
        driver.findElement(searchField).sendKeys(targetProduct+ Keys.ENTER);

        wait.until(ExpectedConditions.not(
                ExpectedConditions.urlToBe(currentUrl)
        ));
        return new ResultPage(driver);

    }







}
