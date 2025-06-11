import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class FormTest {

    private static WebDriver driver;
    private static WebElement form;

    @BeforeAll
    static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        form = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("pay__wrapper")));
    }

    @Test
    void formTitleTest() {
        WebElement header = form.findElement(By.tagName("h2"));
        Assertions.assertEquals("Онлайн пополнение\nбез комиссии", header.getText());
    }

    @Test
    void payLogosTest() {
        WebElement partnersBlock = driver.findElement(By.className("pay__partners"));
        ArrayList<WebElement> ul = (ArrayList<WebElement>) partnersBlock.findElements(By.tagName("li"));
        ArrayList<String> logosNames = new ArrayList<>(List.of(
                "Visa", "Verified By Visa", "MasterCard", "MasterCard Secure Code", "Белкарт"
        ));

        for (WebElement li : ul) {
            WebElement img = li.findElement(By.tagName("img"));
            String src = img.getAttribute("src");
            String alt = img.getAttribute("alt");

            Assertions.assertNotNull(src, "Картинка без src");
            Assertions.assertNotNull(alt, "Картинка без alt");
            Assertions.assertTrue( logosNames.contains(alt),"Неожданное значение alt: " + alt);
        }
    }

    @Test
    void checkHrefTest() {
        WebElement href = form.findElement(By.tagName("a"));
        Assertions.assertEquals("https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/", href.getAttribute("href"));
    }

    @Test
    void checkPayFormTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement phone = form.findElement(By.cssSelector(".phone#connection-phone"));
        WebElement summ = form.findElement(By.cssSelector(".total_rub#connection-sum"));
        WebElement email = form.findElement(By.cssSelector(".email#connection-email"));
        WebElement button = form.findElement(By.cssSelector(".button.button__default "));

        try {
            WebElement cookie = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".btn.btn_black.cookie__ok")
                    )
            );
            cookie.click();
        } catch (TimeoutException e) {
            // Если cookie нет - продолжаем
        }

        phone.click();
        phone.sendKeys("297777777");
        summ.click();
        summ.sendKeys("200");
        email.click();
        email.sendKeys("reikanod@yandex.ru");
        button.click();

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector(".bepaid-iframe")));
        WebElement card = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#cc-number")));
        Assertions.assertNotNull(card, "Элемент #cc-number не найден");
        Assertions.assertTrue(card.isDisplayed(), "Элемент #cc-number не виден");
        driver.switchTo().defaultContent();
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

