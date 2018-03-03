package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AllFiltersPage extends BasePage {
    public AllFiltersPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = ".//input[@id='glf-pricefrom-var']")
    private WebElement setFromValueBtn;

    @FindBy(xpath = ".//span[text()='Показать подходящие']//..")
    private WebElement acceptBtn;

    public AllFiltersPage setFromValue(String value) {
        setFromValueBtn.sendKeys(value);
        return new AllFiltersPage(driver);
    }

    public AllFiltersPage choiceManufacturer(String name) {
        driver.findElement(By.xpath("//span[contains(text(),'Производитель')]//..//..//..//label[text()='"+name+"']")).click();
        return new AllFiltersPage(driver);
    }

    public SubcategoryPage acceptFilters() {
        acceptBtn.click();
        return new SubcategoryPage(driver);
    }

}
