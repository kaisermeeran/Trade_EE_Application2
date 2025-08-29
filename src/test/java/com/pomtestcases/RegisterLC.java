package com.pomtestcases;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.commonfile.charges_tab;
import com.commonfile.parties_tab;
import com.commonfile.Catalog;

public class RegisterLC
{

	WebDriver driver;
	private parties_tab partiesobj;
	
	public RegisterLC(WebDriver driver) 
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.partiesobj = new parties_tab(driver);
	}
	
	
	@FindBy(id = "FORM_OF_LC") WebElement formOfLC;
	@FindBy(id = "APLB_RULE") WebElement aplbRules;
	@FindBy(id="AVAL_BY") WebElement avalBy;
	@FindBy(id = "LC_CCY") WebElement lc_CCY;
	@FindBy(id = "LC_AMT") WebElement lcAmt;
	@FindBy(id = "LC_BAL") WebElement lcBalance;
	//@FindBy(id = "LC_BAL") WebElement lcriskBalance;
	@FindBy(id="LOCAL_AMT") WebElement localAmt;
	@FindBy(id="AMT_SPEC") WebElement tolerance ;
	@FindBy(id="POS_TOL") WebElement posTolerance;
	@FindBy(id="EXPIRY_DT") WebElement expiryDate;
	@FindBy(id = "ADD_AMT_COVRD") WebElement addAmtCovered;
	@FindBy(css = "span#referenceNumber") WebElement referencenumber;
	@FindBy(id = "C") WebElement partiesTab;
	@FindBy(name="APPL_ID_BTN") WebElement applicantIdButton;
	//@FindBy(xpath = "//iframe[contains(@id,'confirmDialogFrame')]") WebElement confirmDialogFrame;
	@FindBy(id="CataListTab") WebElement table;
	@FindBy(name="BENE_ID_BTN") WebElement beneficiaryIdButton;
	@FindBy(id="AC_OFFICER_CODE")WebElement acccofficecode;
	@FindBy(id="SAME_AS_APPL_FLG") WebElement sameAsAppflag;
	@FindBy(name="FORACOF_ID_BTN") WebElement forAccofficerIdButton;
	@FindBy(name="ADV_BK_ID_BTN") WebElement advBankIdButton;
	@FindBy(id="B") WebElement risktab;
	@FindBy(name="ASSET_ACNO_BTN") WebElement banklibbtn;
	@FindBy(name="APPL_AC_MRGN_BTN") WebElement custAccNoButton;
	
	
	//Charges tab elements can be added here as needed
	@FindBy(id="F") WebElement chargestab;
	@FindBy(id="CHG_FLD_ALL_CHARGE_AT") WebElement paidAt;
	@FindBy(id="CHG_GETAC_BTN") WebElement clickchargesAccbutton;
	
	@FindBy(id="APPL_ID") WebElement applicantidtextbox;
	@FindBy(id="BENE_ID") WebElement benificiaryidtextbox;	
	@FindBy(id="FORACOF_ID") WebElement accofficeridtextbox;
	@FindBy(id="ADV_BK_ID") WebElement advbankidtextbox;
	
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	
	
	public void switchToWorkFrame() 
	{
	    driver.switchTo().defaultContent();
	    new WebDriverWait(driver, Duration.ofSeconds(10))
	        .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("work"));
	}
	
	public void waitmethod() throws InterruptedException 
	{
		Thread.sleep(500);
	}

	public void enterFormOfLC(String form) throws InterruptedException 
	{
		
		switchToWorkFrame();
		Select formSelect = new Select(formOfLC);
		formSelect.selectByVisibleText(form);
	}
	
	public void enterAPLB_Rules(String rules) throws InterruptedException 
	 { 
		
		Select aplbSelect = new Select(aplbRules);
		aplbSelect.selectByVisibleText(rules);
	 }
	public void enterAvalBy(String aval) 
	{ 
		
		Select avalSelect = new Select(avalBy);
		avalSelect.selectByVisibleText(aval);
	} 
	public void enterLC_Amt(String amt, String lccy,String getlocalamt, String lcrisk) throws InterruptedException 
	{ 
		Select lcccyselect = new Select(lc_CCY);
		lcccyselect.selectByVisibleText(lccy);
		
		String checklc_CCY = lc_CCY.getAttribute("value"); 
		Assert.assertEquals(checklc_CCY, lccy, "LC Currency is not" + lccy + ".");
		
		//boolean checkLCbalance = lcBalance.isDisplayed();
		//Assert.assertTrue(checkLCbalance,  "LC Balance field is not displayed.");
		
		Thread.sleep(500);
		lcAmt.click();
		lcAmt.sendKeys(amt); 
		
		lcAmt.sendKeys("\t"); // Simulate tab key to trigger any onChange events
		Thread.sleep(500);
		
		String riskBalance = lcBalance.getAttribute("value");
		System.out.println(lcrisk  + " is the LC Risk Balance equal to."+ riskBalance);
		
		
		String getlocalvalue=localAmt.getAttribute("value");
		System.out.println("" + getlocalvalue + " is the local amount.");
		//Assert.assertEquals(getlocalvalue, getlocalamt, "Local Amount is not correct " + getlocalamt + ".");
		
	}
	
	
	public void selectTolerance(String tolexceed, String tolval) throws InterruptedException {
	    Thread.sleep(500);

	    Select toleranceSelect = new Select(tolerance);
	    toleranceSelect.selectByVisibleText(tolexceed);

	    Thread.sleep(500);

	    String selectedText = toleranceSelect.getFirstSelectedOption().getText();

	    if ("NOT EXCEEDING".equals(selectedText)) {
	        System.out.println("Tolerance is selected as Not Exceeding.");
	    } 
	    else {
	        // Check if posTolerance is enabled and displayed before sending input
	        if (posTolerance.isEnabled() && posTolerance.isDisplayed()) {
	            posTolerance.clear();
	            posTolerance.sendKeys(tolval);
	        } else {
	            System.out.println("posTolerance is not editable or visible.");
	        }
	    }
	}
	public void enterExpiryDate(String date) throws InterruptedException 
	{ 
		Thread.sleep(500);
		expiryDate.sendKeys(date); 
		Thread.sleep(500);
	}
	public void enterAddAmtCovered(String amt) throws InterruptedException 
	{ 
		Thread.sleep(500);
		addAmtCovered.sendKeys(amt); 
		Thread.sleep(500);
	}
	
	public void switchToDefaultContent() 
	{
	    driver.switchTo().defaultContent();
	}
	
	public void switchtopathinfo() 
	{
	    driver.switchTo().defaultContent();
	    new WebDriverWait(driver, Duration.ofSeconds(10))
	        .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("eeToolbar"));
	    new WebDriverWait(driver, Duration.ofSeconds(10))
	        .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("pathInfo"));
	}
	
	public String copyrefnum() throws InterruptedException
	{
		switchtopathinfo();
		String refNumber = null;
        try 
        {
        	refNumber = referencenumber.getText();
        	System.out.println("Reference Number is: " + refNumber);
        } 
        catch (Exception e)
        	{
				System.err.println("❌ Could not find or read span#referenceNumber: " + e.getMessage());
        					
        	}
        
        return refNumber;
			
		/*
		 * switchToDefaultContent(); switchToWorkFrame(); Catalog catalogobj = new
		 * Catalog(driver); catalogobj.supervisorCatalog();
		 * catalogobj.commoncatalog(refNumber);
		 */	
	}
	
	public void partiestab() throws InterruptedException
	{
		partiesTab.click();
		waitmethod();
	}
	
	public void clickApplicantIdButton(String applicantid) throws InterruptedException
	{
		if (!applicantidtextbox.getAttribute("value").isEmpty())
		{
			System.out.println("Already Details existing.");
		}
		else
		{
			
		applicantIdButton.click();
		partiesobj.enterAppCustId(applicantid);
		}
		//Thread.sleep(3000);
	}
	
	
	
	public void clickBeneficiaryIdButton(String benificiaryid) throws InterruptedException
	{
		switchToDefaultContent();
		switchToWorkFrame();
		if (!benificiaryidtextbox.getAttribute("value").isEmpty())
		{
			System.out.println("Beneficiary Details existing.");
		}
		else
		{
			beneficiaryIdButton.click();
			partiesobj.enterAppCustId(benificiaryid); // Example ID, replace with actual logic
		}
		
		//Thread.sleep(3000);
	}
	
	public void enterAccOfficeCode(String acccode) throws InterruptedException 
	{ 
		switchToDefaultContent();
		switchToWorkFrame();
		acccofficecode.sendKeys(acccode);
	}
	
	public void enterSameAsApp(String sameasapp1, String Sameasid) throws InterruptedException 
	{ 
		
		parties_tab partiesobj = new parties_tab(driver);
		Select sameas = new Select(sameAsAppflag);
		if( sameasapp1.equals("Yes"))
		{
		sameas.selectByVisibleText(sameasapp1);
		}
		else if(sameasapp1.equals("No"))
		{
			sameas.selectByVisibleText(sameasapp1);
			forAccofficerIdButton.click();
			handle_alert();
			partiesobj.enterAppCustId(Sameasid); // Example ID, replace with actual logic
		}
	}
	public void handle_alert()
	{
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		System.out.println("Alert says: " + alertText);
		alert.accept(); // Accept the alert
	}
	
	public void clickadvBankIdButton(String advisingbankid) throws InterruptedException
	{
		switchToDefaultContent();
		switchToWorkFrame();
		if (!advbankidtextbox.getAttribute("value").isEmpty())
		{
			System.out.println("Already advising bank details existing.");
		}
		else
		{
			advBankIdButton.click();
			waitmethod();
			handle_alert();
			partiesobj.enterAppCustId(advisingbankid); // Example ID, replace with actual logic
		}
		
	}
	
	public void clickRiskTab() throws InterruptedException
	{
		switchToDefaultContent();
		switchToWorkFrame();
		risktab.click();
		waitmethod();
		
	}
	
	public void clickbankliabiiltyButton() throws InterruptedException
	{
		switchToDefaultContent();
		switchToWorkFrame();
		banklibbtn.click();
		waitmethod();
		//handle_alert();
	}
	
	public void clickcustliabiiltyButton() throws InterruptedException
	{
		switchToDefaultContent();
		switchToWorkFrame();
		custAccNoButton.click();
		waitmethod();
		//handle_alert();
	}
}

