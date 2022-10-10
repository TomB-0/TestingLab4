import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
public class main {
    public static void main( String[] args ) throws InterruptedException {
        System.out.println("Hello World!");
        System.setProperty("webdriver.chrome.driver", "/Users/blaza/OneDrive/Desktop/Uni/5 Semestras/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);

        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
        wait.withTimeout(Duration.ofSeconds(50));
        wait.pollingEvery(Duration.ofMillis(250));
        wait.ignoring(NoSuchElementException.class);
        //XPATH pakeist\
        JavascriptExecutor jse = (JavascriptExecutor) driver;

        driver.get("https://demowebshop.tricentis.com/");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//*[@class='ico-login']")).click();
        driver.findElement(By.xpath("//*[@class=\"button-1 register-button\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"gender-male\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"FirstName\"]")).sendKeys("Tomas");
        driver.findElement(By.xpath("//*[@id=\"LastName\"]")).sendKeys("Blazauskas");
        driver.findElement(By.xpath("//*[@id=\"Email\"]")).sendKeys(getSaltString() + "@gmail.com");
        driver.findElement(By.xpath("//*[@id=\"Password\"]")).sendKeys("123456");
        driver.findElement(By.xpath("//*[@id=\"ConfirmPassword\"]")).sendKeys("123456");
        driver.findElement(By.xpath("//*[@id=\"register-button\"]")).click();
        driver.findElement(By.xpath("//*[@class='button-1 register-continue-button']")).click();
        driver.findElement(By.xpath("//*[contains(@class,'inactive') and a[contains(@href,'/computers')]]/a")).click();
        //driver.findElement(By.xpath("//*[@class = 'listbox']/ul/li[2]/a")).click();
        driver.findElement(By.xpath("//*[contains(@class,'inactive') and a[contains(@href,'/desktops')]]/a")).click();
//        driver.findElement(By.xpath("//*[@class=\"block block-category-navigation\"]/div[2]/ul/li[2]/ul/li/a")).click();
//      List<WebElement> text = driver.findElements(By.xpath("//*[@class=\"price actual-price\"]"));
//      for(int i = 0;i<text.size();i++) {
//      	double z = Double.parseDouble(text.get(i).getText());
//      	if(z>1500) {
//      		int value = i+1;
//      		driver.findElement(By.xpath("//*[@class=\"product-grid\"]/div["+value+"]/div/div[2]/div[3]/div[2]/input")).click();
//      		break;
//      	}
//      }

        String parentElements = "//*[contains(@class,'prices') and span[contains(@class,'price actual-price') and text() >= 1500]]/parent::*//input[@value = 'Add to cart']";

        driver.findElement(By.xpath(parentElements)).click();
        ;

        Thread.sleep(1000);


        jse.executeScript("scroll(0, 250);");


        WebElement boo = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.xpath("//*[@id=\"add-to-cart-button-74\"]"));
            }
        });
        boo.click();
        //driver.findElement(By.xpath("//*[@id=\"add-to-cart-button-74\"]")).click();

        jse.executeScript("scroll(0, -250);");
        Thread.sleep(1000);
//        WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
//            public WebElement apply(WebDriver driver) {
//              return driver.findElement(By.xpath("//*[@id=\"topcartlink\"]/a/span[1]"));
//            }
//          });
//        foo.click();
        driver.findElement(By.xpath("//*[@id=\"topcartlink\"]/a/span[1]")).click();
        driver.findElement(By.xpath("//*[@class=\"remove-from-cart\"]/input")).click();
        driver.findElement(By.xpath("//*[@class=\"common-buttons\"]/input")).click();
        String msg = driver.findElement(By.xpath("//*[@class=\"order-summary-content\"]")).getText();
        assert (msg.equals("Your Shopping Cart is empty!"));
        List<WebElement> cart = driver.findElements(By.xpath("//*[class=\"cart-item-row\"]"));
        if (cart.size() == 0) {
            System.out.println("Cart is empty");
            driver.quit();
        }
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
}
