package com.pomtestcases;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class RegisterDocument {
	
	WebDriver driver;
	IPLC_Homepage iplcHomepage;
	
	
	public RegisterDocument(WebDriver driver) 
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath="//span[normalize-space(text())='IPLC Presentation']") WebElement iplcPresentation;
	@FindBy(xpath="//span[normalize-space(text())='Register Document LC']") WebElement registerDocumentLC;
	@FindBy(id="PRES_AMT") WebElement presentation_amt;
	@FindBy(id="ADDIT_PRES_BK_AMTS") WebElement additional_amt;
	@FindBy(id="CHGS_DEDUCTED") WebElement charges_deducted;
	@FindBy(id="PRES_BK_CHGS") WebElement presentater_charges;
	@FindBy(id="TOTAL_AMT") WebElement total_amt;
	@FindBy(id="PRES_BK_REF")WebElement presenter_ref;
	@FindBy(id="B") WebElement partiestab;
	@FindBy(id="DOC_PRES_BY") WebElement Docpresentedby;
	
	
	
	public void ClickRegisterDocumentLC() throws InterruptedException 
	{
		Thread.sleep(2000); // Adding a sleep to ensure the page is fully loaded before clicking
		iplcHomepage = new IPLC_Homepage(driver);
		iplcHomepage.clickImportLC();             //----Click on Import LC --  Should be comment when running the entire suite
		iplcPresentation.click();
		registerDocumentLC.click();
	}
	
	public void presentation_info(Map<String, String> data)
	{
		validate_presentationinfo(data.get("PRESEN_AMT"),data.get("PERCENTER_REF"),data.get("DRF_ORIGINAL"),data.get("DRF_COPY "),data.get("INV_ORIGINAL"),data.get("INV_COPY"),data.get("BL/AWB_ORIGINAL"),data.get("BL/AWB_COPY"),
				data.get("ADD_AMT"),data.get("CHG_DEDUCTED"),data.get("PRESEN_CHG"),data.get("TOT_AMT"));
		//presentation_info1(data);
	}
	
	public void getDocPresentedBy(Map<String, String> data)
	{
		parties_info(data.get("DOC_PRESENTED_BY"));
	}
	
	public void presentation_info1(Map<String, String> data) {
	    // Get multiple values for DRF_ORIGINAL
	    List<String> drfOriginalValues = List.of(data.get("DRF_ORIGINAL").split(","));
	    
	    System.out.println("DRF Original Values: " + drfOriginalValues);
	    for (String value : drfOriginalValues) {
	        System.out.println("Processing DRF Original Value: " + value);
	        // Add your processing logic here
	    }
	   
	}

	
	public void validate_presentationinfo(String peramt, String preref, String drforg, String drfcpy, String invorg, String invcpy, String BLorg, String BLcpy, String addamt,String chgdedect, String presenterchg, String totamt)
	{
		System.out.println("Presentation amt is :" +peramt);
		System.out.println("Presentation ref is: " +preref);
		System.out.println("Presentation draft Original : " +drforg);
		System.out.println("Presentation draft Copy : " +drfcpy);
		System.out.println("Presentation Invoice Original : " +invorg);
		System.out.println("Presentation Invoice copy : " +invcpy);
		System.out.println("Presentation BL/ABW Original : " +BLorg);
		System.out.println("Presentation BL/ABW Copy : " +BLcpy);
		
		presentation_amt.sendKeys(peramt);
		
		additional_amt.click();
		additional_amt.sendKeys(addamt);
		charges_deducted.click();
		charges_deducted.sendKeys(chgdedect);
		presentater_charges.click();
		presentater_charges.sendKeys(presenterchg);
		presentater_charges.sendKeys("\t");
		String totalvalue = total_amt.getAttribute("value");
		System.out.println("Total amt is : " +totalvalue);
		Assert.assertEquals(totalvalue,totamt, "Total amt is not matching");
		presenter_ref.sendKeys(preref);
	}
	
	public void parties_info(String docpresentby)
	{
		partiestab.click();
		Select dropdown = new Select(Docpresentedby);
		dropdown.selectByVisibleText(docpresentby);
		String selectedOption = dropdown.getFirstSelectedOption().getText();
		System.out.println("Selected Document Presented By: " + selectedOption);
	}
	
	

}
