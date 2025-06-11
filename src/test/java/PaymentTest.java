import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PaymentTest {

    private static WebDriver driver;
    private static MtsPayPage mtsPayPage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        mtsPayPage = new MtsPayPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void paymentTest() {
        Assertions.assertEquals(mtsPayPage.getHeader().getText(), "Онлайн пополнение\nбез комиссии");
    }
}
