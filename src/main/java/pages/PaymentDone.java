package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PaymentDone
{
    private WebDriver driver;
    private WebDriverWait wait;
    By order_placed_title = By.xpath("//*[@id=\"form\"]/div/div/div/h2/b");
    By success_message = By.xpath("//*[@id=\"form\"]/div/div/div/p");
    By delete_button = By.xpath("//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[4]/a");

    public void verifyPaymentsDonePage(){

    }
}
