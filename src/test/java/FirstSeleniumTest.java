import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

    public class FirstSeleniumTest {

        private WebDriver driver;

        @Test
        void testSeleniumWebForm() {
            driver = new ChromeDriver();

            driver.get("https://www.selenium.dev/selenium/web/web-form.html");

            String title = driver.getTitle();
            assertEquals("Web form", title);

            WebElement textBox = driver.findElement(By.name("my-text"));
            textBox.sendKeys("Hello Selenium");

            WebElement submitButton = driver.findElement(By.cssSelector("button"));
            submitButton.click();

            WebElement message = driver.findElement(By.id("message"));
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
