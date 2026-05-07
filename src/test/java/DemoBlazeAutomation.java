import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DemoBlazeAutomation {

    private WebDriver driver;
    private WebDriverWait wait;
    private String uniqueUsername;
    private String uniquePassword;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.demoblaze.com/"); // Open the main page
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        uniqueUsername = "user_" + System.currentTimeMillis();
        uniquePassword = "password123";
    }

    @Test
    void userRegistrationAndLogin() {
        registerUser(uniqueUsername, uniquePassword);
        loginUser(uniqueUsername, uniquePassword);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("nameofuser"), "Welcome " + uniqueUsername));
    }

    @Test
    void loginAndAddProducts() {
        registerUser(uniqueUsername, uniquePassword);
        loginUser(uniqueUsername, uniquePassword);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("nameofuser"), "Welcome " + uniqueUsername));

        addProductToCart("Laptops", "Sony vaio i5");
        addProductToCart("Laptops", "Sony vaio i7");
        addProductToCart("Monitors", "ASUS Full HD");
        addProductToCart("Monitors", "Apple monitor 24");
        addProductToCart("Phones", "Samsung galaxy s6");
        addProductToCart("Phones", "Nokia lumia 1520");
        addProductToCart("Phones", "HTC One M9");
    }

    @Test
    void checkoutAndConfirmation() {
        registerUser(uniqueUsername, uniquePassword);
        loginUser(uniqueUsername, uniquePassword);

        addProductToCart("Laptops", "Sony vaio i5");
        addProductToCart("Monitors", "ASUS Full HD");
        addProductToCart("Monitors", "Apple monitor 24");
        addProductToCart("Phones", "Samsung galaxy s6");
        addProductToCart("Phones", "Nokia lumia 1520");
        addProductToCart("Phones", "HTC One M9");

        WebElement cartLink = driver.findElement(By.linkText("Cart"));
        cartLink.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));

        assertCartContains("Sony vaio i5");
        assertCartContains("ASUS Full HD");
        assertCartContains("Apple monitor 24");
        assertCartContains("Samsung galaxy s6");
        assertCartContains("Nokia lumia 1520");
        assertCartContains("HTC One M9");

        WebElement placeOrderButton = driver.findElement(By.id("placeOrder"));
        placeOrderButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("orderModal")));

        driver.findElement(By.id("name")).sendKeys("Dummy Name");
        driver.findElement(By.id("country")).sendKeys("Dummy Country");
        driver.findElement(By.id("city")).sendKeys("Dummy City");
        driver.findElement(By.id("card")).sendKeys("4111111111111111");
        driver.findElement(By.id("month")).sendKeys("12");
        driver.findElement(By.id("year")).sendKeys("2030");

        driver.findElement(By.cssSelector("button[onclick='purchaseOrder()']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sweet-alert")));

        String confirmationMessage = driver.findElement(By.cssSelector(".sweet-alert h2")).getText();
        System.out.println(confirmationMessage);
    }

    @Test
    void sendContactMessage() {
        WebElement contactLink = driver.findElement(By.linkText("Contact"));
        contactLink.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("exampleModal")));

        driver.findElement(By.id("recipient-name")).sendKeys("Dummy Name");
        driver.findElement(By.id("recipient-email")).sendKeys("dummy@example.com");
        driver.findElement(By.id("message-text")).sendKeys("Hello, this is a dummy contact message!");

        WebElement submitButton = driver.findElement(By.cssSelector("button[onclick='send()']"));
        submitButton.click();

        wait.until(ExpectedConditions.alertIsPresent());
        String alertMessage = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        System.out.println(alertMessage);
    }

    private void registerUser(String username, String password) {
        WebElement signUpLink = driver.findElement(By.linkText("Sign up"));
        signUpLink.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signInModal")));

        driver.findElement(By.id("sign-username")).sendKeys(username);
        driver.findElement(By.id("sign-password")).sendKeys(password);
        driver.findElement(By.cssSelector("#signInModal button[onclick='register()']")).click();

        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    private void loginUser(String username, String password) {
        WebElement logInLink = driver.findElement(By.linkText("Log in"));
        logInLink.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logInModal")));

        driver.findElement(By.id("loginusername")).sendKeys(username);
        driver.findElement(By.id("loginpassword")).sendKeys(password);
        driver.findElement(By.cssSelector("#logInModal button[onclick='logIn()']")).click();
    }

    private void addProductToCart(String category, String productName) {
        driver.findElement(By.linkText("Home")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("contcont")));

        driver.findElements(By.id("itemc")).stream()
                .filter(element -> element.getText().equalsIgnoreCase(category))
                .findFirst()
                .ifPresent(WebElement::click);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(productName)));
        driver.findElement(By.linkText(productName)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Add to cart")));

        driver.findElement(By.linkText("Add to cart")).click();
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    private void assertCartContains(String productName) {
        String cartText = driver.findElement(By.id("tbodyid")).getText();
        assertEquals(true, cartText.contains(productName));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}