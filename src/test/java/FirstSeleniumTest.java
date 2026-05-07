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

    public class FirstSeleniumTest {

        private WebDriver driver;
        private WebDriverWait wait;
        @BeforeEach
        void setUp() {
            driver = new ChromeDriver();
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }
        @Test
        void testSeleniumWebForm() {


            driver.get("https://www.selenium.dev/selenium/web/web-form.html");

            String title = driver.getTitle();
            assertEquals("Web form", title);

            WebElement textBox = driver.findElement(By.name("my-text"));
            textBox.sendKeys("Hello Selenium");

            WebElement submitButton = driver.findElement(By.cssSelector("button"));
            submitButton.click();

            WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
            assertEquals("Received!", message.getText());
        }
//
//        @AfterEach
//        void closeBrowser() {
//            if (driver != null) {
//                driver.quit();
//            }
//        }
    }
