import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class FirstTest {

    private WebDriver driver;

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        //System.setProperty("webdriver.gecko.driver", "drivers/geckodriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }

    @After
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void Test1(){

        // 1. Перейти по ссылке http://www.rgs.ru
        driver.get("https://www.rgs.ru/");

        // 2. Выбрать пункт меню - Страхование
        WebElement navbar = driver.findElement(By.xpath("//*[@id='main-navbar']"));
        navbar.findElement(By.xpath(".//a[contains(text(), 'Страхование')]")).click();

        // 3. Выбрать категорию - ДМС
        navbar.findElement(By.xpath(".//a[contains(text(), 'ДМС')]")).click();

        // 4. Проверить наличие заголовка - Добровольное медицинское страхование
        Assert.assertEquals("Заголовок ДМС не соответствует ожидаемому",
                "ДМС \uD83D\uDE91 Добровольное медицинское страхование, рассчитать стоимость в Росгосстрахе",
                driver.getTitle());
        // 5. Нажать на кнопку - Отправить заявку
        driver.findElement(By.xpath(".//a[contains(text(), 'Отправить заявку')]")).click();

        // 6. Проверить, что открылась страница , на которой присутствует текст - Заявка на добровольное медицинское страхование
        WebElement element = driver.findElement(By.xpath(".//*[@class='modal-title']/b"));
        String exptectedTitle = element.getAttribute("innerText");
        Assert.assertEquals("У модального окна не правильный заголовок h4","Заявка на добровольное медицинское страхование", exptectedTitle);

        // 7. Заполнить поля
        //driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.pollingEvery(100, TimeUnit.MILLISECONDS);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(".//*[@class='modal-title']/b"))));
        fillFieldUseChars("Сидоров", ".//label[text()='Фамилия']/following-sibling::input");
        fillFieldUseChars("Петр", ".//label[text()='Имя']/following-sibling::input");
        fillFieldUseChars("Васильевич", ".//label[text()='Отчество']/following-sibling::input");

        element = driver.findElement(By.name("Region"));
        Select select = new Select(element);
        select.selectByVisibleText("Тверская область");

        fillFieldUseChars("9197214920", ".//label[text()='Телефон']/following-sibling::input");
        fillFieldUseChars("abcmail.ru", ".//label[text()='Эл. почта']/following-sibling::input");
        fillFieldUseChars("01.01.2020", ".//label[text()='Предпочитаемая дата контакта']/following-sibling::input");
        fillFieldUseChars("Hello!", ".//label[text()='Комментарии']/following-sibling::textarea");
        driver.findElement(By.xpath(".//*[@class='checkbox']")).click();

        // 8. Проверить, что все поля заполнены введенными значениями
//        Assert.assertTrue("Не заполнено поле Фамилия", !driver.findElement(By.xpath(".//label[text()='Фамилия']/following-sibling::input")).getAttribute("value").isEmpty());
//        Assert.assertTrue("Не заполнено поле Имя", !driver.findElement(By.xpath(".//label[text()='Имя']/following-sibling::input")).getAttribute("value").isEmpty());
//        Assert.assertTrue("Не заполнено поле Отчество", !driver.findElement(By.xpath(".//label[text()='Отчество']/following-sibling::input")).getAttribute("value").isEmpty());
//        Assert.assertTrue("Не заполнено поле Регион", !driver.findElement(By.name("Region")).getAttribute("value").isEmpty());
//        Assert.assertTrue("Не заполнено поле Телефон", !driver.findElement(By.xpath(".//label[text()='Телефон']/following-sibling::input")).getAttribute("value").isEmpty());
//        Assert.assertTrue("Не заполнено поле Эл. почта", !driver.findElement(By.xpath(".//label[text()='Эл. почта']/following-sibling::input")).getAttribute("value").isEmpty());
//        Assert.assertTrue("Не заполнено поле Предпочитаемая дата контакта", !driver.findElement(By.xpath(".//label[text()='Предпочитаемая дата контакта']/following-sibling::input")).getAttribute("value").isEmpty());
//        Assert.assertTrue("Не заполнено поле Комментарии", !driver.findElement(By.xpath(".//label[text()='Комментарии']/following-sibling::textarea")).getAttribute("value").isEmpty());

        Assert.assertEquals("Неправильно заполнено поле Фамилия", "Сидоров", driver.findElement(By.xpath(".//label[text()='Фамилия']/following-sibling::input")).getAttribute("value"));
        Assert.assertEquals("Неправильно заполнено поле Имя", "Петр", driver.findElement(By.xpath(".//label[text()='Имя']/following-sibling::input")).getAttribute("value"));
        Assert.assertEquals("Неправильно заполнено поле Отчество", "Васильевич", driver.findElement(By.xpath(".//label[text()='Отчество']/following-sibling::input")).getAttribute("value"));
        Assert.assertEquals("Неправильно заполнено поле Регион", "69", driver.findElement(By.name("Region")).getAttribute("value"));
        Assert.assertEquals("Неправильно заполнено поле Телефон", "+7 (919) 721-49-20", driver.findElement(By.xpath(".//label[text()='Телефон']/following-sibling::input")).getAttribute("value"));
        Assert.assertEquals("Неправильно заполнено поле Эл. почта", "abcmail.ru", driver.findElement(By.xpath(".//label[text()='Эл. почта']/following-sibling::input")).getAttribute("value"));
        Assert.assertEquals("Неправильно заполнено поле Предпочитаемая дата контакта", "01.01.2020", driver.findElement(By.xpath(".//label[text()='Предпочитаемая дата контакта']/following-sibling::input")).getAttribute("value"));
        Assert.assertEquals("Неправильно заполнено поле Комментарии", "Hello!", driver.findElement(By.xpath(".//label[text()='Комментарии']/following-sibling::textarea")).getAttribute("value"));
        Assert.assertTrue("Не помечен чекбокс на обработку данных", driver.findElement(By.xpath(".//*[@class='checkbox']")).isSelected());

        // 9. Нажать Отправить
        driver.findElement(By.xpath(".//button[@id='button-m']")).click();

        // 0. Проверить, что у Поля - Эл. почта присутствует сообщение об ошибке - Введите корректный email
        Assert.assertTrue("Нет сообщения об ошибке - Введите адрес электронной почты", driver.findElement(By.xpath(".//*[contains(text(), 'Введите адрес электронной почты')]")).isDisplayed());

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
