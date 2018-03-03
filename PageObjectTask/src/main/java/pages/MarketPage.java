package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class  MarketPage extends BasePage{
    public MarketPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = ".//a[contains(text(), 'Электроника')]")
    private WebElement openElectronicsCategoryBtn;

    public CategoryPage openElectronicsCategoryPage() {
        openElectronicsCategoryBtn.click();
        return new CategoryPage(driver);
    }
}
