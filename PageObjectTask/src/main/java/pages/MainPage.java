package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends BasePage{
    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public static MainPage onMainPage(WebDriver driver) {
        return new MainPage(driver);
    }

    @FindBy(xpath = ".//a[@data-id='market']")
    private WebElement yandexMarketBtn;

    public MarketPage openMarketPage() {
        yandexMarketBtn.click();
        return new MarketPage(driver);
    }
}
