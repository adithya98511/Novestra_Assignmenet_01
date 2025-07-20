package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class Payment {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By nameOnCardField = By.xpath("//*[@id=\"payment-form\"]/div[1]/div/input");
    private By cardNumberField = By.xpath("//*[@id=\"payment-form\"]/div[2]/div/input");
    private By cvcField = By.xpath("//*[@id=\"payment-form\"]/div[3]/div[1]/input");
    private By expirationMonthField = By.xpath("//*[@id=\"payment-form\"]/div[3]/div[2]/input");
    private By expirationYearField = By.xpath("//*[@id=\"payment-form\"]/div[3]/div[3]/input");
    private By payAndConfirmButton = By.xpath("//*[@id=\"submit\"]");


    public Payment(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillPaymentForm(String nameOnCard, String cardNumber, String cvc, String month, String year) {
        enterNameOnCard(nameOnCard);
        enterCardNumber(cardNumber);
        enterCVC(cvc);
        enterExpirationMonth(month);
        enterExpirationYear(year);
    }

    public void enterNameOnCard(String name) {
        WebElement nameField = waitForElement(nameOnCardField);
        nameField.clear();
        nameField.sendKeys(name);
    }

    public void enterCardNumber(String cardNumber) {
        WebElement cardField = waitForElement(cardNumberField);
        cardField.clear();
        cardField.sendKeys(cardNumber);
    }

    public void enterCVC(String cvc) {
        WebElement cvcFieldElement = waitForElement(cvcField);
        cvcFieldElement.clear();
        cvcFieldElement.sendKeys(cvc);
    }

    public void enterExpirationMonth(String month) {
        WebElement monthField = waitForElement(expirationMonthField);
        monthField.clear();
        monthField.sendKeys(month);
    }

    public void enterExpirationYear(String year) {
        WebElement yearField = waitForElement(expirationYearField);
        yearField.clear();
        yearField.sendKeys(year);
    }

    public void clickPayAndConfirmOrder() {
        WebElement payButton = waitForElement(payAndConfirmButton);
        payButton.click();
    }



    public boolean isPaymentFormDisplayed() {
        try {
            return waitForElement(nameOnCardField).isDisplayed() &&
                    waitForElement(cardNumberField).isDisplayed() &&
                    waitForElement(payAndConfirmButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getNameOnCardValue() {
        return waitForElement(nameOnCardField).getAttribute("value");
    }

    public String getCardNumberValue() {
        return waitForElement(cardNumberField).getAttribute("value");
    }

    public String getCVCValue() {
        return waitForElement(cvcField).getAttribute("value");
    }

    public String getExpirationMonthValue() {
        return waitForElement(expirationMonthField).getAttribute("value");
    }

    public String getExpirationYearValue() {
        return waitForElement(expirationYearField).getAttribute("value");
    }

    public boolean isPayButtonEnabled() {
        WebElement payButton = waitForElement(payAndConfirmButton);
        return payButton.isEnabled();
    }

    // Helper method to handle multiple locator strategies
    private WebElement waitForElement(By primaryLocator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(primaryLocator));
        } catch (Exception e) {

                throw new RuntimeException("Could not find element with either locator strategy", e);
            }

    }



}