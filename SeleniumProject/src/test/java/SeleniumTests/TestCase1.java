package SeleniumTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;


@Test
public class TestCase1 {

    public String baseUrl = "https://www.saucedemo.com/";
    String driverPath = "C:\\Users\\nstefanovic\\Downloads\\chromedriver.exe";
    public WebDriver driver;
    public String expected = null;
    public String actual = null;

    public String homeUrl = "https://www.saucedemo.com/inventory.html";

    @BeforeTest
    public void launchBrowser(){
        System.setProperty("webdriver.chrome.driver",driverPath);
        driver = new ChromeDriver();
        driver.get(baseUrl);

    }

   // @BeforeMethod
   // public void setup(){
      //  driver = new ChromeDriver();
   // }


    @Test(priority = 0)

    public void login() {

        WebElement username = driver.findElement(By.id("user-name"));
        WebElement password = driver.findElement(By.id("password"));

        username.sendKeys("standard_user");
        password.sendKeys("secret_sauce");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("login-button"))));

        driver.findElement(By.id("login-button")).click();
        Assert.assertEquals(driver.getCurrentUrl(),homeUrl);
        System.out.println("Logged in successfully");
    }
    @Test(priority = 1)
    public void verify() {

        boolean product_status = driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]")).isDisplayed();
        Assert.assertEquals(product_status, true);

        boolean shopping_cart_status = driver.findElement(By.className("shopping_cart_link")).isDisplayed();
        Assert.assertEquals(shopping_cart_status, true);

        boolean burger_menu_status = driver.findElement(By.xpath("//*[@id=\"menu_button_container\"]/div/div[1]/div")).isDisplayed();
        Assert.assertEquals(burger_menu_status, true);

        String bMenuLoc = String.valueOf(driver.findElement(By.xpath("//*[@id=\"menu_button_container\"]/div/div[1]/div")).getLocation());
        System.out.println(bMenuLoc);

        boolean twitter_status = driver.findElement(By.className("social_twitter")).isDisplayed();
        Assert.assertEquals(twitter_status, true);

        boolean facebook_status = driver.findElement(By.className("social_facebook")).isDisplayed();
        Assert.assertEquals(facebook_status, true);

        boolean linkedin_status = driver.findElement(By.className("social_linkedin")).isDisplayed();
        Assert.assertEquals(linkedin_status, true);

        System.out.println("All items displayed successfully");

    }
        //driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

    @Test(priority = 2)
    public void logout() {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("react-burger-menu-btn"))));

            driver.findElement(By.id("react-burger-menu-btn")).click();

            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"logout_sidebar_link\"]"))));
            //driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

            driver.findElement(By.xpath("//*[@id=\"logout_sidebar_link\"]")).click();
            Assert.assertEquals(driver.getCurrentUrl(),baseUrl);
            System.out.println("Logged out successfully");
        }

    @AfterTest
    public void quit(){
        driver.quit();
    }


}

