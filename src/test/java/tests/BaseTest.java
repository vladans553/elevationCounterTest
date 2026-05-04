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

    // 1. Modifikovana pomoćna metoda koja podržava i Remote i Local
    private void openBrowser(String browser, String executionType) {
        WebDriver driver;
        WebDrivers.BrowserType type = browser.equalsIgnoreCase("firefox") ? 
                                      WebDrivers.BrowserType.FIREFOX : WebDrivers.BrowserType.EDGE;

        // Provera gde se test izvršava
        if ("remote".equalsIgnoreCase(executionType)) {
            driver = WebDrivers.createRemoteDriver(type);
        } else {
            driver = WebDrivers.createDriver(type);
        }

        threadDriver.set(driver);
        getDriver().manage().window().maximize();
    }

    // 2. Setup za celu klasu (Fast verzija)
    @BeforeClass
    @Parameters({"browser", "sessionType", "executionType"})
    public void setupClass(String browser, @Optional("method") String sessionType, @Optional("local") String executionType) {
        if (sessionType.equalsIgnoreCase("class")) {
            openBrowser(browser, executionType);
        }
    }

    // 3. Setup za svaku metodu (Pravljenje reporta i opciono otvaranje browsera)
    @BeforeMethod
    @Parameters({"browser", "sessionType", "executionType"})
    public void setupMethod(String browser, @Optional("method") String sessionType, @Optional("local") String executionType, Method method) {
        Test testAnnotation = method.getAnnotation(Test.class);
        String description = (testAnnotation != null) ? testAnnotation.description() : "Nema opisa";
        
        // Dodajemo informaciju o tipu izvršavanja u ime testa u reportu
        String testDisplayName = method.getName() + " [" + browser.toUpperCase() + " - " + executionType.toUpperCase() + "] - " + description;

        ExtentTest test = extent.createTest(testDisplayName);
        extentTest.set(test);

        if (sessionType.equalsIgnoreCase("method")) {
            openBrowser(browser, executionType);
            extentTest.get().info("Browser pokrenut (" + executionType + " sesija)");
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