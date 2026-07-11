package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage{

	public HomePage(WebDriver driver) {
		super(driver);
		
	}
	
	By cartIcon = By.id("shopping_cart_container");
	By burgerMenu = By.id("react-burger-menu-btn");
	By logoutBtn = By.id("logout_sidebar_link");
	
	
	
	
	public boolean homePagePresenceVerification() {
		waiting(cartIcon);
		return driver.findElement(cartIcon).isDisplayed();
	}
	
	
	public LoginPage logout() {
		waiting(burgerMenu);
		clickOn(burgerMenu);
		waiting(logoutBtn);
		clickOn(logoutBtn);		
		return new LoginPage(driver);
	}
	
	
	
	
	
	

}
