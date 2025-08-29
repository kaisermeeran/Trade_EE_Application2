package com.pomtestcases;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.commonfile.parties_tab;

public class IssueamendmentLC {
	
	WebDriver driver;
	IPLC_Homepage iplcHomepage;
	
	
	public IssueamendmentLC(WebDriver driver) 
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//span[normalize-space(text())='Issue Amendment']") WebElement iplcissueamendmentLC;
	@FindBy(id="DETRMNTL_FLG") WebElement detrmtlFlag;
	@FindBy(id="C") WebElement partiesII;
	@FindBy(name="ISSUE_BK_ID_BTN") WebElement issueBankIdBtn;
	@FindBy(id="ISSUE_BK_REF") WebElement issueBankRef;
	
	public void clickIPLCIssueAmendmentLC() throws InterruptedException 
	{
		Thread.sleep(2000); // Adding a sleep to ensure the page is fully loaded before clicking
		IPLC_RegisterAmendmentLC iplcRegisterAmendobj= new IPLC_RegisterAmendmentLC(driver);
		//iplcRegisterAmendobj.clickIPLC();             //----Click on Import LC --  Should be comment when running the entire suite
		//iplcRegisterAmendobj.clickIPLCAmendmentLC(); //----Click on IPLC issue Amendment --  Should be comment when running the entire suite
		iplcissueamendmentLC.click();
	}
	
	public String DetrmtlFlagData(Map<String, String> data)
	{
		String flagValue = data.get("DETRIMENTAL_FLAG");
	    return selectDetrmtlFlag(flagValue);
	}
	
	public String selectDetrmtlFlag(String detrmtlFlagValue)
	{
		Select detrimentalselect = new Select(detrmtlFlag);
		detrimentalselect.selectByVisibleText(detrmtlFlagValue);
		return detrmtlFlagValue;
	}
	
	public void amendparties2value(Map<String, String> data) throws InterruptedException
	{
		clickpartiesII(data.get("ISSUING_BANK"), data.get("ISSUEBANK_REF"));
	}
	
	public void clickpartiesII(String issuebankid, String issuebnkref) throws InterruptedException
	{
		
		partiesII.click();
		issueBankIdBtn.click();
		parties_tab partiesobj = new parties_tab(driver);
		RegisterLC reglcobj = new RegisterLC(driver);
		reglcobj.waitmethod();
		reglcobj.handle_alert();
		partiesobj.enterAppCustId(issuebankid);
		reglcobj.switchToDefaultContent();
		reglcobj.switchToWorkFrame();
		issueBankRef.sendKeys(issuebnkref);
	}
	
}
