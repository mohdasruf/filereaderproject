package framework.pages;

import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RegEnterPage {
    private final WebDriver driver;
    public static String RegEnterPage_ERROR_HEADING = "There was a problem";
    public static String RegEnterPage_ERROR_STRING = "You must enter your registration number in a valid format";

    private static String WEBSITEURL = "https://www.gov.uk/get-vehicle-information-from-dvla";


    public RegEnterPage() {
        driver = new FirefoxDriver();
        this.driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);

    }

    public String getErrorStringHeading() {
        return this.driver.findElement(By.cssSelector(".heading-medium")).getText();
    }

    public String getErrorString() {
        return this.driver.findElement(By.id("Vrm-error")).getText();
    }

    public RegEnterPage enterInvalidFormatRegNo(String registrationNumber) throws Exception {
        WebDriverWait wait = submitRegInfo(registrationNumber);

        WebElement errorElement = this.driver.findElement(By.id("Vrm-error"));
        wait.until(ExpectedConditions.visibilityOf(errorElement));
        return new RegEnterPage();

    }

    public String[] checkDetails(String registrationNumber) throws Exception {
        WebDriverWait wait = submitRegInfo(registrationNumber);

        WebElement newPageElement = this.driver.findElement(By.className("back"));
        wait.until(ExpectedConditions.visibilityOf(newPageElement));
        VehicleDetailsPage vehicleDetailsPage = new VehicleDetailsPage(driver);
        return vehicleDetailsPage.getVehicleInfo();
    }

    public ErrorPage incorrectRegNo(String registrationNumber) throws Exception {

        WebDriverWait wait = submitRegInfo(registrationNumber);

        WebElement newPgElement = this.driver.findElement(By.xpath("//div/p/strong"));
        wait.until(ExpectedConditions.visibilityOf(newPgElement));
        WebElement newPageElement = this.driver.findElement(By.className("heading-large"));
        wait.until(ExpectedConditions.visibilityOf(newPageElement));
        ErrorPage errorPage = new ErrorPage(driver);
        return errorPage;
    }

    public void quit() {
        this.driver.quit();
    }

    private WebDriverWait submitRegInfo(String registrationNumber) {
        this.driver.get(WEBSITEURL);
        this.driver.findElement(By.cssSelector("a.gem-c-button")).click();
        WebDriverWait wait = new WebDriverWait(driver, 500);
        WebElement regPageTitleElement = this.driver.findElement(By.xpath("/html/body/main/form/div/div/div[2]/fieldset/button"));
        wait.until(ExpectedConditions.visibilityOf(regPageTitleElement));
        this.driver.findElement(By.id("Vrm")).sendKeys(registrationNumber);
        this.driver.findElement(By.name("Continue")).submit();
        return wait;
    }

    public void takeScreenshot() {
        if (driver instanceof TakesScreenshot) {
            String path = "./target/screenshots/";
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                String timestamp = new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss").format(new Date());
                FileUtils.copyFile(scrFile, new File(path + "file " + timestamp + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
