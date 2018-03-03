import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.MainPage;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class YandexMarketTest {
    private WebDriver driver;
    private Properties properties = TestProperties.getInstance().getProperties();

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver"));
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(properties.getProperty("app.url"));

    }

    @Test
    public void yandexMarketTVTest() throws Exception {
        MainPage.onMainPage(driver)
                .openMarketPage()
                .openElectronicsCategoryPage()
                .openSubcategory("Телевизоры")
                .openTVAllFilters()
                .setFromValue("20000")
                .choiceManufacturer("Samsung")
                .choiceManufacturer("LG")
                .acceptFilters()
                .checkElementsCount("48", "//*[contains(@class, 'n-snippet-card2__title')]")
                .remFirstElement("fromList")
                .fillSearchWithRemValue()
                .search()
                .compareTitleWithRemValue();
    }

    @Test
    public void yandexMarketHeadphonesTest() throws Exception {
        MainPage.onMainPage(driver)
                .openMarketPage()
                .openElectronicsCategoryPage()
                .openSubcategory("Наушники")
                .openTVAllFilters()
                .setFromValue("5000")
                .choiceManufacturer("Beats")
                .acceptFilters()
                .checkElementsCount("22", "//*[contains(@class, 'n-snippet-cell2__title')]")
                .remFirstElement("fromCells")
                .fillSearchWithRemValue()
                .search()
                .openFirstElement()
                .compareTitleWithRemValue();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

}
