package com.qa.linkedin.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class LinkedinHomePage extends BasePageWeb{

private Logger log = Logger.getLogger(LinkedinHomePage.class);

public LinkedinHomePage(){
	PageFactory.initElements(driver, this);
}

@FindBy(xpath="//a[@class='nav__logo-link']")
private WebElement linkedinLogo;

@FindBy(linkText="Sign in")
private WebElement signinLink;

@FindBy(css="h1.header__content__heading")
private WebElement signinHeaderText;

@FindBy(id = "username")
private WebElement emailEditbox;

@FindBy(name = "session_password")
private WebElement passwordEditbox;

@FindBy(css = "button[aria-label='Sign in']")
private WebElement signinBtn;

String linkedinLoginPageTitle="LinkedIn Login, Sign in | LinkedIn";
String linkedinHomePageTitle="LinkedIn: Log In or Sign Up";

public void verifyLinkedinHomePageTitle() {
	log.debug("waiting for the page title:"+linkedinHomePageTitle);
	wait.until(ExpectedConditions.titleContains(linkedinHomePageTitle));
	Assert.assertEquals(linkedinHomePageTitle, driver.getTitle());
}

public void verifyLinkedinLoginPageTitle() {
	log.debug("waiting for the page title:"+linkedinLoginPageTitle);
	wait.until(ExpectedConditions.titleContains(linkedinLoginPageTitle));
	Assert.assertEquals(linkedinLoginPageTitle, driver.getTitle());
}

public void verifyLinkedinLogo() {
	log.debug("Verifying the linkedin logo in home page");
	wait.until(ExpectedConditions.visibilityOf(linkedinLogo));
	Assert.assertTrue(linkedinLogo.isDisplayed(), "linkedinLogo is not present");
}

public void verifySigninHeaderText() {
	log.debug("Verifying the linkedin login singin header text");
	wait.until(ExpectedConditions.visibilityOf(signinHeaderText));
	Assert.assertTrue(signinHeaderText.isDisplayed(), "signinHeader text is not present");
}

public void clickSigninLink() throws InterruptedException {
	log.debug("click on sign in link in linkedin home page");
	click(signinLink);
}

public void clickSigninBtn() throws InterruptedException {
	log.debug("click on sign in Button in linkedin login page");
	click(signinBtn);
}

public LinkedinLoggedinPage doLogin(String uname,String pwd) throws InterruptedException {
	log.debug("performing the login operation on linkedin website");
	sendKey(emailEditbox,uname);
	sendKey(passwordEditbox,pwd);
	clickSigninBtn();
	return new LinkedinLoggedinPage();
}




}
