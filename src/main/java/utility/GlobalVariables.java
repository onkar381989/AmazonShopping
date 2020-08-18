package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class GlobalVariables {
	private static Logger log = Logger.getLogger(GlobalVariables.class.getName());
	public static final String BASEDIRPATH=System.getProperty("user.dir");
	// Set Test Data File Path
	public static final String TESTDATAFILEPATH=BASEDIRPATH + "/TestData/";
	// Set Test Input File Path
	public static final String TESTINPUTDATAFILEPATH= BASEDIRPATH + "/TestInputFiles/";
	// Set Element Repository Path
	public static final String ELEMENTREPOSITORY= BASEDIRPATH + "/ElementRepository/";
	// Set Device Configuration Path
	public static final String DEVICECONFIGDETAILS = BASEDIRPATH + "/DeviceConfigurationDetails.json";
	// Set Resource Folder Path
	public static final String RESOURCEFOLDERPATH = BASEDIRPATH + "/src/main/resources/";
	// Set test-ouput Folder Path
	public static final String TESTOUTPUTFOLDERPATH = BASEDIRPATH +"/test-output/";
	
	static {
		Properties props = new Properties();
		try {
			File log4JFile = new GlobalVariables().readJarResourceContents("/config/log4j.properties",".properties");

			if(log4JFile.exists()) {
				File logFile = new File(GlobalVariables.TESTOUTPUTFOLDERPATH + "Logfile.log");
				if(logFile.exists()) {
					FileDeleteStrategy.FORCE.delete(logFile);
					logFile = new File(GlobalVariables.TESTOUTPUTFOLDERPATH + "applog.html");
					FileDeleteStrategy.FORCE.delete(logFile);
				}
				props.load(new FileInputStream(log4JFile.getCanonicalPath()));
				props.setProperty("log4j.appender.logfile.File", GlobalVariables.TESTOUTPUTFOLDERPATH + "Logfile.log");
				props.setProperty("log4j.appender.FILE.File", GlobalVariables.TESTOUTPUTFOLDERPATH + "Logfile.log");
				props.setProperty("log4j.appender.rfile.File", GlobalVariables.TESTOUTPUTFOLDERPATH + "applog.html");
				PropertyConfigurator.configure(props);
				log.info("Log4j Configured Successfully");
			}
		} catch(Exception e) {
			log.fatal("GlobalVariables File initialization", new RuntimeException(e.getMessage()));
			e.printStackTrace();
		}
	}
	
	public File readJarResourceContents(String filePath,String extension) {
		File tempFile=null;
		InputStream is = getClass().getResourceAsStream(filePath);
		if (is != null) {
			try {
				tempFile = File.createTempFile(String.valueOf(is.hashCode()), extension);
		        tempFile.deleteOnExit();

		        try (FileOutputStream out = new FileOutputStream(tempFile)) {
		            //copy stream
		            byte[] buffer = new byte[1024];
		            int bytesRead;
		            while ((bytesRead = is.read(buffer)) != -1) {
		                out.write(buffer, 0, bytesRead);
		            }
		        }
			} catch (FileNotFoundException fnfExc) {
				log.fatal("File Not Found Exception in readJarResourceContents() method from GlobalVariables Class",new RuntimeException("FileNotFoundException: " + fnfExc.getMessage()));
				throw new RuntimeException("FileNotFoundException: " + fnfExc.getMessage());
			} catch (IOException ioExc) {
				log.fatal("File Not Found Exception in readJarResourceContents() method from GlobalVariables Class",new RuntimeException("IOException: " + ioExc.getMessage()));
				throw new RuntimeException("IOException: " + ioExc.getMessage());
			}
		}
		return tempFile;
	}
}
