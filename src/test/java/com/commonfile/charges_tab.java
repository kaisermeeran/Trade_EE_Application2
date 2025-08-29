package com.commonfile;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.pomtestcases.RegisterLC;

public class charges_tab {
	
	@FindBy(id="F") WebElement chargestab;
	@FindBy(id="CHG_FLD_ALL_CHARGE_AT") WebElement paidAt;
	@FindBy(id="CHG_GETAC_BTN") WebElement clickchargesAccbutton;
	@FindBy(id="CataListTab") WebElement table;
	@FindBy(id="CHG_FLD_LOCAL_CUST_AC_NO") WebElement chargesAccNoField;
	@FindBy(id="ASSET_ACNO") WebElement custaccno; // Assuming this is the field for asset account number
	@FindBy(id="LIAB_ACNO") WebElement liabaccno; // Assuming this is the field for liability account number

	// Reference to RegisterLC for iframe handling
	
	WebDriver driver;
    RegisterLC reglc;
    
    public charges_tab(WebDriver driver) {
        this.driver = driver;
        reglc = new RegisterLC(driver); // pass driver to RegisterLC
        PageFactory.initElements(driver, this);
    }
    
	public void clickchargestab() throws InterruptedException
	{
		
		reglc.switchToDefaultContent();
		reglc.switchToWorkFrame();
		chargestab.click();
		reglc.waitmethod();
	}
	
	public boolean PaidatFieldVisible(String paidat,String accno)
	{
		reglc.switchToDefaultContent();
		reglc.switchToWorkFrame();
		try 
		{
			Select paidSelect = new Select(paidAt);
			paidSelect.selectByVisibleText(paidat);
			System.out.println("Field with ID '" + paidat + "' is visible and selected.");
			//return true;
			
			if(paidat.equals("TRANSACTION"))
			{
				performClickChargesAccButton();
				chargesaccno(accno); // Replace with actual logic to get account number
			}
			
			return true;
		} 
		catch (Exception e) 
		{
			System.err.println("Field with ID '" + paidat + "' is not visible: " + e.getMessage());
			return false;
		}
	}
	
	
	  public void performClickChargesAccButton() throws InterruptedException
	  {
	  clickchargesAccbutton.click(); 
	  reglc.waitmethod();
	  }
	  
	  
	  public void chargesaccno(String cubkId) throws InterruptedException 
		{ 
			System.out.println("Entering Charges Acc number: " + cubkId);
			reglc.switchToDefaultContent();
			WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
	        WebElement iframeElement = wait1.until(
	        	    ExpectedConditions.presenceOfElementLocated(
	        	        By.xpath("//iframe[contains(@id,'confirmDialogFrame')]")
	        	    )
	        	);
	        try
	        {
			driver.switchTo().frame(iframeElement);
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			String targetRef = cubkId;
			boolean found = false;
			for (WebElement row : rows) 
	        {
	            try {
	                // ✅ Get the first <td> with the <a> link inside it
	                WebElement firstCellLink = row.findElement(By.xpath("./td[1]/a"));

	                String refValue = firstCellLink.getText().trim();
	                System.out.println("Row REF: " + refValue);

	                if (refValue.equals(targetRef)) {
	                    firstCellLink.click();
	                    System.out.println("✅ Clicked: " + refValue);
	                    found = true;
	                    break;
	                }
	            } catch (Exception e) {
	                // Some rows might not have <td[1]/a>, safely skip
	                continue;
	            }
	        }
	        if (!found) {
	            throw new RuntimeException("❌ Reference not found: " + targetRef);
	        }
	        
 }
			catch (Exception e) 
			{
				/*
				 * System.err.println("❌ Could not find or read table : " + e.getMessage());
				 * throw new RuntimeException("Error while selecting charges account number: " +
				 * e.getMessage());
				 */
				reglc.switchToDefaultContent();
				WebElement cancelIcon = driver.findElement(By.cssSelector("svg.MuiSvgIcon-root[title='cancel']"));
				cancelIcon.click();
				reglc.switchToWorkFrame();
				//WebElement chargesaccno = driver.findElement(By.id("CHG_FLD_LOCAL_CUST_AC_NO"));
				//WebElement chargesaccno = wait1.until(ExpectedConditions.visibilityOf(chargesAccNoField));
				//WebElement ASSET_ACNO = wait1.until(ExpectedConditions.visibilityOf(chargesAccNoField));
				//chargesaccno.sendKeys(cubkId);
				try
				{
					WebElement cust = wait1.until(ExpectedConditions.visibilityOf(custaccno));
					String currentValue = cust.getAttribute("value");

					if (currentValue == null || currentValue.isEmpty()) {
					    cust.sendKeys(cubkId);
					}
					else
						{
						wait1.until(ExpectedConditions.visibilityOf(liabaccno)).isDisplayed();
				        liabaccno.sendKeys(cubkId);
					}	
					
					
				} catch (org.openqa.selenium.TimeoutException e1) 
				
				{
					try {
				        WebElement chargesaccno = wait1.until(ExpectedConditions.visibilityOf(chargesAccNoField));
				        chargesaccno.sendKeys(cubkId);
				        
				    }
					catch (Exception e2) {
				        System.err.println("❌ Unable to enter account number: " + e2.getMessage());
				        throw new RuntimeException("Failed to enter account number in either field.");
					}
				
				}
		}
	
		}}
	  
	  
	  
	  //Not for Changres tab, but for iframe handling
	  /*public void findiframe()
	  {
		  List<WebElement> allIframes = driver.findElements(By.tagName("iframe"));
		  System.out.println("Total number of iframes on the page: " + allIframes.size());

		  for (int i = 0; i < allIframes.size(); i++) {
		      String nameOrId = allIframes.get(i).getAttribute("name");
		      if (nameOrId == null || nameOrId.isEmpty()) {
		          nameOrId = allIframes.get(i).getAttribute("id");
		      }
		      System.out.println("Iframe " + i + ": " + nameOrId);
		  }
	  }*/


