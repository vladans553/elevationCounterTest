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
    
    @BeforeMethod
    public void createSession(Method method) {
        WebDriver d = WebDrivers.createDriver(true);
        threadDriver.set(d);
        
    }

    @AfterMethod
    public void tearDown() {
        if (threadDriver.get() != null) {
            threadDriver.get().quit();
            threadDriver.remove();
        }

    
    }

    public WebDriver getDriver() {
        return threadDriver.get();
    }

    
}