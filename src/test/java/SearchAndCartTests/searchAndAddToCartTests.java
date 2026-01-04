package SearchAndCartTests;

import BaseTest.testBase;
import Pages.CartPage;
import Pages.ResultPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

public class searchAndAddToCartTests extends testBase {
    ResultPage resultPage;
    CartPage cartPage;

    int numberOfProductAddedToCart=3;
    List<String> sortedProductNameBeforeAddedToCart;
    List<String>  sortedProductPriceBeforeAddedToCart;
    List<String> sortedProductNameAfterAddedToCart;
    List<String>  sortedProductPriceAfterAddedToCart;

    @Test(priority = 1)
    public void testSearchTargetProductPageCorrectly(){
        String productToSearch = testData.getString("validSearch");
        resultPage= homePage.searchAbout(productToSearch);
        resultPage.verifyCorrectSearchResultAppears(productToSearch);

        sortedProductNameBeforeAddedToCart = resultPage.getItemNameToVerify();

        sortedProductPriceBeforeAddedToCart =resultPage.getItemPriceToVerify();
    }

    @Test(dependsOnMethods = "testSearchTargetProductPageCorrectly",priority = 2)
    public void testProductsNumberAddedToCart() {
        resultPage.addThreeItemsToCartAndGetNameAndPriceOfEachOne();
        resultPage.verifyNumberOfProductOnCart(numberOfProductAddedToCart);

    }

    @Test(dependsOnMethods = "testSearchTargetProductPageCorrectly",priority = 3)
    public void testCartPageOpenCorrectly(){
         cartPage = resultPage.openCart();
         cartPage.verifyCartPageOpenedCorrectly();
         sortedProductNameAfterAddedToCart  =cartPage.getItemNameToVerify();

          sortedProductPriceAfterAddedToCart =cartPage.getItemPriceToVerify();

    }
    @Test(dependsOnMethods ={"testSearchTargetProductPageCorrectly","testCartPageOpenCorrectly"},priority = 4)
    public void testProductsAddedToCartAreCorrect(){

        Assert.assertTrue(sortedProductNameBeforeAddedToCart.containsAll(sortedProductNameAfterAddedToCart)
                ,"item added to cart not correct");
        Assert.assertTrue(sortedProductPriceBeforeAddedToCart.containsAll(sortedProductPriceAfterAddedToCart)
                ,"item price on the cart not the same");



    }
    @Test(priority = 5)
    public void testValidationWithInvalidInput(){
        String invalidData=testData.getString("invalidSearch");
        resultPage= homePage.searchAbout(invalidData);
        Assert.assertTrue(resultPage.errorMessageAppears(),"Products appear when enter invalid input to search");


    }

}
