package com.qa.linkedin.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class SearchResultsPage extends BasePageWeb{

	private Logger log = Logger.getLogger(SearchResultsPage.class);
	
	//constructor
	public SearchResultsPage() {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@class='app-aware-link'][contains(.,'See all people results')]")
	private WebElement seeAllPeopleResultsLink;
	
	@FindBy(css="div[class='pb2 t-black--light t-14']")
	private WebElement searchResultText;
	
	@FindBy(xpath="//ul[contains(@class,'global-nav__primary-items')]/li[1]/a")
	private WebElement homeTab;
	
	String searchResultsPageTitle="Search | LinkedIn";
	
	public void verifySearchResultPageTitle() {
		log.debug("wait for the Search result page title");
		wait.until(ExpectedConditions.titleContains(searchResultsPageTitle));
		Assert.assertTrue(driver.getTitle().contains(searchResultsPageTitle));
	}
	
	public long getResultCount() throws Exception {
		verifySearchResultPageTitle();
		log.debug("wait for the see all people result link");
		if(isElementPresent(By.xpath("//a[@class='app-aware-link'][contains(.,'See all people results')]"))) {
		wait.until(ExpectedConditions.visibilityOf(seeAllPeopleResultsLink));
		log.debug("click on see all people result link");
		click(seeAllPeopleResultsLink);
		return getResult();
		
		}else {
			return getResult();
		}
	}
	
	private long getResult()
	{
		wait.until(ExpectedConditions.visibilityOf(searchResultText));
		log.debug("fetch the results text");
		String txt=searchResultText.getText();
		log.debug("search results text is:"+txt);
		//txt="About 6,400 results";
		String[] str=txt.split("\\s");
		//str[]=["About","6,400","results"]
		//         0        1        2
		log.debug("results count text in string format is:"+str[1]);
	//convert String object into long primitive format
		long count = Long.parseLong(str[1].replace(",", ""));
		return count;
	}
	
	public void clickHomeTab() throws Exception {
		log.debug("click on Hometab");
		clickUsingJsExecutor(homeTab);
		
	}
	
	
}