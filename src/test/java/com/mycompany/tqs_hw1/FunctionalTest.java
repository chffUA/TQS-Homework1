package com.mycompany.tqs_hw1;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class FunctionalTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new ChromeDriver();
    baseUrl = "https://www.katalon.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testUntitledTestCase() throws Exception {
    driver.get("http://localhost:8080/TQS-Homework1-master/faces/index.xhtml");
    driver.findElement(By.name("j_idt6:j_idt8")).clear();
    driver.findElement(By.name("j_idt6:j_idt8")).sendKeys("1.01");
    new Select(driver.findElement(By.name("j_idt6:j_idt10"))).selectByVisibleText("BTC");
    new Select(driver.findElement(By.name("j_idt6:j_idt13"))).selectByVisibleText("EUR");
    driver.findElement(By.name("j_idt6:j_idt16")).click();
    // Warning: verifyTextPresent may require manual changes
    try {
      ConvBean b = new ConvBean();
      String res = String.format("%.4f",b.calculate("BTC", "EUR", 1.01));
      assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains(res));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
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
