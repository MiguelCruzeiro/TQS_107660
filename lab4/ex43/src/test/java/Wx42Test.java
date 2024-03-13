import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

import pages.HomePage;
import pages.ChooseFlight;
import pages.PurchaseFlight;
import pages.ConfirmationPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Wx42Test {

    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    private String baseUrl = "https://blazedemo.com/";

    @BeforeEach
    public void setUp() {
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void wx42Test() {
        driver.get(baseUrl);
        HomePage homePage = new HomePage(driver);
        homePage.setFromPort("Boston");
        homePage.setToPort("London");
        homePage.clickFindFlights();
        ChooseFlight chooseFlight = new ChooseFlight(driver);
        chooseFlight.clickChooseThisFlight();
        chooseFlight.setZipCode("12345");
        PurchaseFlight purchaseFlight = new PurchaseFlight(driver);
        purchaseFlight.setInputName("John Doe");
        purchaseFlight.setAddress("123 Main St");
        purchaseFlight.setCity("Boston");
        purchaseFlight.setState("MA");
        purchaseFlight.setZipCode("12345");
        purchaseFlight.setCardType("Visa");
        purchaseFlight.setCreditCardNumber("4111111111111111");
        purchaseFlight.setCreditCardMonth("12");
        purchaseFlight.setCreditCardYear("2023");
        purchaseFlight.setNameOnCard("John Doe");
        purchaseFlight.clickPurchaseFlight();
        ConfirmationPage confirmationPage = new ConfirmationPage(driver);
        assertEquals("Thank you for your purchase today!", confirmationPage.getConfirmationHeader());
    }

    
}
