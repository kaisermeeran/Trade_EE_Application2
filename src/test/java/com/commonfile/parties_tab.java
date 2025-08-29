package com.commonfile;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.pomtestcases.RegisterLC;

public class parties_tab {
	
	private WebDriver driver;
	
	public parties_tab(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	public void enterAppCustId(String cubkId) throws InterruptedException {
	    String targetRef = cubkId.trim();  // Trim to avoid whitespace issues
	    boolean found = false;
	    // Switch to default frame and then to the iframe
	   // switchToDefaultContent();
	    driver.switchTo().defaultContent();
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    WebElement iframeElement = wait.until(
	        ExpectedConditions.presenceOfElementLocated(
	            By.xpath("//iframe[contains(@id,'confirmDialogFrame')]")
	        )
	    );
	    driver.switchTo().frame(iframeElement);

	    // Attempt 1: Try to find the ID in the initial table
	    List<WebElement> rows = driver.findElements(By.xpath("//table[@id='CataListTab']//tr"));
	    System.out.println("Total rows found: " + rows.size());

	    for (WebElement row : rows) {
	        List<WebElement> cells = row.findElements(By.tagName("td"));
	        if (cells.size() > 0) {
	            String refValue = cells.get(0).getText().trim();
	            System.out.println("Checking row value: [" + refValue + "]");

	            if (refValue.equalsIgnoreCase(targetRef)) {
	                cells.get(0).findElement(By.tagName("a")).click();
	                System.out.println("Clicked on direct match: " + targetRef);
	                found = true;
	                break;
	            }
	        }
	    }

	    // If not found in direct table rows, use the filter option
	    if (!found) {
	        System.out.println("⚠️ ID not found in direct table rows. Using filter to search...");

	        // Switch back to default and again into frame (if filter is outside)
	        driver.switchTo().defaultContent();
	        driver.switchTo().frame(iframeElement);

	        WebElement filterBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("CatalogSwitch")));
	        filterBtn.click();

	        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("SEARCH_VALUE")));
	        searchInput.clear();
	        searchInput.sendKeys(targetRef);

	        // Click the Search button (Filter)
	        WebElement searchBtn = driver.findElement(By.xpath("(//span[@class='MuiButton-label'])[2]"));
	        searchBtn.click();

	        // Wait for table results to load — optional wait for spinner to disappear
	        Thread.sleep(1500);  // Or replace with explicit wait

	        // Now look again for the same ID
	        rows = driver.findElements(By.xpath("//table[@id='CataListTab']//tr"));
	        for (WebElement row : rows) {
	            List<WebElement> cells = row.findElements(By.tagName("td"));
	            if (cells.size() > 0) {
	                String refValue = cells.get(0).getText().trim();
	                System.out.println("Checking (filtered) row value: [" + refValue + "]");

	                if (refValue.equalsIgnoreCase(targetRef)) {
	                    cells.get(0).findElement(By.tagName("a")).click();
	                    System.out.println("Clicked on filtered ID: " + targetRef);
	                    found = true;
	                    break;
	                }
	            }
	        }
	    }

	    if (!found) {
	        throw new RuntimeException("❌ Reference not found in any page: " + targetRef);
	    }
	}
	

}
