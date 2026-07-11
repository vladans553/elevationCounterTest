package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage; 
import pages.LoginPage;
import utils.TestDataManager;
import utils.TestDataManager.MyVariable;

public class TestClass extends BaseTest {
    
    
    @Test
    public void invalidLoginTest() {
    	
        LoginPage lp = new LoginPage(getDriver());
        lp.openLoginPage();
        lp.loginExpectFailure(TestDataManager.getGitHubVariable(MyVariable.VALID_USERNAME), "bogusPassword");
        String errorMSGactual = lp.getErrorMsgText();
        
        System.out.println("MESSAGE " + errorMSGactual);
        Assert.assertTrue(errorMSGactual.contains(lp.errorMsg));
    }
    
    @Test
    public void validLoginTest() {
    	
        LoginPage lp = new LoginPage(getDriver());
        lp.openLoginPage();
        HomePage hp = lp.loginExpectSuccess(
        		TestDataManager.getGitHubVariable(MyVariable.VALID_USERNAME),
        		TestDataManager.getGitHubVariable(MyVariable.VALID_PASSWORD));
        boolean isAtHomePage = hp.homePagePresenceVerification();
        
        System.out.println("On Home page is " + isAtHomePage);
        Assert.assertTrue(isAtHomePage);
    }
    
    @Test
    public void logoutTest() {
        LoginPage lp = new LoginPage(getDriver());
        lp.openLoginPage();
        HomePage hp = lp.loginExpectSuccess(
        		TestDataManager.getGitHubVariable(MyVariable.VALID_USERNAME),
        		TestDataManager.getGitHubVariable(MyVariable.VALID_PASSWORD));
        hp.homePagePresenceVerification();
        
        LoginPage loginPageNakonOdjave = hp.logout();
        
        boolean flag = loginPageNakonOdjave.loginPagePresenceVerification();
        System.out.println("WE ARE ON LOGIN PAGE " + flag);
        
        Assert.assertTrue(flag);        
    }
}