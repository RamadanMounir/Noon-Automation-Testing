package HomeTests;

import BaseTest.testBase;
import Pages.SamsungPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class homeTests extends testBase {



    @Test
    public void testOpenSamsungPage(){
        homePage.hoverOnElectronics();
        SamsungPage samsungPage=homePage.openSamsungPage();
        Assert.assertTrue(samsungPage.getSamsungPageTitle()
                        .contains("Samsung Electronics")
                        ,"Error: You are not on samsung page");
    }

}
