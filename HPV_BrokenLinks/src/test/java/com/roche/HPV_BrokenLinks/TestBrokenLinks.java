package com.roche.HPV_BrokenLinks;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.codoid.products.exception.FilloException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.roche.dependency.ExcelUtility_Fillo;
import com.roche.dependency.Extent;
import com.roche.dependency.Verify_Link;

public class TestBrokenLinks {

	public WebDriver driver;
	// public static String URL = "https://lifescience.roche.com/en_in.html";
	public static String Driver_Path = "C:\\Selenium\\DRIVERS_PATH\\";
	public int null_link = 0;
	public Verify_Link verify_link = new Verify_Link();

	/*
	 * For Extent Report
	 */
	ExtentReports report;
	ExtentTest test;

	/*
	 * For Fillo Excel API
	 */

	ExcelUtility_Fillo fillo = new ExcelUtility_Fillo();
	Object[][] return_Data = null;

	@DataProvider(name = "TestURL")
	public Object[][] getTestURL() throws FilloException {
		return_Data = fillo.DataRead();
		return return_Data;

	}

	@Test(dataProvider = "TestURL")
	public void Test(String URL) {

		// Extent report
		test = report.startTest(URL);
		test.assignAuthor("Run by --> Debanjan Bhattacharya");
		test.assignCategory("Environment -->  PROD");

		driver.navigate().to(URL);

		List<WebElement> All_Links = driver.findElements(By.tagName("a"));
		test.log(LogStatus.INFO, "Total Links: " + All_Links.size());

		// Show links name
		for (int i = 0; i < All_Links.size() - 1; i++) {
			WebElement link = All_Links.get(i);
			String Link_URL = link.getAttribute("href");

			if (Link_URL == null) {
				System.out.println("URL is either not configured for anchor tag or it is empty");
				null_link++;
			} else {
				System.out.println(Link_URL + "  -  " + verify_link.VerifyLinkStatus(Link_URL));
				int res = verify_link.VerifyLinkStatus(Link_URL);
				if (res >= 400) {
					test.log(LogStatus.FAIL, Link_URL + " there is an error " + "HTTP status code is " + res);
				} else if(res>=300 && res <400)
				{
					test.log(LogStatus.INFO, Link_URL + " is Redirected HTTP status code is " + res);
				} else{
					test.log(LogStatus.PASS, Link_URL + " is OK");
				}
			}

		}
		test.log(LogStatus.INFO, "Total number of null Link : " + null_link);
	}

	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.chrome.driver", Driver_Path + "chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

	@BeforeClass
	public void beforeClass() {
		report = Extent.getInstance();
	}

	@AfterClass
	public void afterClass() {
		report.endTest(test);
		report.flush();
	}
}
