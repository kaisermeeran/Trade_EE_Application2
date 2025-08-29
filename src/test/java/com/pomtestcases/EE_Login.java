package com.pomtestcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EE_Login {
	
	WebDriver driver;
	public EE_Login(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(id = "proceed-button") WebElement proceedButton;
	@FindBy(id = "C_BUSINESS_UNIT")
	WebElement businessunit;
	@FindBy(id = "C_USER_ID")
	WebElement userid;
	@FindBy(id = "C_PASSWORD")
	WebElement password;
	@FindBy(xpath = "//*[@id=\"formLogin\"]/button")
	WebElement loginButton;
	
	// Action methods to interact with the page elements
	public void clickProceedButton() throws InterruptedException {
		Thread.sleep(2000); // Wait for the page to load
		proceedButton.click();
	}
	public void enterUsername(String bunit) 
	 {
		 businessunit.sendKeys(bunit); 
	 }
	
	 public void enteruserid(String uid) 
	 {
		 userid.sendKeys(uid); 
	 }
	 
	 public void enterPassword(String pwd) 
	 {
		 password.sendKeys(pwd); 
	 }
	  
	  public void clickLoginButton() 
	  { 
		  loginButton.click(); 
	  }

}
