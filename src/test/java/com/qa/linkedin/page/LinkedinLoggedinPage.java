package com.qa.linkedin.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class LinkedinLoggedinPage extends BasePageWeb{

private Logger log = Logger.getLogger(LinkedinLoggedinPage.class);

public LinkedinLoggedinPage(){
	PageFactory.initElements(driver, this);
}

@FindBy(xpath="//div[contains(@class,'profile-rail-card')]")
private WebElement profileRailCard;

@FindBy(xpath="//*[@class='global-nav__me-photo ember-view']")
private WebElement profileImageIcon;

@FindBy(xpath="//a[contains(.,'Sign Out')]")
private WebElement signOutLink;

@FindBy(xpath="//input[@placeholder='Search']")
private WebElement searchEditbox;

public void verifyProfileRailCard() {
	log.debug("Verifying the linkedin loggedin page profile raicard eleemnt");
	wait.until(ExpectedConditions.visibilityOf(profileRailCard));
	Assert.assertTrue(profileRailCard.isDisplayed(), "profileRailCard is not present");
}

public void doLogout() throws InterruptedException {
	log.debug("perform the logout operation");
	log.info("click on profile image icon");
	click(profileImageIcon);
	wait.until(ExpectedConditions.visibilityOf(signOutLink));
	click(signOutLink);
}
public SearchResultsPage doPeopleSearch(String keyword) throws InterruptedException {
	log.debug("Performing people search for:"+keyword);
	sendKey(searchEditbox,keyword);
	log.debug("Press ENTER key");
	searchEditbox.sendKeys(Keys.ENTER);
	Thread.sleep(2000);
	return new SearchResultsPage();
}



}
