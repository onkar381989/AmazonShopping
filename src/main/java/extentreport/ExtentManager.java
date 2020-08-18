package extentreport;

import org.apache.log4j.Logger;
import com.relevantcodes.extentreports.ExtentReports;
import utility.GlobalVariables;

public class ExtentManager {
	private static Logger log = Logger.getLogger(ExtentManager.class.getName());
	private static ExtentReports extentReports;

	public synchronized ExtentReports getInstance() {
		if (extentReports == null) {
			createInstance(System.getProperty("user.dir") + "/test-output/TestReport.html");
		}
		return extentReports;
	}

	public synchronized ExtentReports createInstance(String reportPath) {
		if(extentReports==null) {
			extentReports = new ExtentReports(reportPath, true);
			extentReports.loadConfig(new GlobalVariables().readJarResourceContents("/config/extent-config.xml",".xml"));
			log.info("ExtentManager class, createInstance() loaded extent-config.xml file successfully");	
		}
		return extentReports;
	}
}