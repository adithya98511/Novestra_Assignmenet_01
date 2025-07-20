package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class Home
{
    private WebDriver driver;
    private WebDriverWait wait;
    By btn_home = By.xpath("//a[contains(text(),'Home')]");
    By carousal_home = By.xpath("//*[@id=\"slider-carousel\"]");
    By featured_prod_Title =  By.xpath("/html/body/section[2]/div/div/div[2]/div[1]/h2");
    By single_product = By.xpath("/html/body/section[2]/div/div/div[2]/div[1]/div[4]");
    By delete_button = By.xpath("//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[4]/a");




    public Home(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public void verifyHomePage() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(featured_prod_Title));
            Assert.assertEquals(driver.findElement(featured_prod_Title).getText(), "Features Items");


        } catch (TimeoutException e) {
            System.out.println("Timed out waiting for the home page to load.");
            throw e;
        } catch (Exception e) {
            System.out.println("Error occurred while verifying home page: " + e.getMessage());
            throw e;
        }
    }

    public void verifyFeaturedProductsInHomePage(){
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(carousal_home));
            Assert.assertEquals(driver.findElement(carousal_home).getText(), "AutomationExercise");


        } catch (TimeoutException e) {
            System.out.println("Timed out waiting for the featured items in home page to load.");
            throw e;
        } catch (Exception e) {
            System.out.println("Error occurred while verifying featured items in home page: " + e.getMessage());
            throw e;
        }
    }

    public void addSingleFeaturedProductToCart(){
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(single_product));

            WebElement product = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(single_product));
            WebElement addButton = product.findElement(
                    By.xpath("/html/body/section[2]/div/div/div[2]/div[1]/div[4]/div/div[2]/ul/li/a"));
            addButton.click();

        } catch (TimeoutException e) {
            System.out.println("Timed out waiting for the featured items in home page to load.");
            throw e;
        } catch (Exception e) {
            System.out.println("Error occurred while verifying featured items in home page: " + e.getMessage());
            throw e;
        }
    }

    public void navigateHome() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(btn_home)).click();

    }

    public void deleteAccount(){

    }

}
