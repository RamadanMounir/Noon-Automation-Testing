package SamsungProductTest;

import BaseTest.testBase;
import Pages.SamsungPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class samsungProductTests extends testBase {
    SamsungPage samsungPage;
    // Get value by column name
    String csvColumnNameMinPrice = "minPrice";
    String csvColumnNameMaxPrice = "maxPrice";
    String CsvColumnNameBrandName ="brandName";

    @Test
    public void testOpenSamsungPage() {

        homePage.hoverOnElectronics();
        samsungPage = homePage.openSamsungPage();

        Assert.assertTrue(samsungPage.getSamsungPageTitle()
                        .contains("Samsung Electronics")
                ,"Error: You are not on Samsung page");

    }
    @Test(dependsOnMethods = "testOpenSamsungPage")
    public void testFilterByBrandAndPriceWorkCorrectly() {
        int getCsvColumnNameMinPrice = Integer.parseInt(csv.getValue(0, csvColumnNameMinPrice));
        int getCsvColumnNameMaxPrice = Integer.parseInt(csv.getValue(0, csvColumnNameMaxPrice));
        String getCsvColumnNamebrandName=csv.getValue(0,CsvColumnNameBrandName);

        samsungPage.filterPrice(getCsvColumnNameMinPrice,getCsvColumnNameMaxPrice);

        samsungPage.verifyThatReturnedProductsCorrectAndInPriceRange
                (getCsvColumnNamebrandName,getCsvColumnNameMinPrice,getCsvColumnNameMaxPrice);

    }

}
