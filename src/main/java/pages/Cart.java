package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.Product.*;

import java.time.Duration;

public class Cart {

    private WebDriver driver;
    private WebDriverWait wait;
    Product product;

    // Locators
    By cart_button = By.xpath("//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[3]/a");
    By cart_text = By.xpath("//section[@id='cart_items']//h2[contains(text(),'Shopping Cart')]");
    By proceed_to_checkout_btn = By.xpath("//*[@id=\"do_action\"]/div[1]/div/div/a");
    By checkout_modal_text = By.xpath("//*[@id=\"checkoutModal\"]/div/div/div[1]/h4");
    By register_login_link = By.xpath("//*[@id=\"checkoutModal\"]/div/div/div[2]/p[2]/a");
    By login_title = By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/h2");
    By register_title = By.xpath("//*[@id=\"form\"]/div/div/div[3]/div/h2");
    By address_title = By.xpath("//*[@id=\"cart_items\"]/div/div[2]/h2");
    By delivery_address_div = By.xpath("//*[@id=\"address_delivery\"]");
    By billing_address_div = By.xpath("//*[@id=\"address_invoice\"]");
    By place_order_button = By.xpath("//*[@id=\"cart_items\"]/div/div[7]/a");


    // Constructor
    public Cart(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Clicks cart button and verifies cart page
    public void verifyCartPage() {
        try {
            // Click on the Cart button
            wait.until(ExpectedConditions.elementToBeClickable(cart_button)).click();

            // Wait until "Shopping Cart" heading appears
            wait.until(ExpectedConditions.presenceOfElementLocated(cart_text));

            // Verify the text is "Shopping Cart"
            String cartHeading = driver.findElement(cart_text).getText();
            Assert.assertEquals(cartHeading, "Shopping Cart");
            System.out.println("Cart page verified successfully.");

        } catch (TimeoutException e) {
            System.out.println("Timed out waiting for the cart page to load.");
            throw e;
        } catch (Exception e) {
            System.out.println("Error occurred while verifying cart page: " + e.getMessage());
            throw e;
        }
    }


    public void proceedToCartBeforeLogin() {
        try {
            // Click proceed to checkout
            wait.until(ExpectedConditions.elementToBeClickable(proceed_to_checkout_btn)).click();

            // Wait until the modal is visible
            wait.until(ExpectedConditions.presenceOfElementLocated(checkout_modal_text));

            // Verify modal title text (optional)
            String modalText = driver.findElement(checkout_modal_text).getText();
            Assert.assertEquals(modalText, "Checkout", "Checkout modal title mismatch");

            // Click on Register/Login link inside the modal
            wait.until(ExpectedConditions.elementToBeClickable(register_login_link)).click();

            // Wait for either Login or Register title to appear
            boolean isLoginVisible = wait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(login_title),
                    ExpectedConditions.presenceOfElementLocated(register_title)
            ));

            // Assert at least one of them is displayed
            boolean loginDisplayed = driver.findElement(login_title).isDisplayed();
            boolean registerDisplayed = driver.findElement(register_title).isDisplayed();

            Assert.assertTrue(loginDisplayed || registerDisplayed, "Neither Login nor Register page is displayed.");
            System.out.println("Navigated to Login/Register page successfully.");

        } catch (TimeoutException e) {
            System.out.println("Timed out waiting during checkout before login.");
            throw e;
        } catch (Exception e) {
            System.out.println("Error during proceedToCartBeforeLogin: " + e.getMessage());
            throw e;
        }
    }

    public void proceedToCartAfterSignup() {
        try {
            // Click proceed to checkout
            wait.until(ExpectedConditions.elementToBeClickable(proceed_to_checkout_btn)).click();

            // Wait until the user redirected to the address details page
            wait.until(ExpectedConditions.presenceOfElementLocated(address_title));

            // Find and verify billing address title
            WebElement billingAddressElement = wait.until(
                    ExpectedConditions.presenceOfElementLocated(billing_address_div)
            );
            String billingAddressText = billingAddressElement.getText();

            // Find and verify delivery address title
            WebElement deliveryAddressElement = wait.until(
                    ExpectedConditions.presenceOfElementLocated(delivery_address_div)
            );
            String deliveryAddressText = deliveryAddressElement.getText();

            // Assert the titles are correct
            Assert.assertEquals(billingAddressText, "YOUR BILLING ADDRESS",
                    "Billing address title does not match expected text");
            Assert.assertEquals(deliveryAddressText, "YOUR DELIVERY ADDRESS",
                    "Delivery address title does not match expected text");

            System.out.println("Address titles verified successfully:");
            System.out.println("Billing Address: " + billingAddressText);
            System.out.println("Delivery Address: " + deliveryAddressText);

        } catch (TimeoutException e) {
            System.out.println("Timed out waiting during checkout after signup.");
            throw e;
        } catch (AssertionError e) {
            System.out.println("Assertion failed: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println("Error during proceedToCartAfterSignup: " + e.getMessage());
            throw e;
        }
    }

    public void reviewOrder() {
        try {
            // Wait for the Review Order page to load
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(text(),'Review Your Order')]")));

            // Verify page title
            WebElement pageTitle = driver.findElement(By.xpath("//h2[contains(text(),'Review Your Order')]"));
            Assert.assertTrue(pageTitle.isDisplayed(), "Review Your Order page title should be visible");
            System.out.println("Review Order page loaded successfully");

            // Verify order table headers
            verifyTableHeaders();

            // Get and verify order item details
            Product product1 = product.getProductItemDetails();

            // Verify total amount
            String totalAmount = getTotalAmount();

            // Verify comment section
            verifyCommentSection();
            addComment("comment");

            wait.until(ExpectedConditions.elementToBeClickable(place_order_button)).click();

            System.out.println("Order review completed successfully");

        } catch (TimeoutException e) {
            System.out.println("Timed out waiting for Review Order page to load");
            throw e;
        } catch (Exception e) {
            System.out.println("Error during order review: " + e.getMessage());
            throw e;
        }
    }

    private void verifyCommentSection() {
        try {
            // Verify comment section
            WebElement commentLabel = driver.findElement(By.xpath("//text()[contains(.,'If you would like to add a comment about your order')]"));
            WebElement commentTextArea = driver.findElement(By.xpath("//textarea"));

            Assert.assertTrue(commentLabel != null, "Comment instruction text should be present");
            Assert.assertTrue(commentTextArea.isDisplayed(), "Comment text area should be visible");
            Assert.assertTrue(commentTextArea.isEnabled(), "Comment text area should be enabled");

            System.out.println("Comment section verified successfully");

        } catch (Exception e) {
            System.out.println("Error verifying comment section: " + e.getMessage());
            throw e;
        }
    }

    private void verifyTableHeaders() {
        try {
            // Verify all table headers are present
            String[] expectedHeaders = {"Item", "Description", "Price", "Quantity", "Total"};

            for (String header : expectedHeaders) {
                WebElement headerElement = driver.findElement(By.xpath("//th[contains(text(),'" + header + "')]"));
                Assert.assertTrue(headerElement.isDisplayed(), header + " header should be visible");
            }

            System.out.println("All table headers verified successfully");

        } catch (Exception e) {
            System.out.println("Error verifying table headers: " + e.getMessage());
            throw e;
        }
    }


    public void addComment(String comment) {
        try {
            WebElement commentTextArea = driver.findElement(By.xpath("//textarea"));
            commentTextArea.clear();
            commentTextArea.sendKeys(comment);

            System.out.println("Order comment added: " + comment);

        } catch (Exception e) {
            System.out.println("Error adding order comment: " + e.getMessage());
            throw e;
        }
    }
    private String getTotalAmount() {
        try {
            // Find and verify total amount
            WebElement totalAmountLabel = driver.findElement(By.xpath("//td[contains(text(),'Total Amount')]"));
            WebElement totalAmountValue = driver.findElement(By.xpath("//td[contains(text(),'Total Amount')]//following-sibling::td"));

            Assert.assertTrue(totalAmountLabel.isDisplayed(), "Total Amount label should be visible");
            Assert.assertTrue(totalAmountValue.isDisplayed(), "Total Amount value should be visible");

            String totalAmount = totalAmountValue.getText();
            Assert.assertEquals("Rs. 400", totalAmount, "Total amount should match expected value");

            System.out.println("Total amount verified: " + totalAmount);
            return totalAmount;

        } catch (Exception e) {
            System.out.println("Error verifying total amount: " + e.getMessage());
            throw e;
        }


    }





}
