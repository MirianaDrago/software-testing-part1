package automation.task1;

import dev.failsafe.internal.util.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import static automation.task1.screenscraper.*;

public class websitetest {

    WebDriver driver;

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver","/home/miriana/webdrivers/chromedriver.exe");
        driver = new ChromeDriver();
    }
    @Test
    public void visitWebsite() {
        driver.get("https://www.emotionimports.com");
        driver.manage().window().maximize();
    }

    @Test
    public void clickOnSearchAndCheckURL() {
        visitWebsite();
        WebElement searchCars = driver.findElement(By.id("mega-menu-item-374"));
        Assertions.assertEquals("Search Cars",searchCars.getText());
        searchCars.click();
        Assertions.assertEquals("https://emotionimports.com/cars/", driver.getCurrentUrl());
    }

    @Test
    public void fillInSearchOptionsForm() throws InterruptedException {
        visitWebsite();
        clickOnSearchAndCheckURL();

        //car make
        WebElement CarMakeButton = driver.findElement(By.className("sf-field-post-meta-make"));
        CarMakeButton.click();
        Select selectMake = new Select(driver.findElement(By.name("_sfm_make[]")));
        selectMake.selectByValue("BMW");
        Thread.sleep(1000);

        //car model
        WebElement CarModelButton = driver.findElement(By.className("sf-field-post-meta-model"));
        CarModelButton.click();
        Select selectModel = new Select(driver.findElement(By.name("_sfm_model[]")));
        selectModel.selectByValue("1 Series");
        Thread.sleep(1000);

        //car transmission
        WebElement CarTransmissionButton = driver.findElement(By.className("sf-field-post-meta-transmission"));
        CarTransmissionButton.click();
        Select selectTransmission = new Select(driver.findElement(By.name("_sfm_transmission[]")));
        selectTransmission.selectByValue("Manual");
        Thread.sleep(1000);

        //car body type
        WebElement CarBodyTypeButton = driver.findElement(By.className("sf-field-post-meta-body_type"));
        CarBodyTypeButton.click();
        Select selectBodyType = new Select(driver.findElement(By.name("_sfm_body_type[]")));
        selectBodyType.selectByValue("Hatchback");
        Thread.sleep(1000);

        WebElement searchBtn = driver.findElement(By.className("sf-field-submit"));
        searchBtn.submit();
        Thread.sleep(10000);
    }

    @Test
    public void viewPriceButton() throws InterruptedException {
        visitWebsite();
        clickOnSearchAndCheckURL();
        fillInSearchOptionsForm();
        driver.findElement(By.xpath("//*[@id=\"resultcontainer\"]/div[1]/div/div[2]/div[3]/a[1]")).click();
    }

    @AfterEach
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}
