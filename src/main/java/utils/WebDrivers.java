package utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDrivers {
    
    // Umesto hardkodovanja, čitamo iz Environment varijabli (bitno za GitHub Actions)
    static String userName = System.getenv("BROWSERSTACK_USERNAME");
    static String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
    static String cloudUrl = "https://" + userName + ":" + accessKey + "@hub.browserstack.com/wd/hub";

    public static WebDriver createDriver(BrowserType browserType) {
        WebDriver driver = null;

        switch (browserType) {
        case EDGE:
            // Selenium Manager (verzija 4.6.0+) automatski nalazi drajver, 
            // pa ti System.setProperty više i ne treba ako imaš noviji Selenium.
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.addArguments("--start-maximized");
            driver = new EdgeDriver(edgeOptions);
            break;

        case FIREFOX:
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addArguments("--start-maximized");
            driver = new FirefoxDriver(firefoxOptions);
            break;

        default:
            throw new IllegalArgumentException("Browser type not supported: " + browserType);
        }
        return driver;
    }

    public static WebDriver createRemoteDriver(BrowserType browserType) {
        MutableCapabilities capabilities;

        switch (browserType) {
            case EDGE:
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized");
                capabilities = edgeOptions;
                break;
            case FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximized");
                capabilities = firefoxOptions;
                break;
            default:
                throw new IllegalArgumentException("Remote browser type not supported: " + browserType);
        }

        Map<String, Object> bstackOptions = new HashMap<>();
        bstackOptions.put("os", "Windows");
        bstackOptions.put("osVersion", "11");
        bstackOptions.put("projectName", "Moj Projekt");
        capabilities.setCapability("bstack:options", bstackOptions);

        try {
            return new RemoteWebDriver(new URL(cloudUrl), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Neispravan Cloud URL. Proveri environment varijable!");
        }
    }

    public static enum BrowserType {
        EDGE, FIREFOX
    }
}