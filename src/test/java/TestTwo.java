import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestTwo {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver= new ChromeDriver();
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");
        WebElement textBox = driver.findElement(By.id("my-text-id"));
        textBox.sendKeys("Hello Selenium");
        WebElement submit=driver.findElement(By.className("btn-outline-primary"));
        submit.click();
        Thread.sleep(2000);
        driver.quit();
    }


}
