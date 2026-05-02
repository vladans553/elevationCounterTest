package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;

public class TestClass extends BaseTest {

    private HomePage homePage;

    // Inicijalizujemo HomePage pre svakog testa
    @BeforeMethod
    public void setupPage() {        
        homePage = new HomePage(getDriver());
    }

    @Test(priority = 1, description = "Provera da li se početna stranica uspešno učitava")
    public void test_01_HomePageLoads() {
        homePage.open();

        // Proveravamo da li je stranica učitana (koristimo isAt metodu)
        boolean isPageLoaded = homePage.isAt();
        Assert.assertTrue(isPageLoaded, "Kritična greška: Home stranica nije učitana!");
    }

    @Test(priority = 2, description = "Validacija naslova u tabu browser-a")
    public void test_02_pageTitleVerification() {
        homePage.open();

        String expectedTitle = "Elevation Finder - Find Altitude of Any Place Instantly"; // Zameni sa tačnim naslovom tvog sajta
        String actualTitle = homePage.getBrowserTitle();

        Assert.assertEquals(actualTitle, expectedTitle, "Browser Title (u tabu) se ne poklapa!");
    }

    @Test(priority = 3, description = "Validacija glavnog H1 naslova na stranici")
    public void test_03_pageHTitleVerification() {
        homePage.open();

        String expectedH1 = "Elevation Finder"; // Zameni sa tačnim tekstom koji tvoj H1 treba da ima
        String actualH1 = homePage.getHeaderText();

        Assert.assertEquals(actualH1, expectedH1, "Glavni H1 naslov na stranici nije ispravan!");
    }
    
    
    @Test(priority = 4, description = "Provera da li je input polje vidljivo i omogućeno")
    public void test_04_inputFieldVerification() {
        homePage.open();
        Assert.assertTrue(homePage.isInputFieldReady(), "Input polje nije spremno za rad!");
    }

    @Test(priority = 5, description = "Provera da li je Search dugme vidljivo i omogućeno")
    public void test_02_searchButtonVerification() {
        homePage.open();
        Assert.assertTrue(homePage.isSearchButtonReady(), "Search dugme nije spremno za rad!");
    }

    @Test(priority = 6, description = "Provera da li je Clear dugme vidljivo i omogućeno")
    public void test_03_clearButtonVerification() {
        homePage.open();
        Assert.assertTrue(homePage.isClearButtonReady(), "Clear dugme nije spremno za rad!");
    }
    
    
    
    
    
    
    
    
    
    
    
    
}