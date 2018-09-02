package com.roche.dependency;

import com.relevantcodes.extentreports.ExtentReports;

public class Extent {
	public static ExtentReports getInstance(){
		ExtentReports extent;
		String path = System.getProperty("user.dir") + "\\target\\Extent_Report.html";
		extent = new ExtentReports(path, true);//, DisplayOrder.NEWEST_FIRST);
		extent.addSystemInfo("Browser", "Chrome");
		extent.addSystemInfo("Platform", "Windows");
		return extent;
	}

}
