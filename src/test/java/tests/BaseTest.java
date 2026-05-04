package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import utils.ExtentManager;
import utils.WebDrivers;
import java.lang.reflect.Method;

public class BaseTest {

    protected static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
    protected static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    protected static ExtentReports extent = ExtentManager.getInstance();

    // Modifikovana pomoćna metoda koja prihvata i headless boolean
    private void openBrowser(String browser, String executionType, boolean headless) {
        WebDriver driver;
        WebDrivers.BrowserType type = browser.equalsIgnoreCase("firefox") ? 
                                      WebDrivers.BrowserType.FIREFOX : WebDrivers.BrowserType.EDGE;

        if ("remote".equalsIgnoreCase(executionType)) {
            // Ako ikada odlučiš da se vratiš na BrowserStack
            driver = WebDrivers.createRemoteDriver(type); 
        } else {
            // Lokalno pokretanje (uključujući GitHub Actions) sa headless kontrolom
            driver = WebDrivers.createDriver(type, headless);
            
            // Maksimizujemo samo ako NIJE headless (na tvom ekranu)
            if (!headless) {
                driver.manage().window().maximize();
            }
        }

        threadDriver.set(driver);
    }

    @BeforeClass
    @Parameters({"browser", "sessionType", "executionType", "headless"})
    public void setupClass(String browser, 
                           @Optional("method") String sessionType, 
                           @Optional("local") String executionType,
                           @Optional("false") String headless) {
        if (sessionType.equalsIgnoreCase("class")) {
            boolean headlessBool = Boolean.parseBoolean(headless);
            openBrowser(browser, executionType, headlessBool);
        }
    }

    @BeforeMethod
    @Parameters({"browser", "sessionType", "executionType", "headless"})
    public void setupMethod(String browser, 
                            @Optional("method") String sessionType, 
                            @Optional("local") String executionType, 
                            @Optional("false") String headless, 
                            Method method) {
        
        Test testAnnotation = method.getAnnotation(Test.class);
        String description = (testAnnotation != null) ? testAnnotation.description() : "Nema opisa";
        
        String testDisplayName = method.getName() + " [" + browser.toUpperCase() + " - " + executionType.toUpperCase() + "] - " + description;

        ExtentTest test = extent.createTest(testDisplayName);
        extentTest.set(test);

        if (sessionType.equalsIgnoreCase("method")) {
            boolean headlessBool = Boolean.parseBoolean(headless);
            openBrowser(browser, executionType, headlessBool);
            extentTest.get().info("Browser pokrenut (Headless: " + headless + ")");
        } else {
            extentTest.get().info("Nastavak u postojećoj sesiji (Fast mode)");
        }
    }

    public WebDriver getDriver() { return threadDriver.get(); }

    @AfterMethod
    @Parameters("sessionType")
    public void tearDownMethod(@Optional("method") String sessionType) {
        if (sessionType.equalsIgnoreCase("method") && getDriver() != null) {
            closeDriver();
        }
    }

    @AfterClass
    @Parameters("sessionType")
    public void tearDownClass(@Optional("method") String sessionType) {
        if (sessionType.equalsIgnoreCase("class") && getDriver() != null) {
            closeDriver();
        }
    }

    private void closeDriver() {
        if (getDriver() != null) {
            getDriver().quit();
        }
        threadDriver.remove();
        extentTest.remove();
    }

    @AfterSuite
    public void flushReport() {
        extent.flush();
    }
}