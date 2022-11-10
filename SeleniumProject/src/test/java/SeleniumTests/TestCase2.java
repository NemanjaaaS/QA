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

public class TestCase2 {

    public String baseUrl = "https://www.saucedemo.com/";
    String driverPath = "C:\\Users\\nstefanovic\\Downloads\\chromedriver.exe";
    public WebDriver driver;
    public String expected = null;
    public String actual = null;

    public String homeUrl = "https://www.saucedemo.com/inventory.html";

    @BeforeTest
    public void launchBrowser() {
        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();
        driver.get(baseUrl);
    }

    @Test(priority = 0)
//provera login
    public void login() {


        WebElement username = driver.findElement(By.id("user-name"));
        WebElement password = driver.findElement(By.id("password"));

        username.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("login-button"))));

        driver.findElement(By.id("login-button")).click();
        Assert.assertEquals(driver.getCurrentUrl(), homeUrl);
        System.out.println("Logged in successfully");

    }
//kliktanje na ranac i provera da li se otvara  nova stranica sa rancem i podacima
    @Test(priority = 1)
    public void slbClick() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]"))));
        driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"inventory_item_container\"]"))));
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory-item.html?id=4");
        System.out.println("Clicked on backpack and backpack page opened");
    }
//provera da li su prisutni svi elementi i njihovi podaci
    @Test(priority = 2)
    public void verify() {
        boolean name = driver.findElement(By.xpath("//*[@id=\"inventory_item_container\"]/div/div/div[2]/div[1]")).isDisplayed();
        Assert.assertEquals(name, true);

        boolean desctiption = driver.findElement(By.xpath("//*[@id=\"inventory_item_container\"]/div/div/div[2]/div[2]")).isDisplayed();
        Assert.assertEquals(desctiption, true);

        boolean price = driver.findElement(By.xpath("//*[@id=\"inventory_item_container\"]/div/div/div[2]/div[3]")).isDisplayed();
        Assert.assertEquals(price, true);
        System.out.println("All elements displayed successfully");

    }
//klitanje na addtochart za dodavanje u korpu i provera da li je dugme postalo REMOVE

    @Test(priority = 3)
    public void addToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-backpack\"]"))));

        //String backpack = driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div")).getText();
        //System.out.println(backpack);
        driver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-backpack\"]")).click();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("remove-sauce-labs-backpack"))));

        String removeBtn = driver.findElement(By.id("remove-sauce-labs-backpack")).getText();
        Assert.assertEquals(removeBtn, "REMOVE");
        System.out.println("Clicked on 'Add to chat' and remove button displayed successfully");


    }


//vracanje na homa page
    @Test(priority = 4)
    public void goBack() {
        driver.findElement(By.id("back-to-products")).click();
        Assert.assertEquals(driver.getCurrentUrl(), homeUrl);
        System.out.println("Go back successfully");

    }

    //provera da li je ranac u korpi
    @Test(priority = 5)
    public void checkCart(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        boolean cartBadgeStatus = driver.findElement(By.className("shopping_cart_badge")).isDisplayed();
        Assert.assertEquals(cartBadgeStatus,true);

        driver.findElement(By.className("shopping_cart_link")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("cart_list"))));

        boolean backpackStatus = driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div")).isDisplayed();
        Assert.assertEquals(backpackStatus,true);

        driver.findElement(By.id("continue-shopping")).click();
        System.out.println("Item is displayed in cart");


    }
