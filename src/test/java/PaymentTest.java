import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.qameta.allure.*;

import java.util.ArrayList;
import java.util.List;



@Epic("Тема: Selenium. Часть 2")
@Feature("UI-тесты с отчетами")
@Story("Проверка оформления заказа")
@Severity(SeverityLevel.CRITICAL)
@DisplayName("Проверка оформления заказа")
public class PaymentTest {

    public static WebDriver driver;
    private static MtsPayPage mtsPayPage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        mtsPayPage = new MtsPayPage(driver);
        mtsPayPage.acceptCookie();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @Step("Проверка заголовка на странице")
    public void headerTest() {
        Assertions.assertEquals(mtsPayPage.getHeader().getText(), "Онлайн пополнение\nбез комиссии");
    }

    @Test
    @Step("Проверка партнеров")
    public void partnerLogosTest() {
        ArrayList<WebElement> ul = (ArrayList<WebElement>) mtsPayPage.getPartnerLogos().findElements(By.tagName("li"));
        ArrayList<String> logosNames = new ArrayList<>(List.of(
                "Visa", "Verified By Visa", "MasterCard", "MasterCard Secure Code", "Белкарт"
        ));

        for (WebElement li : ul) {
            WebElement img = li.findElement(By.tagName("img"));
            String src = img.getAttribute("src");
            String alt = img.getAttribute("alt");

            Assertions.assertNotNull(src, "Картинка без src");
            Assertions.assertNotNull(alt, "Картинка без alt");
            Assertions.assertTrue(logosNames.contains(alt), "Неожданное значение alt: " + alt);
        }
    }

    @Test
    @Step("Проверка ссылки")
    public void aboutServiceLink() {
        Assertions.assertEquals(
                "https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/",
                mtsPayPage.getAboutServiceLink().getAttribute("href")
        );
    }

    @Test
    @Step("Проверка формы оплаты")
    public void checkPayFormTest() {
        WebElement phone = mtsPayPage.getPhoneInput();
        WebElement summ = mtsPayPage.getSumInput();
        WebElement email = mtsPayPage.getEmailInput();
        WebElement button = mtsPayPage.getSubmitButton();

        phone.click();
        phone.sendKeys("297777777");
        summ.click();
        summ.sendKeys("200");
        email.click();
        email.sendKeys("reikanod@yandex.ru");
        button.click();

        mtsPayPage.moveToPaymentCredentialsFrame();
        WebElement card = mtsPayPage.getCardInput();
        Assertions.assertTrue(card.isDisplayed());
    }

    @Test
    @Step("Проверка плейсхолдеров")
    public void checkPlaceholdersInCommunicationServices() {
        mtsPayPage.selectCommunicationServices();
        Assertions.assertEquals("Номер телефона", mtsPayPage.getPhoneInput().getAttribute("placeholder"));
        Assertions.assertEquals("Сумма", mtsPayPage.getSumInput().getAttribute("placeholder"));
        Assertions.assertEquals("E-mail для отправки чека", mtsPayPage.getEmailInput().getAttribute("placeholder"));
    }

    @Test
    @Step("Проверка плейсхолдеров")
    public void checkPlaceholdersInHomeInternet() {
        mtsPayPage.selectHomeInternet();
        Assertions.assertEquals("Номер абонента", mtsPayPage.getPhoneInputInHomeInternet().getAttribute("placeholder"));
        Assertions.assertEquals("Сумма", mtsPayPage.getSumInHomeInternet().getAttribute("placeholder"));
        Assertions.assertEquals("E-mail для отправки чека", mtsPayPage.getMailInHomeInternet().getAttribute("placeholder"));
    }

    @Test
    @Step("Проверка плейсхолдеров")
    public void checkPlaceholdersInInstallment() {
        mtsPayPage.selectInstallment();
        Assertions.assertEquals("Номер счета на 44", mtsPayPage.getScoreInstallment().getAttribute("placeholder"));
        Assertions.assertEquals("Сумма", mtsPayPage.getSumInInstallment().getAttribute("placeholder"));
        Assertions.assertEquals("E-mail для отправки чека", mtsPayPage.getMailInInstallment().getAttribute("placeholder"));
    }

    @Test
    @Step("Проверка плейсхолдеров")
    public void checkPlaceholdersInDebt() {
        mtsPayPage.selectDebt();
        Assertions.assertEquals("Номер счета на 2073", mtsPayPage.getScoreDebt().getAttribute("placeholder"));
        Assertions.assertEquals("Сумма", mtsPayPage.getSumInDebt().getAttribute("placeholder"));
        Assertions.assertEquals("E-mail для отправки чека", mtsPayPage.getMailInDebt().getAttribute("placeholder"));
    }

    @Test
    @Step("Проверка формы оплаты расширенная")
    public void extraCheckPayFormTest() {
        WebElement phone = mtsPayPage.getPhoneInput();
        WebElement summ = mtsPayPage.getSumInput();
        WebElement email = mtsPayPage.getEmailInput();
        WebElement button = mtsPayPage.getSubmitButton();

        phone.click();
        phone.sendKeys("297777777");
        summ.click();
        summ.sendKeys("200");
        email.click();
        email.sendKeys("reikanod@yandex.ru");
        button.click();

        mtsPayPage.moveToPaymentCredentialsFrame();
        WebElement cardPlaceholder = mtsPayPage.getCardInputPlaceholderInCredentialsForm();
        WebElement cvcPlaceholder = mtsPayPage.getCvcPlaceholderInCredentialsForm();
        WebElement expiredPlaceholder = mtsPayPage.getCardTimeInCredentialsForm();
        WebElement namePlaceholder = mtsPayPage.getNamePlaceholderInCredentialsForm();

        WebElement totalCost = mtsPayPage.getPayCostInCredentialsForm();
        WebElement descriptionCost = mtsPayPage.getDescriptionCostInCredentialsForm();
        WebElement submitButton = mtsPayPage.getSubmitButtonInCredentialsForm();

        Assertions.assertEquals("Номер карты", cardPlaceholder.getText());
        Assertions.assertEquals("Срок действия", expiredPlaceholder.getText());
        Assertions.assertEquals("CVC", cvcPlaceholder.getText());
        Assertions.assertEquals("Имя и фамилия на карте", namePlaceholder.getText());

        Assertions.assertEquals("200.00 BYN", totalCost.getText().trim());
        Assertions.assertEquals("Оплата: Услуги связи Номер:375297777777", descriptionCost.getText().trim());
        Assertions.assertEquals("Оплатить 200.00 BYN", submitButton.getText().trim());
    }

}