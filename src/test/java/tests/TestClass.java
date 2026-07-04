package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.LoginPage;


public class TestClass extends BaseTest {
	
	
	public LoginPage lp;
	String errorMsg = "Epic sadface: Username and password do not match any user in this service";
	
	@Test
	public void invalidLoginTest() {
		
		lp = new LoginPage(getDriver());
		lp.openLoginPage();
		lp.login("standard_user", "bogusPassword");
		String errorMSGactual = lp.getErrorMsgText();
		
		System.out.println("MESSAGE "+errorMSGactual);
		Assert.assertTrue(errorMSGactual.contains(errorMsg));
	}

 
    
    
}