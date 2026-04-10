package reusenew;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Reuse {

    static ExtentReports report;
    static ExtentTest test;

    private static final Logger log = LogManager.getLogger(Reuse.class);

    // Create report
    public static void createReport(String reportName, String scriptName, String moduleName) {

        String folderPath = System.getProperty("user.dir") + "/" + moduleName;

        // Create folder
        File dir = new File(folderPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String path = folderPath + "/" + reportName;

        ExtentSparkReporter spark = new ExtentSparkReporter(path);
        report = new ExtentReports();
        report.attachReporter(spark);

        test = report.createTest(scriptName);
    }

    // PASS Screenshot
    public static void captureScreenshotPass(WebDriver driver, String screenshotName, String data) throws IOException {

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        File dir = new File(System.getProperty("user.dir") + "/pass/");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        TakesScreenshot ts = (TakesScreenshot) driver;
        File srcFile = ts.getScreenshotAs(OutputType.FILE);

        String destPath = System.getProperty("user.dir") + "/pass/" + screenshotName + "_" + time + ".png";
        FileUtils.copyFile(srcFile, new File(destPath));

        test.pass(data, MediaEntityBuilder.createScreenCaptureFromPath(destPath).build());
        report.flush();

        log.info("PASS Screenshot: " + destPath);
    }

    // FAIL Screenshot
    public static void captureScreenshotFail(WebDriver driver, String screenshotName, String data) throws IOException {

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        File dir = new File(System.getProperty("user.dir") + "/fail/");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        TakesScreenshot ts = (TakesScreenshot) driver;
        File srcFile = ts.getScreenshotAs(OutputType.FILE);

        String destPath = System.getProperty("user.dir") + "/fail/" + screenshotName + "_" + time + ".png";
        FileUtils.copyFile(srcFile, new File(destPath));

        test.fail(data, MediaEntityBuilder.createScreenCaptureFromPath(destPath).build());
        report.flush();

        log.error("FAIL Screenshot: " + destPath);
    }

    // Close report
    public static void closeReport() {
        if (report != null) {
            report.flush();
        }
    }
}