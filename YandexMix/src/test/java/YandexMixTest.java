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

public class YandexMixTest {
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
    public void yandexTVTest() {
        // 1. Открыть браузер и развернуть на весь экран.
        // 2. Зайти на yandex.ru.
        // 3. Перейти в яндекс маркет
        // 4. Выбрать раздел Электроника
        openYandexMarketElectronicsCategory();

        // 5. Выбрать раздел Телевизоры
        openElectronicsSubCategory("//div[@class='catalog-menu__item']//a[contains(text(), 'Телевизоры')]");

        // 6. Зайти в расширенный поиск
        openAdvancedSearch();

        // 7. Задать параметр поиска от 20000 рублей.
        setSearchFromValue("20000");

        // 8. Выбрать производителей Samsung и LG
        choiceManufacturer("Samsung");
        choiceManufacturer("LG");

        // 9. Нажать кнопку Применить.
        clickAccept();

        // 10. Проверить, что элементов на странице 48.
        checkElementsCount("48", "//*[contains(@class, 'n-snippet-card2__title')]");

        // 11. Запомнить первый элемент в списке.
        scrollToElement(".//input[@id='header-search']");
        String firstElement = driver.findElement(By.xpath("//div[contains(@class, 'n-snippet-card2__title')]//a")).getText();

        // 12. В поисковую строку ввести запомненное значение.
        fillSearchWithRemValue(firstElement);

        // 13. Найти и проверить, что наименование товара соответствует запомненному значению.
        driver.findElement(By.xpath(".//span[@class='search2__button']//button")).click();
        compareTitleWithRemValue(firstElement);

    }

    @Test
    public void yandexHeadphonesTest(){
        // 1. Открыть браузер и развернуть на весь экран.
        // 2. Зайти на yandex.ru.
        // 3. Перейти в яндекс маркет
        // 4. Выбрать раздел Электроника
        openYandexMarketElectronicsCategory();

        // 5. Выбрать раздел Наушники
        openElectronicsSubCategory("//*[text()='Портативная техника']//following-sibling::div//a[contains(text(), 'Наушники и')]");

        // 6. Зайти в расширенный поиск
        openAdvancedSearch();

        // 7. Задать параметр поиска от 5000 рублей.
        setSearchFromValue("5000");

        // 8. Выбрать производителя Beats
        choiceManufacturer("Beats");

        // 9. Нажать кнопку Применить.
        clickAccept();

        /// 10. Проверить, что элементов на странице 22.
        checkElementsCount("22", "//*[contains(@class, 'n-snippet-cell2__title')]");

        // 11. Запомнить первый элемент в списке.
        scrollToElement(".//input[@id='header-search']");
        String firstElement = driver.findElement(By.xpath("//div[contains(@class, 'n-snippet-cell2__title')]//a")).getText();

        // 12. В поисковую строку ввести запомненное значение.
        fillSearchWithRemValue(firstElement);

        // 13. Найти и проверить, что наименование товара соответствует запомненному значению.
        //driver.findElement(By.xpath(".//span[@class='search2__button']//button")).click();
        driver.findElement(By.xpath("//div[contains(@class, 'n-snippet-cell2__title')]//a")).click();
        compareTitleWithRemValue(firstElement);
    }

    private void waitElement(String xpath){
        wait.pollingEvery(100, TimeUnit.MILLISECONDS);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(xpath))));
    }

    private void scrollToElement(String xpath){
        element = driver.findElement(By.xpath(xpath));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(false);", element);
    }

    private void  openYandexMarketElectronicsCategory(){
        driver.get("https://www.yandex.ru/");
        driver.findElement(By.xpath(".//a[@data-id='market']")).click();
        driver.findElement(By.xpath(".//a[contains(text(), 'Электроника')]")).click();
    }

    private void openElectronicsSubCategory(String xpath){
        waitElement(xpath);
        driver.findElement(By.xpath(xpath)).click();
    }

    private void openAdvancedSearch(){
        scrollToElement(".//a[contains(text(), 'Перейти ко всем фильтрам')]");
        driver.findElement(By.xpath(".//a[contains(text(), 'Перейти ко всем фильтрам')]")).click();
    }

    private void setSearchFromValue(String value) {
        driver.findElement(By.xpath(".//input[@id='glf-pricefrom-var']")).sendKeys(value);
    }

    private void choiceManufacturer(String name) {
        driver.findElement(By.xpath("//span[contains(text(),'Производитель')]//..//..//..//label[text()='"+name+"']")).click();
    }

    private void clickAccept(){
        driver.findElement(By.xpath(".//span[text()='Показать подходящие']//..")).click();
    }

    private void checkElementsCount(String count, String xpath){
        Assert.assertEquals("Кол-во элементов не равно "+count, count, String.valueOf(driver.findElements(By.xpath(xpath)).size()));
    }

    private void fillSearchWithRemValue(String value) {
        driver.findElement(By.xpath(".//input[@id='header-search']")).sendKeys(value);
    }

    private void compareTitleWithRemValue(String value) {
        waitElement(".//*[@class='n-title__text']//h1");
        Assert.assertEquals("Первый товар в списке не совпадает с запомненным", value, driver.findElement(By.xpath(".//*[@class='n-title__text']//h1")).getText());
    }
}
