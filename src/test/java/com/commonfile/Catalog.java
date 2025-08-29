package com.commonfile;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.pomtestcases.RegisterLC;

public class Catalog {
	
	WebDriver driver;
    RegisterLC reglc;
    
    public Catalog(WebDriver driver) {
        this.driver = driver;
        reglc = new RegisterLC(driver); // pass driver to RegisterLC
        PageFactory.initElements(driver, this);
    }
    
    public void supervisorCatalog() throws InterruptedException {
        reglc.switchToDefaultContent();
        Thread.sleep(1000);

        // Check if Supervisor Release is visible
        List<WebElement> supervisorElements = driver.findElements(By.xpath("//span[normalize-space(text())='Supervisor Release']"));

        if (supervisorElements.size() == 0 || !supervisorElements.get(0).isDisplayed()) {
            // Click Maintenance only if Supervisor Release is NOT visible
            WebElement maintenanceMenu = driver.findElement(By.xpath("//span[text()='IPLC Maintenance']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", maintenanceMenu);
            Thread.sleep(1000);
            maintenanceMenu.click();
            Thread.sleep(500);
        }

        // Now click Supervisor Release
        WebElement supervisorclick = driver.findElement(By.xpath("//span[normalize-space(text())='Supervisor Release']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", supervisorclick);
        Thread.sleep(1000);
        supervisorclick.click();
    }

    
    public void commoncatalog(String ref) throws InterruptedException {
		// TODO Auto-generated method stub
		driver.switchTo().frame("work");
		Thread.sleep(500);
		WebElement conid = driver.findElement(By.id("CatalogSwitch"));
		conid.click();
		System.out.println("CatalogSwitch click");

		WebElement filterFeild = driver.findElement(By.name("OP2"));

		filterFeild.sendKeys(ref);
		Thread.sleep(500);
		WebElement filterSpan = driver
				.findElement(By.xpath("//*[@id=\"FILTER_ETL_DIALOG\"]/div/div[3]/div/div[3]/button[2]/span[1]"));
		filterSpan.click();
		Thread.sleep(500);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("eeToolbar");

		WebElement next = driver.findElement(By.id("_next"));
		next.click();
		Thread.sleep(500);
		System.out.println("click next");
		driver.switchTo().defaultContent();
		driver.switchTo().frame("work");
	}

    
    public void super_release(String ref) throws InterruptedException, TimeoutException {
        
        // Switch to the main frame
        driver.switchTo().frame("work");
        Thread.sleep(500);

        WebElement conid = driver.findElement(By.id("CatalogSwitch"));
        conid.click();
        System.out.println("CatalogSwitch clicked");

        WebElement filterField = driver.findElement(By.name("OP2"));
        filterField.sendKeys(ref);
        Thread.sleep(500);

        WebElement filterSpan = driver.findElement(
            By.xpath("//*[@id='FILTER_ETL_DIALOG']/div/div[3]/div/div[3]/button[2]/span[1]")
        );
        filterSpan.click();
        Thread.sleep(500);

        WebElement next = driver.findElement(By.id("transaction"));
        next.click();
        Thread.sleep(500);

        driver.switchTo().defaultContent();
        driver.switchTo().frame("eeToolbar");

        // Handle confirm alert if present
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
        try {
            Alert alert = shortWait.until(ExpectedConditions.alertIsPresent());
            System.out.println("Alert text: " + alert.getText());
            alert.accept();
            System.out.println("Alert accepted.");
        } catch (TimeoutException e) {
            System.out.println("No alert appeared after confirm.");
        }

        // Click success button
        driver.findElement(By.name("success")).click();

        // Wait until Cancel button is clickable
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(120)); // max wait 2 min
        WebElement cancelBtn = longWait.until(ExpectedConditions.elementToBeClickable(By.name("cancel")));
        cancelBtn.click();
        System.out.println("Cancel button clicked successfully.");
        System.out.println("Next step clicked.");

        // Handle any unexpected alerts
        try {
            driver.switchTo().defaultContent();
        } catch (UnhandledAlertException e) {
            try {
                Alert alert = driver.switchTo().alert();
                System.out.println("Unexpected Alert Text: " + alert.getText());
                alert.accept();
                System.out.println("Unexpected alert handled.");
            } catch (NoAlertPresentException ex) {
                System.out.println("Alert disappeared before handling.");
            }
        }
    }


private boolean alertIsPresent() {
	// TODO Auto-generated method stub
	return false;
}



}
