package testautomation.task2;

import automation.task1.alert;
import automation.task1.screenscraper;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class cucumbertest {

    String postedBy = "bb59b5de-29c0-44a1-9788-d3f03719a886";
    String InvalidpostedBy = "f8sdff-sf4-s-vgfs";
    int numOfAlerts = 0;
    WebDriver driver;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver","/home/miriana/webdrivers/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Given("I am a user of marketalertum")
    public void iAmAUserOfMarketalertum() {
        driver.get("https://www.marketalertum.com/");
        driver.manage().window().maximize();
    }

    @When("I login using valid credentials")
    public void iLoginUsingValidCredentials() {
        WebElement loginMenuOption = driver.findElement(By.xpath("/html/body/header/nav/div/div/ul/li[3]/a"));
        loginMenuOption.click();
        WebElement UserIDtextBox = driver.findElement(By.id("UserId"));
        UserIDtextBox.sendKeys(postedBy);
        UserIDtextBox.submit();
    }

    @Then("I should see my alerts")
    public void iShouldSeeMyAlerts() {
        Assertions.assertEquals("Latest alerts for Miriana Drago",driver.findElement(By.xpath("/html/body/div/main/h1")).getText());
        Assertions.assertEquals("https://www.marketalertum.com/Alerts/List",driver.getCurrentUrl());
    }

    @When("I login using invalid credentials")
    public void iLoginUsingInvalidCredentials() {
        WebElement loginMenuOption = driver.findElement(By.xpath("/html/body/header/nav/div/div/ul/li[3]/a"));
        loginMenuOption.click();
        WebElement UserIDtextBox = driver.findElement(By.id("UserId"));
        UserIDtextBox.sendKeys(InvalidpostedBy);
        UserIDtextBox.submit();
    }

    @Then("I should see the login screen again")
    public void iShouldSeeTheLoginScreenAgain() {
        Assertions.assertEquals("https://www.marketalertum.com/Alerts/Login",driver.getCurrentUrl());
    }

    @Given("I am an administrator of the website and I upload {int} alerts")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAlerts(int arg0) throws Exception {
        //used the same alert for all 3
        for (int i = 0; i<arg0; i++) {
            alert newAlert = new alert(1,
                    "This is a heading test",
                    "This is a description test",
                    "https://emotionimports.com/cars/210707182659/",
                    "https://imgd-ct.aeplcdn.com/370x208/n/cw/ec/106257/venue-exterior-right-front-three-quarter-2.jpeg?isig=0&q=75",
                    postedBy,
                    160000);
            screenscraper.sendRequest(newAlert);
        }
    }
    @When("I view a list of alerts")
    public void iViewAListOfAlerts() {
        for (WebElement elem : driver.findElements(By.tagName("table"))) {
            //System.out.println(elem.getText() + " ");
            numOfAlerts++;
        }
        System.out.println("The number of alerts on the current page is " + numOfAlerts);
    }

    @Then("each alert should contain an icon")
    public void eachAlertShouldContainAnIcon() {
        for (int i = 1; i <= numOfAlerts; i++) {
            driver.findElement(By.xpath("/html/body/div/main/table[" + i + "]/tbody/tr[1]/td/h4/img")).isDisplayed();
        }
    }

    @And("each alert should contain a heading")
    public void eachAlertShouldContainAHeading() {
        for (int i = 1; i <= numOfAlerts; i++) {
            String heading = driver.findElement(By.xpath("/html/body/div/main/table[" + i + "]/tbody/tr[1]/td/h4")).getText();
            heading = heading.substring(0, heading.lastIndexOf(" (")); //space is necessary
            Assertions.assertEquals("This is a heading test", heading);
        }
    }

    @And("each alert should contain a description")
    public void eachAlertShouldContainADescription() {
        for (int i = 1; i <= numOfAlerts; i++) {
            String description = driver.findElement(By.xpath("/html/body/div/main/table[" + i + "]/tbody/tr[3]/td")).getText();
            Assertions.assertEquals("This is a description test", description);
        }
    }

    @And("each alert should contain an image")
    public void eachAlertShouldContainAnImage() {
        for (int i = 1; i <= numOfAlerts; i++) {
            driver.findElement(By.xpath("/html/body/div/main/table[" + i + "]/tbody/tr[2]/td/img")).isDisplayed();
        }
    }

    @And("each alert should contain a price")
    public void eachAlertShouldContainAPrice() {
        for (int i = 1; i <= numOfAlerts; i++) {
            driver.findElement(By.xpath("/html/body/div/main/table[" + i + "]/tbody/tr[4]/td")).isDisplayed();
        }
    }

    @And("each alert should contain a link to the original product website")
    public void eachAlertShouldContainALinkToTheOriginalProductWebsite() {
        for (int i = 1; i <= numOfAlerts; i++) {
            String link = driver.findElement(By.xpath("/html/body/div/main/table[" + i + "]/tbody/tr[5]/td/a")).getAttribute("href");
            Assertions.assertEquals("https://emotionimports.com/cars/210707182659/", link);
        }
    }

    @Given("I am an administrator of the website and I upload more than {int} alerts")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadMoreThanAlerts(int arg0) throws Exception {
        for (int i = 0; i<arg0+2; i++) { //creating 2 more alerts than the parameter
            alert newAlert = new alert(1,
                    "This is a heading test",
                    "This is a description test",
                    "https://emotionimports.com/cars/210707182659/",
                    "https://imgd-ct.aeplcdn.com/370x208/n/cw/ec/106257/venue-exterior-right-front-three-quarter-2.jpeg?isig=0&q=75",
                    postedBy,
                    160000);
            screenscraper.sendRequest(newAlert);
        }
    }

    @When("I view a list of alerts, I should see {int} alerts")
    public void iViewAListOfAlertsIShouldSeeAlerts(int arg0) {
        for (WebElement elem : driver.findElements(By.tagName("table"))) {
            //System.out.println(elem.getText() + " ");
            numOfAlerts++;
        }
        //System.out.println("The number of alerts on the current page is " + numOfAlerts);
        Assertions.assertEquals(arg0, numOfAlerts);
        //This means that only 5 alerts can be uploaded to the website, other alerts are hidden
    }

    @Given("I am an administrator of the website and I upload an alert of type {int}")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAnAlertOfTypeAlertType(int type) throws Exception {
        alert newAlert = new alert(type,
                "This is a heading test",
                "This is a description test",
                "https://emotionimports.com/cars/210707182659/",
                "https://imgd-ct.aeplcdn.com/370x208/n/cw/ec/106257/venue-exterior-right-front-three-quarter-2.jpeg?isig=0&q=75",
                postedBy,
                160000);
        screenscraper.sendRequest(newAlert);
        numOfAlerts++;
    }

    @And("the icon displayed should be <icon file name>")
    public void theIconDisplayedShouldBeIconFileName(String fileName) {
        System.out.println(numOfAlerts);
        for (int i = 1; i<=numOfAlerts; i++) {
            String icon_file_name = driver.findElement(By.xpath("/html/body/div/main/table[" + i + "]/tbody/tr[1]/td/h4/img")).getAttribute("src");
            Assertions.assertEquals(fileName, icon_file_name);
        }
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
