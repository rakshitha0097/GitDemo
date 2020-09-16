package project;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Ubuy {

	WebDriver driver;

	@Parameters({ "browser" })
	@BeforeTest
	public void openBrowser(String browser) throws Exception {
		// Checking if browser is chrome
		if (browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
			// Creating object of chrome options class
			ChromeOptions co = new ChromeOptions();
			// To disable notifications
			co.addArguments("--disable-notifications");
			// To disable popup
			co.addArguments("--disable-popup-blocking");
			// passing options class object as an argument inside chrome driver
			driver = new ChromeDriver(co);
		}
		// Checking if browser is firefox
		else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/drivers/geckodriver.exe");
			// Creating object of firefox options class
			FirefoxOptions fo = new FirefoxOptions();
			// To disable notifications
			fo.addArguments("--disable-notifications");
			// To disable popup
			fo.addArguments("--disable-popup-blocking");
			// passing options class object as an argument inside firefox driver
			driver = new FirefoxDriver(fo);
		} else {
			throw new Exception("Browser is not correct");
		}
		System.out.println("Opening the " + browser + " browser");
		System.out.println("*********************************");
	}

	@Parameters({ "baseUrl" })
	@BeforeClass
	public void openWebsite(String baseUrl) {
		maximizeWindow(driver);
		// To open the desired Url
		System.out.println("Opening the Website " + baseUrl);
		System.out.println();
		driver.get(baseUrl);
		implicitWait(driver);
	}

	@Test(priority = 0)
	public void searchItem() {
		// Passing the desired item value inside the search text box
		driver.findElement(By.xpath("//input[@class='search-box-text']")).sendKeys("Home Appliances");
		// Hitting on Enter keys to navigate to desired product page
		driver.findElement(By.xpath("//input[@class='search-box-text']")).sendKeys(Keys.ENTER);
	}

	@Test(priority = 1)
	public void addToCart() {
		// Creating object of WebDriverWait class
		WebDriverWait wait = new WebDriverWait(driver, 15);
		for (int i = 0; i < 3; i++) {
			// Storing the products in a list using the common locators
			List<WebElement> products = (driver.findElements(By.cssSelector(".search-img")));
			// To wait until all the products are visible on the page
			wait.until(ExpectedConditions.visibilityOfAllElements(products));
			// Click on the product
			products.get(i).click();
			// Storing the locator of 'add to cart ' button inside the webelement add
			WebElement add = driver.findElement(By.id("add-to-cart-btn"));
			// To wait until 'add to cart' button is visible
			wait.until(ExpectedConditions.visibilityOf(add));
			// To wait until 'add to cart' button is clickable
			wait.until(ExpectedConditions.elementToBeClickable(add));
			// Click on 'add to cart button'
			add.click();
			navigateFunction(driver);
			if ((i == 1) || (i == 2)) {
				// Click on the cart icon
				driver.findElement(By.id("user_cart_count")).click();
				// Store the locator of total Price in webelement total
				WebElement total = driver.findElement(By.xpath("//span[contains(@class,'subtotal-price-text')]"));
				// Printing the Total price in the console
				System.out.println("After adding " + (i + 1) + " products " + total.getText());
				navigateFunction(driver);
			}
		}
	}
	@Parameters({ "browser" })
	@AfterTest
//To close browser
	public void closeBrowser(String browser) {
		System.out.println("*********************************");
		System.out.println("Closing the " + browser + " browser");
		driver.close();
		
		
	}

	// Implicit waits
	public void implicitWait(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	// To maximize browser
	public void maximizeWindow(WebDriver driver) {
		driver.manage().window().maximize();
	}

	// To navigate back to product page
	public void navigateFunction(WebDriver driver) {
		driver.navigate().back();
	}

}
