import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import java.time.*;
import pages.*;

import static org.testng.Assert.*;


public class TestCase14 {
    private WebDriver driver;
    final String url = "http://automationexercise.com";
    private Home HomePage;
    private Cart CartPage;


    @BeforeClass
    public void setup(){
        driver = new ChromeDriver();
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        // Initialize page objects after driver is created
        HomePage = new Home(driver);
        CartPage = new Cart(driver);

    }

    @Test
    public void PlaceOrder(){
        HomePage.verifyHomePage();
        HomePage.verifyFeaturedProductsInHomePage();
        HomePage.addSingleFeaturedProductToCart();
        CartPage.verifyCartPage();
        CartPage.proceedToCartBeforeLogin();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}
