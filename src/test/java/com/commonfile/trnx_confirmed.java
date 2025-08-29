package com.commonfile;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class trnx_confirmed {
    private String selectname;
    private String frame;
    private String text;
    private WebDriver driver;

    public trnx_confirmed(WebDriver driver) {
        this.driver = driver;
    }

    public void confirm() throws InterruptedException {
        System.out.println("confirm");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        // Handle first possible alert
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            System.out.println("Alert text: " + alert.getText());
            alert.accept();
            System.out.println("Alert accepted.");
        } catch (TimeoutException e) {
            System.out.println("Alert did not appear within timeout.");
        }

        driver.switchTo().defaultContent();
        driver.switchTo().frame("eeToolbar");

        if (frame == null && selectname == null) {
            System.out.println(" confirm and cancel start");

            // Click confirm
            WebElement conid = driver.findElement(By.id("_confirm"));
            conid.click();
            System.out.println("transaction confirm");
            Thread.sleep(1000);

            // Handle confirm alert
            try {
                Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                System.out.println("Alert text: " + alert.getText());
                alert.accept();
                System.out.println("Alert accepted.");
            } catch (TimeoutException e) {
                System.out.println("No alert appeared after confirm.");
            }

            // Click cancel
            Thread.sleep(1000);
            WebElement canid = driver.findElement(By.id("_cancel"));
            canid.click();
            System.out.println("transaction cancel");

        } else if ("referenceNumber".equals(selectname)) {
            Thread.sleep(1000);
            System.out.println("pathInfo");
            driver.switchTo().frame("pathInfo");
            JavascriptExecutor js1 = (JavascriptExecutor) driver;
            this.text = (String) js1.executeScript(
                    "return document.getElementById('referenceNumber').textContent");
            System.out.println("Reference Number: " + this.text);
        }
    }
}
