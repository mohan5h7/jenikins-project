package tests;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import reusenew.Reuse;

public class SeleniumPrograms2 {

    WebDriver driver;

    @BeforeMethod
    public void setup() {

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void openAmazon() throws InterruptedException, IOException {

        Reuse.createReport("Amazon_Report.html", "Amazon Test", "Amazon_Module");

        driver.get("https://www.amazon.com/");
        Thread.sleep(5000);

        WebElement req = driver.findElement(By.xpath("(//*[text()='Prime Video'])[2]"));

        try {
            if (req.isDisplayed()) {
                Reuse.captureScreenshotPass(driver, "Amazon", "Amazon opened successfully");
            }
        } catch (Exception e) {
            Reuse.captureScreenshotFail(driver, "Amazon", "Amazon not opened");
            Assert.fail("Amazon not opened");
        }
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
        Reuse.closeReport();
    }
}
