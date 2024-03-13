package pages;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

public class HomePage {

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get("https://blazedemo.com/");
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "fromPort")
    private WebElement fromPort;

    @FindBy(name = "toPort")
    private WebElement toPort;

    @FindBy(css = ".btn-primary")
    private WebElement findFlights;

    public void setFromPort(String value) {
        fromPort.click();
        fromPort.findElement(By.xpath("//option[. = '" + value + "']")).click();
    }

    public void setToPort(String value) {
        toPort.click();
        toPort.findElement(By.xpath("//option[. = '" + value + "']")).click();
    }

    public void clickFindFlights() {
        findFlights.click();
    }
    
}
