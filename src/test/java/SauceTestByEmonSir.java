import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SauceTestByEmonSir {
    private WebDriver driver;
    private WebDriverWait wait;
    private String url ="https://www.saucedemo.com/";


    @BeforeEach
    void setup()
    {
         driver = new ChromeDriver();
         driver.get(url);
         wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() throws InterruptedException { Thread.sleep(1000);
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
        Thread.sleep(1000);
        assertTrue(driver.getCurrentUrl().contains("inventory"),"Should redirect to inventory");
        WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("react-burger-menu-btn")));
        menu.click();
        Thread.sleep(1000);
//        wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link"))).click();
       // driver.findElement(By.id("logout_sidebar_link")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link"))).click();
        Thread.sleep(2000);
        assertEquals("https://www.saucedemo.com/",driver.getCurrentUrl(),"should be in the home page");
    }
}
