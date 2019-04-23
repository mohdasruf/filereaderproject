package framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ErrorPage {
    private final WebDriver driver;

    public static String ErrorPage_CONTACT_INFO = "Please contact vehicle enquiries:";

    public ErrorPage(WebDriver driver){
        this.driver = driver;
    }

    public String getErrorContactInfoString() {
        return this.driver.findElement(By.xpath("//div/p/strong")).getText();
    }

}
