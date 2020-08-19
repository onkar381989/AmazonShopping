package pages;

import org.apache.log4j.Logger;

import appiumfunctions.LibraryMethods;

public class HomePage extends LibraryMethods{
	private static Logger log = Logger.getLogger(HomePage.class.getName());
	
	/*
	 * This method takes care of clicking on search text and enter the text in the search textbox
	 */
	public boolean enterTextInSearchtextBox(String searchText) {
		boolean isSearched = false;
		clickOnTextField("Search");
		isSearched = enterText(searchText, "Search");
		if(isSearched) {
			pressEnterKey();
		}
		log.info("HomePage :: enterTextInSearchtextBox() is executed sucessfully");
		return isSearched;
	}
}
