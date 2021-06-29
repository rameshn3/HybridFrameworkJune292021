package com.qa.linkedin.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.log4testng.Logger;

import com.qa.linkedin.base.TestBase;

public class TestUtil extends TestBase{
	private static Logger log = Logger.getLogger(TestUtil.class);
	//capture screenshot
	public static String captureScreenshot(String methodName) throws IOException {
			
			String fileName=getScreenshotName(methodName);
		    String directory="target/surefire-reports/failedTestScreenshots/";
			//String directory=System.getProperty("user.dir")+"/target/surefire-reports/failedTestScreenshots/";
			log.debug("creates the specified folder structure by mkdirs() ");
		    new File(directory).mkdirs();
			String path=directory + fileName;
			try {
			log.debug("capturing the screenshot using takesScreenshot interface and getScreenshotAs()");
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			log.debug("copying the screeenshot file to specified location under my project");
			FileUtils.copyFile(scrFile,	new File(path));
			log.debug("********************************************************************************");
			log.debug("Screenshot stored at path: "+path);
			log.debug("********************************************************************************");
			}catch(Exception e) {
				e.printStackTrace();
			}
			return path;
		}

		public static String getScreenshotName(String methodName) {
			log.debug("create object for Date class");
			Date d = new Date();
			String fileName = methodName+"-"+ d.toString().replace(":", "_").replace(" ", "_") + ".png";
			return fileName;
		}

}
