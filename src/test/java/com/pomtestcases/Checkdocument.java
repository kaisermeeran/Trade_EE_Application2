package com.pomtestcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Checkdocument {
	
	WebDriver driver;
	IPLC_Homepage iplcHomepage;
	
	public Checkdocument(WebDriver driver) 
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath="//span[normalize-space(text())='Check Document']") WebElement checkDocumentLC;
	@FindBy(xpath="//span[normalize-space(text())='IPLC Presentation']") WebElement iplcPresentation;
	
	public void ClickCheckDocumentLC() throws InterruptedException 
	{
		Thread.sleep(2000); // Adding a sleep to ensure the page is fully loaded before clicking
		iplcHomepage = new IPLC_Homepage(driver);
		//iplcHomepage.clickImportLC();   //----Click on Import LC --  Should be comment when running the entire suite
		//iplcPresentation.click(); 		//----Click on iplcPresentation --  Should be comment when running the entire suite
		checkDocumentLC.click();
		
	}

}
