package pages;

import org.apache.log4j.Logger;

import appiumfunctions.LibraryMethods;

public class HomePage extends LibraryMethods{
	private static Logger log = Logger.getLogger(HomePage.class.getName());
	
	public boolean clickOnSearchButton(String searchText) {
		boolean isSearched = false;
		clickOnTextField("Search");
		isSearched = enterText(searchText, "Search");
		if(isSearched) {
			pressEnterKey();
		}
		log.info("HomePage :: clickOnSearchButton() is executed sucessfully");
		return isSearched;
	}
}
