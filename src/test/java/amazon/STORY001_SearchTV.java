package amazon;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import setup.BaseTest;
import utility.MaxRetryCount;
import utility.TestData;

public class STORY001_SearchTV  extends BaseTest{
	LoginPage loginPage = null;
	HomePage homePage = null; 
	
	@BeforeClass
	public void initializePageObject() throws Exception {
		loginPage = new LoginPage();
		homePage = new HomePage();
	}
	
	@Test
	@MaxRetryCount(3)
	public void skipLogin() throws InterruptedException{
		Assert.assertTrue(loginPage.clickOnSkipLoginButton(), "Failed to click on Skip Login Button");
	}
	
	@MaxRetryCount(3)
	@Test(dependsOnMethods = "skipLogin", dataProviderClass = TestData.class,dataProvider = "testdata")
	public void searchTelevision(JSONObject testData) {
		Assert.assertTrue(homePage.enterTextInSearchtextBox(testData.getString("SearchText")), "Failed to enter text on  SearchTextBox");
	}
}
