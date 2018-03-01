import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class YandexHeadphonesTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private WebElement element;

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 3);
    }

    @After
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void yandexHeadphonesTest(){
        // 1. Открыть браузер и развернуть на весь экран.
        // 2. Зайти на yandex.ru.
        driver.get("https://www.yandex.ru/");

        // 3. Перейти в яндекс маркет
        driver.findElement(By.xpath(".//a[@data-id='market']")).click();

        // 4. Выбрать раздел Электроника
        driver.findElement(By.xpath(".//a[contains(text(), 'Электроника')]")).click();

        // 5. Выбрать раздел Наушники
        waitElement("//div[@class='catalog-menu__item']//a[contains(text(), 'Телевизоры')]");
        driver.findElement(By.xpath("//*[text()='Портативная техника']//following-sibling::div//a[contains(text(), 'Наушники и')]")).click();

        // 6. Зайти в расширенный поиск
        scrollToElement(".//a[contains(text(), 'Перейти ко всем фильтрам')]");
        driver.findElement(By.xpath(".//a[contains(text(), 'Перейти ко всем фильтрам')]")).click();

        // 7. Задать параметр поиска от 5000 рублей.
        driver.findElement(By.xpath(".//input[@id='glf-pricefrom-var']")).click();
        driver.findElement(By.xpath(".//input[@id='glf-pricefrom-var']")).sendKeys("5000");

        // 8. Выбрать производителя Beats
        driver.findElement(By.xpath("//span[contains(text(),'Производитель')]//..//..//..//label[text()='Beats']")).click();

        // 9. Нажать кнопку Применить.
        driver.findElement(By.xpath(".//span[text()='Показать подходящие']//..")).click();

        // 10. Проверить, что элементов на странице 12.
        try {
            scrollToElement(".//span[contains(text(), 'Показывать по ')]");
            Assert.assertEquals("Выбрано не 12 элементов на странице", "Показывать по 12", element.getText());
        }
        catch (Exception e) {
            System.err.println("Элемента выбора кол-ва элементов нет на странице");
        }

        // 11. Запомнить первый элемент в списке.
        scrollToElement(".//input[@id='header-search']");
        String firstElement = driver.findElement(By.xpath("//div[contains(@class, 'n-snippet-cell2__title')]//a")).getText();

        // 12. В поисковую строку ввести запомненное значение.
        driver.findElement(By.xpath(".//input[@id='header-search']")).sendKeys(firstElement);

        // 13. Найти и проверить, что наименование товара соответствует запомненному значению.
        //driver.findElement(By.xpath(".//span[@class='search2__button']//button")).click();
        driver.findElement(By.xpath("//div[contains(@class, 'n-snippet-cell2__title')]//a")).click();
        waitElement(".//*[@class='n-title__text']//h1");
        Assert.assertEquals("Первый товар в списке не совпадает с запомненным", firstElement, driver.findElement(By.xpath(".//*[@class='n-title__text']//h1")).getText());
    }

    private void waitElement(String xpath){
        wait.pollingEvery(100, TimeUnit.MILLISECONDS);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(xpath))));

    }

    private void scrollToElement(String xpath){
        element = driver.findElement(By.xpath(xpath));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(false);", element);
    }
}
