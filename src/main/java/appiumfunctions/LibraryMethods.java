package appiumfunctions;

import java.util.Hashtable;
import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.StaleElementReferenceException;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.MobileElement;
import setup.AppiumConfigurationSetUp;
import utility.GlobalVariables;
import utility.ReadData;

public class LibraryMethods {
	private static Logger log = Logger.getLogger(LibraryMethods.class.getName());
	private int HEIGHT, WIDTH;
	private int CENTER_X, CENTER_Y;
	Hashtable<String, Hashtable<String, String>> objectRepository = null;

	public LibraryMethods() {
		try {
			// Read Element Repository File
			objectRepository = ReadData.getElementParamsByTitle(GlobalVariables.ELEMENTREPOSITORY + this.getClass().getSimpleName() + ".xml");
			log.info("LibraryMethods constructor ::objectRepository is loaded sucessfully");
		} catch (Exception e) {
			e.printStackTrace();
			log.fatal("LibraryMethods Error::"+e.getMessage());
		}
	}

	public boolean enterText(String inputText, String xpathTag) {
		boolean isTextEntered = false;
		MobileElement mobileElement = null;
		try {
			mobileElement = AppiumConfigurationSetUp.driver.findElement(By.xpath(objectRepository.get(xpathTag).get("XPath1")));
			for (int i = 0; i < 10; i++) {
				if (isElementLoaded(mobileElement)) {
					mobileElement.sendKeys(inputText);
					isTextEntered = true;
					break;
				}
			}
			log.info("LibraryMethods :: enterText() is executed sucessfully");
		} catch (Exception e) {
			e.printStackTrace();
			log.fatal("LibraryMethods enterText() Error::"+e.getMessage());
		}
		return isTextEntered;
	}

	public boolean clickOnButton(String xpathTag) {
		boolean isClicked = false;
		MobileElement mobileElement = null;
		try {
			mobileElement = AppiumConfigurationSetUp.driver.findElement(By.xpath(objectRepository.get(xpathTag).get("XPath1")));
			for (int i = 0; i < 10; i++) {
				if (isElementLoaded(mobileElement)) {
					setScreenSize(); 
					mobileElement.click();
					isClicked = true;
					break;
				} else {
					Thread.sleep(1000);
				}
			}
			log.info("LibraryMethods :: clickOnButton() is executed sucessfully");
		} catch (Exception e) {
			e.printStackTrace();
			log.fatal("LibraryMethods clickOnButton() Error::"+e.getMessage());
		}
		return isClicked;
	}

	public boolean clickOnTextField(String xpathTag) {
		boolean isClicked = false;
		MobileElement mobileElement = null;
		try {
			mobileElement = AppiumConfigurationSetUp.driver.findElement(By.xpath(objectRepository.get(xpathTag).get("XPath1")));
			for (int i = 0; i < 10; i++) {
				if (isElementLoaded(mobileElement)) {
					setScreenSize(); 
					mobileElement.click();
					isClicked = true;
					break;
				} else {
					Thread.sleep(1000);
				}
			}
			log.info("LibraryMethods :: clickOnTextField() is executed sucessfully");
		} catch (Exception e) {
			e.printStackTrace();
			log.fatal("LibraryMethods clickOnTextField() Error::"+e.getMessage());
		}
		return isClicked;
	}

	public boolean pressEnterKey() {
		boolean isClicked = false;
		try {
			AppiumConfigurationSetUp.driver.executeScript("mobile:performEditorAction", ImmutableMap.of("action", "search"));
			isClicked = true;
			log.info("LibraryMethods :: pressEnterKey() is executed sucessfully");
		} catch (Exception e) {
			e.printStackTrace();
			log.fatal("LibraryMethods pressEnterKey() Error::"+e.getMessage());
		}
		return isClicked;
	}

	public static boolean isElementLoaded(MobileElement mobileElement) {
		boolean isElementDisplayed = false;
		try {
			for (int i = 0; i < 30; i++) {
				if (mobileElement.isDisplayed()) {
					isElementDisplayed = true;
					break;
				} else
					Thread.sleep(1000);
			}
			log.info("LibraryMethods :: isElementLoaded() is executed sucessfully");
		} catch (InterruptedException e) {
			e.printStackTrace();
			log.fatal("LibraryMethods isElementLoaded() Error::"+e.getMessage());
		}
		return isElementDisplayed;
	}

	public static boolean areElementsLoaded(List<MobileElement> mobileElement) {
		boolean isElementDisplayed = false;
		try {
			for (int i = 0; i < 30; i++) {
				if (!mobileElement.isEmpty()) {
					isElementDisplayed = true;
					break;
				} else
					Thread.sleep(1000);
			}
			log.info("LibraryMethods :: areElementsLoaded() is executed sucessfully");
		} catch (InterruptedException e) {
			e.printStackTrace();
			log.fatal("LibraryMethods areElementsLoaded() Error::"+e.getMessage());
		}
		return isElementDisplayed;
	}

	private void setScreenSize() {
		int count = 0;
		while (count < 5) {
			try {
				Dimension dm = AppiumConfigurationSetUp.driver.manage().window().getSize();
				HEIGHT = dm.getHeight();
				WIDTH = dm.getWidth();
				CENTER_X = WIDTH / 2;
				CENTER_Y = HEIGHT / 2;
				System.out.println("Device dimensions are : Height: " + HEIGHT + "\n Width: " + WIDTH);
				System.out.println("Center of device dimensions are :" + CENTER_X + "\n" + CENTER_Y);
				break;
			} catch (StaleElementReferenceException e) {
				count++;
				log.fatal("LibraryMethods setScreenSize() Error::"+e.getMessage());
			}
			log.info("LibraryMethods :: setScreenSize() is executed sucessfully");
		}
	}

}
