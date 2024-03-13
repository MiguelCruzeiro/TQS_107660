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

public class PurchaseFlight {

    private WebDriver driver;

    public PurchaseFlight(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".btn-primary")
    private WebElement purchaseFlight;

    @FindBy(id = "inputName")
    private WebElement inputName;

    @FindBy(id = "address")
    private WebElement address;

    @FindBy(id = "city")
    private WebElement city;

    @FindBy(id = "state")
    private WebElement state;

    @FindBy(id = "zipCode")
    private WebElement zipCode;

    @FindBy(id = "cardType")
    private WebElement cardType;

    @FindBy(id = "creditCardNumber")
    private WebElement creditCardNumber;

    @FindBy(id = "creditCardMonth")
    private WebElement creditCardMonth;

    @FindBy(id = "creditCardYear")
    private WebElement creditCardYear;

    @FindBy(id = "nameOnCard")
    private WebElement nameOnCard;

    public void setInputName(String value) {
        inputName.sendKeys(value);
    }

    public void setAddress(String value) {
        address.sendKeys(value);
    }

    public void setCity(String value) {
        city.sendKeys(value);
    }

    public void setState(String value) {
        state.sendKeys(value);
    }

    public void setZipCode(String value) {
        zipCode.sendKeys(value);
    }

    public void setCardType(String value) {
        cardType.sendKeys(value);
    }

    public void setCreditCardNumber(String value) {
        creditCardNumber.sendKeys(value);
    }

    public void setCreditCardMonth(String value) {
        creditCardMonth.sendKeys(value);
    }

    public void setCreditCardYear(String value) {
        creditCardYear.sendKeys(value);
    }

    public void setNameOnCard(String value) {
        nameOnCard.sendKeys(value);
    }

    public void clickPurchaseFlight() {
        purchaseFlight.click();
    }
    
    public void close() {
        driver.quit();
    }
    
}
