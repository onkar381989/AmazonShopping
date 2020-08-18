package pages;

import org.apache.log4j.Logger;

import appiumfunctions.LibraryMethods;

public class LoginPage extends LibraryMethods{
	private static Logger log = Logger.getLogger(LoginPage.class.getName());
	
	public boolean skipLogin() {
		boolean isSignUpSkipped = false;
		isSignUpSkipped = clickOnButton("skipLogin");
		log.info("LoginPage :: skipLogin() is executed sucessfully");
		return isSignUpSkipped;
	}
}
