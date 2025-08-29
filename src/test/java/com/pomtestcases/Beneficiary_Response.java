package com.pomtestcases;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class Beneficiary_Response {
	
	WebDriver driver;
	IPLC_Homepage iplcHomepage;
	
	
	public Beneficiary_Response(WebDriver driver) 
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath="//span[normalize-space(text())='Beneficiary Response']") WebElement BeneResponse;
	@FindBy(id="BENE_CONS_FLG") WebElement BeneConsFlg;
	
	public void clickBeneficiaryResponse() throws InterruptedException 
	{
		Thread.sleep(2000); // Adding a sleep to ensure the page is fully loaded before clicking
		//IPLC_RegisterAmendmentLC iplcRegisterAmendobj= new IPLC_RegisterAmendmentLC(driver);
		//iplcRegisterAmendobj.clickIPLC();             //----Click on Import LC --  Should be comment when running the entire suite
		//iplcRegisterAmendobj.clickIPLCAmendmentLC(); //----Click on IPLC issue Amendment --  Should be comment when running the entire suite
		BeneResponse.click();
	}
	
	public void selectBeneConsFlag(Map<String, String> data) throws InterruptedException
	{
		
		BeneConsFlag(data.get("BENE_DECISION"));
	}
	
	public void BeneConsFlag(String beneConsFlagValue)
	{
		Select beneConsSelect = new Select(BeneConsFlg);
		beneConsSelect.selectByVisibleText(beneConsFlagValue);
		if (beneConsFlagValue.equalsIgnoreCase("Accepted")) 
		{
			System.out.println("Beneficiary Response: Accepted");
			/*driver.switchTo().defaultContent();
			driver.switchTo().frame("eeToolbar");
			driver.findElement(By.name("voucher")).click();
			RegisterLC registerLC = new RegisterLC(driver);
			registerLC.handle_alert(); // Accept the alert after clicking the button
			driver.switchTo().defaultContent();
			driver.switchTo().frame("eeToolbar");
			WebElement voucherElement = driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/div[1]/h6"));
			String voucher = voucherElement.getText().trim();
			System.out.println("Beneficiary Response Voucher Generated: " + voucher);*/
		} 
		else {
			System.out.println("Beneficiary Response: No Action Taken");
		}
	}
	
}
