package com.pomtestcases;

import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class IPLC_RegisterAmendmentLC 
{
	WebDriver driver;
	IPLC_Homepage iplcHomepage;
	
	
	public IPLC_RegisterAmendmentLC(WebDriver driver) 
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.iplcHomepage = new IPLC_Homepage(driver);
	}
	
	@FindBy(xpath="//span[normalize-space(text())='IPLC Amendment']") WebElement iplcAmendmentLC;
	@FindBy(xpath="//span[normalize-space(text())='Register Amendment']") WebElement registerAmendmentLC;
	@FindBy(name="INC_AMT") WebElement inclcamt;
	@FindBy(name="DEC_AMT") WebElement declcamt;
	@FindBy(name="NEW_LC_AMT") WebElement newlcamt;
	@FindBy(name="NEW_LC_BAL") WebElement newlcbalance;
	@FindBy(name="NEW_AMT_SPEC") WebElement newtoldropvalue;
	@FindBy(name="NEW_POS_TOL") WebElement newpostolerance;
	
	
	public void clickIPLC()
	{
		iplcHomepage.clickImportLC();
	}
	
	public void clickIPLCAmendmentLC() throws InterruptedException 
	{
		Thread.sleep(2000); // Adding a sleep to ensure the page is fully loaded before clicking
		iplcAmendmentLC.click();
	}
	public void clickRegisterAmendmentLC() 
	{
		registerAmendmentLC.click();
	}
	//=================Register Amendment LC Fields validation========================
	
	public void Amendment_information(Map<String, String> data)
	{
		IncreaselcAmount(data.get("INC_LC_AMT"), data.get("DEC_LC_AMT"),data.get("NEW_LC_AMT"),data.get("NEW_LC_BAL"));
	}
	
	public void Tolerance_value(Map<String, String> data) throws InterruptedException
	{
		Amend_tolerance(data.get("NEW_TOL"), data.get("POS_TOL"));
	}
	
	
	public void IncreaselcAmount(String incamt, String decamt, String excelnewlcamt, String newlcbal)
	{
		System.out.println("Increase LC Amount:" + incamt);
		System.out.println("Decrease LC Amount:" + decamt);
		String newlcamtValuebeforeamd = newlcamt.getAttribute("value");
		System.out.println("New LC Amount before increment/decrement: " + newlcamtValuebeforeamd);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		if (incamt != null && !incamt.trim().isEmpty() &&
		        (decamt == null || decamt.trim().isEmpty())) 
		{
			
			wait.until(ExpectedConditions.elementToBeClickable(inclcamt));
			inclcamt.click();
			inclcamt.sendKeys(incamt);
			inclcamt.sendKeys("\t");
		}
		else if(decamt != null && !decamt.trim().isEmpty() &&
	             (incamt == null || incamt.trim().isEmpty()))
		{
			wait.until(ExpectedConditions.elementToBeClickable(declcamt));
			declcamt.click();
			declcamt.sendKeys(decamt);
			declcamt.sendKeys("\t");
		}
		
		String newlcamtValue = newlcamt.getAttribute("value");
		System.out.println("New LC Amount after increment/decrement: " + newlcamtValue);
		Assert.assertEquals(excelnewlcamt, newlcamtValue, "New LC Amount does not match the expected value.");
		
		String newlcbalancevalue = newlcbalance.getAttribute("value");
		Assert.assertEquals(newlcbalancevalue, newlcbal, "New LC Balance does not match the expected value.");	
	}
	
	public void Amend_tolerance(String newtol, String postol) throws InterruptedException
	{
		System.out.println("New Tolerance: " + newtol);
		System.out.println("Positive Tolerance: " + postol);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		Select newtolselect = new Select(newtoldropvalue);
		newtolselect.selectByVisibleText(newtol);
		
		String selectedText = newtolselect.getFirstSelectedOption().getText();
		if ("NOT EXCEEDING".equals(selectedText)) {
			System.out.println("Tolerance is selected as Not Exceeding.");
		} else {
			if (newpostolerance.isEnabled() && newpostolerance.isDisplayed()) {
				newpostolerance.clear();
				newpostolerance.sendKeys(postol);
				Thread.sleep(1000);
			} else {
				System.out.println("newpostolerance is not editable or visible.");
			}
		}
		
	}
	
	

}
