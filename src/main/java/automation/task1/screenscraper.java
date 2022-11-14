package automation.task1;

import com.beust.ah.A;
import com.google.gson.Gson;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;

public class screenscraper {

    static ArrayList<alert> Alerts = new ArrayList<>();
    static int status_code = 0;

    /*
    function: adds alert to array
    params: the alert
     */
    public static void addAlert(alert Alert) {
        Alerts.add(Alert);
    }

    /*
    function: sends the alert to the website API as a POST HTTP request in order to update 'My Alerts'
    params: the alert we want to send
     */
    public static void sendRequest(alert Alert) throws Exception {
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(Alert); //saving the response in order to be able to check that the request was successful
        System.out.println("The JSON request is: " + jsonRequest);
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI("https://api.marketalertum.com/Alert")) //setting the URL
                .header("Content-Type", "application/json; charset=utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        status_code = postResponse.statusCode(); //testing purposes
        System.out.println("The JSON response is: " + postResponse.body());
    }

    public static void main(String[] args) throws Exception {

        String postedBy = "bb59b5de-29c0-44a1-9788-d3f03719a886";
        WebDriver driver;
        System.setProperty("webdriver.chrome.driver","/home/miriana/webdrivers/chromedriver.exe");
        driver = new ChromeDriver();
        //1. visiting the website used for screen scraping

        driver.get("https://www.emotionimports.com");
        //maximizing the website so that all elements appear as supposed
        driver.manage().window().maximize();

        //creating a web element for the search cars element that navigates to another page
        WebElement searchCars = driver.findElement(By.id("mega-menu-item-374"));
        searchCars.click();

        //2. detailed search (form)

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

        //car year of manufacture
        //WebElement CarYearOfManufactureButton = driver.findElement(By.className("sf-field-post-meta-year"));
        //left this as default as not to overcomplicate things for now (2001-2019)

        //car transmission
        WebElement CarTransmissionButton = driver.findElement(By.className("sf-field-post-meta-transmission"));
        CarTransmissionButton.click();
        Select selectTransmission = new Select(driver.findElement(By.name("_sfm_transmission[]")));
        selectTransmission.selectByValue("Manual");
        Thread.sleep(1000);

        //car price
        //WebElement CarPriceButton = driver.findElement(By.className("sf-field-post-meta-price"));
        //also left as default

        //car body type (basically default as well)
        WebElement CarBodyTypeButton = driver.findElement(By.className("sf-field-post-meta-body_type"));
        CarBodyTypeButton.click();
        Select selectBodyType = new Select(driver.findElement(By.name("_sfm_body_type[]")));
        selectBodyType.selectByValue("Hatchback");
        Thread.sleep(1000);

        //clicking on search
        WebElement searchBtn = driver.findElement(By.className("sf-field-submit"));
        searchBtn.submit();
        Thread.sleep(10000);

        //3. going through the cars and making alerts

        String headingVal;
        String descriptionVal;
        String URLVal;
        String imageURLVal;
        int price = 0;
        String strPrice;

        //going through 5 cars & generating the respective alerts
        for (int i = 1; i <= 5; i++) {
            driver.findElement(By.xpath(" //*[@id=\"resultcontainer\"]/div[" + i + "]/div/div[2]/div[3]/a[1]")).click();
            Thread.sleep(1000);
            //the properties all have the same xpath - so we use the same variables
            headingVal = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[1]/div/h1")).getText();
            descriptionVal = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[1]/div/h2")).getText();
            URLVal = driver.getCurrentUrl();
            imageURLVal = driver.findElement(By.xpath("//*[@id=\"car_slider\"]/div[1]/div[1]/div[1]/div/img")).getAttribute("src");
            strPrice = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[1]/div/div[5]/div[1]/div[2]")).getText();
            if (strPrice.equals("On Request")) {
                price = 0;
            } else {
                //System.out.println(strPrice);
                //splitting the price
                String[] arrOfStr = strPrice.split(",");
                String temp = arrOfStr[0];
                String[] arrOfStr2 = temp.split("€");
                String hundreds = arrOfStr[1] + "00";
                String thousands = arrOfStr2[1];
                //System.out.println(arrOfStr[1]); //hundreds
                //System.out.println(arrOfStr2[1]); //thousands

                strPrice = thousands.concat(hundreds);
                //System.out.println(strPrice);
                //now we are able to parse the string and change it into an integer
                price = Integer.parseInt(strPrice);
            }

            //create alert for each car
            alert newAlert = new alert(1,
                    headingVal,
                    descriptionVal,
                    URLVal,
                    imageURLVal,
                    postedBy,
                    price);
            addAlert(newAlert);
            sendRequest(newAlert);
            //navigates back to the results page
            driver.navigate().back();
        }
        //sending the alerts to the website
        for(int i=0; i<Alerts.size();i++) {
            sendRequest(Alerts.get(i));
        }
        //exits the website
        driver.close();
        driver.quit();
    }
}
