package BaseTest;


import Pages.HomePage;
import io.qameta.allure.Allure;
import org.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.CSVFileManager;
import java.io.ByteArrayInputStream;
import java.time.Duration;
import utilities.JsonReader;

public class testBase {
 WebDriver driver;
 public HomePage homePage;

    protected JSONObject testData,configData;
    protected CSVFileManager csv;
    private  final String configPath="src/test/java/Data/config.json";
    protected final String csvPath= "src/test/java/Data/data.csv";
    protected final String jsonPath="src/test/java/Data/jsonData.json";


    @BeforeClass
    public void setUp(){

        configData = JsonReader.readJsonFile(configPath);
        String URL =configData.getString("URL");

        ChromeOptions options =new ChromeOptions();
        options.addArguments("--Incognito");
        driver = new ChromeDriver(options);

        driver.get(URL);

        csv = new CSVFileManager(csvPath);
        testData = JsonReader.readJsonFile(jsonPath);

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        System.out.println("Website Name is "+driver.getTitle());
        homePage=new HomePage(driver);
    }

    @BeforeMethod
    public void pageLoad(){
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
    }
    @AfterMethod
    public void recordFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Screenshot: " + result.getName(),
                    new ByteArrayInputStream(screenshotBytes));
        }
    }



    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
