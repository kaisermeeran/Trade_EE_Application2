package com.pomtestcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Descrepancy_Document {
	
	WebDriver driver;
	IPLC_Homepage iplcHomepage;
	
	public Descrepancy_Document(WebDriver driver) 
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//span[normalize-space(text())='IPLC Discrepancies']") WebElement DicrepancyDocument;
	@FindBy(xpath="//span[normalize-space(text())='Register Discrepancies']") WebElement RegisterDiscrepancy;
	
	public void Clickdescrepancies() throws InterruptedException 
	{
		Thread.sleep(2000); // Adding a sleep to ensure the page is fully loaded before clicking
		iplcHomepage = new IPLC_Homepage(driver);
		iplcHomepage.clickImportLC();   //----Click on Import LC --  Should be comment when running the entire suite
		DicrepancyDocument.click();
		RegisterDiscrepancy.click();
	}
	
	

}
