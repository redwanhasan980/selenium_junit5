import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Driver;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCase {
    public String url = "https://www.saucedemo.com/";
    WebDriver driver = new ChromeDriver();
@BeforeEach
void setUp(){
    driver.get(url);
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
}

@AfterEach
void tearDown() throws InterruptedException {
    Thread.sleep(3000);
    driver.quit();
}

    @Test
    void login() throws InterruptedException {

        WebElement username = driver.findElement(By.id("user-name"));
        username.sendKeys("standard_user");
        WebElement pass =driver.findElement(By.id("password"));
        pass.sendKeys("secret_sauces");
        Thread.sleep(3000);
        WebElement submit = driver.findElement(By.id("login-button"));
        submit.click();
        assertEquals("https://www.saucedemo.com/inventory.html",driver.getCurrentUrl());

    }
    @Test
    void loginError()
    {
        WebElement username= driver.findElement(By.id("user-name"));
        username.sendKeys("standard_user");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("secret_sauces");
        driver.findElement(By.id("login-button")).click();
        WebElement errormsg = driver.findElement(By.className("error-message-container"));
        assertTrue(errormsg.getText().contains("Username and password do not match"));
    }
}
