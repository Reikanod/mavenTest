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
    public WebElement acceptCookieButton() { return waitElement(By.cssSelector(".btn.btn_black.cookie__ok")); }
    public void selectCommunicationServices() { selectInChoosableElement("Communication services"); }
    public void selectHomeInternet() { selectInChoosableElement("Home Internet"); }
    public WebElement getPhoneInputInHomeInternet() { return payForm.findElement(By.cssSelector(".phone#internet-phone")); }
    public WebElement getSumInHomeInternet() { return payForm.findElement(By.cssSelector(".total_rub#internet-sum")); }
    public WebElement getMailInHomeInternet() { return payForm.findElement(By.cssSelector(".email#internet-email")); }
    public void selectInstallment() { selectInChoosableElement("Installment");  }
    public WebElement getScoreInstallment() { return payForm.findElement(By.cssSelector(".score#score-instalment")); }
    public WebElement getSumInInstallment() { return payForm.findElement(By.cssSelector(".total_rub#instalment-sum")); }
    public WebElement getMailInInstallment() { return payForm.findElement(By.cssSelector(".email#instalment-email")); }
    public void selectDebt() { selectInChoosableElement("Debt");  }
    public WebElement getScoreDebt() { return payForm.findElement(By.cssSelector(".score#score-arrears")); }
    public WebElement getSumInDebt() { return payForm.findElement(By.cssSelector(".total_rub#arrears-sum")); }
    public WebElement getMailInDebt() { return payForm.findElement(By.cssSelector(".email#arrears-email")); }

    // Вспомогательная функция выбора вида услуги
    private void selectInChoosableElement(String serviceName) {
        WebElement chooseButton = payForm.findElement(By.className("select__header"));
        chooseButton.click();
        List<WebElement> selectOptions = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".select__item"))
        );

        switch (serviceName) {
            case "Communication services": {
                selectOptions.get(0).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".pay-form#pay-connection")));
                break;
            }
            case "Home Internet": {
                selectOptions.get(1).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".pay-form#pay-internet")));
                break;
            }
            case "Installment": {
                selectOptions.get(2).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".pay-form#pay-instalment")));
                break;
            }
            case "Debt": {
                selectOptions.get(3).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".pay-form#pay-arrears")));
                break;
            }
            default: {
                throw new IllegalArgumentException("Неизвестное имя услуги: " + serviceName);
            }

        }

    }



    // Локаторы, когда мы во фрейме CredentialsForm
    public WebElement getCardInput() { return waitElement(By.cssSelector("#cc-number")); }

    public WebElement getCardInputPlaceholderInCredentialsForm() {
        return waitElement(By.xpath("//input[@id='cc-number']/following-sibling::label"));
    }
    public WebElement getCardTimeInCredentialsForm() {
        return waitElement(By.xpath("//input[@formcontrolname='expirationDate']/following::label[1]"));
    }
    public WebElement getCvcPlaceholderInCredentialsForm() {
        return waitElement(By.xpath("//input[@formcontrolname='cvc']/following::label[1]"));
    }
    public WebElement getNamePlaceholderInCredentialsForm() {
        return waitElement(By.xpath("//input[@formcontrolname='holder']/following::label[1]"));
    }

    public WebElement getPayCostInCredentialsForm() {
        return waitElement(By.xpath("//div[@class='pay-description__cost']//span"));
    }
    public WebElement getDescriptionCostInCredentialsForm() {
        return waitElement(By.xpath("//div[@class='pay-description__text']//span"));
    }
    public WebElement getSubmitButtonInCredentialsForm() { return waitElement(By.className("colored")); }




}
