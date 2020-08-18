package setup;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.LocksDevice;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import utility.GlobalVariables;
import utility.JsonUtility;

public class AppiumConfigurationSetUp {
	private static Logger log = Logger.getLogger(AppiumConfigurationSetUp.class.getName());
	public static AppiumDriver<MobileElement> driver;
	public static AppiumDriverLocalService service;

	private void startAppiumServer() {
		AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
		service = AppiumDriverLocalService.buildService(serviceBuilder);
		service.start();
	}

	public WebDriver androidDeviceConfiguration() throws IOException {
		try {
			// Kill existing running node
			killProcess();
			// Start Appium Server
			startAppiumServer();
			// Read Device Config Details
			JSONObject deviceConfigDetails = JsonUtility.createJsonObject4mFile(GlobalVariables.DEVICECONFIGDETAILS);
			JSONArray jsonArray = deviceConfigDetails.getJSONArray("DeviceList");

			DesiredCapabilities capabilities = new DesiredCapabilities();

			capabilities.setCapability("VERSION", jsonArray.getJSONObject(0).get("DeviceVersion"));
			capabilities.setCapability("deviceName", jsonArray.getJSONObject(0).get("DeviceName"));
			capabilities.setCapability("platformName", jsonArray.getJSONObject(0).get("PlatformName"));
			capabilities.setCapability("appPackage", jsonArray.getJSONObject(0).get("AppPackage"));
			capabilities.setCapability("appActivity", jsonArray.getJSONObject(0).get("AppActivity"));

			AppiumServiceBuilder builder = new AppiumServiceBuilder().usingAnyFreePort();
			service = AppiumDriverLocalService.buildService(builder);
			driver = new AndroidDriver<MobileElement>(
					new URL("http://" + jsonArray.getJSONObject(0).getString("ServerURL") + ":"
							+ jsonArray.getJSONObject(0).getString("Port") + "/wd/hub"),
					capabilities);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			((LocksDevice) driver).unlockDevice();
		} catch (Exception e) {
			e.printStackTrace();
			log.fatal("AppiumConfigurationSetUp Error::androidDeviceConfiguration() " + e.getMessage());
		}
		return driver;
	}

	public static void killProcess() {
		Runtime runtime = Runtime.getRuntime();
		try {
			if (System.getProperty("os.name").startsWith("Windows"))
				runtime.exec("taskkill /F /IM node.exe");
			else
				runtime.exec("killall node");
		} catch (IOException e) {
			e.printStackTrace();
			log.fatal("AppiumConfigurationSetUp Error::killProcess() " + e.getMessage());
		}
	}
}
