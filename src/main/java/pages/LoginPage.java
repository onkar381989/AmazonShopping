package pages;

import org.apache.log4j.Logger;

import appiumfunctions.LibraryMethods;

public class LoginPage extends LibraryMethods{
	private static Logger log = Logger.getLogger(LoginPage.class.getName());
	
	/*
	 * This method takes care of clicking on skipLogin button which pops up when app is launched
	 */
	public boolean clickOnSipLoginButton() {
		boolean isSignUpSkipped = false;
		isSignUpSkipped = clickOnButton("skipLogin");
		log.info("LoginPage :: skipLogin() is executed sucessfully");
		return isSignUpSkipped;
	}
}
