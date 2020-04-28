

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class CreatePage {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver",  "lib/win/chromedriver.exe");
    driver = new ChromeDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testCreatePage() throws Exception {
    driver.get("http://ec2-13-59-114-64.us-east-2.compute.amazonaws.com:8080/webproject/home.html");
    Thread.sleep(3000);
    driver.findElement(By.linkText("Create Page")).click();
    Thread.sleep(3000);
    driver.findElement(By.name("name")).click();
    driver.findElement(By.name("name")).clear();
    driver.findElement(By.name("name")).sendKeys("Test");
    Thread.sleep(3000);
    driver.findElement(By.name("email")).click();
    driver.findElement(By.name("email")).clear();
    driver.findElement(By.name("email")).sendKeys("test@unomaha.edu");
    Thread.sleep(3000);
    driver.findElement(By.name("major")).click();
    driver.findElement(By.name("major")).clear();
    driver.findElement(By.name("major")).sendKeys("testing");
    Thread.sleep(3000);
    driver.findElement(By.name("minor")).click();
    driver.findElement(By.name("minor")).clear();
    driver.findElement(By.name("minor")).sendKeys("also testing");
    Thread.sleep(3000);
    driver.findElement(By.name("organization")).click();
    driver.findElement(By.name("organization")).clear();
    driver.findElement(By.name("organization")).sendKeys("Test.com");
    Thread.sleep(3000);
    driver.findElement(By.name("graduation")).click();
    driver.findElement(By.name("graduation")).clear();
    driver.findElement(By.name("graduation")).sendKeys("test o' clock");
    Thread.sleep(3000);
    driver.findElement(By.name("bio")).click();
    driver.findElement(By.name("bio")).clear();
    driver.findElement(By.name("bio")).sendKeys("currently testing");
    Thread.sleep(3000);
    driver.findElement(By.xpath("//input[@value='Submit']")).click();
    Thread.sleep(3000);
    driver.findElement(By.linkText("Go Home")).click();
    Thread.sleep(3000);
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
