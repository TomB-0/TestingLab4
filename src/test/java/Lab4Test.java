import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Lab4Test {
    WebDriver driver;
    static String fName = getSaltString();
    static String lName = getSaltString();
    static String email = getSaltString() + "@gmail.com";
    static String password = getSaltString();



    @Before
    public void setup() {


        try {
            System.setProperty("webdriver.chrome.driver", "/Users/blaza/OneDrive/Desktop/Uni/5 Semestras/chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            driver = new ChromeDriver(options);
        } catch (Exception ignore) { }
    }

    @After
    public void tearDown() {
        try {
            driver.quit();
        } catch (Exception ignore) { }
        driver = null;
    }

    @Test
    public void foo1() {
        driver.get("https://demowebshop.tricentis.com/");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//*[@class='ico-register']")).click();
        driver.findElement(By.xpath("//*[@id=\"gender-male\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"FirstName\"]")).sendKeys(fName);
        driver.findElement(By.xpath("//*[@id=\"LastName\"]")).sendKeys(lName);
        driver.findElement(By.xpath("//*[@id=\"Email\"]")).sendKeys(email);
        driver.findElement(By.xpath("//*[@id=\"Password\"]")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"ConfirmPassword\"]")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"register-button\"]")).click();
        driver.findElement(By.xpath("//*[@class='button-1 register-continue-button']")).click();


    }
    @Test
    public void foo2() throws FileNotFoundException, IOException, InterruptedException {
        int isFirst = 0;
        List<String> data1 = new ArrayList<String>();
        String path1 = "/Users/blaza/eclipse-workspace/Selenium/src/test/java/com/selenium/data1.txt";
        readFromFile(path1, data1);
        completeItemPurchase(driver, data1, isFirst);
        tearDown();
    }
    @Test
    public void foo3() throws FileNotFoundException, IOException {
        int isFirst = 1;
        List<String> data2 = new ArrayList<String>();
        String path2 = "/Users/blaza/eclipse-workspace/Selenium/src/test/java/com/selenium/data2.txt";
        readFromFile(path2, data2);
        completeItemPurchase(driver, data2, isFirst);
    }

    public static String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    public static void readFromFile(String fileLocation, List<String> data) throws FileNotFoundException, IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            int i = 0;
            while (line != null && line != "") {
                data.add(line);
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
        }
    }

    public static void completeItemPurchase(WebDriver driver, List<String> data, int isFirst) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        driver.get("https://demowebshop.tricentis.com/");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//*[@class='ico-login']")).click();
        driver.findElement(By.xpath("//*[@id=\"Email\"]")).sendKeys(email);
        driver.findElement(By.xpath("//*[@id=\"Password\"]")).sendKeys(password);
        driver.findElement(By.xpath("//*[@class='button-1 login-button']")).click();
        driver.findElement(By.xpath("//*[contains(@class,'inactive') and a[contains(@href,'/digital-downloads')]]/a")).click();
        System.out.println(data.size());
        for(int i = 0; i < data.size(); i++) {

            driver.findElement(By.xpath("//*[text() = '"+ data.get(i)+"']/parent::*[@class = 'product-title']/following-sibling::*[@class = 'add-info']/div[2]/input")).click();
            wait.until(ExpectedConditions.attributeContains(By.xpath("//*[@class='ajax-loading-block-window']"), "style", "display: none;"));
        }
        driver.findElement(By.xpath("//*[@id=\"topcartlink\"]/a/span[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"termsofservice\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"checkout\"]")).click();
        if(isFirst == 0) {
            Select dropdown = new Select(driver.findElement(By.xpath("//*[@id=\"BillingNewAddress_CountryId\"]")));
            dropdown.selectByIndex(1);
            driver.findElement(By.xpath("//*[@id=\"BillingNewAddress_City\"]")).sendKeys(getSaltString());
            driver.findElement(By.xpath("//*[@id=\"BillingNewAddress_Address1\"]")).sendKeys(getSaltString());
            driver.findElement(By.xpath("//*[@id=\"BillingNewAddress_ZipPostalCode\"]")).sendKeys(getSaltString());
            driver.findElement(By.xpath("//*[@id=\"BillingNewAddress_PhoneNumber\"]")).sendKeys(getSaltString());
        }
        driver.findElement(By.xpath("//*[@id=\"billing-buttons-container\"]/input")).click();
        wait.until(ExpectedConditions.attributeContains(By.xpath("//*[@id=\"opc-payment_method\"]"), "class", "tab-section allow active"));
        driver.findElement(By.xpath("//*[@id=\"payment-method-buttons-container\"]/input")).click();
        wait.until(ExpectedConditions.attributeContains(By.xpath("//*[@id=\"opc-payment_info\"]"), "class", "tab-section allow active"));
        driver.findElement(By.xpath("//*[@id=\"payment-info-buttons-container\"]/input")).click();
        WebElement temp = driver.findElement(By.xpath("//*[@id=\"confirm-order-buttons-container\"]/input"));
        jse.executeScript("arguments[0].scrollIntoView(true);", temp);
        wait.until(ExpectedConditions.attributeContains(By.xpath("//*[@id=\"opc-confirm_order\"]"), "class", "tab-section allow active"));
        temp.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='section order-completed']")));
        WebElement isCompleted = driver.findElement(By.xpath("//*[@class='title']"));
        System.out.println(isCompleted.getText());
        assert(isCompleted.getText().equals("Your order has been successfully processed!"));
    }

}
