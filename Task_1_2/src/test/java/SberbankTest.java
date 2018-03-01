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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class SberbankTest {
    private WebDriver driver;

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void SberbankRegionAndSocialMediaTest(){
        // 1. Перейти на страницу http://www.sberbank.ru/ru/person
        driver.get("http://www.sberbank.ru/ru/person");

        // 2. Нажать на кнопку выбора региона
        driver.findElement(By.xpath("//*[@class='sbrf-div-list-inner --area bp-area header-container']//span[@class='region-list__arrow']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.pollingEvery(100, TimeUnit.MILLISECONDS);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(".//div[@class='kit-modal-header']"))));

        // 3. В появившемся «окне»  при помощи поиска найти и выбрать Нижегородская область
        fillFieldUseChars("Ниже", ".//input[@placeholder='Введите название региона']");
        driver.findElement(By.xpath("//div[@data-value='52']")).click();

        // 4. Проверить, что на главной странице отображается выбранная область
        Assert.assertEquals("Не отображается регион Нижегородская область", "Нижегородская область",
                driver.findElement(By.xpath("//*[@class='sbrf-div-list-inner --area bp-area header-container']//span[@class='region-list__name']")).getText());

        // 5. Сделать скролл до footer объекта на главной странице.
        WebElement element = driver.findElement(By.xpath(".//div[@class='sbrf-div-list-inner --area bp-area footer-container']"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(false);", element);

        // 6. Проверить, что footer содержит значки социальных сетей
        Assert.assertTrue("Не отображается значок Facebook", driver.findElement(By.xpath(".//*[@class='social__icon social__icon_type_fb']")).isDisplayed());
        Assert.assertTrue("Не отображается значок Twitter", driver.findElement(By.xpath(".//*[@class='social__icon social__icon_type_tw']")).isDisplayed());
        Assert.assertTrue("Не отображается значок YouTube", driver.findElement(By.xpath(".//*[@class='social__icon social__icon_type_yt']")).isDisplayed());
        Assert.assertTrue("Не отображается значок Instagramm", driver.findElement(By.xpath(".//*[@class='social__icon social__icon_type_ins']")).isDisplayed());
        Assert.assertTrue("Не отображается значок Vkontakte", driver.findElement(By.xpath(".//*[@class='social__icon social__icon_type_vk']")).isDisplayed());
        Assert.assertTrue("Не отображается значок Одноклассники", driver.findElement(By.xpath(".//*[@class='social__icon social__icon_type_ok']")).isDisplayed());

    }

    private void fillFieldUseChars(String value, String xpath){
        WebElement el = driver.findElement(By.xpath(xpath));
        el.click();
        el.clear();
        for(int i=0; i<value.length(); i++){
            el.sendKeys(String.valueOf(value.charAt(i)));
        }
    }

}
