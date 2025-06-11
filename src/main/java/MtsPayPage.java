import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MtsPayPage {

    private static final int TIMEOUT_IN_SECONDS = 10;
    private WebDriverWait wait;
    private WebElement payForm;

    public MtsPayPage(WebDriver driver) {
        driver.get("https://www.mts.by/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_IN_SECONDS));
        payForm = waitElement(By.className("pay__wrapper"));
    }

    // Ожидание
    public WebElement waitElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Геттер
    public WebElement getPayForm() {
        return payForm;
    }

    // Локаторы
    public WebElement getHeader() {
        return payForm.findElement(By.tagName("h2"));
    }
    public WebElement getPartnerLogos() {
        return waitElement(By.className("pay__partners"));
    }
    public WebElement getAboutServiceLink() {
        return waitElement(By.tagName("a"));
    }
    public WebElement getSelectButton() {
        return waitElement(By.className("select__now"));
    }
    public WebElement getPhonePlaceholder() {
        return waitElement(By.cssSelector(".phone#connection-phone"));
    }
    public WebElement getSumPlaceholder() {
        return waitElement(By.cssSelector(".total_rub#connection-sum"));
    }
    public WebElement getEmailPlaceholder() {
        return waitElement(By.cssSelector(".email#connection-email"));
    }
    public WebElement getSubmitButton() {
        return waitElement(By.cssSelector(".button.button__default "));
    }

}
