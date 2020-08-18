package utility;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;
import extentreport.ExtentReportListener;

@Listeners(ExtentReportListener.class)
public class RetryAnalyzer implements IRetryAnalyzer {
	private int currentRetry = 0;

	public boolean retry(ITestResult iTestResult) {
		MaxRetryCount failureRetryCount = iTestResult.getMethod().getConstructorOrMethod().getMethod()
				.getAnnotation(MaxRetryCount.class);

		int maxRetryCount = (failureRetryCount == null) ? 1 : failureRetryCount.value();

		if (!iTestResult.isSuccess()) { // Check if test not succeed
			if (++currentRetry < maxRetryCount) { // Check if maxtry count is reached
				iTestResult.setAttribute("RetryAttempt", currentRetry);
				iTestResult.setStatus(ITestResult.SKIP); // Mark test as failed
				return true; // Tells TestNG to re-run the test
			} else {
				iTestResult.setStatus(ITestResult.FAILURE); // If maxCount reached,test marked as failed
			}
		} else {
			iTestResult.setStatus(ITestResult.SUCCESS); // If test passes, TestNG marks it as passed
		}
		return false;
	}
}
