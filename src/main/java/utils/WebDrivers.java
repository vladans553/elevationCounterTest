package utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDrivers {
    
    // Prilagodi preuzimanje URL-a u zavisnosti od tvog projekta (npr. iz env varijabli ili properties fajla)
    private static final String cloudUrl = System.getenv("CLOUD_URL"); 

   
    public static WebDriver createDriver(boolean headless) {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        
        if (headless) {
            firefoxOptions.addArguments("-headless");
            firefoxOptions.addArguments("--window-size=1920,1080");
        }
        firefoxOptions.addArguments("--start-maximized");
        
        return new FirefoxDriver(firefoxOptions);
    }

    /**
     * Kreira udaljeni (Remote) Firefox WebDriver za BrowserStack/Grid.
     */
    public static WebDriver createRemoteDriver() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        
        // HEADLESS ZA FIREFOX
        firefoxOptions.addArguments("-headless");
        firefoxOptions.addArguments("--window-size=1920,1080");

        // BrowserStack specifične opcije
        Map<String, Object> bstackOptions = new HashMap<>();
        bstackOptions.put("os", "Windows");
        bstackOptions.put("osVersion", "11");
        bstackOptions.put("projectName", "Moj Projekt");
        firefoxOptions.setCapability("bstack:options", bstackOptions);

        try {
            return new RemoteWebDriver(new URL(cloudUrl), firefoxOptions);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Neispravan Cloud URL. Proveri environment varijable!");
        }
    }
}