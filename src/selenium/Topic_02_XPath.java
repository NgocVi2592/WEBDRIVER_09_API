package selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_02_XPath {
	WebDriver driver;
	
	@BeforeClass
  	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://live.guru99.com/");
	}
	
	@Test
	public void TC_01_LoginWithUserNameAndPasswordEmpty() {
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("");
		driver.findElement(By.id("pass")).sendKeys("");
		driver.findElement(By.id("send2")).click();
		
		String emailRequired = driver.findElement(By.id("advice-required-entry-email")).getText();
		Assert.assertTrue(emailRequired.equals("This is a required field."));
		
		String passwordRequired = driver.findElement(By.id("advice-required-entry-pass")).getText();
		Assert.assertTrue(passwordRequired.equals("This is a required field."));
		
	}
	
	@Test
	public void TC_02_LoginWithEmailInvalid() {
		driver.findElement(By.id("email")).sendKeys("123434234@12312.123123");
		driver.findElement(By.id("send2")).click();
		
		String emailRequired = driver.findElement(By.id("advice-validate-email-email")).getText();
		Assert.assertTrue(emailRequired.equals("Please enter a valid email address. For example johndoe@domain.com."));
		
	}
	
	@Test
	public void TC_03_LoginWithPasswordLessThan6Characters() {
		
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
		
		driver.findElement(By.id("pass")).sendKeys("123");
		driver.findElement(By.id("send2")).click();
		
		String emailRequired = driver.findElement(By.id("advice-validate-password-pass")).getText();
		Assert.assertTrue(emailRequired.equals("Please enter 6 or more characters without leading or trailing spaces."));
		
	}
	
	@Test
	public void TC_04_LoginWithPasswordIncorrect() {
		
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("pass")).clear();
		
		driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("123123123");
		driver.findElement(By.id("send2")).click();
		
		//1.468s
//		String InvalidNotification = driver.findElement(By.xpath("//span[contains(text(), \"Invalid login or password.\")]")).getText();
//		Assert.assertTrue(InvalidNotification.equals("Invalid login or password."));
		
		//1.388s
		String InvalidNotification = driver.findElement(By.xpath("//li[@class=\"error-msg\"]/ul/li/span")).getText(); 
		Assert.assertTrue(InvalidNotification.equals("Invalid login or password."));
		 
	}
	
	@AfterClass
	public void afterClass() {
		  driver.quit();
	}

}
