package com.pomtestcases;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

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

public class IssueLetterofCredit {
	RegisterLC registerLC;
	IPLC_Homepage iplcHomepage;
	
	WebDriver driver;
	public IssueLetterofCredit(WebDriver driver) 
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.registerLC = new RegisterLC(driver);
		this.iplcHomepage = new IPLC_Homepage(driver);
	}
	@FindBy(xpath = "//span[normalize-space(text())='Import Letter of Credit']") WebElement Iplcmodule;
	@FindBy(xpath = "//span[normalize-space(text())='IPLC Issuance']") WebElement IPLCissuance;
	@FindBy(xpath = "//span[normalize-space(text())='Issue Letter of Credit']") WebElement issueLC;
	@FindBy(id="B") WebElement Parties1;
	@FindBy(id="REV_LC") WebElement revolvingFlag;
	@FindBy(id="ISSUE_DT") WebElement issueDate;
	@FindBy(id="ADV_THU_BK_ID") WebElement advisingThroughBanktextbox;
	@FindBy(name="ADV_THRU_BK_ID_BTN") WebElement advBankIdButton;
	@FindBy(xpath="(//td[@class='title_top'])[3]") WebElement clickPartiesTab2;
	@FindBy(id="REIM_BK_AUTH_REQ") WebElement reimBankAuthReq;
	@FindBy(id="REIM_BK_ID") WebElement reimbursingBanktextbox;
	@FindBy(name="REIM_BK_ID_BTN") WebElement reimbursingBankIdButton;
	@FindBy(id="REIM_BK_CHRG_DESC") WebElement reimcharge;
	@FindBy(id="D") WebElement tenortab;
	@FindBy(id="AVAL_BY") WebElement avalBy;
	@FindBy(id="TENOR_DAYS") WebElement tenorDays;
	@FindBy(id="TENOR_TYPE") WebElement tenorType;
	@FindBy(id="DRAFTS_AT") WebElement draftsAt;
	@FindBy(id="DEF_PMT_DET") WebElement Defngodetails;
	@FindBy(id="K") WebElement mixedpaymenttab;
	@FindBy(id="PaymentTerms_ADD") WebElement addPaymentTerms;
	@FindBy(name="CPYT_C_SDA_FLAG") private WebElement mixedpaymentdropdown;
	@FindBy(name="CPYT_C_PAY_PER") WebElement mixedpercentage;
	@FindBy(name="CPYT_I_TENOR_DAYS")WebElement mixedtenordays;
	@FindBy(name="CPYT_C_TENOR_TYPE") WebElement mixedtenortype;
	@FindBy(id="PaymentTerms_SAVE") WebElement  mixpaysavebutton;
	@FindBy(id="H") WebElement Chargestab;
	
	
	public void doLogin(Map<String, String> data) throws InterruptedException 
	{
		ClickRevolvingFlag(data.get("REVOLVING_FLAG"));
		verifyFORM_OF_LC(data.get("FORM_OF_LC"));
		verify_APLB_Rules(data.get("APLB_RULE"));
		Verify_Date_of_Issue(data.get("ISSUE_DATE"));
		verify_Expiry_Date(data.get("EXPIRY_DT"));
		
    }
	
	public void parties1(Map<String, String> data) throws InterruptedException 
	{
		clickadvisingthroughbank(data.get("ADV_THROUGH_BANK"));
	}
	
	public void parties2(Map<String, String> data) throws InterruptedException 
	{
		clickreimbursebank(data.get("REIM_AUTH"), data.get("REIM_BANK_ID"),data.get("REIM_CHARGE"));
		
	}
	
	public void tenortabdetails(Map<String, String> data) 
	{
		
		
		checktenordetails(data.get("AVAL_BY"), data.get("TENOR_DAY"),data.get("TENOR_DESC"),
				data.get("MIX_PAY_DETAILS"),data.get("PERCEN"),data.get("MIX_TENOR_DAYS"),
				data.get("MIX_TENOR_TYP"));
		 }
		//data.get("MIX_PAY_DETAILS")
		
	
	
	public void Chargestabdetails(Map<String, String> data) throws InterruptedException
	{
		issuechargesdetails(data.get("PAID_AT"), data.get("CHARGES_ACC_NO"));
	}
	
	
	
	
	
	//===================================================================================================
	public void ClickRevolvingFlag(String revolvingFlagText) 
	{
		System.out.println("Revolving Flag: " + revolvingFlagText);
		
		  Select selectRevolvingFlag = new Select(revolvingFlag);
		  selectRevolvingFlag.selectByVisibleText(revolvingFlagText); 
	}
	
	public void clickIssueLetterofCredit() {
	  
		   
		   //Iplcmodule.click();
		   //IPLCissuance.click();
		   issueLC.click();
	  
	}

	
	/*public void clickIssueLetterofCredit() 
	{
		IPLCissuance.click();
	}
	public void clickIssueIPLC() 
	{
		issueLC.click();
	}*/
	
	//General Information Section
	public void verifyFORM_OF_LC(String formOfLCText) 
	{
		System.out.println("Form of LC: " + formOfLCText);
		
		Select selectFormOfLC = new Select(registerLC.formOfLC);
		
		String selectedOption = selectFormOfLC.getFirstSelectedOption().getText();
		Assert.assertEquals(selectedOption, formOfLCText, "Form of LC selection mismatch");
	}
	
	public void verify_APLB_Rules(String aplbRulesText) 
	{
		System.out.println("APLB Rules: " + aplbRulesText);
		
		Select selectAplbRules = new Select(registerLC.aplbRules);
		
		String selectedOption = selectAplbRules.getFirstSelectedOption().getText();
		Assert.assertEquals(selectedOption, aplbRulesText, "APLB Rules selection mismatch");
	}
	
	public void Verify_Date_of_Issue(String expectedIssueDateText) {
	    System.out.println("Expected Date of Issue: " + expectedIssueDateText);
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.visibilityOf(issueDate));
	    String actualIssueDateText = issueDate.getAttribute("value");
	    System.out.println("Actual Date of Issue: " + actualIssueDateText);
	    Assert.assertEquals(actualIssueDateText, expectedIssueDateText, "Date of Issue does not match expected value");   
	}

	public void verify_Expiry_Date(String expiryDateText)
	{
		// Add verification logic for Expiry Date
		System.out.println("Expiry Date: " + expiryDateText);
		WebElement expiryDateField = registerLC.expiryDate;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(expiryDateField));
		String actualExpiryDateText = expiryDateField.getAttribute("value");
		System.out.println("Actual Expiry Date: " + actualExpiryDateText);
		Assert.assertEquals(actualExpiryDateText, expiryDateText, "Expiry Date does not match expected value");
	}
	
	public void clickParties1() throws InterruptedException 
	{
		Thread.sleep(1000); // Wait for the element to be clickable
		Parties1.click();
	}
		
	public void clickadvisingthroughbank(String advBankid) throws InterruptedException
	{
		parties_tab partiesobj = new parties_tab(driver);
		if (!advisingThroughBanktextbox.getAttribute("value").isEmpty())
		{
			System.out.println("Already Details existing.");
		}
		else
		{
		RegisterLC reglcobj = new RegisterLC(driver);
		advBankIdButton.click();
		reglcobj.waitmethod();
		reglcobj.handle_alert();
		partiesobj.enterAppCustId(advBankid);	
		}
	}
	
	public void clickpartiestab2() throws InterruptedException 
	{
		// Switch to the default content and then to the work frame
		registerLC.switchToDefaultContent();
		registerLC.switchToWorkFrame();	
		// Wait for the element to be visible before clicking
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.visibilityOf(clickPartiesTab2));
		clickPartiesTab2.click();
	}
	
	//Reimbursing Bank Section
	public void clickreimbursebank(String reimBankAuthReqText, String reimbankid, String reimcha) throws InterruptedException 
	{
		System.out.println("Reimbursing Bank Authorization Required: " + reimBankAuthReqText);
		
		Select selectReimBankAuthReq = new Select(reimBankAuthReq);
		selectReimBankAuthReq.selectByVisibleText(reimBankAuthReqText);
		
		if (reimBankAuthReqText.equals("Yes")) {
			reimbursingBankIdButton.click();
			parties_tab partiesobj = new parties_tab(driver);
			 // Handle any alert that appears
			RegisterLC reglcobj = new RegisterLC(driver);
			reglcobj.handle_alert();
			partiesobj.enterAppCustId(reimbankid); // Replace with actual logic
			registerLC.switchToDefaultContent();
			registerLC.switchToWorkFrame();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			
			Select reimchar = new Select(reimcharge);
			reimchar.selectByVisibleText(reimcha);
			
		}
		else {
			System.out.println("Reimbursing Bank Authorization is not required.");
		}
		
	}
	
	public void tenortab()
	{
		registerLC.switchToDefaultContent();
		registerLC.switchToWorkFrame();	
		// Wait for the element to be visible before clicking
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.visibilityOf(clickPartiesTab2));
	    tenortab.click();
	}
	
	public void checktenordetails(String aval, String tenorDay, String tenorDesc, String Mixeddetails, String percval, String mixedtendays, String mixedtentyp)
	{
		if(aval.equals("By Payment")) 
		{
		Select avalSelect = new Select(avalBy);
		avalSelect.selectByVisibleText(aval);
		}
		else if(aval.equals("By Acceptance")) 
		{
			Select avalSelect = new Select(avalBy);
			avalSelect.selectByVisibleText(aval);
			tenorDays.clear();
			tenorDays.sendKeys(tenorDay);
			Select tenorTypeSelect = new Select(tenorType);
			tenorTypeSelect.selectByVisibleText(tenorDesc);
			String draftvalue = draftsAt.getAttribute("value");
			Assert.assertEquals(draftvalue, tenorDay + " " + tenorDesc, "Drafts At value does not match expected value");
		}
		else if(aval.equals("By Negotiation")) 
		{
			Select avalSelect = new Select(avalBy);
			avalSelect.selectByVisibleText(aval);
			tenorDays.clear();
			tenorDays.sendKeys(tenorDay);
			Select tenorTypeSelect = new Select(tenorType);
			tenorTypeSelect.selectByVisibleText(tenorDesc);
			String negodetails = Defngodetails.getAttribute("value");
			Assert.assertEquals(negodetails, tenorDay + " " + tenorDesc, "Negotiation value does not match expected value");
		}
		
		else if(aval.equals("By Def Payment")) 
		{
			Select avalSelect = new Select(avalBy);
			avalSelect.selectByVisibleText(aval);
			tenorDays.clear();
			tenorDays.sendKeys(tenorDay);
			Select tenorTypeSelect = new Select(tenorType);
			tenorTypeSelect.selectByVisibleText(tenorDesc);
			String defdetails = Defngodetails.getAttribute("value");
			Assert.assertEquals(defdetails, tenorDay + " " + tenorDesc, "Deferred value does not match expected value");
		}
		
		else if(aval.equals("BY MIXED PYMT")) 
		{ 
			registerLC.switchToWorkFrame();
			Select avalSelect = new Select(avalBy);
			avalSelect.selectByVisibleText(aval);
			// Wait for the element to be visible before clicking
			//registerLC.switchToDefaultContent();
			//registerLC.switchToWorkFrame();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    wait.until(ExpectedConditions.visibilityOf(clickPartiesTab2));
			mixedpaymenttab.click();
			addPaymentTerms.click();
			Select mixedPaymentSelect = new Select(mixedpaymentdropdown);
			mixedPaymentSelect.selectByVisibleText(Mixeddetails);
			if (Mixeddetails.equals("Sight"))
			{	
				mixedpercentage.clear();
				mixedpercentage.sendKeys(percval);
				mixedpercentage.sendKeys("\t"); // Simulate tab key to trigger any onChange events
				mixpaysavebutton.click();
				//--------------add balance % value----------------
				addPaymentTerms.click();
				mixedPaymentSelect.selectByVisibleText("Deferred"); //add Sight Deferred or Acceptance
				mixedtenordays.sendKeys("160");
				Select mixedtenortypeSelect = new Select(mixedtenortype);
				mixedtenortypeSelect.selectByVisibleText(mixedtentyp);
				mixpaysavebutton.click();
				
			}
			else if (Mixeddetails.equals("Deferred")|| Mixeddetails.equals("Acceptance"))
			{
				mixedpercentage.clear();
				mixedpercentage.sendKeys(percval);
				mixedtenordays.clear();
				mixedtenordays.sendKeys(mixedtendays);
				Select mixedtenortypeSelect = new Select(mixedtenortype);
				mixedtenortypeSelect.selectByVisibleText(mixedtentyp);
				mixpaysavebutton.click();
				//--------------add balance % value----------------
				addPaymentTerms.click();
				mixedPaymentSelect.selectByVisibleText("Acceptance"); //add Sight Deferred or Acceptance
				
				if(mixedPaymentSelect.getFirstSelectedOption().getText().equals("Sight"))
					{
					
					
						mixpaysavebutton.click();
					}
					else 
					{
						mixedtenordays.sendKeys("160");
						mixedtenortypeSelect.selectByVisibleText(mixedtentyp);
						mixpaysavebutton.click();
					}
				
			}
			
			else 
			{
				System.out.println("Invalid Mixed Payment Details: " + Mixeddetails);
				Assert.fail("Invalid Mixed Payment Details: " + Mixeddetails);
			}
			
		}
		else 
		{
			System.out.println("Invalid Aval By option: " + aval);
			Assert.fail("Invalid Aval By option: " + aval);
		}
	}
	
	//-------Charges Tab Section-------
	
	public void issuechargestab() 
	{
		registerLC.switchToDefaultContent();
		registerLC.switchToWorkFrame();	
		// Wait for the element to be visible before clicking
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    wait.until(ExpectedConditions.visibilityOf(clickPartiesTab2));
	    Chargestab.click();
	}
	
	public void issuechargesdetails(String paidat, String chargesaccno) throws InterruptedException
	{
		charges_tab chargesobj=new charges_tab(driver);
		//chargesobj.clickchargestab();
		chargesobj.PaidatFieldVisible(paidat,chargesaccno);
	}
}