//dodavanje jakne u korpu sa home page
    @Test(priority = 6)
    public void addFromHome() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket"))));
        String jacket = driver.findElement(By.xpath("//*[@id=\"item_5_title_link\"]/div")).getText();
        driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket")).click();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("remove-sauce-labs-fleece-jacket"))));

        String removeBtn = driver.findElement(By.id("remove-sauce-labs-fleece-jacket")).getText();
        Assert.assertEquals(removeBtn, "REMOVE");

        boolean cartBadgeStatus = driver.findElement(By.className("shopping_cart_badge")).isDisplayed();
        Assert.assertEquals(cartBadgeStatus,true);

        driver.findElement(By.className("shopping_cart_link")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("cart_list"))));

        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"item_5_title_link\"]")).getText(), jacket);
        System.out.println("Jacket added form home successfully");


    }
    //provera da li je janka u korpi

    @Test(priority = 7)
    public void checkCartHome(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        boolean cartBadgeStatus = driver.findElement(By.className("shopping_cart_badge")).isDisplayed();
        Assert.assertEquals(cartBadgeStatus,true);

        driver.findElement(By.className("shopping_cart_link")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("cart_list"))));

        boolean backpackStatus = driver.findElement(By.xpath("//*[@id=\"item_5_title_link\"]/div")).isDisplayed();
        Assert.assertEquals(backpackStatus,true);

        driver.findElement(By.id("continue-shopping")).click();

        System.out.println("Item is displayed in cart");

    }
//kliktanje na korpu i provera da li je otvara njena stranica
    @Test(priority = 8)
    public void scClick() {

        String cartUrl = "https://www.saucedemo.com/cart.html";
        driver.findElement(By.className("shopping_cart_link")).click();
        Assert.assertEquals(driver.getCurrentUrl(), cartUrl);
        System.out.println("clicked on cart and cart page opened successfully");
    }
//kliktanje na chackout i da li vodi na pravu stranicu
    @Test(priority = 9)
    public void checkout() {
        String checkoutUrl = "https://www.saucedemo.com/checkout-step-one.html";
        driver.findElement(By.id("checkout")).click();
        Assert.assertEquals(driver.getCurrentUrl(), checkoutUrl);
        System.out.println("clicked on checkout and checkout step one page opened successfully");
    }
//unos podataka i odlazak na sledecu stranicu
    @Test(priority = 10)
    public void cont()

    {
        WebElement fname = driver.findElement(By.id("first-name"));
        WebElement lname = driver.findElement(By.id("last-name"));
        WebElement zcode = driver.findElement(By.id("postal-code"));

        fname.sendKeys("Nemanja");
        lname.sendKeys("Stefanovic");
        zcode.sendKeys("3400");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"continue\"]"))));

        driver.findElement(By.xpath("//*[@id=\"continue\"]")).click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html");
        System.out.println("Data inputted, clicked on continue and page opened successfully");
    }
    //klik na finish i odlazak thankyou stranicu
    @Test(priority = 11)
    public void finish(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"finish\"]"))));

        driver.findElement(By.xpath("//*[@id=\"finish\"]")).click();

        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/checkout-complete.html");
        System.out.println("Clicked on finish and checkout complite page opens");

}

//provera da li je thankyou displated
    @Test(priority = 12)
    public void verifyThankyou(){

        boolean thankyouStatus = driver.findElement(By.xpath("//*[@id=\"checkout_complete_container\"]/h2")).isDisplayed();
        Assert.assertEquals(thankyouStatus,true);

        String thankyou = driver.findElement(By.xpath("//*[@id=\"checkout_complete_container\"]/h2")).getText();

        Assert.assertEquals(thankyou,"THANK YOU FOR YOUR ORDER");
        System.out.println("Thank you is displayed successfully");

    }
//vracanje na home page i odjava i provera da li smo se odjavili
    @Test(priority = 13)
    public void logout(){
        goBack();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("react-burger-menu-btn"))));


        driver.findElement(By.id("react-burger-menu-btn")).click();


        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"logout_sidebar_link\"]"))));

        driver.findElement(By.xpath("//*[@id=\"logout_sidebar_link\"]")).click();
        Assert.assertEquals(driver.getCurrentUrl(),baseUrl);
        System.out.println("Logged out successfully");
    }








    @AfterTest
    public void quit(){
        driver.quit();
    }


}

