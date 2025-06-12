import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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
    public void headerTest() {
        Assertions.assertEquals(mtsPayPage.getHeader().getText(), "Онлайн пополнение\nбез комиссии");
    }
    @Test
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
            Assertions.assertTrue( logosNames.contains(alt),"Неожданное значение alt: " + alt);
        }
    }
    @Test
    public void aboutServiceLink() {
        Assertions.assertEquals(
                "https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/",
                mtsPayPage.getAboutServiceLink().getAttribute("href")
        );
    }
    @Test
    public void checkPayFormTest() {
        WebElement phone = mtsPayPage.getPhonePlaceholder();
        WebElement summ = mtsPayPage.getSumPlaceholder();
        WebElement email = mtsPayPage.getEmailPlaceholder();
        WebElement button = mtsPayPage.getSubmitButton();

        phone.click();
        phone.sendKeys("297777777");
        summ.click();
        summ.sendKeys("200");
        email.click();
        email.sendKeys("reikanod@yandex.ru");
        button.click();

        mtsPayPage.moveToPaymentCredentialsFrame();
        WebElement card = mtsPayPage.getCardPlaceholder();
        Assertions.assertTrue(card.isDisplayed());
    }
}
