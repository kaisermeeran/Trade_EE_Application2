package com.pomtestcases;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

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
	@FindBy(id="DOC_STAT") WebElement documentStatus;
	@FindBy(id="DISC_DET") WebElement discrepancyNotes;
	@FindBy(id="LOCAL_AMT") WebElement localAmount;
	@FindBy(id="LC_AMT") WebElement lcAmount;
	@FindBy(id="PRES_AMT") WebElement presentationAmount;
	
	public void ClickCheckDocumentLC() throws InterruptedException 
	{
		Thread.sleep(2000); // Adding a sleep to ensure the page is fully loaded before clicking
		iplcHomepage = new IPLC_Homepage(driver);
		iplcHomepage.clickImportLC();   //----Click on Import LC --  Should be comment when running the entire suite
		iplcPresentation.click(); 		//----Click on iplcPresentation --  Should be comment when running the entire suite
		checkDocumentLC.click();
	}
	
	public String Document_Status(Map<String, String> data)
	{
		return DocStatus (data.get("DOC_STATUS"),data.get("DISC_NOTED")); 
	}
	
	public String DocStatus(String docstatus, String discnote)
	{
		Select dropdown_docstatus = new Select(documentStatus);
		dropdown_docstatus.selectByVisibleText(docstatus);
		//String selectedOption = dropdown_docstatus.getFirstSelectedOption().getText();
		if(docstatus.equalsIgnoreCase("Discrepancy Found"))
		{
			discrepancyNotes.sendKeys(discnote);
		}
		else
		{
		
		}
		return docstatus;
	}
	
	public void validate_presentationamt()
	{
		String locamt = localAmount.getAttribute("value");
		String lcamt = lcAmount.getAttribute("value");
		String presamt = presentationAmount.getAttribute("value");
		
		System.out.println("Presentation Local Amount: " + locamt);
		System.out.println("Presentation LC Amount: " + lcamt);
		System.out.println("Presentation Amount: " + presamt);
	}
	

}
