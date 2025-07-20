package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class Product {

    private WebDriver driver;
    private WebDriverWait wait;
    public static String name;
    public static String category;
    public static String price;
    public static String quantity;
    public static String total;



    public Product(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    // Method to verify specific order details with parameters
    public Product getProductItemDetails() {
        try {
            // Get item details from the table
            WebElement itemName = driver.findElement(By.xpath("//td//strong[contains(text(),'Men Tshirt')]"));
            WebElement itemCategory = driver.findElement(By.xpath("//td[contains(text(),'Men > Tshirts')]"));
            WebElement itemPrice = driver.findElement(By.xpath("//td[contains(text(),'Rs. 400')][1]"));
            WebElement itemQuantity = driver.findElement(By.xpath("//input[@value='1']"));
            WebElement itemTotal = driver.findElement(By.xpath("//td[contains(text(),'Rs. 400')][last()]"));

            // Create order item object
            Product orderItem = new Product(driver);
            Product.name = itemName.getText();
            Product.category = itemCategory.getText();
            Product.price = itemPrice.getText();
            Product.quantity = itemQuantity.getAttribute("value");
            Product.total = itemTotal.getText();

            // Verify item details
            Assert.assertEquals("Men Tshirt", Product.name, "Item name should match");
            Assert.assertEquals("Men > Tshirts", Product.category, "Item category should match");
            Assert.assertEquals("Rs. 400", Product.price, "Item price should match");
            Assert.assertEquals("1", Product.quantity, "Item quantity should match");
            Assert.assertEquals("Rs. 400", Product.total, "Item total should match");

            System.out.println("Order item details verified successfully");
            return orderItem;

        } catch (Exception e) {
            System.out.println("Error getting order item details: " + e.getMessage());
            throw e;
        }
    }

    public void verifyProductDetails(String expectedItem, String expectedPrice, String expectedQuantity, String expectedTotal) {
        try {
            Product orderItem = getProductItemDetails();

            Assert.assertEquals(expectedItem, Product.name, "Item name verification failed");
            Assert.assertEquals(expectedPrice, Product.price, "Item price verification failed");
            Assert.assertEquals(expectedQuantity, Product.quantity, "Item quantity verification failed");
            Assert.assertEquals(expectedTotal, Product.total, "Item total verification failed");

            System.out.println("Custom order details verification completed successfully");

        } catch (Exception e) {
            System.out.println("Error in custom order verification: " + e.getMessage());
            throw e;
        }
    }

}