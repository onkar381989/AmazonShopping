package extentreport;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReportListener implements ITestListener {
	protected static ExtentTest logger;
	public static String className = null;
	public static String methodName = null;
	private static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
	protected ExtentReports extentReports = new ExtentManager()
			.createInstance(System.getProperty("user.dir") + "/test-output/TestReport.html");

	public static synchronized ExtentTest getTest() {
		return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}

	public synchronized void onTestStart(ITestResult result) {
		ITestNGMethod testNGMethod = result.getMethod();
		Method method = testNGMethod.getConstructorOrMethod().getMethod();
		Test testAnnotation = method.getAnnotation(Test.class);
		className = result.getInstanceName();
		if (testAnnotation != null) {
			methodName = testAnnotation.testName();
			if (methodName.trim().equalsIgnoreCase("")) {
				methodName = result.getMethod().getMethodName();
			}
		} else {
			methodName = result.getMethod().getMethodName();
		}
			if (null != logger) {
				if (!logger.getTest().getName().equalsIgnoreCase(methodName)) {
					logger = extentReports.startTest(methodName);
				}
			} else {
				extentTestMap.put((int) (long) (Thread.currentThread().getId()), new ExtentTest(methodName, ""));
				logger = extentReports.startTest(methodName);
			}
	}

	public synchronized void onTestSuccess(ITestResult result) {
		logInExtentReport(LogStatus.INFO, result.getMethod().getMethodName() + " test is passed");
	}

	public synchronized void onTestFailure(ITestResult result) {
		logInExtentReport(LogStatus.FAIL, result.getMethod().getMethodName() + " test is failed",
				result.getThrowable());
	}

	public synchronized void onTestSkipped(ITestResult result) {
		logInExtentReport(LogStatus.SKIP, "Retried " + result.getMethod().getMethodName()
				+ " test marking status SKIPPED for the " + result.getAttribute("RetryAttempt") + " time(s)");
	}

	public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		logInExtentReport(LogStatus.FATAL, result.getMethod().getMethodName() + " test is skipped");
	}

	public synchronized void onStart(ITestContext context) {
		// reports = new ExtentReports(reportPath,true);
		// reports.loadConfig(new File(GlobalVariables.resourcesFolderPath+
		// "config/extent-config.xml"));
	}

	public synchronized void onFinish(ITestContext context) {
		try {
			extentReports.endTest(extentTestMap.get((int) (long) (Thread.currentThread().getId())));
			extentReports.flush();
		} catch (Exception e) {
		}
	}

	public static synchronized void endTest() {
		// extentReports.endTest(extentTestMap.get((int) (long)
		// (Thread.currentThread().getId())));
		System.out.println("endTest");
	}

	public synchronized ExtentTest getLogger() {
		return logger;
	}

	public synchronized void logInExtentReport(LogStatus logStatus, String message, Throwable... throwables) {
		if (throwables.length > 1)
			logger.log(logStatus, message, throwables[0]);
		else
			logger.log(logStatus, message);
	}
}
