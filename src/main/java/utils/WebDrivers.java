package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;



public class WebDrivers {

	
	static String geckoPath = "C:\\Users\\vladan\\eclipse-workspace\\elevationCounterTest\\geckodriver.exe";
	static String webDriverProperty = "webdriver.gecko.driver";
	
	public static WebDriver createDriver(BrowserType browserType) {
		WebDriver driver = null;

		switch (browserType) {
		case EDGE:
			System.setProperty("edge will not be used :(", "");
			driver = new EdgeDriver();
			break;

		case FIREFOX:
			System.setProperty(webDriverProperty,geckoPath);
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.addArguments("--start-maximized");
			firefoxOptions.addArguments("--disable-popup-blocking");
			firefoxOptions.addArguments("--disable-notifications");
			firefoxOptions.addPreference("dom.disable_open_during_load", true);
			firefoxOptions.addPreference("dom.popup_maximum", 0);
			firefoxOptions.addPreference("permissions.default.desktop-notification", 2);

			driver = new FirefoxDriver(firefoxOptions);
			break;

		default:
			throw new IllegalArgumentException("Browser type not supported: " + browserType);
		}
		return driver;
	}

	public static enum BrowserType {
		EDGE, FIREFOX
	}

}
