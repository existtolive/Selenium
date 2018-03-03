package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SubcategoryPage extends BasePage {
    public SubcategoryPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    private static String firstElement;

    @FindBy(xpath = ".//a[contains(text(), 'Перейти ко всем фильтрам')]")
    private WebElement openTVAllFiltersBtn;

    @FindBy(xpath = "//div[contains(@class, 'n-snippet-card2__title')]//a")
    private WebElement elementFromList;

    @FindBy(xpath = "//div[contains(@class, 'n-snippet-cell2__title')]//a")
    private WebElement elementFromCells;

    @FindBy(xpath = "//input[@id='header-search']")
    private WebElement searchInput;

    @FindBy(xpath = ".//span[@class='search2__button']//button")
    private WebElement searchBtn;

    @FindBy(xpath = ".//*[@class='n-title__text']//h1")
    private WebElement productTitle;


    public AllFiltersPage openTVAllFilters() {
        openTVAllFiltersBtn.click();
        return new AllFiltersPage(driver);
    }

    public SubcategoryPage checkElementsCount(String count, String xpath){
        Assert.assertEquals("Кол-во элементов не равно "+count, count, String.valueOf(driver.findElements(By.xpath(xpath)).size()));
        return new SubcategoryPage(driver);
    }

    public SubcategoryPage remFirstElement(String view){
        if(view.equals("fromList"))
            firstElement = elementFromList.getText();
        if(view.equals("fromCells"))
            firstElement = elementFromCells.getText();
        return new SubcategoryPage(driver);
    }

    public SubcategoryPage fillSearchWithRemValue(){
        searchInput.sendKeys(firstElement);
        return new SubcategoryPage(driver);
    }

    public SubcategoryPage search(){
        searchBtn.click();
        return new SubcategoryPage(driver);
    }

    public SubcategoryPage compareTitleWithRemValue(){
        Assert.assertEquals("Первый товар в списке не совпадает с запомненным", firstElement, productTitle.getText());
        return new SubcategoryPage(driver);
    }

    public SubcategoryPage openFirstElement(){
        elementFromCells.click();
        return new SubcategoryPage(driver);
    }

}
