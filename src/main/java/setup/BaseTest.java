package setup;

import java.util.Hashtable;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import utility.GlobalVariables;
import utility.ReadData;

public class BaseTest extends AppiumConfigurationSetUp {
	private static Logger log = Logger.getLogger(BaseTest.class.getName());
	protected static Hashtable<String, String> testInputData = null;

	@SuppressWarnings("unchecked")
	@BeforeSuite
	public void environmentPreSetUp() throws Exception {
		// Start Appium Server and Android Driver Session
		for (int i = 0; i < 5; i++) {
			// Check if driver is already initialized or not
			if (driver == null) {
				// Start Android Driver Session
				driver = (AppiumDriver<MobileElement>) androidDeviceConfiguration();
				log.info("BaseTest :: environmentPreSetUp() is executed sucessfully");
			} else {
				break;
			}
		}

		log.info("BaseTest :: environmentPreSetUp() loaded Test Data sucessfully");
		
		// Read Test Input Files Data - File like TextFile,App to be uploaded
		// testInputData= ReadData.getPropertiesFileData(FileConstants.TESTINPUTDATAFILEPATH + this.getClass().getSimpleName());

	}

	@AfterSuite
	public void stopAppiumServer() {
		service.stop();
		log.info("BaseTest :: stopAppiumServer() stopped Appium Server sucessfully");
	}
}
