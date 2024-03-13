package pages;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.Dimension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

public class ChooseFlight {

    private WebDriver driver;

    public ChooseFlight(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "tr:nth-child(1) .btn")
    private WebElement chooseThisFlight;

    @FindBy(id = "zipCode")
    private WebElement zipCode;

    public void clickChooseThisFlight() {
        chooseThisFlight.click();
    }

    public void setZipCode(String value) {
        zipCode.sendKeys(value);
    }  
    
}
