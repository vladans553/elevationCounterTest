package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage{

	public LoginPage(WebDriver driver) {
		super(driver);
		
	}
	
	
	String url = "https://www.saucedemo.com/";
	String userNameInput = "user-name";
	String passwordInput = "password";
	String loginBtn = "login-button";
	String errorMsgClass = "//h3[@data-test='error']";
	
	public void openLoginPage() {
		driver.get(url);
	}
	
	
	public void login(String uName, String password) {
		sendKeys(uName, By.id(userNameInput));
		sendKeys(password, By.id(passwordInput));
		clickOn(By.id(loginBtn));
	}
	
	public String getErrorMsgText() {
		waiting(By.xpath(errorMsgClass));
		return driver.findElement(By.xpath(errorMsgClass))
				.getText();
	}
	
	
	
	

}
