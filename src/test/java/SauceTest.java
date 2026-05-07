import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SauceTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String url ="https://www.saucedemo.com/";


    @BeforeEach
    void setup()
    {
         driver = new FirefoxDriver();
        driver.manage().window().maximize();
         driver.get(url);
         wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        Thread.sleep(1000);
         driver.quit();
    }

    void login(String username, String pass) throws InterruptedException {
        Thread.sleep(1000);
        WebElement userName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        userName.sendKeys(username);
        WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        password.sendKeys(pass);
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button"))).click();
        Thread.sleep(1000);
    }
    @Order(1)
    @DisplayName("Successful Login Test")
    @Test
    void testSuccessFullLogin() throws InterruptedException {
        login("standard_user","secret_sauce");
       // assertEquals("https://www.saucedemo.com/inventory.html",driver.getCurrentUrl());
        assertTrue(driver.getCurrentUrl().contains("inventory"),"Should redirect to inventory");
    }
    @Order(2)
    @DisplayName("Valid Login Test")
    @Test
    void testValidLogin() throws InterruptedException {
        login("standard_user","secret_sauce");
        // assertEquals("https://www.saucedemo.com/inventory.html",driver.getCurrentUrl());
        assertTrue(driver.getCurrentUrl().contains("inventory"),"Should redirect to inventory");
    }

    @Order(3)
    @DisplayName("Invalid Login Test")
    @Test
    void testInvalidInput() throws InterruptedException {
        login("invalid","invalid");
        Thread.sleep(2000);
        //assertEquals("https://www.saucedemo.com/",driver.getCurrentUrl());
       // WebElement error = driver.findElement(By.className("error-message-container"));
        WebElement error = driver.findElement(By.cssSelector("[data-test='error']"));
        assertTrue(error.getText().contains("Username and password do not match any"),"Should throw error message");
    }
    @Order(4)
    @DisplayName("Test Locked User")
    @Test
    void testLockedUser() throws InterruptedException {
        login("locked_out_user","secret_sauce");
        Thread.sleep(1000);
        WebElement error = driver.findElement(By.cssSelector("[data-test='error']"));
        assertTrue(error.getText().contains("Sorry, this user has been locked out."));

    }
    @Order(5)
    @DisplayName("Add to Cart")
    @Test
    void addToCart() throws InterruptedException {
        login("standard_user", "secret_sauce");
        Thread.sleep(1000);
        //List<WebElement> addToCartButton = driver.findElements(By.cssSelector(".btn_small"));
        List<WebElement> addToCartButton = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".btn_inventory")));

        assertTrue(addToCartButton.size()>0,"Add elements");
        addToCartButton.get(0).click();
        Thread.sleep(5000);
       // WebElement cartBadge = driver.findElement(By.cssSelector("[data-test='shopping-cart-badge']"));
  WebElement cartBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='shopping-cart-badge']")));
        assertTrue(cartBadge.isDisplayed(),"Should be added to cart");

        assertEquals("1",cartBadge.getText(),"Cart should be 1");
    }
    @Order(6)
    @DisplayName("Logout")
    @Test
    void logOut() throws InterruptedException {
        login("standard_user","secret_sauce");

        assertTrue(driver.getCurrentUrl().contains("inventory"),"Should redirect to inventory");
        WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("react-burger-menu-btn")));
        menu.click();
        //Thread.sleep(5000);
    wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link"))).click();
       // driver.findElement(By.id("logout_sidebar_link")).click();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link"))).click();
        //Thread.sleep(2000);
        assertEquals("https://www.saucedemo.com/",driver.getCurrentUrl(),"should be in the home page");
    }
    @Order(7)
    @DisplayName("Test Checkout Process")
    @Test
    void testCheckout( )throws InterruptedException {
        login("standard_user","secret_sauce");
        Thread.sleep(1000);
        addItemtoCart(0);
        Thread.sleep(1000);
        addItemtoCart(1);
        Thread.sleep(1000);
        WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".shopping_cart_link")));
        cartIcon.click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout"))).click();
        Thread.sleep(1000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name"))).sendKeys("Redwan");
          wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("last-name"))).sendKeys("hasan");
          wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("postal-code"))).sendKeys("1380");
        Thread.sleep(1000);
          wait.until(ExpectedConditions.elementToBeClickable(By.id("continue"))).click();
        Thread.sleep(1000);
   wait.until(ExpectedConditions.elementToBeClickable(By.id("finish"))).click();
   WebElement text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".complete-header")));
   assertEquals("Thank you for your order!",text.getText(),"Should be there");
    }
    @Order(8)
    @DisplayName("Test Checkout Field Empty")
    @Test
    void testCheckoutEmpty() throws InterruptedException {
        login("standard_user","secret_sauce");
        Thread.sleep(1000);
        addItemtoCart(0);
        Thread.sleep(1000);
        addItemtoCart(1);
        Thread.sleep(1000);
        WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".shopping_cart_link")));
        cartIcon.click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout"))).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name"))).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("last-name"))).sendKeys("hasan");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("postal-code"))).sendKeys("1380");
        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("continue"))).click();
        Thread.sleep(1000);
        WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));
        assertTrue(msg.getText().contains("Error: First Name is required"),"Should throw error");
    }

    @Order(8)
    @DisplayName("Using Xpath")
    @Test
    void xpathTest() throws InterruptedException {
        login("standard_user","secret_sauce");
        //find by xpath
        WebElement product = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//div[text()='Sauce Labs Backpack']")));
        assertTrue(product.isDisplayed(),"Not available");
        WebElement addToCart =driver.findElement(By.xpath
                ("//div[text()='Sauce Labs Backpack']" +
                        "/ancestor::div[@class='inventory_item']"+
                        "//button"
                ));
         addToCart.click();
         WebElement badge =driver.findElement(By.xpath("//span[@class='shopping_cart_badge']"));
         assertEquals("1",badge.getText());
    }
    void addItemtoCart(int index)
    {
       List<WebElement> addButton = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".btn_inventory")));
       assertTrue(addButton.size()>0,"Should have item");
       addButton.get(index).click();
    }
}
