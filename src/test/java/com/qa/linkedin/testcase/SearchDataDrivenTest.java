package com.qa.linkedin.testcase;

import org.testng.annotations.Test;

import com.qa.linkedin.base.TestBase;
import com.qa.linkedin.page.LinkedinHomePage;
import com.qa.linkedin.page.LinkedinLoggedinPage;
import com.qa.linkedin.page.SearchResultsPage;
import com.qa.linkedin.util.ExcelUtils;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.AfterClass;

public class SearchDataDrivenTest extends TestBase {
	private Logger log = Logger.getLogger(SearchDataDrivenTest.class);
	private String path = System.getProperty("user.dir")
			+ "\\src\\test\\java\\com\\qa\\linkedin\\data\\searchData.xlsx";
	LinkedinHomePage lHomePg;
	LinkedinLoggedinPage llPg;
	SearchResultsPage srPg;

	@BeforeClass
	public void beforeClass() {
		log.debug("assign the Object value for each page class");
		lHomePg = new LinkedinHomePage();
		llPg = new LinkedinLoggedinPage();
		srPg = new SearchResultsPage();
	}

	@Test(priority = 1)
	public void performLoginTest() throws InterruptedException, IOException {
		log.debug("Started executing the performLoginTest()");
		log.debug("Verify Linkedin Home page title");
		lHomePg.verifyLinkedinHomePageTitle();
		log.debug("verify the presence of Linkedin logo in home page");
		lHomePg.verifyLinkedinLogo();
		log.debug("click on sign in link in home page");
		lHomePg.clickSigninLink();
		log.debug("Verif ythe linkedin login page title");
		lHomePg.verifyLinkedinLoginPageTitle();
		log.info("Verify the linkedin login page sign in header text");
		lHomePg.verifySigninHeaderText();
		log.info("perform the login test");
		llPg = lHomePg.doLogin(readPropertyValue("username"), readPropertyValue("pwd"));
		llPg.verifyProfileRailCard();

	}

	@Test(dependsOnMethods = { "performLoginTest" }, dataProvider = "searchData")
	public void searchTest(String keyword) throws Exception {
		log.debug("performing the search for : " + keyword);
		srPg = llPg.doPeopleSearch(keyword);
		log.debug("click on see all peole results link");
		long count = srPg.getResultCount();
		log.debug("search results count for:" + keyword + " is: " + count);
		log.debug("click on Home tab");
		srPg.clickHomeTab();
		Thread.sleep(3000);
	}

	@DataProvider
	public Object[][] searchData() throws InvalidFormatException, IOException {
		log.debug("rading the excel sheet data to dataprovider annotation");
		Object[][] data = new ExcelUtils().getTestData(path, "Sheet1");
		return data;
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		log.debug("Perform the logout");
		llPg.doLogout();
	}

}
