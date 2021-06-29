package com.qa.linkedin.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	public static WebDriver driver = null;
	public WebDriverWait wait = null;

	private Logger log = Logger.getLogger(TestBase.class);

	public String readPropertyValue(String key) throws IOException {
		log.debug("Create Object for the class -Properties");
		Properties prop = new Properties();
		log.debug("read the properties file..");
		try {
			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\java\\com\\qa\\linkedin\\config\\config.properties");
			log.debug("load the properties");
			prop.load(fis);
		} catch (FileNotFoundException e) {
			log.error("file is not found in the config package");
			e.printStackTrace();
		}

		return prop.getProperty(key);

	}

	@BeforeSuite
	public void setup() throws IOException {
		log.debug("fetch the browser value");
		String browserName = readPropertyValue("browser");
		if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			// interface refvar = new implementedclass();
			WebDriver driver = new FirefoxDriver();

		} else if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			// interface refvar = new implementedclass();
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			// launch the edge browser
			driver = new EdgeDriver();
		} else if (browserName.equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			// launch the IE browser
			driver = new InternetExplorerDriver();
		}

		log.debug("maximize the window");
		driver.manage().window().maximize();
		log.debug("add implicit wait");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		log.debug("create object for WebDriverWait");
		wait = new WebDriverWait(driver, 30);

		log.debug("launch the application url" + readPropertyValue("applicationUrl"));
		driver.get(readPropertyValue("applicationUrl"));

	}

	@AfterSuite
	public void tearDown() {
		log.debug("Close the browser");
		if (driver != null) {
			driver.quit();
		}
	}

}
