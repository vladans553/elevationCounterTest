package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object za Home stranicu "Elevation Finder" aplikacije.
 * Primenjen nivo enkapsulacije, DRY princip i logička separacija elemenata.
 */
public class HomePage extends BasePage {

    // 1. Centralizovani lokatori (Koristimo ID gde god je moguće radi brzine)
    private final String URL = "https://elevationcounter.netlify.app/";
    
    private final By mainHeader   = By.xpath("//h1");
    private final By inputField   = By.id("locationInput"); 
    private final By searchButton = By.id("submitBtn");
    private final By clearButton  = By.id("clearBtn");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Otvara početnu stranicu i vraća instancu klase za Fluent interface.
     */
    public HomePage open() {
        driver.get(URL);
        return this; 
    }

    // --- GETTERS (Dohvatanje informacija) ---

    public String getHeaderText() {
        return waiting(mainHeader).getText();
    }

    public String getBrowserTitle() {
        return driver.getTitle();
    }

    // --- VERIFICATIONS (Provere stanja) ---

    /**
     * Proverava da li je glavni naslov vidljiv. 
     * Za tekstualne elemente koristimo samo isDisplayed().
     */
    public boolean isAt() {
        try {
            return waiting(mainHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isInputFieldReady() {
        return isInteractiveElementReady(inputField);
    }

    public boolean isSearchButtonReady() {
        return isInteractiveElementReady(searchButton);
    }

    public boolean isClearButtonReady() {
        return isInteractiveElementReady(clearButton);
    }

    /**
     * Privatna pomoćna metoda za interaktivne elemente (Input, Button).
     * Proverava i vidljivost i da li je element omogućen za rad (nije disabled).
     */
    private boolean isInteractiveElementReady(By locator) {
        try {
            WebElement element = waiting(locator);
            return element.isDisplayed() && element.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
}