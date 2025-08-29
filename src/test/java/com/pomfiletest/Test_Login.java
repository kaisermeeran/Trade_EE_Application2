package com.pomfiletest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.commonfile.Catalog;
import com.commonfile.charges_tab;
import com.commonfile.parties_tab;
import com.commonfile.trnx_confirmed;
import com.pomtestcases.Beneficiary_Response;
import com.pomtestcases.EE_Login;
import com.pomtestcases.IPLC_Homepage;
import com.pomtestcases.IPLC_RegisterAmendmentLC;
import com.pomtestcases.IssueLetterofCredit;
import com.pomtestcases.IssueamendmentLC;
import com.pomtestcases.RegisterDocument;
import com.pomtestcases.RegisterLC;
import com.utilitypackage.*;
public class Test_Login {
	
	WebDriver driver;
	EE_Login login;
	IPLC_Homepage homepage;
	String refnum ="IP005247BEDV";
	String appCustId;
	String benCustId;
	String advBankId;
	String chargesTab;
	String chargesaccno;
	String detrimental;
	
	
	
	@BeforeClass
	void setup() throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		prefs.put("safebrowsing.enabled", false);
		options.setExperimentalOption("prefs", prefs);
		options.addArguments("--incognito");
		options.addArguments("--disable-popup-blocking");
		options.addArguments("--disable-notifications");

		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		driver.get("http://106.51.3.36:9080/EximBillWeb/SYS_index.htm");
		login = new EE_Login(driver);
		homepage = new IPLC_Homepage(driver);
	}
	
	@AfterClass
	public void teardown() {
		//if (driver != null) 
			//driver.quit();
	}
	
	@DataProvider(name = "loginData")
	public Object[][] loginData() throws Exception {
		String filepath = System.getProperty("user.dir") + "\\Data\\Testdata.xlsx";
		int rows = excelutils.getRowCount(filepath, "Login");

		Object[][] data = new Object[rows][3];
		for (int i = 1; i <= rows; i++) {
			data[i - 1][0] = excelutils.getCellData(filepath, "Login", i, 0);
			data[i - 1][1] = excelutils.getCellData(filepath, "Login", i, 1);
			data[i - 1][2] = excelutils.getCellData(filepath, "Login", i, 2);
		}
		return data;
	}

	@Test(priority = 1, dataProvider = "loginData")
	void EE_login(String businessUnit, String userId, String password) throws Exception {
		login.clickProceedButton();
		Thread.sleep(2000);
		login.enterUsername(businessUnit);
		login.enteruserid(userId);
		login.enterPassword(password);
		Thread.sleep(2000);
		login.clickLoginButton();
		Thread.sleep(2000);
	}

	@Test(priority = 2, dependsOnMethods = "EE_login")
	void Homepage() throws InterruptedException {
		RegisterLC registerLC = new RegisterLC(driver);
		homepage.clickImportLC();
		homepage.clickIPLCIssuance();
		homepage.clickNewIPLC();
		//System.out.println("Homepage verification logic goes here.");
		
	}
	
	@Test(priority = 3, dependsOnMethods = "Homepage")
	void RegisterLC() throws Exception {
		String filepath = System.getProperty("user.dir") + "\\Data\\Testdata.xlsx";
		int rows = excelutils.getRowCount(filepath, "RegisterLC");

		for (int i = 1; i <= rows; i++) 
		{
			  int colIndex = excelutils.getColumnIndexByName(filepath, "RegisterLC","FORM_OF_LC"); 
			  String formOfLC = excelutils.getCellData(filepath,"RegisterLC", i, colIndex);
			  int colIndex1 = excelutils.getColumnIndexByName(filepath, "RegisterLC", "APLB_RULE"); 
			  String aplbRules = excelutils.getCellData(filepath, "RegisterLC", i, colIndex1);                 
			  int colIndex2 = excelutils.getColumnIndexByName(filepath, "RegisterLC","AVAL_BY"); 
			  String avalBy = excelutils.getCellData(filepath, "RegisterLC", i, colIndex2); 
			  int colIndex3 = excelutils.getColumnIndexByName(filepath, "RegisterLC", "LC_AMT"); 
			  String lcAmt = excelutils.getCellData(filepath,"RegisterLC", i, colIndex3); 
			  int colIndex4 = excelutils.getColumnIndexByName(filepath, "RegisterLC", "EXPIRY_DT"); 
			  String expiryDate = excelutils.getCellData(filepath, "RegisterLC", i, colIndex4);
			  int colIndex5 = excelutils.getColumnIndexByName(filepath, "RegisterLC","ADD_AMT_COVRD"); 
			  String addAmtCovered = excelutils.getCellData(filepath, "RegisterLC", i, colIndex5);
			  int colIndex6 = excelutils.getColumnIndexByName(filepath, "RegisterLC","APP_CUST_ID");
			  String appCustId = excelutils.getCellData(filepath, "RegisterLC", i, colIndex6);
			  int colIndex7 = excelutils.getColumnIndexByName(filepath, "RegisterLC","BEN_CUST_ID");
			  String benCustId = excelutils.getCellData(filepath, "RegisterLC", i, colIndex7);
			  int colIndex8 = excelutils.getColumnIndexByName(filepath, "RegisterLC","ACC_OFFICE_CODE");
			  String acccofficecode = excelutils.getCellData(filepath, "RegisterLC", i, colIndex8);
			  int colIndex9 = excelutils.getColumnIndexByName(filepath, "RegisterLC","SAME_AS_APP");
			  String sameasapp = excelutils.getCellData(filepath, "RegisterLC", i, colIndex9);
			  int colIndex10 = excelutils.getColumnIndexByName(filepath, "RegisterLC","ADV_BANK_ID");
			  String advBankId = excelutils.getCellData(filepath, "RegisterLC", i, colIndex10);
			  int colIndex11 = excelutils.getColumnIndexByName(filepath, "RegisterLC","CUST_ACC_NO");
			  String custAccNo = excelutils.getCellData(filepath, "RegisterLC", i, colIndex11);
			  int colIndex12 = excelutils.getColumnIndexByName(filepath, "RegisterLC","BANK_ACC_NO");
			  String bankAccNo = excelutils.getCellData(filepath, "RegisterLC", i, colIndex12);
			  
			  //Charges tab can be added here as needed
			  int colIndex13 = excelutils.getColumnIndexByName(filepath, "RegisterLC","PAID_AT");
			  String chargesTab = excelutils.getCellData(filepath, "RegisterLC", i, colIndex13);
			  int colIndex14 = excelutils.getColumnIndexByName(filepath, "RegisterLC","CHARGES_ACC");
			  String chargesaccno = excelutils.getCellData(filepath, "RegisterLC", i, colIndex14);
			  
			  int colIndex15 = excelutils.getColumnIndexByName(filepath, "RegisterLC","LC_CCY");
			  String lcCcy = excelutils.getCellData(filepath, "RegisterLC", i, colIndex15);
			  int colIndex16 = excelutils.getColumnIndexByName(filepath, "RegisterLC","LOCAL_CC_BALANCE");
			  String localCCBalance = excelutils.getCellData(filepath, "RegisterLC", i, colIndex16);
			  int colIndex17 = excelutils.getColumnIndexByName(filepath, "RegisterLC","CCY_BALANCE");
			  String ccyBalance = excelutils.getCellData(filepath, "RegisterLC", i, colIndex17);
			  int colIndex18 = excelutils.getColumnIndexByName(filepath, "RegisterLC","TOL_NOTEXCEEDING");
			  String tolNotExceeding = excelutils.getCellData(filepath, "RegisterLC", i, colIndex18);
			  int colIndex19 = excelutils.getColumnIndexByName(filepath, "RegisterLC","TOLERANCE");
			  String tolerance = excelutils.getCellData(filepath, "RegisterLC", i, colIndex19);
			  int colIndex20 = excelutils.getColumnIndexByName(filepath, "RegisterLC","SAME_AS_ID");
			  String sameAsId = excelutils.getCellData(filepath, "RegisterLC", i, colIndex20);
			  
			RegisterLC registerLC = new RegisterLC(driver);
			registerLC.enterFormOfLC(formOfLC);	 
			registerLC.enterAPLB_Rules(aplbRules); 
			registerLC.enterAvalBy(avalBy);
			registerLC.enterLC_Amt(lcAmt,lcCcy,localCCBalance,ccyBalance);
			registerLC.selectTolerance(tolNotExceeding,tolerance);
			registerLC.enterExpiryDate(expiryDate);
			registerLC.enterAddAmtCovered(addAmtCovered);
			
			
			parties_tab partiesObj = new parties_tab(driver);
			registerLC.partiestab();
			registerLC.clickApplicantIdButton(appCustId);
			//partiesObj.enterAppCustId(appCustId);
			
			registerLC.clickBeneficiaryIdButton(benCustId);
			//partiesObj.enterAppCustId(benCustId);
			
			registerLC.enterAccOfficeCode(acccofficecode);
			registerLC.enterSameAsApp(sameasapp,sameAsId);
			
			registerLC.clickadvBankIdButton(advBankId);
			//partiesObj.enterAppCustId(advBankId);
			
											
			
			charges_tab chargesobj=new charges_tab(driver);
			
			
			registerLC.clickRiskTab();
			registerLC.clickbankliabiiltyButton();
			//partiesObj.enterAppCustId(custAccNo);
			chargesobj.chargesaccno(custAccNo);
			
			registerLC.clickcustliabiiltyButton();
			chargesobj.chargesaccno(bankAccNo);
			
			
			//Charges tab can be added here as needed
			
			chargesobj.clickchargestab();
			chargesobj.PaidatFieldVisible(chargesTab,chargesaccno);
			refnum=registerLC.copyrefnum();
			
			//Confirm transaction method
			trnx_confirmed trnx = new trnx_confirmed(driver);
			trnx.confirm();
			
			//switchToDefaultContent();
			//switchToWorkFrame();
			Catalog catalogobj = new Catalog(driver);
			catalogobj.supervisorCatalog();
			catalogobj.super_release(refnum);
			
			
			
			System.out.println("Applicable Rules is."+expiryDate);
		}
		System.out.println("Register LC verification logic goes here.");
	}
	
	@DataProvider(name = "Issuance")
    public Object[][] Issuance() throws Exception {
		String filepath = System.getProperty("user.dir") + "\\Data\\Testdata.xlsx";
	    String sheetName = "IssueLC"; // <--- Make sure this is the correct sheet name

	    Map<String, String> data = excelutils.getLoginData(filepath, sheetName);

	    return new Object[][] { { data } };
    }
		
		
	
	@Test(priority = 4, dataProvider = "Issuance", dependsOnMethods = "RegisterLC")
	public void IssueLC(Map<String, String> data) throws Exception 
	{
		
			IssueLetterofCredit issueLCObj = new IssueLetterofCredit(driver);
			
			
			//Thread.sleep(2000);
			issueLCObj.clickIssueLetterofCredit();
			/*issueLCObj.clickIssueLetterofCredit();
			issueLCObj.clickIssueIPLC();*/
			
			Catalog catalogobj = new Catalog(driver);
			catalogobj.commoncatalog(refnum);
			 //String appCustId1="C006761"; // Example ID, replace with actual logic
			
			issueLCObj.doLogin(data);
			//issueLCObj.verifyFORM_OF_LC(data.get("REVOLVING_FLAG"));
			
			//issueLCObj.verify_Form_of_LC();
			RegisterLC registerLC1 = new RegisterLC(driver);
			issueLCObj.clickParties1();
			Thread.sleep(1000);
			registerLC1.clickApplicantIdButton(appCustId);
			registerLC1.clickBeneficiaryIdButton(benCustId);
			registerLC1.clickadvBankIdButton(advBankId);
			issueLCObj.parties1(data);
			issueLCObj.clickpartiestab2();
			issueLCObj.parties2(data);
			issueLCObj.tenortab();
			issueLCObj.tenortabdetails(data);
			
			//Charges tab can be added here as needed
			issueLCObj.issuechargestab();
			issueLCObj.Chargestabdetails(data);
			
			//confirm issueLC transaction method
			trnx_confirmed trnx = new trnx_confirmed(driver);
			trnx.confirm();
			
			//IssueLC supervisor catalog and release
			catalogobj.supervisorCatalog();
			catalogobj.super_release(refnum);
		//}
		System.out.println("Issue LC verification logic goes here.");
	}
	
	
	@DataProvider(name = "Amendment")
    public Object[][] Amendment() throws Exception {
		String filepath = System.getProperty("user.dir") + "\\Data\\Testdata.xlsx";
	    String sheetName = "RegAmend"; // <--- Make sure this is the correct sheet name

	    Map<String, String> data = excelutils.getLoginData(filepath, sheetName);

	    return new Object[][] { { data } };
    }
	
	
	
	@Test(priority = 5, dataProvider = "Amendment")
	public void Register_amendment(Map<String, String> data) throws InterruptedException
	{
		IPLC_RegisterAmendmentLC iplcregamend=new IPLC_RegisterAmendmentLC(driver);
		//iplcregamend.clickIPLC();              //Should comment when running the entire test suite
		iplcregamend.clickIPLCAmendmentLC();
		iplcregamend.clickRegisterAmendmentLC();
		Catalog catalogobj = new Catalog(driver);
		catalogobj.commoncatalog(refnum);
		
		// Amendment Information
		iplcregamend.Tolerance_value(data);
		iplcregamend.Amendment_information(data);
		// Tolerance Value
		
		trnx_confirmed trnx = new trnx_confirmed(driver);
		trnx.confirm();
		
		//IssueLC supervisor catalog and release
		catalogobj.supervisorCatalog();
		try {
			catalogobj.super_release(refnum);
		} catch (InterruptedException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test(priority = 6, dataProvider = "Amendment")
	public void IssueamendLC(Map<String, String> data) throws InterruptedException
	{
		IssueamendmentLC issueamendObj = new IssueamendmentLC(driver);
		issueamendObj.clickIPLCIssueAmendmentLC();
		
		Catalog catalogobj = new Catalog(driver);
		catalogobj.commoncatalog(refnum);
		detrimental=issueamendObj.DetrmtlFlagData(data);
		System.out.println("Detrimental Flag is: " + detrimental);
		//parties tab can be added here as needed
		issueamendObj.amendparties2value(data);
		
		trnx_confirmed trnx = new trnx_confirmed(driver);
		trnx.confirm();
		
		catalogobj.supervisorCatalog();
			try 
			{
				catalogobj.super_release(refnum);
			} 
			catch (InterruptedException | TimeoutException e) 
			{
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			if (detrimental.equalsIgnoreCase("Yes")) 
			{
				System.out.println("Detrimental flag is set to Yes, proceeding with further actions.");
				// Add any additional logic for when the detrimental flag is Yes
				Beneficiary_Response Beneresobj = new Beneficiary_Response(driver);
				Beneresobj.clickBeneficiaryResponse();
				catalogobj.commoncatalog(refnum);
				Beneresobj.selectBeneConsFlag(data);
				trnx.confirm();
				catalogobj.supervisorCatalog();
				try 
				{
					catalogobj.super_release(refnum);
				} 
				catch (InterruptedException | TimeoutException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} 
			else 
			{
				System.out.println("Detrimental flag is not set to Yes, no further actions required.");
			}
		
	}
	
	
	@DataProvider(name = "RegisterDoc")
    public Object[][] RegisterDoc() throws Exception {
		String filepath = System.getProperty("user.dir") + "\\Data\\Testdata.xlsx";
	    String sheetName = "RegDoc"; // <--- Make sure this is the correct sheet name

	    Map<String, String> data = excelutils.getLoginData(filepath, sheetName);

	    return new Object[][] { { data } };
    }
	
	
	@Test(priority = 7, dataProvider = "RegisterDoc")
	public void Register_document(Map<String, String> data) throws InterruptedException
	{
		System.out.println("Register Document verification logic goes here.");
		RegisterDocument registerDocuObj = new RegisterDocument(driver);
		registerDocuObj.ClickRegisterDocumentLC();
		Catalog catalogobj = new Catalog(driver);
		catalogobj.commoncatalog(refnum);
		registerDocuObj.presentation_info(data);
		registerDocuObj.presentation_info1(data);
		registerDocuObj.getDocPresentedBy(data);
	}

}








//registerLC.enterAppCustId(appCustId);
//registerLC.enterAppCustId(benCustId);

/*
 * String formOfLC = excelutils.getCellData(filepath, "RegisterLC", i, 0); 
 * String aplbRules = excelutils.getCellData(filepath, "RegisterLC", i, 1);
 * String avalBy = excelutils.getCellData(filepath, "RegisterLC", i, 2); String
 * lcAmt = excelutils.getCellData(filepath, "RegisterLC", i, 3); String
 * expiryDate = excelutils.getCellData(filepath, "RegisterLC", i, 4); String
 * addAmtCovered = excelutils.getCellData(filepath, "RegisterLC", i, 5);
 */

//registerLC.clickchargestab();
//registerLC.clickchargesAccbutton();

 		
