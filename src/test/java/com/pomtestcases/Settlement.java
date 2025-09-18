package com.pomtestcases;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.commonfile.charges_tab;

public class Settlement {
	
	WebDriver driver;
	IPLC_Homepage iplcHomepage;
	
	public Settlement(WebDriver driver) 
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//span[normalize-space(text())='IPLC Settlement']") WebElement Settlement;
	@FindBy(xpath="//span[normalize-space(text())='Pay/Accept']") WebElement payaccept;
	@FindBy(id="TENOR_START_DT") WebElement tenorstartdate;
	@FindBy(id="AVAL_BY") WebElement avalby;
	@FindBy(id="D") WebElement paymenttab;
	
	//Debit and Credit Function
	@FindBy(id="do_PaymentDebitHeader_Tab") WebElement debittab;
	@FindBy(xpath="//*[@id=\"_BUTTON_do_PaymentInstrDeal\"]/table") WebElement debitaddtable;
	@FindBy(id="PaymentDebit_ADD") WebElement debitaddbutton;
	@FindBy(name="CPYT_DR_VAL_DATE") WebElement debitvaluedate;
	@FindBy(name="CPYT_DR_AC_BTN") WebElement debitchargesaccbutton;
	@FindBy(id="PaymentDebit_SAVE") WebElement debitsavebutton;
	
	
	//Credit Elements
	@FindBy(id="do_PaymentCreditHeader_Tab") WebElement credittab;
	@FindBy(id="PaymentCredit_ADD") WebElement creditaddbutton;
	@FindBy(name="CPYT_CR_VAL_DATE") WebElement creditvaluedate;
	@FindBy(name="CPYT_ASSGN_ID_BTN") WebElement creditchargesaccbutton;
	//@FindBy(xpath="//*[@id=\"do_PaymentCredit_M\"]/table/tbody/tr[3]/td[4]/input[1]") WebElement creditchargesAcctxtbox;
	@FindBy(id="PaymentCredit_SAVE") WebElement creditsavebutton;
	
	
	@FindBy(xpath="//*[@id=\"do_PaymentDebit\"]/table/tbody/tr[6]/td[4]/input[1]") WebElement debitchargesAcctxtbox;
	@FindBy(xpath="//*[@id=\"do_PaymentCredit_M\"]/table/tbody/tr[3]/td[4]/input[1]") WebElement creditchargesAcctxtbox;
	
	public void ClickSettlement() throws InterruptedException 
	{
		Thread.sleep(2000); // Adding a sleep to ensure the page is fully loaded before clicking
		iplcHomepage = new IPLC_Homepage(driver);
		iplcHomepage.clickImportLC();   //----Click on Import LC --  Should be comment when running the entire suite
		Settlement.click();
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.PAGE_DOWN).perform();
		Thread.sleep(2000); // Adding a sleep to ensure the page is fully loaded before clicking
		payaccept.click();
	}
	
	public void get_prsentationInfo(Map<String, String> data) throws InterruptedException
	{
		presentationInfo(data.get("TENOR_DT"));
	}
	
	public void presentationInfo(String tenordate) throws InterruptedException 
	{
		Thread.sleep(2000); // Adding a sleep to ensure the page is fully loaded before clicking
		tenorstartdate.sendKeys(tenordate);
		tenorstartdate.sendKeys("\t");	
	}
	
	public void avalBy() throws InterruptedException {
	    Select dropdown = new Select(avalby);
	    String selectedOption = dropdown.getFirstSelectedOption().getText();
	    System.out.println("Selected option: " + selectedOption);

	    if (selectedOption.equals("BY MIXED PYMT") || selectedOption.equals("BY ACCEPTANCE") || selectedOption.equals("BY DEF PAYMENT") || selectedOption.equals("BY PAYMENT")) {
	        paymenttab.click();

	        // find all rows in table
	        List<WebElement> rows = driver.findElements(By.xpath("//div[starts-with(@id,'GD_0_')]/table"));
	        System.out.println("Total rows: " + rows.size());

	        for (int i = 0; i < rows.size(); i++) {
	            String cellId = "GD_0_" + i + "_CPYT_C_SDA_FLAG";
	            String radioId = "GridDO_Child_0_" + i;
	            handlePaymentRow(cellId, radioId, "2025-12-31",selectedOption);
	        }
	    } else {
	        System.out.println("Aval By is Not Bank");
	    }
	}

	private void handlePaymentRow(String cellId, String radioId, String date, String avalby) throws InterruptedException {
	    WebElement cell = driver.findElement(By.id(cellId));
	    System.out.println("Cell value: " + cell.getText());
	    if(avalby.equals("BY PAYMENT"))
	    {
	    	//if(cell.getText().equals("Acceptance") || cell.getText().equals("Deferred"))
		    driver.findElement(By.id(radioId)).click();
		    driver.findElement(By.id("PaymentInstrDeal_EDIT")).click();

		    Debit();
		    Credit();
		    driver.findElement(By.id("PaymentInstrDeal_CANCEL")).click();
		    //driver.findElement(By.id("PaymentInstrDeal_SAVE")).click();
		    try 
		    { 
		    	Thread.sleep(1000); 
		    } catch (InterruptedException e) { }
	    
	    }
	    else if(avalby.equals("BY ACCEPTANCE") || avalby.equals("BY DEF PAYMENT") || avalby.equals("BY MIXED PYMT"))
	    {
	    	 driver.findElement(By.id(radioId)).click();
			    driver.findElement(By.id("PaymentInstrDeal_EDIT")).click();

			    WebElement dateField = driver.findElement(By.name("CPYT_D_MAT_DATE"));
			    String dateValue = dateField.getAttribute("value");
		    if (dateField.getAttribute("value").isEmpty()) 
		    {	
		    	dateField.clear();
			    dateField.sendKeys(date);
		        
		        //return; // No need to update
		    }else 
		    {
		    	System.out.println("Date is already set to " + dateValue);
		    }
		    
		    driver.findElement(By.id("PaymentInstrDeal_SAVE")).click();
		    try 
		    { 
		    	Thread.sleep(1000); 
		    } catch (InterruptedException e) { }
	    	
	    }

	}
	
	public void Debit() throws InterruptedException
	{
		System.out.println("Debit Function");
		debittab.click();
		if(debitaddtable.isDisplayed())
		{
			debitaddbutton.click();
			debitvaluedate.sendKeys("2024-12-31");
			debitchargesaccbutton.click();
			charges_tab charges = new charges_tab(driver);
			charges.enterChargesAccNo("123456789012", debitchargesAcctxtbox);
			debitsavebutton.click();
			try 
		    { 
		    	Thread.sleep(1000); 
		    } catch (InterruptedException e) { }
		}
		else
		{
			System.out.println("Debit Add Table is not displayed");
		}
	}
	
	public void Credit() throws InterruptedException
	{
		System.out.println("Credit Function");
		credittab.click();
		creditaddbutton.click();
		creditvaluedate.sendKeys("2024-12-31");
		creditchargesaccbutton.click();
		charges_tab charges1 = new charges_tab(driver);
		charges1.enterChargesAccNo("C003210", creditchargesAcctxtbox);
		
		creditsavebutton.click();
		try 
	    { 
	    	Thread.sleep(1000); 
	    } catch (InterruptedException e) { }
		
	}
	

}
