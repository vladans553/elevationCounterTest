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

    // 1. Pomoćna metoda koja samo otvara browser (bez pravljenja testa u reportu)
    private void openBrowser(String browser) {
        WebDriver driver = WebDrivers.createDriver(browser.equalsIgnoreCase("firefox") ? 
                          WebDrivers.BrowserType.FIREFOX : WebDrivers.BrowserType.EDGE);
        threadDriver.set(driver);
        getDriver().manage().window().maximize();
    }

    // 2. Pokretanje browsera JEDNOM za celu klasu (Fast verzija)
    @BeforeClass
    @Parameters({"browser", "sessionType"})
    public void setupClass(String browser, @Optional("method") String sessionType) {
        if (sessionType.equalsIgnoreCase("class")) {
            openBrowser(browser);
        }
    }

    // 3. OVO JE KLJUČ: Pravljenje zapisa u reportu za SVAKU metodu, u oba scenarija
    @BeforeMethod
    @Parameters({"browser", "sessionType"})
    public void setupMethod(String browser, @Optional("method") String sessionType, Method method) {
        // Izvlačimo opis
        Test testAnnotation = method.getAnnotation(Test.class);
        String description = (testAnnotation != null) ? testAnnotation.description() : "Nema opisa";
        String testDisplayName = method.getName() + " [" + browser.toUpperCase() + "] - " + description;

        // Pravimo novi test u Extent Reportu
        ExtentTest test = extent.createTest(testDisplayName);
        extentTest.set(test);

        // Ako je sesija podešena na "method", ovde otvaramo browser (Isolated verzija)
        if (sessionType.equalsIgnoreCase("method")) {
            openBrowser(browser);
            extentTest.get().info("Browser pokrenut (Nova sesija)");
        } else {
            extentTest.get().info("Nastavak u postojećoj sesiji (Fast mode)");
        }
    }

    public WebDriver getDriver() { return threadDriver.get(); }

    @AfterMethod
    @Parameters("sessionType")
    public void tearDownMethod(@Optional("method") String sessionType) {
        // Zatvaramo drajver posle svake metode SAMO ako je Isolated mod
        if (sessionType.equalsIgnoreCase("method") && getDriver() != null) {
            closeDriver();
        }
    }

    @AfterClass
    @Parameters("sessionType")
    public void tearDownClass(@Optional("method") String sessionType) {
        // Zatvaramo drajver na kraju klase SAMO ako je Fast mod
        if (sessionType.equalsIgnoreCase("class") && getDriver() != null) {
            closeDriver();
        }
    }

    private void closeDriver() {
        getDriver().quit();
        threadDriver.remove();
        extentTest.remove();
    }

    @AfterSuite
    public void flushReport() {
        extent.flush();
    }
}