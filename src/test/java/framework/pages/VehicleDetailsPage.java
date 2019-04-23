package framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class VehicleDetailsPage {
    private final WebDriver driver;

    public VehicleDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    public String[] getVehicleInfo() {
        String registrationNumber = this.driver.findElement(By.id("Vrm")).getAttribute("value");
        String make = this.driver.findElement(By.id("Make")).getAttribute("value");
        String colour = this.driver.findElement(By.id("Colour")).getAttribute("value");
        return new String[] {registrationNumber, make, colour};
    }
}
