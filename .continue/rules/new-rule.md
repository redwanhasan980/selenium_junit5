# Rules for Java Selenium JUnit 5 Project

You are helping me write and maintain a Java test automation project using Selenium WebDriver and JUnit 5.

## Main Goal

Help me build clean, beginner-friendly Selenium automation tests using:

- Java
- IntelliJ IDEA
- Selenium WebDriver
- JUnit 5
- Maven or Gradle

Always write simple, readable code and explain things in beginner-friendly language when asked.

---

## Tech Stack Rules

Use:

- Java 21 or newer
- Selenium WebDriver 4
- JUnit 5 / JUnit Jupiter
- Maven preferred unless the project already uses Gradle
- Chrome browser with Selenium Manager

Do not use:

- JUnit 4
- TestNG unless I specifically ask
- Old Selenium 3 style
- Manual ChromeDriver setup unless absolutely necessary
- `Thread.sleep()` unless I specifically ask for a very simple demo

---

## Maven Rules

If the project uses Maven, use this type of dependency setup:

```xml
<dependencies>
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>4.43.0</version>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.14.4</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

Also use Maven Surefire plugin for running JUnit 5 tests.

---

## Gradle Rules

If the project uses Gradle, use this style:

```gradle
dependencies {
    testImplementation 'org.seleniumhq.selenium:selenium-java:4.43.0'
    testImplementation 'org.junit.jupiter:junit-jupiter:5.14.4'
}

test {
    useJUnitPlatform()
}
```

---

## JUnit 5 Rules

Always use JUnit 5 imports:

```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
```

Use JUnit 5 assertions:

```java
import static org.junit.jupiter.api.Assertions.*;
```

Use lifecycle methods like this:

```java
@BeforeEach
void setUp() {
}

@AfterEach
void tearDown() {
}
```

Never use JUnit 4 imports:

```java
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
```

---

## Selenium WebDriver Rules

Use this basic Selenium structure:

```java
private WebDriver driver;

@BeforeEach
void setUp() {
    driver = new ChromeDriver();
    driver.manage().window().maximize();
}

@AfterEach
void tearDown() {
    if (driver != null) {
        driver.quit();
    }
}
```

Use these imports when needed:

```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
```

---

## Wait Rules

Prefer explicit waits:

```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
```

Use imports:

```java
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
```

Avoid:

```java
Thread.sleep(3000);
```

Only use `Thread.sleep()` if I specifically ask for a quick beginner example.

---

## Locator Rules

Prefer stable locators in this order:

1. `id`
2. `name`
3. `cssSelector`
4. `xpath` only when necessary

Good examples:

```java
driver.findElement(By.id("username"));
driver.findElement(By.name("email"));
driver.findElement(By.cssSelector("button[type='submit']"));
```

Avoid fragile XPath like this:

```java
driver.findElement(By.xpath("/html/body/div[1]/div[2]/button"));
```

---

## Test Structure Rules

Each test should follow:

```text
Arrange
Act
Assert
```

Example:

```java
@Test
void userCanLoginWithValidCredentials() {
    // Arrange
    driver.get("https://example.com/login");

    // Act
    driver.findElement(By.id("username")).sendKeys("admin");
    driver.findElement(By.id("password")).sendKeys("password");
    driver.findElement(By.cssSelector("button[type='submit']")).click();

    // Assert
    assertTrue(driver.getCurrentUrl().contains("dashboard"));
}
```

---

## Test Naming Rules

Use clear test method names.

Good:

```java
userCanLoginWithValidCredentials()
errorMessageShowsForInvalidPassword()
searchReturnsMatchingProducts()
```

Bad:

```java
test1()
loginTest()
abc()
```

---

## Page Object Model Rules

When test code becomes large, suggest Page Object Model.

Page classes should:

- Represent one page
- Store locators
- Expose actions as methods
- Avoid test assertions inside page classes unless necessary

Example:

```java
public class LoginPage {

    private WebDriver driver;

    private By usernameInput = By.id("username");
    private By passwordInput = By.id("password");
    private By loginButton = By.cssSelector("button[type='submit']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String username) {
        driver.findElement(usernameInput).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }
}
```

---

## Beginner Explanation Rules

When explaining code:

- Explain step by step
- Use simple language
- Explain every important import
- Explain every annotation like `@Test`, `@BeforeEach`, and `@AfterEach`
- Explain why the code is needed
- Avoid advanced design unless I ask

---

## Debugging Rules

When fixing an error:

1. Identify the exact error
2. Explain what the error means
3. Show the corrected code
4. Explain why the fix works

Common things to check:

- Wrong JUnit import
- Maven or Gradle not synced
- Test file not inside `src/test/java`
- Missing `@Test`
- Missing dependency
- Browser driver issue
- Wrong locator
- Element not loaded yet
- Need explicit wait
- Browser closes too quickly

---

## IntelliJ Rules

When giving IntelliJ instructions:

- Mention exact menu names when possible
- Explain where to create files
- Explain where to paste code
- Explain how to reload Maven or sync Gradle
- Explain how to run a test class
- Explain how to run a single test method
- Explain why the green run button may not appear

---

## Code Quality Rules

Always write code that is:

- Simple
- Clean
- Beginner-friendly
- Properly indented
- Easy to understand
- Uses meaningful class names
- Uses meaningful method names
- Uses comments only when useful

Do not overcomplicate the code.

---

## Final Code Rule

When I ask for code, give the complete final code unless I specifically ask for only the changed part.

---

## Default Example Test Template

Use this as the default Selenium JUnit 5 test template:

```java
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FirstSeleniumTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    void userCanSubmitWebForm() {
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        WebElement textBox = driver.findElement(By.name("my-text"));
        textBox.sendKeys("Hello Selenium");

        WebElement submitButton = driver.findElement(By.cssSelector("button"));
        submitButton.click();

        WebElement message = driver.findElement(By.id("message"));
        assertEquals("Received!", message.getText());
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
```