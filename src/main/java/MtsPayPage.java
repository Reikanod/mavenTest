import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

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
    public WebElement getPhoneInput() { return payForm.findElement(By.cssSelector(".phone#connection-phone")); }
    public WebElement getSumInput() { return payForm.findElement(By.cssSelector(".total_rub#connection-sum")); }
    public WebElement getEmailInput() { return payForm.findElement(By.cssSelector(".email#connection-email")); }
    public WebElement getSubmitButton() { return payForm.findElement(By.cssSelector(".button.button__default ")); }
    public WebElement getPaymentCredentials() { return payForm.findElement(By.cssSelector(".bepaid-iframe")); }
    public WebElement acceptCookieButton() { return waitElement(By.cssSelector(".btn.btn_black.cookie__ok")); }
    public void selectCommunicationServices() { selectInChoosableElement("Communication services"); }
    public void selectHomeInternet() { selectInChoosableElement("Home Internet");  }
    public void selectInstallment() { selectInChoosableElement("Installment");  }
    public void selectDebt() { selectInChoosableElement("Debt");  }

    // Вспомогательная функция выбора вида услуги
    private void selectInChoosableElement(String serviceName) {
        WebElement chooseButton = payForm.findElement(By.className("select__header"));
        chooseButton.click();

        WebElement selectedOption;
        List<WebElement> options = chooseButton.findElements(By.tagName("li"));

        switch (serviceName) {
            case "Communication services": {
                selectedOption = options.get(0);
                waitElement(By.tagName("li"));
                wait.until(ExpectedConditions.elementToBeClickable(selectedOption));
                selectedOption.click();
            }
            case "Home Internet": {
                selectedOption = options.get(1);
                selectedOption.click();
            }
            case "Installment": {
                selectedOption = options.get(2);
                selectedOption.click();
            }
            case "Debt": {
                selectedOption = options.get(3);
                selectedOption.click();
            }
        }
    }



    // Локаторы, когда мы во фрейме CredentialsForm
    public WebElement getCardInput() { return waitElement(By.cssSelector("#cc-number")); }

}
