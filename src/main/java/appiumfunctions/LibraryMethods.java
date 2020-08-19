package appiumfunctions;

import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import setup.AppiumConfigurationSetUp;
import utility.GlobalVariables;
import utility.ReadData;

/*
 * 	This class take care of Appium methods which is going to be used across all the Pages
 */
public class LibraryMethods {
	private static Logger log = Logger.getLogger(LibraryMethods.class.getName());
	private int HEIGHT, WIDTH;
	private int CENTER_X, CENTER_Y;
	Hashtable<String, Hashtable<String, String>> objectRepository = null;

	/*
	 *	This default constructor will make sure the objectRepository is loaded with the 
	 *	instance of the class who extends LibraryMethods
	 */
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

	/*
	 * This method takes care of entering the text based on  element tag provided 'xpathTag'
	 * inputText - text top enter
	 */
	public boolean enterText(String inputText, String xpathTag) {
		boolean isTextEntered = false;
		MobileElement mobileElement = null;
		try {
			for (int i = 0; i < 10; i++) {
				mobileElement  = (MobileElement) isElementLoaded(xpathTag);
				if (mobileElement!=null) {
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

	/*
	 * This method takes care of clicking on button based on tag 'xpathTag'
	 */
	public boolean clickOnButton(String xpathTag) {
		boolean isClicked = false;
		MobileElement mobileElement = null;
		try {
			mobileElement  = (MobileElement) isElementLoaded(xpathTag);
				if (mobileElement!=null) {
					mobileElement.click();
					isClicked = true;
				} else {
					log.fatal("LibraryMethods :: clickOnButton() -> Failed to load the element");
				}
			log.info("LibraryMethods :: clickOnButton() is executed sucessfully");
		} catch (Exception e) {
			e.printStackTrace();
			log.fatal("LibraryMethods clickOnButton() Error::"+e.getMessage());
		}
		return isClicked;
	}

	/*
	 * This method takes care of clicking on TextField based on tag 'xpathTag' 
	 */
	public boolean clickOnTextField(String xpathTag) {
		boolean isClicked = false;
		MobileElement mobileElement = null;
		try {
			mobileElement  = (MobileElement) isElementLoaded(xpathTag);
			if (mobileElement!=null) {
					mobileElement.click();
					isClicked = true;
				} else {
					log.fatal("LibraryMethods :: clickOnTextField() -> Failed to load the element");
				}
			log.info("LibraryMethods :: clickOnTextField() is executed sucessfully");
		} catch (Exception e) {
			e.printStackTrace();
			log.fatal("LibraryMethods clickOnTextField() Error::"+e.getMessage());
		}
		return isClicked;
	}

	/*
	 * This method takes care of PressKey Event i.e. enterKey 
	 */
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

	/*
	 * This method takes care of checking whether element is loaded or not based on 'mobileElement'
	 * this provides stability of automation test
	 */
	@SuppressWarnings("deprecation")
	public WebElement isElementLoaded(String xpathTag) {
		WebElement element = null;
		try {
			FluentWait<AppiumDriver<MobileElement>> wait = new FluentWait<AppiumDriver<MobileElement>>(AppiumConfigurationSetUp.driver)
			        .withTimeout(30, TimeUnit.SECONDS)
			        .pollingEvery(250, TimeUnit.MILLISECONDS)
			        .ignoring(NoSuchElementException.class)
			        .ignoring(TimeoutException.class);
			element = wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath(objectRepository.get(xpathTag).get("XPath1")))));
			log.info("LibraryMethods :: isElementLoaded() is executed sucessfully");
		} catch (Exception e) {
			e.printStackTrace();
			log.fatal("LibraryMethods isElementLoaded() Error::"+e.getMessage());
		}
		return element;
	}
}
