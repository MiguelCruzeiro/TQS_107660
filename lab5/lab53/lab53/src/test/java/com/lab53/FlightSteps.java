package com.lab53;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.invoke.MethodHandles;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FlightSteps {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private WebDriver driver;

    @When("I am at {string}")
    public void iNavigateTo(String url) {
        log.debug("Navigate to {}", url);
        WebDriverManager.firefoxdriver().setup();
        driver = WebDriverManager.firefoxdriver().create();
        driver.get(url);
    }

    @And("I choose my departure city {string} and my destination city {string}")
    public void iChooseDepartureAndDestinationCities(String departureCity, String destinationCity) {
        log.debug("Choose departure city {} and destination city {}", departureCity, destinationCity);
        driver.findElement(By.name("fromPort")).sendKeys(departureCity);
        driver.findElement(By.name("toPort")).sendKeys(destinationCity);
    }

    @And("I click Find Flights button")
    public void iClickFindFlightsButton() {
        log.debug("Click Find Flights button");
        driver.findElement(By.tagName("input")).click();
    }

    @And("Page should say {string}")
    public void pageShouldSay(String expectedMessage) {
        log.debug("Verify page message {}", expectedMessage);
        String actualMessage = driver.findElement(By.tagName("h3")).getText();
        assertEquals(expectedMessage, actualMessage);
    }

    @And("I choose a flight and click the button")
    public void iChooseAFlightAndClickTheButton() {
        log.debug("Choose a flight and click the button");
        driver.findElement(By.tagName("input")).click();
    }

    @And("I fill in my name as {string}")
    public void iFillInMyNameAs(String name) {
        log.debug("Fill in name as {}", name);
        driver.findElement(By.id("inputName")).sendKeys(name);
    }

    @And("I fill in my city as {string}")
    public void iFillInMyCityAs(String city) {
        log.debug("Fill in city as {}", city);
        driver.findElement(By.id("city")).sendKeys(city);
    }

    @And("I click Purchase Flight button")
    public void iClickPurchaseFlightButton() {
        log.debug("Click Purchase Flight button");
        driver.findElement(By.tagName("input")).click();
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
