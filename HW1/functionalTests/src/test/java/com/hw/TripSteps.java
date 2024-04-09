package com.hw;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.invoke.MethodHandles;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TripSteps {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private WebDriver driver;


    @When("I am at {string}")
    public void iNavigateTo(String url) {
        log.debug("Navigate to {}", url);
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("ignore-certificate-errors");
        options.addArguments("--ignore-ssl-errors=yes");
        WebDriverManager.firefoxdriver().setup();
        driver = new org.openqa.selenium.firefox.FirefoxDriver(options);
        driver.get(url);
    }

    @And("I choose my departure city {string} and my destination city {string}")
    public void iChooseDepartureAndDestinationCities(String departureCity, String destinationCity) {
        log.debug("Choose departure city {} and destination city {}", departureCity, destinationCity);
        driver.findElement(By.id("initialCity")).sendKeys(departureCity);
        driver.findElement(By.name("finalCity")).sendKeys(destinationCity);
    }

    @And("I click Get Trips button")
    public void iClickFindFlightsButton() {
        log.debug("Click Find Flights button");
        driver.findElement(By.cssSelector("button[type=\"submit\"]")).click();
    }

    @And("I choose a trip and click the button")
    public void iChooseAFlightAndClickTheButton() {
        log.debug("Choose a trip and click the button");
        driver.findElement(By.cssSelector(".list-group-item a")).click();
    }

    @And("I fill in my name as {string}")
    public void iFillInMyNameAs(String name) {
        log.debug("Fill in name as {}", name);
        driver.findElement(By.id("name")).sendKeys(name);
    }

    @And("I fill in my number of seats as {int}")
    public void iFillInMyCityAs(Integer seats) {
        log.debug("Fill in number of seats as {}", seats);
        driver.findElement(By.id("numSeats")).sendKeys(seats.toString());
    }

    @And("I click Submit Reservation button")
    public void iClickPurchaseFlightButton() {
        log.debug("Click Submit Reservation button");
        driver.findElement(By.cssSelector("button[type=\"submit\"]")).click();
    }

    @Then("Page title should be {string}")
    public void pageTitleShouldBe(String expectedTitle) {
        log.debug("Verify page title {}", expectedTitle);
        assertEquals(expectedTitle, driver.getTitle());
        driver.quit();
    }

    @And("I wait for {int} seconds")
    public void iWaitForSeconds(int seconds) {
        log.debug("Wait for {} seconds", seconds);
        try {
            Thread.sleep(Duration.ofSeconds(seconds).toMillis());
        } catch (InterruptedException e) {
            log.error("Error waiting", e);
        }
    }

    @And("I close the browser")
    public void iCloseTheBrowser() {
        log.debug("Close the browser");
        driver.quit();
    }
    
}
