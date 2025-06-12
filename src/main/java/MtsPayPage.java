import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MtsPayPage {

    private static final int TIMEOUT_IN_SECONDS = 5;
    private WebDriverWait wait;
    private WebElement payForm;

    public MtsPayPage(WebDriver driver) {
        driver.get("http://www.mts.by/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_IN_SECONDS));
        payForm = waitElement(By.className("pay__wrapper"));
    }

    // Ожидание
    public WebElement waitElement(By locator) { return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)); }
    // Переключиться на фрейм
    public void moveToPaymentCredentialsFrame() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector(".bepaid-iframe")));
    }
    // Возвращаемся с фрейма на базовую страницу
    public void moveToHomePage(WebDriver driver) { driver.switchTo().defaultContent(); }
    // Принимаем куки
    public void acceptCookie() {
        try {
            acceptCookieButton().click();
        } catch (TimeoutException e) {
            // Если cookie нет - продолжаем
        }
    }

    // Локаторы
    public WebElement getHeader() { return payForm.findElement(By.tagName("h2")); }
    public WebElement getPartnerLogos() { return payForm.findElement(By.className("pay__partners")); }
    public WebElement getAboutServiceLink() { return payForm.findElement(By.tagName("a")); }
    public WebElement getSelectButton() { return payForm.findElement(By.className("select__now")); }
    public WebElement getPhonePlaceholder() { return payForm.findElement(By.cssSelector(".phone#connection-phone")); }
    public WebElement getSumPlaceholder() { return payForm.findElement(By.cssSelector(".total_rub#connection-sum")); }
    public WebElement getEmailPlaceholder() { return payForm.findElement(By.cssSelector(".email#connection-email")); }
    public WebElement getSubmitButton() { return payForm.findElement(By.cssSelector(".button.button__default ")); }
    public WebElement getPaymentCredentials() { return payForm.findElement(By.cssSelector(".bepaid-iframe")); }
    public WebElement acceptCookieButton() { return waitElement(By.cssSelector(".btn.btn_black.cookie__ok")); }
    // Локаторы, когда мы во фрейме CredentialsForm
    public WebElement getCardPlaceholder() { return waitElement(By.cssSelector("#cc-number")); }

}
